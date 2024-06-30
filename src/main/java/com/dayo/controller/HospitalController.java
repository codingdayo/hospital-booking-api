package com.dayo.controller;

import com.dayo.dto.Response;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.entity.User;
import com.dayo.service.interfac.HospitalService;
import com.dayo.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createHospital(@RequestBody Hospital hospital){
        Response response = hospitalService.saveHospital(hospital);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/save/{hospitalId}/doctor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createDoctor(@PathVariable Long hospitalId, @RequestBody Doctor doctor){
        Response response = hospitalService.addNewDoctor(hospitalId, doctor);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/all")
    public ResponseEntity<Response> getAllHospital(){
        Response response = hospitalService.getAllHospitals();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{hospitalId}")
    public ResponseEntity<Response> deleteUser(@PathVariable("hospitalId") String hospitalId){
        Response response = hospitalService.deleteHospital(Long.valueOf(hospitalId));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("hospitalId") String hospitalId){
        Response response = hospitalService.getHospitalById(hospitalId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
