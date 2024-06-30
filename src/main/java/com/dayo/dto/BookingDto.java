package com.dayo.dto;

import com.dayo.entity.Doctor;
import com.dayo.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BookingDto {

    private Long id;
    private LocalDate bookingDate;
    private String bookingConfirmationCode;
    private UserDto user;
    private DoctorDto doctor;
}
