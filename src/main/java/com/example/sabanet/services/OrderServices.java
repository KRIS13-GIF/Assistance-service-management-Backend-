package com.example.sabanet.services;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Ordering;
import com.example.sabanet.entities.Product;
import com.example.sabanet.models.FileName;
import com.example.sabanet.models.OrderResponse;
import com.example.sabanet.repositories.CustomerRepository;
import com.example.sabanet.repositories.OrderRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final ProductServices productServices;

    private final CustomerServices customerServices;

    public OrderServices(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductServices productServices, CustomerServices customerServices) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productServices = productServices;
        this.customerServices = customerServices;
    }

    public OrderResponse acceptProduct(String id, FileName fileName){
        Product product=productServices.findProduct(id);
        if (product.isAccept()){
            System.out.println("The product is already been accepted");
        }
        Customer customer=customerServices.findCustomer(product.getCustomer().getId());
        customer.setNrFile(customer.getNrFile()+1);
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
                "Accept: " + product.isAccept() + "\n"+
                "File NO: "+ product.getFileNum();

        try (FileWriter fileWriter = new FileWriter(fileName.getName())) {
            fileWriter.write(productInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Ordering order=new Ordering();
        order.setCustomer(product.getCustomer());
        order.setProduct(product);
        order.setFileNumber(customer.getNrFile());


        Ordering savedOrder=orderRepository.save(order);
        OrderResponse response=new OrderResponse(savedOrder.getId());
        return response;

    }





}
