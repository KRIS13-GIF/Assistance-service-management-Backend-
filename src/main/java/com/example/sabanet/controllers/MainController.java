package com.example.sabanet.controllers;

import com.example.sabanet.entities.Ordering;
import com.example.sabanet.models.*;
import com.example.sabanet.services.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/sabanet")
public class MainController {

    private final CustomerServices customerServices;
    private final ProductServices productServices;

    private final OrderServices orderServices;
    private final PersonelServices personelServices;

    private final ExtractData extractData;


    public MainController(CustomerServices customerServices, ProductServices productServices, OrderServices orderServices, PersonelServices personelServices, ExtractData extractData) {
        this.customerServices = customerServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.personelServices = personelServices;
        this.extractData = extractData;
    }

    @PostMapping("/intervent/{id}")
    public void intervent(
            @PathVariable String id
    ) throws Exception {
        customerServices.intervent(id);
    }



    @PostMapping("/createProduct/{id}")
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable String id,
            @RequestBody ProductRequest productRequest
    ) throws Exception {
        ProductResponse response = productServices.createProduct(id, productRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/acceptProduct/{id1}/{id2}")

    public void acceptOrder(
            @PathVariable String id1, // for the personel to be acceptance
            @PathVariable String id2, // id of the product
            @RequestBody FileName fileName
    ) throws Exception {

        if (personelServices.checkPersonelAcceptance(id1)) {
         orderServices.acceptProduct(id2, fileName);
        }


    }



    @PutMapping("/repairOrder/{id}")
    public void repairOrder(
            @PathVariable String id
    ) throws Exception {
        orderServices.repairOrder(id);
    }

    @PutMapping("/notRepairingOrder/{id}")
    public void notRepairing(
            @PathVariable String id
    ) throws Exception {
        orderServices.doNotRepair(id);
    }


    @PostMapping("/putToFinish/{id}")
    public ResponseEntity<FinishResponse> putToFinish(
            @PathVariable String id,
            @RequestBody FinishRequest finishRequest
    ) throws Exception {
        FinishResponse finishResponse = orderServices.putToFinish(id, finishRequest);
        return new ResponseEntity<>(finishResponse, HttpStatus.OK);
    }

    @PostMapping("/inform/{id1}/{id2}")
    public String informCustomer(
            @PathVariable String id1,
            @PathVariable String id2
    ) throws Exception {

        return personelServices.informCustomer(id1, id2);
    }

    @PostMapping("/putProductToOrder/{id1}/{id2}")
    public ResponseEntity<OrderResponse>putOrder(
            @PathVariable String id1,
            @PathVariable String id2
    )throws Exception{
        OrderResponse orderResponse=personelServices.putProductToOrder(id1, id2);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


    @PutMapping("/collect/{id1}/{id2}")
    public void collectAndPay(
            @PathVariable String id1,
            @PathVariable String id2
    ) throws Exception {
        customerServices.collectAndPay(id1, id2);
    }


    @PutMapping("/consult/{id}")
    public ResponseEntity<ProductResponse> modifyProduct(
            @PathVariable String id,
            @RequestBody UpdateRequestProduct updateRequestProduct
    ) throws Exception {
        ProductResponse productResponse = customerServices.consult(id, updateRequestProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping("/report/completedRepairs")
    public ResponseEntity<Integer> getTotalCompletedRepairs(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            int totalCompletedRepairs = extractData.getTotalCompletedRepairs(sqlStartDate, sqlEndDate);
            return ResponseEntity.ok(totalCompletedRepairs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @GetMapping("/report/notCompletedRepairs")
    public ResponseEntity<Integer> getTotalCompletedNotRepaired(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            int totalCompletedNotRepaired = extractData.getTotalCompletedNotRepairs(sqlStartDate, sqlEndDate);
            return ResponseEntity.ok(totalCompletedNotRepaired);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @GetMapping("/report/productionCost")
    public ResponseEntity<Double> getTotalProductionCost(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            Double totalProductionCost = extractData.getTotalProductionCost(sqlStartDate, sqlEndDate);
            return ResponseEntity.ok(totalProductionCost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(0.0);
        }
    }


    @GetMapping("/report/repairsByTechnician")
    public ResponseEntity<List<Object[]>> getRepairsByTechnician(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

        List<Object[]> repairsByTechnician = extractData.getRepairsByTechnician(sqlStartDate, sqlEndDate);
        return ResponseEntity.ok(repairsByTechnician);
    }

    @GetMapping("/getOrdersByCustomer/{id}")
    public List <Ordering>getOrderByCustomerId(
            @PathVariable String id
    )throws Exception{
        return orderServices.getAllOrdersByCustomer(id);
    }

    @GetMapping("/products/fileNumByCustomerId/{id}")
    public List<Integer>getFileNumByCustomerId(
            @PathVariable String id
    )throws Exception{
        return customerServices.getFileNumByCustomerId(id);
    }








}

