package com.dayo.repo;

import com.dayo.entity.Doctor;
import com.dayo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT DISTINCT d.specialty FROM Doctor d")
    List<String> findDistinctSpecialties();



    //for seeding out available doctors
    @Query("SELECT d FROM Doctor d WHERE d.id NOT IN (SELECT b.doctor.id FROM Booking b)")
    List<Doctor> getAllAvailableDoctors();
}
