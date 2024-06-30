package com.dayo.service.interfac;

import com.dayo.dto.Response;
import com.dayo.entity.Booking;

public interface BookingService {

    Response saveBooking(Long doctorId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
