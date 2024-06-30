package com.dayo.controller;

import com.dayo.dto.Response;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.service.interfac.DoctorService;
import com.dayo.service.interfac.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.math.BigDecimal;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HospitalService hospitalService;


    @GetMapping("/all")
    public ResponseEntity<Response> getAllDoctors(){
        Response response = doctorService.getAllDoctors();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<Response> deleteDoctor(@PathVariable("doctorId") String doctorId){
        Response response = doctorService.deleteDoctor(Long.valueOf(doctorId));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/{doctorId}")
    public ResponseEntity<Response> getDoctorById(@PathVariable("doctorId") String doctorId){
        Response response = doctorService.getDoctorById(Long.valueOf(doctorId));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{doctorId}")
    public ResponseEntity<Response> updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor doctor){
        Response response = doctorService.updateExistingDoctor(doctorId, doctor);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/availableDoctors")
    public ResponseEntity<Response> getAvailableDoctors() {
        Response response = doctorService.getAllAvailableDoctors();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
