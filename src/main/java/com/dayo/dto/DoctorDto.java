package com.dayo.dto;

import com.dayo.entity.Booking;
import com.dayo.entity.Hospital;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DoctorDto {

    private Long id;
    private String name;
    private String worksAt;
    private String specialty;
    private BigDecimal bookingFee;
    private List<BookingDto> bookings;
    private HospitalDto hospital;
}
