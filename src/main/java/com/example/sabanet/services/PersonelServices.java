package com.example.sabanet.services;

import com.example.sabanet.entities.Personel;

import com.example.sabanet.enumerations.PersonelType;
import com.example.sabanet.repositories.PersonelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonelServices {

    private final PersonelRepository personelRepository;
    private final OrderServices orderServices;

    public PersonelServices(PersonelRepository personelRepository, OrderServices orderServices) {
        this.personelRepository = personelRepository;

        this.orderServices = orderServices;
    }

    public boolean checkPersonelAcceptance(String id) {
        Optional<Personel> personel = personelRepository.findById(id);
        Personel personel1 = personel.get();
        if (personel1.getType() == PersonelType.ACCEPTANCE) {
            return true;
        }
        return false;
    }

}




