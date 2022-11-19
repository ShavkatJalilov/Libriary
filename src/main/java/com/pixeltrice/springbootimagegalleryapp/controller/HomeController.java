package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.*;
import com.pixeltrice.springbootimagegalleryapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
@Autowired
    ImageGalleryRepository imageGalleryRepository;

    @Autowired
    NewsGalleryRepository newsGalleryRepository;

    @Autowired
    QRbookGalleryRepository qRbookGalleryRepository;

    @GetMapping("/about")public String about(){return "About/about";}
    @GetMapping("/register")public String register(){return "About/register";}

    @GetMapping("/books")
    String showCard(Model map){
        List<ImageGallery> all = imageGalleryRepository.findAll();
        map.addAttribute("all", all);

        return "About/books";
    }

    @GetMapping("/news")
    String showCard2(Model map){
        List<NewsGallery> all = newsGalleryRepository.findAll();
        map.addAttribute("news",all);
        return "About/news";
    }


    @GetMapping("/index")
    String showCard1(Model map){
        List<ImageGallery> badiy = imageGalleryRepository.findByCategory("Badiy");
        map.addAttribute("badiy", badiy);

        List<ImageGallery> jahon_adabiyoti = imageGalleryRepository.findByCategory("Jahon adabiyoti");
        map.addAttribute("jahon", jahon_adabiyoti);

        List<ImageGallery> ilmiy = imageGalleryRepository.findByCategory("Ilmiy");
        map.addAttribute("ilmiy",ilmiy);

        List<NewsGallery> all = newsGalleryRepository.findAll();
        map.addAttribute("news",all);

        List<QRbookGallery> all1 = qRbookGalleryRepository.findAll();
        map.addAttribute("QR", all1);

        return "About/index";
    }


    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
        if (byId.isPresent()){
            ImageGallery imageGallery = byId.get();
            response.setContentType(imageGallery.getImageGalleryByte().getContentType());
            response.getOutputStream().write(imageGallery.getImageGalleryByte().getImageByte());
            response.getOutputStream().close();
        }
    }

    @Autowired
    ImageGalleryRepositoryByte imageGalleryRepositoryByte;
    @GetMapping("/download/{id}")
    public void download_file(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
        if (byId.isPresent()) {
            ImageGallery imageGallery=byId.get();
            Optional<ImageGalleryByte> file = imageGalleryRepositoryByte.findById(imageGallery.getId());
            if (file.isPresent()) {
                ImageGalleryByte imageGalleryByte=file.get();
                response.setContentType(imageGalleryByte.getFileContentType());
                response.setHeader("Content-Disposition", "attachment; name=\"" + imageGallery.getName()+ "\"");
                FileCopyUtils.copy(imageGalleryByte.getFileByte(), response.getOutputStream());
            }
        }
    }




}
