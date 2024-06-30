package com.dayo.service.interfac;

import com.dayo.dto.LoginRequest;
import com.dayo.dto.Response;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.entity.User;

import java.math.BigDecimal;

public interface HospitalService {


    Response saveHospital(Hospital hospital);



    Response getAllHospitals();

    Response deleteHospital(Long hospitalId);

    Response getHospitalById(String userId);



    Response addNewDoctor(Long hospitalId, Doctor doctor);
}
