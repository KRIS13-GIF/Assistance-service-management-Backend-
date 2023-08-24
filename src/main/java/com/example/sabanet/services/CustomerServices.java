package com.example.sabanet.services;


import com.example.sabanet.entities.Customer;
import com.example.sabanet.entities.Finish;
import com.example.sabanet.entities.Product;

import com.example.sabanet.models.ProductResponse;
import com.example.sabanet.models.UpdateRequestProduct;
import com.example.sabanet.repositories.CustomerRepository;
import com.example.sabanet.repositories.FinishRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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

// make the consult function from the beginning

    public ProductResponse consult(String id, UpdateRequestProduct updateRequestProduct) throws Exception {

        //Check both of them
        if (updateRequestProduct.getNr() != 0 && !Objects.equals(updateRequestProduct.getSerialNo(), "")) {
            // you need first check the nr files available for this customer
            List<Integer> fileNrList = productRepository.findFileNumByCustomerId(id);

            if (fileNrList.contains(updateRequestProduct.getNr())) {
                Product product = productRepository.findProductByFileNumAndCustomerId(updateRequestProduct.getNr(), id);


                if (!Objects.equals(product.getSerialNo(), updateRequestProduct.getSerialNo())) {
                    System.out.println("The current serial number for this is  " + product.getSerialNo());
                    throw new Exception("Please enter a valid serial number");
                }

                System.out.println(product.getSerialNo());

                if (!product.isAccept()) {
                    throw new Exception("The product is not yet accepted !");
                }
                if (product.isProcess()) {
                    throw new Exception("The product can not be updated because is in process phase");
                }

                if (updateRequestProduct.getBrand() != null) {
                    product.setBrand(updateRequestProduct.getBrand());
                }
                if (updateRequestProduct.getTemplate() != null) {
                    product.setTemplate(updateRequestProduct.getTemplate());
                }
                if (updateRequestProduct.getDescription() != null) {
                    product.setDescription(updateRequestProduct.getDescription());
                }
                if (updateRequestProduct.getNotes() != null) {
                    product.setNotes(updateRequestProduct.getNotes());
                }
                if (updateRequestProduct.getPassword() != null) {
                    product.setPassword(updateRequestProduct.getPassword());
                }
                if (updateRequestProduct.getFullAddress() != null) {
                    product.setFullAddress(updateRequestProduct.getFullAddress());
                }

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
                try (FileWriter fileWriter = new FileWriter(product.getFilename())) {
                    fileWriter.write(productInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Product savedProduct = productRepository.save(product);
                return new ProductResponse(savedProduct.getId());
            } else {
                throw new Exception("Invalid number inserted ! This customer does not have this number");
            }
        }
        else {
            throw new Exception("Please enter values for the number case and the serial number");
        }
        }




    public List<Integer> getFileNumByCustomerId(String customerId) {
        return productRepository.findFileNumByCustomerId(customerId);
    }






}
