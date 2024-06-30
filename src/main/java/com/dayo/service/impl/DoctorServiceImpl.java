package com.dayo.service.impl;

import com.dayo.dto.DoctorDto;
import com.dayo.dto.Response;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.exception.CustomException;
import com.dayo.repo.BookingRepository;
import com.dayo.repo.DoctorRepository;
import com.dayo.repo.HospitalRepository;
import com.dayo.service.interfac.DoctorService;
import com.dayo.service.interfac.HospitalService;
import com.dayo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public Response getAllDoctors() {
        Response response = new Response();

        try {
            List<Doctor> doctorList = doctorRepository
                    .findAll(Sort.by(Sort.Direction.DESC, "id"));

            List<DoctorDto> doctorDtoList = Utils
                    .mapDoctorListEntityToDoctorListDto(doctorList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setDoctorList(doctorDtoList);
        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all doctors " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteDoctor(Long doctorId) {
        Response response = new Response();

        try {
            doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new CustomException("Doctor Not Found"));
            doctorRepository.deleteById(doctorId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting the Doctor " + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getDoctorById(Long doctorId) {
        Response response = new Response();
        try{
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new CustomException("Doctor Not Found"));
            DoctorDto doctorDto = Utils.mapDoctorEntityToDoctorDtoPlusBookings(doctor);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setDoctor(doctorDto);
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting the Doctor by Id " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateExistingDoctor(Long doctorId, Doctor doctor) {
        Response response = new Response();

        Optional<Doctor> existingDoctorOptional = doctorRepository.findById(doctorId);

        try {
            if (existingDoctorOptional.isPresent()){
                Doctor existingDoctor = existingDoctorOptional.get();

                // Update the fields
                existingDoctor.setName(doctor.getName());
                existingDoctor.setWorksAt(doctor.getWorksAt());
                existingDoctor.setSpecialty(doctor.getSpecialty());
                existingDoctor.setBookingFee(doctor.getBookingFee());

                 Doctor updatedDoctor = doctorRepository.save(existingDoctor);
                 DoctorDto doctorDto = Utils.mapDoctorEntityToDoctorDto(updatedDoctor);

                    response.setStatusCode(200);
                    response.setMessage("doctor updated successfully");
                    response.setDoctor(doctorDto);
            }else {
                response.setStatusCode(404);
                response.setMessage("Doctor not found");
            }
        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating the Doctor " + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getAllAvailableDoctors() {
        Response response = new Response();

        try {
            List<Doctor> doctorList = doctorRepository.getAllAvailableDoctors();
            List<DoctorDto> doctorDtoList = Utils.mapDoctorListEntityToDoctorListDto(doctorList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setDoctorList(doctorDtoList);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all available doctors " + e.getMessage());
        }
        return response;
    }
}
