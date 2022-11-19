package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.QRbookGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.QRbookGalleryByte;
import com.pixeltrice.springbootimagegalleryapp.repository.QRbookGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.QRbookGalleryRepository;
import com.pixeltrice.springbootimagegalleryapp.service.QRbooksGalleryService;
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
public class QRbookController {

    @Autowired
    QRbookGalleryRepository qRbookGalleryRepository;

    @Autowired
    QRbookGalleryByteRepository qRbookGalleryByteRepository;

    @Autowired
    QRbooksGalleryService qRbooksGalleryService;

    @GetMapping("/qRbooks")
    String showQR(Model map){
        List<QRbookGallery> all = qRbookGalleryRepository.findAll();
        map.addAttribute("books",all);
        return "About/qRbooks";
    }

    @GetMapping("/qrKodliKitoblar")
    String showbook(Model map) {
        List<QRbookGallery> allActiveImages = qRbooksGalleryService.getAllActiveImages();
        map.addAttribute("allActiveImages", allActiveImages);
        return "AdminPanel/qrKodliKitoblar";
    }

    @GetMapping("/QRbooks/display/{id}")
    @ResponseBody
    void showQRbook(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<QRbookGallery> byId = qRbookGalleryRepository.findById(id);
        if (byId.isPresent()){
            QRbookGallery qRbookGallery=byId.get();
            response.setContentType(qRbookGallery.getQRbookGalleryByte().getContentType());
            response.getOutputStream().write(qRbookGallery.getQRbookGalleryByte().getImageByte());
            response.getOutputStream().close();
        }
    }

    @PostMapping("/image/saveQRBooksDetails")
    public @ResponseBody
    ResponseEntity<?> createQRProduct(@RequestParam("name") String name, @RequestParam("muallif") String muallif, @RequestParam("janri") String janri
            , @RequestParam("image") MultipartFile image, @RequestParam("category") String category) throws IOException {

        QRbookGallery booksGallery=new QRbookGallery();
        booksGallery.setName(name);
        booksGallery.setCategory(category);
        booksGallery.setJanri(janri);
        booksGallery.setMuallif(muallif);

        QRbookGalleryByte booksGalleryByte=new QRbookGalleryByte();
        booksGalleryByte.setImageByte(image.getBytes());
        booksGalleryByte.setContentType(image.getContentType());
        QRbookGalleryByte galleryByte=qRbookGalleryByteRepository.save(booksGalleryByte);
        booksGallery.setQRbookGalleryByte(galleryByte);
        qRbooksGalleryService.saveImage(booksGallery);
        return new ResponseEntity<>("QR kitob joylandi - ", HttpStatus.OK);
    }
    @DeleteMapping("/deleteQRbook/{id}")
    public ResponseEntity<?> deleteQRBook(@PathVariable Long id){
        Optional<QRbookGallery> byId = qRbookGalleryRepository.findById(id);
        if (byId.isPresent()){
            qRbookGalleryRepository.deleteById(id);
            qRbookGalleryByteRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }

    @PutMapping("/updateQRBooks/{id}")
    public @ResponseBody ResponseEntity<?> updateQRKitob(@PathVariable Long id, @RequestParam("name") String name, @RequestParam("muallif") String muallif, @RequestParam("janri") String janri
            ,@RequestParam("image") MultipartFile image, @RequestParam("category") String category ) throws IOException {
        Optional<QRbookGallery> byId = qRbookGalleryRepository.findById(id);
        Optional<QRbookGalleryByte> byId1 = qRbookGalleryByteRepository.findById(id);
        if (byId1.isPresent()){
            if (byId.isPresent()){
                QRbookGallery booksGallery=byId.get();
                booksGallery.setJanri(janri);
                booksGallery.setName(name);
                booksGallery.setCategory(category);
                booksGallery.setMuallif(muallif);

                QRbookGalleryByte booksGalleryByte=byId1.get();

                booksGalleryByte.setImageByte(image.getBytes());
                booksGalleryByte.setContentType(image.getContentType());
                QRbookGalleryByte galleryByte =qRbookGalleryByteRepository.save(booksGalleryByte);
                booksGallery.setQRbookGalleryByte(galleryByte);
                qRbooksGalleryService.saveImage(booksGallery);
                return new ResponseEntity<>("QR kitob taxrirlandi - ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("QR kitob topilmadi - ", HttpStatus.NOT_FOUND);
    }
}
