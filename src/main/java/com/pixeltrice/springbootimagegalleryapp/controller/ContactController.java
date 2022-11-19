package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.Messages;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {
    @GetMapping("/contact")public String contact(){return "About/contact";}
    @Autowired
    MessagesRepository messagesRepository;
    @PostMapping("/saveMessage")
    public @ResponseBody ResponseEntity<?> saveUser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("subject") String subject, @RequestParam("message") String message){
        Messages messages=new Messages();
        messages.setName(name);
        messages.setMessage(message);
        messages.setEmail(email);
        messages.setSubject(subject);
        messagesRepository.save(messages);

        return new ResponseEntity<>("Xabar muavffaqiyatli saqlandi", HttpStatus.OK);
    }

    @GetMapping("/contactPanel")
    public String contactpanel(Model map){
        List<Messages> messages = messagesRepository.findAll();
        map.addAttribute("mes",messages);

        return "AdminPanel/contact-us-panel";}

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id){
        Optional<Messages> byId = messagesRepository.findById(id);
        if (byId.isPresent()){
            messagesRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }



}
