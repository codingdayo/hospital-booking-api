package com.dayo.service.impl;

import com.dayo.dto.BookingDto;
import com.dayo.dto.Response;
import com.dayo.entity.Booking;
import com.dayo.entity.Doctor;
import com.dayo.entity.User;
import com.dayo.exception.CustomException;
import com.dayo.repo.BookingRepository;
import com.dayo.repo.DoctorRepository;
import com.dayo.repo.UserRepository;
import com.dayo.service.interfac.BookingService;
import com.dayo.service.interfac.DoctorService;
import com.dayo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response saveBooking(Long doctorId, Long userId, Booking bookingRequest) {
        Response response = new Response();
        LocalDate currentDate = LocalDate.now();
        try {
            if (bookingRequest.getBookingDate()
                    .isBefore(currentDate)){
                throw  new IllegalArgumentException("Booking Date must come after Current Date");
            }
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new CustomException("Doctor Not Found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException("User Not Found"));

            List<Booking> existingBookings = doctor.getBookings();
            //created  DoctorIsAvailable method below
            if(!DoctorIsAvailable(existingBookings)){
                throw new CustomException("Doctor not Available right now");
            }

            bookingRequest.setDoctor(doctor);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateConfirmationCode(10);
            //missed the setting of BCcode, reason why it didn't save to the db
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Saving a booking: " + e.getMessage());

        }
        return response;
    }

    private boolean DoctorIsAvailable(List<Booking> existingBookings) {
        return existingBookings.isEmpty(); // Doctor is available if there are no existing bookings
    }



    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new CustomException("Booking not found"));
            //changed this to return roles and users; map the user with 'true'
            //BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);
            BookingDto bookingDTO = Utils.mapBookingEntityPlusBookedDoctors(booking, true);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBooking(bookingDTO);


        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error finding a booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> booking = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            //changed this to return roles and users
            List<BookingDto> bookingDTOList = Utils.mapBookingListEntityToBookingListDto(booking);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBookingList(bookingDTOList);


        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
            bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new CustomException("Booking Does not Exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Successful");

        } catch (CustomException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Canceling a booking: " + e.getMessage());

        }
        return response;
    }
}
