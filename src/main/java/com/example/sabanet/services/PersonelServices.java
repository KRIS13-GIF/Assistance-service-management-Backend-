package com.example.sabanet.services;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Product;
import com.example.sabanet.models.FileName;
import com.example.sabanet.repositories.CustomerRepository;
import com.example.sabanet.repositories.PersonelRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class PersonelServices {

    private final PersonelRepository personelRepository;
    private final CustomerRepository customerRepository;
    private  final ProductRepository productRepository;
    private final ProductServices productServices;

    private final CustomerServices customerServices;

    public PersonelServices(PersonelRepository personelRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductServices productServices, CustomerServices customerServices) {
        this.personelRepository = personelRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productServices = productServices;
        this.customerServices = customerServices;
    }


    public void acceptProduct(String id, FileName fileName){
        Product product=productServices.findProduct(id);

        if (product.isAccept()){
            System.out.println("The product is already been accepted");
        }
        Customer customer=customerServices.findCustomer(product.getCustomer().getId());
        customer.setNrFile(customer.getNrFile()+1);
        customerRepository.save(customer);
        System.out.println("Done");

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


    }




}
