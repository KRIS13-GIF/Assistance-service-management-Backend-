package com.example.sabanet.services;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Ordering;
import com.example.sabanet.entities.Personel;
import com.example.sabanet.entities.Product;
import com.example.sabanet.enumerations.PersonelType;
import com.example.sabanet.models.FileName;
import com.example.sabanet.models.OrderResponse;
import com.example.sabanet.repositories.CustomerRepository;
import com.example.sabanet.repositories.OrderRepository;
import com.example.sabanet.repositories.PersonelRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final ProductServices productServices;

    private final PersonelRepository personelRepository;

    private final CustomerServices customerServices;

    public OrderServices(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductServices productServices, PersonelRepository personelRepository, CustomerServices customerServices) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productServices = productServices;
        this.personelRepository = personelRepository;
        this.customerServices = customerServices;
    }


    public Ordering findOrder(String id){
        return orderRepository.findById(id).get();
    }

    public OrderResponse acceptProduct(String id, FileName fileName) throws Exception {
        Product product=productServices.findProduct(id);
        if (product.isAccept()){
            System.out.println("The product is already been accepted");
            throw new Exception("The product is accedpted !");
        }


        Customer customer=customerServices.findCustomer(product.getCustomer().getId());



        // Check if the intervention has been done

        if (customerRepository.existsByInterventIsFalseAndId(customer.getId())){

            throw new Exception("You can not make an order if you have not done previously the intervention");
            //System.out.println("You can not make an order if you have not done previously the intervention");
            //System.out.println(customer);

        }

        else
            customer.setNrFile(customer.getNrFile() + 1);
            customerRepository.save(customer);

            product.setAccept(true);
            product.setFileNum(customer.getNrFile());
            productRepository.save(product);


            String productInfo = "Product Information:\n" +
                    "Serial Number: " + product.getSerialNo() + "\n" +
                    "Brand: " + product.getBrand() + "\n" +
                    "Template: " + product.getTemplate() + "\n" +
                    "Description: " + product.getDescription() + "\n" +
                    "Date Purchase: " + product.getDatePurchase() + "\n" +
                    "Expiry Date: " + product.getExpiryDate() + "\n" +
                    "Notes: " + product.getNotes() + "\n" +
                    "Customer Name: " + product.getCustomerName() + "\n" +
                    "Full Address: " + product.getFullAddress() + "\n" +
                    "Telephone Number: " + product.getTelephoneNumber() + "\n" +
                    "Email: " + product.getEmail() + "\n" +
                    "Fiscal Code: " + product.getFiscalCode() + "\n" +
                    "VAT Number: " + product.getVatNumber() + "\n" +
                    "PEC: " + product.getPec() + "\n" +
                    "Accept: " + product.isAccept() + "\n" +
                    "File NO: " + product.getFileNum();

            try (FileWriter fileWriter = new FileWriter(fileName.getName())) {
                fileWriter.write(productInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Ordering order = new Ordering();
            order.setCustomer(product.getCustomer());
            order.setProduct(product);
            order.setFileNumber(customer.getNrFile());


            Ordering savedOrder = orderRepository.save(order);
            OrderResponse response = new OrderResponse(savedOrder.getId());
            return response;

    }

    public void addTechnicToOrder(String id1, String id2){
        Optional<Ordering> order=orderRepository.findById(id1);
        Ordering ordering=order.get();

        Optional<Personel> personel=personelRepository.findById(id2);
        Personel personel1=personel.get();

        System.out.println(personel1.getName());
        if (personel1.getType()== PersonelType.TECHNICIANS) {
            ordering.setPersonel(personel1);
            orderRepository.save(ordering);
            System.out.println("Technic added to order");
        }
        else {
            System.out.println("Nothing ");
        }

    }





}
