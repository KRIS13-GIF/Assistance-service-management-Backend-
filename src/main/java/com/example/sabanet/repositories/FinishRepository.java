package com.example.sabanet.repositories;

import com.example.sabanet.entities.Finish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface FinishRepository extends JpaRepository<Finish, String> {

    @Query(value = "SELECT COUNT(*) FROM Finish WHERE repaired = true AND finish_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    int getTotalCompletedRepairs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT COUNT(*) FROM Finish WHERE repaired = false AND finish_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    int getTotalNotCompletedRepairs(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT SUM(money) FROM Finish WHERE finish_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Double getTotalProductionCost(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT personel_id, COUNT(*) FROM Finish WHERE finish_date BETWEEN :startDate AND :endDate GROUP BY personel_id", nativeQuery = true)
    List<Object[]> getRepairsByTechnician(@Param("startDate") Date startDate, @Param("endDate") Date endDate);






}
