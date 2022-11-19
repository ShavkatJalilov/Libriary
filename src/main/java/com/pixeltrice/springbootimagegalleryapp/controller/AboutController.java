package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.About;
import com.pixeltrice.springbootimagegalleryapp.entity.AboutByte;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGalleryByte;
import com.pixeltrice.springbootimagegalleryapp.repository.AboutByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.AboutRepository;
import com.pixeltrice.springbootimagegalleryapp.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AboutController {
    @Autowired
    AboutService aboutService;
    @Autowired
    AboutRepository aboutRepository;
    @Autowired
    AboutByteRepository aboutByteRepository;

    @GetMapping("/aboutPanel")
    public String aboutpanel(Model map){

        List<About> all = aboutRepository.findAll();
        map.addAttribute("about",all);
        return "AdminPanel/about-uz-panel";
    }


    @GetMapping("/about/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Integer id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<About> byId = aboutRepository.findById(id);
        if (byId.isPresent()){
            About about=byId.get();
            response.setContentType(about.getAboutByte().getContentType());
            response.getOutputStream().write(about.getAboutByte().getImage());
            response.getOutputStream().close();
        }
    }

    @PostMapping("/saveAboutDetails")
    public @ResponseBody
    ResponseEntity<?> createProduct(@RequestParam("subject") String subject, @RequestParam("about") String malumot
            , @RequestParam("muqova") MultipartFile image,@RequestParam("comment") String comment ) throws IOException {
        About about=new About();
       about.setSubject(subject);
       about.setInformation(malumot);
       about.setComment(comment);

        AboutByte aboutByte=new AboutByte();
        aboutByte.setImage(image.getBytes());
        aboutByte.setContentType(image.getContentType());
        AboutByte aboutGallery=aboutByteRepository.save(aboutByte);
        about.setAboutByte(aboutGallery);
        aboutService.saveImage(about);
        return new ResponseEntity<>("Yangilik joylandi - ", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAbout/{id}")
    public ResponseEntity<?> deleteAbout(@PathVariable Integer id){
        Optional<About> byId = aboutRepository.findById(id);
        if (byId.isPresent()){
            aboutRepository.deleteById(id);
            aboutByteRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }



    @PutMapping("/updateAbout/{id}")
    public @ResponseBody
    ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestParam("subject1") String subject1, @RequestParam("about1") String malumot1
            , @RequestParam("muqova1") MultipartFile image, @RequestParam("comment1") String comment1) throws IOException {
        Optional<About> byId = aboutRepository.findById(id);
        Optional<AboutByte> byId1 = aboutByteRepository.findById(id);
        if (byId.isPresent()) {
            if (byId1.isPresent()) {
            About about=byId.get();
            about.setSubject(subject1);
            about.setInformation(malumot1);
            about.setComment(comment1);


                AboutByte aboutByte=byId1.get();
                aboutByte.setImage(image.getBytes());
                aboutByte.setContentType(image.getContentType());
                AboutByte aboutGallery=aboutByteRepository.save(aboutByte);
                about.setAboutByte(aboutGallery);
                aboutService.saveImage(about);
                return new ResponseEntity<>("Ma'lumot taxrirlandi - ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Ma'lumot topilmadi- ", HttpStatus.NOT_FOUND);
    }

}
