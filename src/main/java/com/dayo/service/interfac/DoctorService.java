package com.dayo.service.interfac;

import com.dayo.dto.Response;
import com.dayo.entity.Doctor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DoctorService {



    Response getAllDoctors();

    Response deleteDoctor(Long doctorId);

    Response getDoctorById(Long doctorId);

    Response updateExistingDoctor(Long doctorId, Doctor doctorDetails);

    Response getAllAvailableDoctors();

}
