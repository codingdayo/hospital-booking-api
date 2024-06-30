package com.dayo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "doctor")

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Work Place is required")
    private String worksAt;

    @NotBlank(message = "Specialty  is required")
    private String specialty;

    @NotNull(message = "Booking fee  is required")
    private BigDecimal bookingFee;

    @OneToMany( mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;


    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", worksAt='" + worksAt + '\'' +
                ", specialty='" + specialty + '\'' +
                ", bookingFee=" + bookingFee +

                ", hospital=" + hospital +
                '}';
    }
}
