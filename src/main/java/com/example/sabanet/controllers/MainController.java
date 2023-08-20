package com.example.sabanet.controllers;

import com.example.sabanet.models.*;
import com.example.sabanet.services.CustomerServices;
import com.example.sabanet.services.OrderServices;
import com.example.sabanet.services.PersonelServices;
import com.example.sabanet.services.ProductServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sabanet")
public class MainController {

    private final CustomerServices customerServices;
    private final ProductServices productServices;

    private final OrderServices orderServices;
    private final PersonelServices personelServices;


    public MainController(CustomerServices customerServices, ProductServices productServices, OrderServices orderServices, PersonelServices personelServices) {
        this.customerServices = customerServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.personelServices = personelServices;
    }

    @PostMapping("/intervent/{id}")
    public void intervent(
            @PathVariable String id
    )throws Exception{
      customerServices.intervent(id);
    }

    @PostMapping("/createProduct/{id}")
    public ResponseEntity<ProductResponse>createProduct(
            @PathVariable String id,
            @RequestBody ProductRequest productRequest
            )throws Exception{
        ProductResponse response= productServices.createProduct(id, productRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/acceptProduct/{id1}/{id2}")

    public ResponseEntity<OrderResponse> acceptOrder(
            @PathVariable String id1, // for the personel to be acceptance
            @PathVariable String id2, // id of the product
            @RequestBody FileName fileName
    )throws Exception {

        if (personelServices.checkPersonelAcceptance(id1)) {
            OrderResponse orderResponse = orderServices.acceptProduct(id2, fileName);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        }
        return null;

    }

    @PutMapping("/addTechnic/{id1}/{id2}")
    public void addTechnic(
            @PathVariable String id1,
            @PathVariable String id2
    )throws Exception{
        orderServices.addTechnicToOrder(id1,id2);
    }

    @PutMapping("/repairOrder/{id}")
    public void repairOrder(
            @PathVariable String id
    )throws Exception{
        orderServices.repairOrder(id);
    }

    @PostMapping("/putToFinish/{id}")
    public ResponseEntity<FinishResponse>putToFinish(
            @PathVariable String id,
            @RequestBody FinishRequest finishRequest
    )throws Exception{
        FinishResponse finishResponse=orderServices.putToFinish(id, finishRequest);
        return  new ResponseEntity<>(finishResponse, HttpStatus.OK);
    }

    @PostMapping("/inform/{id1}/{id2}")
    public String informCustomer(
            @PathVariable String id1,
            @PathVariable String id2
    )throws Exception{

        return  personelServices.informCustomer(id1,id2);

    }

    @PutMapping("/collect/{id1}/{id2}")
    public void collectAndPay(
            @PathVariable String id1,
            @PathVariable String id2
    )throws Exception{
        customerServices.collectAndPay(id1, id2);
    }


    }

