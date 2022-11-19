package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.Payload.ApiResponse;
import com.pixeltrice.springbootimagegalleryapp.entity.Users;
import com.pixeltrice.springbootimagegalleryapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public ApiResponse save(String username, String password) {
        Optional<Users> byEmailAndPassword = usersRepository.findByEmailAndPassword(username, password);
        if (!byEmailAndPassword.isPresent()) return new ApiResponse("error",false);
        Users u = byEmailAndPassword.get();
        int id = Math.toIntExact(u.getId());
        return new ApiResponse(true,id);
    }
}
