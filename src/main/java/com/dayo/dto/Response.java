package com.dayo.dto;

import com.dayo.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {


    private int statusCode;
    private String message;
    private String token;
    private String role;
    //private String expirationTime;
    private String bookingConfirmationCode;

    private UserDto user;
    private DoctorDto doctor;
    private BookingDto booking;

    private HospitalDto hospital;

    private List<UserDto> userList;
    private List<DoctorDto> doctorList;
    private List<BookingDto> bookingList;
    private List<HospitalDto> hospitalList;

}
