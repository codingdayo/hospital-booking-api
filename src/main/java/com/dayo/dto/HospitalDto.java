package com.dayo.dto;

import com.dayo.entity.Doctor;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalDto {

    private Long id;
    private String hospitalName;
    private String address;
    private List<DoctorDto> doctors;
}
