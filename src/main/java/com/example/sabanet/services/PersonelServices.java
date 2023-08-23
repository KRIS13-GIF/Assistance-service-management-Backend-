package com.example.sabanet.services;

import com.example.sabanet.entities.Finish;
import com.example.sabanet.entities.Ordering;
import com.example.sabanet.entities.Personel;

import com.example.sabanet.entities.Product;
import com.example.sabanet.enumerations.PersonelType;
import com.example.sabanet.models.OrderResponse;
import com.example.sabanet.repositories.FinishRepository;
import com.example.sabanet.repositories.OrderRepository;
import com.example.sabanet.repositories.PersonelRepository;
import com.example.sabanet.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonelServices {

    private final PersonelRepository personelRepository;
    private final OrderServices orderServices;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final FinishRepository finishRepository;

    public PersonelServices(PersonelRepository personelRepository, OrderServices orderServices, ProductRepository productRepository, OrderRepository orderRepository, FinishRepository finishRepository) {
        this.personelRepository = personelRepository;

        this.orderServices = orderServices;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.finishRepository = finishRepository;
    }


    public boolean checkPersonelAcceptance(String id) {
        Optional<Personel> personel = personelRepository.findById(id);
        Personel personel1 = personel.get();
        if (personel1.getType() == PersonelType.ACCEPTANCE) {
            return true;
        }
        return false;
    }

    public String informCustomer(String id, String id2) throws Exception {
        String message="The product is ready to be collected";
        Optional<Personel> personel = personelRepository.findById(id);
        Personel personel1 = personel.get();
        if (personel1.getType() == PersonelType.ACCEPTANCE) {
            Optional<Finish> finish=finishRepository.findById(id2);
            if (finish.isEmpty()){
                throw new Exception("Id is not correct for the Finish DB");
            }

            Finish finish1=finish.get();
            finish1.setReady(true);
            finishRepository.save(finish1);
            return message;
        }
        else {
            throw new Exception("The personnel who informs is an ACCEPTANCE PERSONEL ");
        }
    }

    public OrderResponse putProductToOrder(String id, String id2) throws Exception {
        Optional<Personel> personel=personelRepository.findById(id);
        if (personel.isEmpty()){
            throw new Exception("This id does not exist for personnel");
        }
        Personel personel1=personel.get();
        if (personel1.getType()==PersonelType.TECHNICIANS){
            Optional<Product> product=productRepository.findById(id2);
            if (product.isEmpty()){
                throw new Exception("Not a valid ID");
            }
            Product product1=product.get();
            if (!product1.isAccept()){
                throw new Exception("Can not put to order if a product is not accepted by acceptance phase");
            }
            product1.setProcess(true);
            productRepository.save(product1);

            Ordering order = new Ordering();
            order.setCustomer(product1.getCustomer());
            order.setProduct(product1);
            order.setFileNumber(product1.getFileNum());
            order.setPersonel(personel1);
            Ordering savedOrder = orderRepository.save(order);
            OrderResponse response = new OrderResponse(savedOrder.getId());
            return response;
        }
        else {
            throw new Exception("Not good personnel type");
        }
    }


}




