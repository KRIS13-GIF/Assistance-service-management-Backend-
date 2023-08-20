package com.example.sabanet.services;


import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Finish;
import com.example.sabanet.entities.Product;
import com.example.sabanet.models.OrderResponse;
import com.example.sabanet.models.ProductResponse;
import com.example.sabanet.models.UpdateRequestProduct;
import com.example.sabanet.repositories.CustomerRepository;
import com.example.sabanet.repositories.FinishRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {

    private final CustomerRepository customerRepository;
    private final FinishRepository finishRepository;
    private final ProductRepository productRepository;






    public CustomerServices(CustomerRepository customerRepository, FinishRepository finishRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.finishRepository = finishRepository;

        this.productRepository = productRepository;
    }

    public void intervent(String id) throws Exception {
        Optional<Customer> customers=customerRepository.findById(id);
        if (customers.isEmpty()){
            throw new Exception("This user does not exist");
        }
        Customer customer=customers.get();
        customer.setIntervent(true);
        customerRepository.save(customer);
        System.out.println("Done");

    }

    public Customer findCustomer(String id){
        return customerRepository.findById(id).get();
    }

    public void collectAndPay(String idCustomer, String idFinish) throws Exception {
        Optional<Customer> customer=customerRepository.findById(idCustomer);
        if (customer.isEmpty()){
            throw new Exception("This is not a correct Id for the user");
        }
        Customer customer1=customer.get(); // retrieve the customer from the id
        Optional<Finish> finish=finishRepository.findById(idFinish);
        if (finish.isEmpty()){
            throw new Exception("This finish Id does not exist");
        }
        Finish finish1=finish.get();

        if (finish1.getCustomer().getId()==customer1.getId()){ // check if the Id of the customer matches with the customer id of the product
            if (finish1.isReady()) {
                finish1.setCollect(true);
                finishRepository.save(finish1);
            }
            else{
                throw new Exception("The product is not yet ready to be collected");
            }
        }
        else{
            throw new Exception("This finish does not belong to this customer. ");
        }

    }


    public ProductResponse consult(String id, UpdateRequestProduct updateRequestProduct) throws Exception {
        Optional<Customer> customer=customerRepository.findById(id);
        if (customer.isEmpty()){
            throw new Exception("This is not a correct Id for the user");
        }

        List<Product>productList=productRepository.findProductByCustomerId(id);
        Optional<Product> optionalProduct = productList.stream()
                .filter(p -> p.getFileNum() == updateRequestProduct.getNr())
                .findFirst();

        Product product = optionalProduct.get();

        if (product.isAccept()){
            throw new Exception("You can not modify this product , because it is accepted");
        }

        if (updateRequestProduct.getSerialNo()!=null){
            product.setSerialNo(updateRequestProduct.getSerialNo());
        }
        if (updateRequestProduct.getBrand()!=null){
            product.setBrand(updateRequestProduct.getBrand());
        }
        if (updateRequestProduct.getTemplate()!=null){
            product.setTemplate(updateRequestProduct.getTemplate());
        }
        if (updateRequestProduct.getDescription()!=null){
            product.setDescription(updateRequestProduct.getDescription());
        }
        if (updateRequestProduct.getNotes()!=null){
            product.setNotes(updateRequestProduct.getNotes());
        }
        if (updateRequestProduct.getPassword()!=null){
            product.setPassword(updateRequestProduct.getPassword());
        }
        if (updateRequestProduct.getFullAddress()!=null){
            product.setFullAddress(updateRequestProduct.getFullAddress());
        }

        Product savedProduct= productRepository.save(product);


        ProductResponse productResponse=new ProductResponse(savedProduct.getId());
        return productResponse;
    }






}
