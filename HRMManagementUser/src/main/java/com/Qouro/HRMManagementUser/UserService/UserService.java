package com.Qouro.HRMManagementUser.UserService;


import com.Qouro.HRMManagementUser.usersShared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserDetailsById(String userId);
}
