package com.dayo.service.impl;

import com.dayo.dto.DoctorDto;
import com.dayo.dto.HospitalDto;
import com.dayo.dto.Response;
import com.dayo.dto.UserDto;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.entity.User;
import com.dayo.exception.CustomException;
import com.dayo.repo.BookingRepository;
import com.dayo.repo.DoctorRepository;
import com.dayo.repo.HospitalRepository;
import com.dayo.repo.UserRepository;
import com.dayo.service.interfac.DoctorService;
import com.dayo.service.interfac.HospitalService;
import com.dayo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;



    @Override
    public Response saveHospital(Hospital hospital) {

        Response response = new Response();

        try {

            //if (hospitalRepository.existsById(hospital.getId())){
            //    throw new CustomException(hospital.getId() + "Already Exists");
            //}

            Hospital newHos = hospitalRepository.save(hospital);

            HospitalDto hospitalDto = Utils.mapHospitalEntityToHospitalDto(newHos);

            response.setStatusCode(200);
            response.setHospital(hospitalDto);
        } catch (CustomException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred while creating Hospital " + e.getMessage());
        }

        return response;
    }


    //public Response addNewDoctor(Long hospitalId, Doctor doctor) {
    //    return null;
    //}
    @Override
    public Response addNewDoctor(Long hospitalId, Doctor doctor) {

        Response response = new Response();

        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);

        try {
            if (hospital.isPresent()) {
            doctor.setHospital(hospital.get());
            Doctor savedDoctor = doctorRepository.save(doctor);

            DoctorDto doctorDto = Utils.mapDoctorEntityToDoctorDto(savedDoctor);

            response.setStatusCode(200);
            response.setDoctor(doctorDto);
        }
        } catch (CustomException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred while creating Hospital " + e.getMessage());
        }

        return response;

}

    @Override
    public Response getAllHospitals() {
        Response response = new Response();

        try {
            List<Hospital> hospital = hospitalRepository.findAll();
            //changed this to return roles and users
            List<HospitalDto> hospitalList = Utils.mapHospitalListEntityToHospitalListDTO(hospital);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setHospitalList(hospitalList);


        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all hospitals: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteHospital(Long hospitalId) {
        Response response = new Response();

        try {
            hospitalRepository.findById(hospitalId).orElseThrow(() -> new CustomException("Hospital Does not Exist"));
            hospitalRepository.deleteById(hospitalId);
            response.setStatusCode(200);
            response.setMessage("Successful");

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting an hospital: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getHospitalById(String userId) {
        Response response = new Response();
        try{
            Hospital hospital = hospitalRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomException("Hospital Not Found"));
            HospitalDto hospitalDto = Utils.mapHospitalEntityToHospitalDto(hospital);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setHospital(hospitalDto);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting the Hospital by Id " + e.getMessage());
        }
        return response;
    }


}
