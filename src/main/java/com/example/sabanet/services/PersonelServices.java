package com.example.sabanet.services;

import com.example.sabanet.entities.Finish;
import com.example.sabanet.entities.Personel;

import com.example.sabanet.enumerations.PersonelType;
import com.example.sabanet.repositories.FinishRepository;
import com.example.sabanet.repositories.PersonelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonelServices {

    private final PersonelRepository personelRepository;
    private final OrderServices orderServices;

    private final FinishRepository finishRepository;

    public PersonelServices(PersonelRepository personelRepository, OrderServices orderServices, FinishRepository finishRepository) {
        this.personelRepository = personelRepository;

        this.orderServices = orderServices;
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
            throw new Exception("The personel who informs is an ACCEPTANCE PERSONEL ");
        }


    }


}




