package com.example.sabanet.services;
import com.example.sabanet.repositories.FinishRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;


@Service
public class ExtractData {

    private final FinishRepository finishRepository;

    public ExtractData(FinishRepository finishRepository) {
        this.finishRepository = finishRepository;
    }

    public int getTotalCompletedRepairs(Date startDate, Date endDate) {
        return finishRepository.getTotalCompletedRepairs(startDate, endDate);
    }
}
