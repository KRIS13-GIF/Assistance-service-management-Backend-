package com.example.sabanet.services;
import com.example.sabanet.repositories.FinishRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;


@Service
public class ExtractData {

    private final FinishRepository finishRepository;

    public ExtractData(FinishRepository finishRepository) {
        this.finishRepository = finishRepository;
    }

    public int getTotalCompletedRepairs(Date startDate, Date endDate) {
        return finishRepository.getTotalCompletedRepairs(startDate, endDate);
    }
    public int getTotalCompletedNotRepairs(Date startDate, Date endDate) {
        return finishRepository.getTotalNotCompletedRepairs(startDate, endDate);
    }
    public Double getTotalProductionCost(Date startDate, Date endDate) {
        return finishRepository.getTotalProductionCost(startDate, endDate);
    }
    public List<Object[]> getRepairsByTechnician(Date startDate, Date endDate) {
        return finishRepository.getRepairsByTechnician(startDate, endDate);
    }

}
