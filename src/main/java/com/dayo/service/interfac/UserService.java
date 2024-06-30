package com.dayo.service.interfac;


import com.dayo.dto.LoginRequest;
import com.dayo.dto.Response;
import com.dayo.entity.User;

public interface UserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);



}
