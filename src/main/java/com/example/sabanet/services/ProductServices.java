package com.example.sabanet.services;

import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Product;
import com.example.sabanet.models.ProductRequest;
import com.example.sabanet.models.ProductResponse;
import com.example.sabanet.repositories.ProductRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
public class ProductServices {

    private final CustomerServices customerServices;
    private final ProductRepository productRepository;


    public ProductServices(CustomerServices customerServices, ProductRepository productRepository) {
        this.customerServices = customerServices;
        this.productRepository = productRepository;
    }


    public ProductResponse createProduct(String id, ProductRequest productRequest){
        Customer customer=customerServices.findCustomer(id);
        Product product=new Product();
        product.setSerialNo(productRequest.getSerialNo());
        product.setBrand(productRequest.getBrand());
        product.setTemplate(productRequest.getTemplate());
        product.setDescription(productRequest.getDescription());
        product.setDatePurchase(Date.valueOf(LocalDate.now()));
        product.setExpiryDate(Date.valueOf(LocalDate.now().plus(3, ChronoUnit.DAYS)));
        product.setNotes(productRequest.getNotes());
        product.setPassword(BCrypt.hashpw(productRequest.getPassword(), BCrypt.gensalt()));
        product.setCustomerName(customer.getName());
        product.setFullAddress(productRequest.getFullAddress());
        product.setTelephoneNumber(customer.getTelephone());
        product.setEmail(customer.getEmail());
        product.setFiscalCode(customer.getFiscalCode());
        product.setVatNumber(customer.getVatNumber());
        product.setPec(customer.getPec());
        product.setFileNum(customer.getNrFile()+1);
        product.setCustomer(customer);
        product.setAccept(false);
        return new ProductResponse(productRepository.save(product).getId());
    }

    public Product findProduct(String id){
        return productRepository.findById(id).get();
    }



}
