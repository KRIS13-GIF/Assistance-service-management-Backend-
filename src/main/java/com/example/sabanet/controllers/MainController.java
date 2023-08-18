package com.example.sabanet.controllers;

import com.example.sabanet.models.FileName;
import com.example.sabanet.models.ProductRequest;
import com.example.sabanet.models.ProductResponse;
import com.example.sabanet.services.CustomerServices;
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

    private final PersonelServices personelServices;


    public MainController(CustomerServices customerServices, ProductServices productServices, PersonelServices personelServices) {
        this.customerServices = customerServices;
        this.productServices = productServices;
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

    @PostMapping("/acceptProduct/{id}")

    public void acceptProduct(
            @PathVariable String id,
            @RequestBody FileName fileName
    )throws Exception{

        personelServices.acceptProduct(id, fileName);
    }





}
