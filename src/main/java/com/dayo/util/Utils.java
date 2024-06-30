package com.dayo.util;

import com.dayo.dto.BookingDto;
import com.dayo.dto.DoctorDto;
import com.dayo.dto.HospitalDto;
import com.dayo.dto.UserDto;
import com.dayo.entity.Booking;
import com.dayo.entity.Doctor;
import com.dayo.entity.Hospital;
import com.dayo.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
            //  the string builder will help to generate random character from the AlphanumericString.
        }
        return stringBuilder.toString();
    }

//    map the UserDto to User Entity

    public static UserDto mapUserEntityToUserDTO(User user) {
        UserDto userDTO = new UserDto();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

//    map bookingdto to bookingEntity
    public static BookingDto mapBookingEntityToBookingDto(Booking booking){

        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        return bookingDto;
    }



//    map DoctorEntity to DoctorDto
    public static DoctorDto mapDoctorEntityToDoctorDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setSpecialty(doctor.getSpecialty());
        doctorDto.setWorksAt(doctor.getWorksAt());
        doctorDto.setBookingFee(doctor.getBookingFee());

        return doctorDto;
    }


//    map HospitalEntity to HospitalDto
    public static HospitalDto mapHospitalEntityToHospitalDto(Hospital hospital){
        HospitalDto hospitalDto = new HospitalDto();

        hospitalDto.setId(hospital.getId());
        hospitalDto.setHospitalName(hospital.getHospitalName());
        hospitalDto.setAddress(hospital.getAddress());
        return hospitalDto;
    }


//map UserEntity To Userdto plus User bookings and doctors,
// this is for the history
public static UserDto mapUserEntityToUserDtoPlusUserBookingsAndDoctors(User user) {
    UserDto userDTO = new UserDto();

    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setEmail(user.getEmail());
    userDTO.setPhoneNumber(user.getPhoneNumber());
    userDTO.setRole(user.getRole());

    if (!user.getBookings().isEmpty()) {
        userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityPlusBookedDoctors(booking, false)).collect(Collectors.toList()));
    }
    return userDTO;
}


//    map DoctorEntity to DoctorDtoPlusbookings

    public static DoctorDto mapDoctorEntityToDoctorDtoPlusBookings(Doctor doctor){
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setSpecialty(doctor.getSpecialty());
        doctorDto.setWorksAt(doctor.getWorksAt());
        doctorDto.setBookingFee(doctor.getBookingFee());


        if (doctor.getBookings() != null){
            doctorDto.setBookings(doctor.getBookings().stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList()));
        }

        return doctorDto;
    }

//    map bookingEntity to BookingDto + booked doctors

    public static BookingDto mapBookingEntityPlusBookedDoctors(Booking booking, boolean mapUser){
        BookingDto bookingDto =  new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());



        if (mapUser){
            bookingDto.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getDoctor() != null){
            DoctorDto doctorDto = new DoctorDto();

            doctorDto.setId(booking.getDoctor().getId());
            doctorDto.setName(booking.getDoctor().getName());
            doctorDto.setWorksAt(booking.getDoctor().getWorksAt());
            doctorDto.setSpecialty(booking.getDoctor().getSpecialty());
            doctorDto.setBookingFee(booking.getDoctor().getBookingFee());
            bookingDto.setDoctor(doctorDto);
        }

        return bookingDto;
    }

    //    map HospitalEntity to HospitalDtoPlusDoctors

    public static HospitalDto mapHospitalEntityToHospitalDtoPlusDoctors(Hospital hospital) {
        HospitalDto hospitalDto = new HospitalDto();

        hospitalDto.setId(hospital.getId());
        hospitalDto.setHospitalName(hospital.getHospitalName());
        hospitalDto.setAddress(hospitalDto.getAddress());


        if (hospital.getDoctors() != null) {
            hospitalDto.setDoctors(hospital.getDoctors().stream().map(Utils::mapDoctorEntityToDoctorDto).collect(Collectors.toList()));
            //List<DoctorDto> doctorDtos = hospital.getDoctors().stream()
            //        .map(Utils::mapDoctorEntityToDoctorDto)
            //        .collect(Collectors.toList());
            //hospitalDto.setDoctors(doctorDtos);
        }



        return hospitalDto;
    }


    //Map UserListEntity to UserListDto
    public static List<UserDto> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());

    }

//    map DoctorListEntity To DoctorListDTO
    public static  List<DoctorDto> mapDoctorListEntityToDoctorListDto(List<Doctor> doctorList){
        return doctorList.stream().map(Utils::mapDoctorEntityToDoctorDto).collect(Collectors.toList());
    }

//    mapBookingListEntityToBookingListDTO
public static  List<BookingDto> mapBookingListEntityToBookingListDto(List<Booking> bookingList){
    return bookingList.stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList());
}


    //    mapHospitalListEntityToHospitalListDTO
    public static  List<HospitalDto>  mapHospitalListEntityToHospitalListDTO (List<Hospital> hospitalList){
        return hospitalList.stream().map(Utils::mapHospitalEntityToHospitalDto).collect(Collectors.toList());
    }



}
