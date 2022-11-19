package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.Payload.ApiResponse;
import com.pixeltrice.springbootimagegalleryapp.entity.Users;
import com.pixeltrice.springbootimagegalleryapp.repository.UsersRepository;
import com.pixeltrice.springbootimagegalleryapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;
    @PostMapping("/loginKirish")
    public @ResponseBody
    ResponseEntity<Object> login(
            @RequestParam("username1") String username,
            @RequestParam("password1") String password

    ){
        ApiResponse apiResponse = usersService.save(username,password);
        return ResponseEntity.status(apiResponse.isSuccess()?200:208).body(apiResponse.getObject());
    }

    @PostMapping("/saveUsers")
    public @ResponseBody ResponseEntity<?> saveUser(@RequestParam("ismandfamilya") String ismFam, @RequestParam("seriya") String seriya, @RequestParam("email") String email, @RequestParam("number") String TelNomer, @RequestParam("parol") String password){
        Optional<Users> byEmail = usersRepository.findFirstByEmail(email);
        if (!byEmail.isPresent()){
            Users users=new Users();
            users.setUsername(ismFam);
            users.setPasport(seriya);
            users.setEmail(email);
            users.setPassword(password);
            users.setTelNomer(TelNomer);
            usersRepository.save(users);
            return new ResponseEntity<>("Foydalanuvchi joylandi", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bunday foydalanuvchi mavjud", HttpStatus.ALREADY_REPORTED);
    }

    @GetMapping("/login1")public String login(){

        return "About/login1";}

    @GetMapping("/loginKirish/{user}/{parol}")
    public String LOGIN(@RequestParam String user, @RequestParam String parol){
        Optional<Users> byEmailAndPassword = usersRepository.findByEmailAndPassword(user, parol);
        if (byEmailAndPassword.isPresent()){
            return "About/booksOrder/"+"userId="+byEmailAndPassword.get().getId();
        }
        return null;
    }
    ///////////////////end Login password*//////////////////////

}
