package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGalleryByte;
import com.pixeltrice.springbootimagegalleryapp.repository.NewsGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.NewsGalleryRepository;
import com.pixeltrice.springbootimagegalleryapp.service.NewsGalleryService;
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
public class NewsController {

    @Autowired
    NewsGalleryByteRepository newsGalleryByteRepository;
    @Autowired
    NewsGalleryRepository newsGalleryRepository;
    @Autowired
    NewsGalleryService newsGalleryService;

    @DeleteMapping("/delete1/{id}")
    public ResponseEntity<?> deleteKitob(@PathVariable Long id){
        Optional<NewsGallery> byId = newsGalleryRepository.findById(id);
        if (byId.isPresent()){
            newsGalleryRepository.deleteById(id);
            newsGalleryByteRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }

    @GetMapping("/news/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<NewsGallery> byId = newsGalleryRepository.findById(id);
        if (byId.isPresent()){
            NewsGallery newsGallery=byId.get();
            response.setContentType(newsGallery.getNewsGalleryByte().getContentType());
            response.getOutputStream().write(newsGallery.getNewsGalleryByte().getImageByte());
            response.getOutputStream().close();
        }
    }

    @PostMapping("/image/saveNewsDetails")
    public @ResponseBody
    ResponseEntity<?> createProduct(@RequestParam("sana") String sana, @RequestParam("sarlavha") String sarlavha
            , @RequestParam("muqova") MultipartFile image, @RequestParam("matni") String matni) throws IOException {
        NewsGallery newsGallery=new NewsGallery();
        newsGallery.setMatni(matni);
        newsGallery.setSana(sana);
        newsGallery.setSarlavha(sarlavha);

        NewsGalleryByte newsGalleryByte=new NewsGalleryByte();
        newsGalleryByte.setImageByte(image.getBytes());
        newsGalleryByte.setContentType(image.getContentType());
        NewsGalleryByte galleryByte=newsGalleryByteRepository.save(newsGalleryByte);
        newsGallery.setNewsGalleryByte(galleryByte);
        newsGalleryService.saveImage(newsGallery);
        return new ResponseEntity<>("Yangilik joylandi - ", HttpStatus.OK);
    }

    @PutMapping("/updateNews/{id}")
    public @ResponseBody
    ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestParam("sana1") String sana, @RequestParam("sarlavha1") String sarlavha
            , @RequestParam("muqova1") MultipartFile image, @RequestParam("matni1") String matni) throws IOException {
        Optional<NewsGallery> byId = newsGalleryRepository.findById(id);
        Optional<NewsGalleryByte> byId1 = newsGalleryByteRepository.findById(id);
        if (byId.isPresent()) {
            if (byId1.isPresent()) {
                NewsGallery newsGallery = byId.get();
                newsGallery.setMatni(matni);
                newsGallery.setSana(sana);
                newsGallery.setSarlavha(sarlavha);

                NewsGalleryByte newsGalleryByte = byId1.get();
                newsGalleryByte.setImageByte(image.getBytes());
                newsGalleryByte.setContentType(image.getContentType());
                NewsGalleryByte galleryByte = newsGalleryByteRepository.save(newsGalleryByte);
                newsGallery.setNewsGalleryByte(galleryByte);
                newsGalleryService.saveImage(newsGallery);
                return new ResponseEntity<>("Yangilik taxrirlandi - ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Yangilik topilmadi- ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/yangiliklar")
    String show(Model map) {
        List<NewsGallery> images = newsGalleryService.getAllActiveImages();

        map.addAttribute("news", images);
        return "AdminPanel/yangiliklar";
    }
}
