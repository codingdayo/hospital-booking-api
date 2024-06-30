package com.dayo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hospital Name is required")
    private String hospitalName;

    @NotBlank(message = "address is required")
    private String address;


    @OneToMany( mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Doctor> doctors = new ArrayList<>();




}
