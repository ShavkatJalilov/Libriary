package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.*;
import com.pixeltrice.springbootimagegalleryapp.repository.*;
import com.pixeltrice.springbootimagegalleryapp.service.BooksGalleryService;
import com.pixeltrice.springbootimagegalleryapp.service.NewsGalleryService;
import com.pixeltrice.springbootimagegalleryapp.service.QRbooksGalleryService;
import com.pixeltrice.springbootimagegalleryapp.service.TadbirlarGalleryService;
import org.attoparser.dom.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @GetMapping("/admin-panel")public String adminpanel(){return "AdminPanel/admin-panel";}


    @Autowired
    BooksGalleryRepository booksGalleryRepository;
    @Autowired
    BooksGalleryByteRepository booksGalleryByteRepository;
    @Autowired
    BooksGalleryService booksGalleryService;





    @GetMapping("/KitobBuyurtmalar")
    String showtable(Model map) {
        List<BooksGallery> allActiveImages = booksGalleryService.getAllActiveImages();
        map.addAttribute("allActiveImages", allActiveImages);
        return "AdminPanel/KitobBuyurtmalar";
    }







    ///////////////////////* Start Kitoblar*//////////////////////////////

    @GetMapping("/books/display/{id}")
    @ResponseBody
    void showbook(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<BooksGallery> byId1 = booksGalleryRepository.findById(id);
        if (byId1.isPresent()){
            BooksGallery newsGallery=byId1.get();
            response.setContentType(newsGallery.getBooksGalleryByte().getContentType());
            response.getOutputStream().write(newsGallery.getBooksGalleryByte().getFileByte());
            response.getOutputStream().close();
        }
    }

    @PostMapping("/image/saveBooksDetails")
    public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name, @RequestParam("muallif") String muallif, @RequestParam("janri") String janri
            ,@RequestParam("image") MultipartFile image, @RequestParam("category") String category, @RequestParam("kuni") String kuni) throws IOException {
        BooksGallery booksGallery=new BooksGallery();
        booksGallery.setName(name);
        booksGallery.setCategory(category);
        booksGallery.setJanri(janri);
        booksGallery.setMuallif(muallif);
        booksGallery.setIjaraKuni(kuni);

        BooksGalleryByte booksGalleryByte=new BooksGalleryByte();
        booksGalleryByte.setFileByte(image.getBytes());
        booksGalleryByte.setContentType(image.getContentType());
        BooksGalleryByte galleryByte=booksGalleryByteRepository.save(booksGalleryByte);
        booksGallery.setBooksGalleryByte(galleryByte);
        booksGalleryService.saveImage(booksGallery);
        return new ResponseEntity<>("Yangilik joylandi - ", HttpStatus.OK);
    }
    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Optional<BooksGallery> byId1 = booksGalleryRepository.findById(id);
        if (byId1.isPresent()){
            booksGalleryRepository.deleteById(id);
            booksGalleryByteRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }

    @PutMapping("/updateBooks/{id}")
    public @ResponseBody ResponseEntity<?> updateKitob(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("muallif") String muallif, @RequestParam("janri") String janri
            ,@RequestParam("image") MultipartFile image, @RequestParam("category") String category, @RequestParam("kuni1") String kuni ) throws IOException {
        Optional<BooksGallery> byId = booksGalleryRepository.findById(id);
        Optional<BooksGalleryByte> byId1 = booksGalleryByteRepository.findById(id);
        if (byId1.isPresent()){
            if (byId.isPresent()){
               BooksGallery booksGallery=byId.get();
                booksGallery.setJanri(janri);
                booksGallery.setName(name);
                booksGallery.setCategory(category);
                booksGallery.setMuallif(muallif);
                booksGallery.setIjaraKuni(kuni);

                BooksGalleryByte booksGalleryByte=byId1.get();

                booksGalleryByte.setFileByte(image.getBytes());
                booksGalleryByte.setContentType(image.getContentType());
               BooksGalleryByte galleryByte = booksGalleryByteRepository.save(booksGalleryByte);
                booksGallery.setBooksGalleryByte(galleryByte);
                booksGalleryService.saveImage(booksGallery);
                return new ResponseEntity<>("Kitob taxrirlandi - ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Kitob topilmadi - ", HttpStatus.NOT_FOUND);
    }
}
