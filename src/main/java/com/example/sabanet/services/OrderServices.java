package com.example.sabanet.services;

import com.example.sabanet.entities.*;
import com.example.sabanet.enumerations.PersonelType;
import com.example.sabanet.models.FileName;
import com.example.sabanet.models.FinishRequest;
import com.example.sabanet.models.FinishResponse;
import com.example.sabanet.models.OrderResponse;
import com.example.sabanet.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final ProductServices productServices;
    private final PersonelRepository personelRepository;
    private final FinishRepository finishRepository;

    private final CustomerServices customerServices;

    public OrderServices(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductServices productServices, PersonelRepository personelRepository, FinishRepository finishRepository, CustomerServices customerServices) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productServices = productServices;
        this.personelRepository = personelRepository;
        this.finishRepository = finishRepository;
        this.customerServices = customerServices;
    }


    public Ordering findOrder(String id){
        return orderRepository.findById(id).get();
    }

    public OrderResponse acceptProduct(String id, FileName fileName) throws Exception {
        Product product=productServices.findProduct(id);
        if (product.isAccept()){
            //System.out.println("The product is already been accepted");
            throw new Exception("The product is accepted !");
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

    public void addTechnicToOrder(String id1, String id2) throws Exception {
        Optional<Ordering> order=orderRepository.findById(id1);
        if (order.isEmpty()){
            throw  new Exception("Invalid Id for the order");
        }
        Ordering ordering=order.get();

        Optional<Personel> personel=personelRepository.findById(id2);
        if (personel.isEmpty()){
            throw new Exception("Invalid Id for the personel");
        }

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


    public void repairOrder(String idOrder) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(idOrder);
        if (ordering.isEmpty()){
            throw new Exception("This orderId does not exist");
        }
        Ordering ordering1=ordering.get();
        // check if the technician is attached to the order
        if (ordering1.getPersonel()!=null){

            LocalDate startLocalDate = (Date.valueOf(ordering1.getProduct().getDatePurchase().toLocalDate())).toLocalDate();
            Date startDate = Date.valueOf(startLocalDate);

            LocalDate endLocalDate= (Date.valueOf(ordering1.getProduct().getExpiryDate().toLocalDate()).toLocalDate());
            Date endDate = Date.valueOf(endLocalDate); // Convert Timestamp to Date


            LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
            DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date target= Date.valueOf(date.format(formatter1));

            if (target.compareTo(startDate)>=0 && target.compareTo(endDate)<=0){
                ordering1.setRepaired(true);
                ordering1.setRepNonRepDate(target);
                orderRepository.save(ordering1);
                System.out.println("Task Repaired");
            }
            else {
                throw new Exception("The Data of for this repairing is not between the desired values");
            }
        }
        else {
            throw new Exception("You need a technician to work on this task");
        }

    }



    public FinishResponse putToFinish(String id, FinishRequest finishRequest) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(id);
        if (ordering.isEmpty()){
            throw new Exception("The order id is not good");
        }
        Ordering ordering1=ordering.get();

        if (ordering1.isCompleted()){
            throw new Exception("You can not put to finish this order ! It is already been put");
        }
        Finish finish=new Finish();
        finish.setOrdering(ordering1);
        finish.setMoney(finishRequest.getMoney());
        finish.setDescription(finishRequest.getDescription());
        finish.setCollect(false); // you need to be an acceptance to inform for collection
        finish.setNrFile(ordering1.getFileNumber());
        finish.setRepaired(ordering1.isRepaired());
        finish.setCustomer(ordering1.getCustomer());

        LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
        DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date finishDate= Date.valueOf(date.format(formatter1));
        finish.setFinishDate(finishDate);

        ordering1.setCompleted(true);
        orderRepository.save(ordering1);

        FinishResponse finishResponse=new FinishResponse(finishRepository.save(finish).getId());
        return finishResponse;

    }


    public void doNotRepair(String id) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(id);
        if (ordering.isEmpty()){
            throw new Exception("The order id is not good");
        }
        Ordering ordering1=ordering.get();
        // check if the technician is attached to the order
        if (ordering1.getPersonel()!=null){

            LocalDate startLocalDate = (Date.valueOf(ordering1.getProduct().getDatePurchase().toLocalDate())).toLocalDate();
            Date startDate = Date.valueOf(startLocalDate);

            LocalDate endLocalDate= (Date.valueOf(ordering1.getProduct().getExpiryDate().toLocalDate()).toLocalDate());
            Date endDate = Date.valueOf(endLocalDate);


            LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
            DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date target= Date.valueOf(date.format(formatter1));

            if (target.compareTo(startDate)>=0 && target.compareTo(endDate)<=0){
                ordering1.setRepNonRepDate(target);
                orderRepository.save(ordering1);
            }
            else {
                throw new Exception("The Data of for this repairing is not between the desired values");
            }
        }
        else {
            throw new Exception("You need a technician to work on this task");
        }

    }







}
