package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.TadbirlarGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.TadbirlarGalleryByte;
import com.pixeltrice.springbootimagegalleryapp.repository.TadbirlarGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.TadbirlarGalleryRepository;
import com.pixeltrice.springbootimagegalleryapp.service.TadbirlarGalleryService;
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
public class TadbirController {
    @Autowired
    TadbirlarGalleryRepository tadbirlarGalleryRepository;
    @Autowired
    TadbirlarGalleryByteRepository tadbirlarGalleryByteRepository;
    @Autowired
    TadbirlarGalleryService tadbirlarGalleryService;

    @GetMapping("/Tadbirlar")
    String showtadbir(Model map) {
        List<TadbirlarGallery> allActiveImages = tadbirlarGalleryService.getAllActiveImages();
        map.addAttribute("allActiveImages", allActiveImages);
        return "AdminPanel/Tadbirlar";
    }

    @GetMapping("/tadbirlar1")
    String showTadbir(Model map) {
        List<TadbirlarGallery> allActiveImages = tadbirlarGalleryService.getAllActiveImages();
        map.addAttribute("allActiveImages", allActiveImages);
        return "About/tadbirlar1";
    }


    @GetMapping("/Tadbirlar/display/{id}")
    @ResponseBody
    void showTadbirlar(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<TadbirlarGallery> byId = tadbirlarGalleryRepository.findById(id);

        if (byId.isPresent()){
            TadbirlarGallery tadbirlarGallery=byId.get();
            response.setContentType(tadbirlarGallery.getTadbirlarGalleryByte().getContentType());
            response.getOutputStream().write(tadbirlarGallery.getTadbirlarGalleryByte().getImageByte());
            response.getOutputStream().close();
        }
    }
    @DeleteMapping("/deleteTadbirlar/{id}")
    public ResponseEntity<?> deleteTadbirlar(@PathVariable Long id){
        Optional<TadbirlarGallery> byId = tadbirlarGalleryRepository.findById(id);
        if (byId.isPresent()){
            tadbirlarGalleryRepository.deleteById(id);
            tadbirlarGalleryByteRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }


    @PostMapping("/saveTadbirlar")
    public @ResponseBody
    ResponseEntity<?> createTadbir(@RequestParam("sana") String sana, @RequestParam("sarlavha") String sarlavha
            , @RequestParam("image") MultipartFile image, @RequestParam("matni") String matni) throws IOException {
        TadbirlarGallery tadbirlarGallery=new TadbirlarGallery();
        tadbirlarGallery.setSarlavha(sarlavha);
        tadbirlarGallery.setSana(sana);
        tadbirlarGallery.setMatn(matni);
        TadbirlarGalleryByte tadbirlarGalleryByte=new TadbirlarGalleryByte();
        tadbirlarGalleryByte.setImageByte(image.getBytes());
        tadbirlarGalleryByte.setContentType(image.getContentType());
        TadbirlarGalleryByte galleryByte=tadbirlarGalleryByteRepository.save(tadbirlarGalleryByte);
        tadbirlarGallery.setTadbirlarGalleryByte(galleryByte);
        tadbirlarGalleryService.saveImage(tadbirlarGallery);
        return new ResponseEntity<>("Tadbir ma'lumotlari joylandi - ", HttpStatus.OK);
    }

    @PutMapping("/updateTadbir/{id}")
    public @ResponseBody ResponseEntity<?> updateTadbir(@PathVariable Long id, @RequestParam("sarlavha1") String sarlavha, @RequestParam("sana1") String sana1
            ,@RequestParam("muqova1") MultipartFile image, @RequestParam("matni1") String matni1 ) throws IOException {
        Optional<TadbirlarGallery> byId = tadbirlarGalleryRepository.findById(id);
        Optional<TadbirlarGalleryByte> byId1 = tadbirlarGalleryByteRepository.findById(id);
        if (byId1.isPresent()){
            if (byId.isPresent()){
                TadbirlarGallery tadbirlarGallery=byId.get();
                tadbirlarGallery.setSarlavha(sarlavha);
                tadbirlarGallery.setSana(sana1);
                tadbirlarGallery.setMatn(matni1);


                TadbirlarGalleryByte tadbirlarGalleryByte=byId1.get();

                tadbirlarGalleryByte.setImageByte(image.getBytes());
                tadbirlarGalleryByte.setContentType(image.getContentType());
                TadbirlarGalleryByte galleryByte =tadbirlarGalleryByteRepository.save(tadbirlarGalleryByte);
                tadbirlarGallery.setTadbirlarGalleryByte(galleryByte);
                tadbirlarGalleryService.saveImage(tadbirlarGallery);
                return new ResponseEntity<>("Tadbir ma'lumotlari taxrirlandi - ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Tadbir ma'lumotlari topilmadi - ", HttpStatus.NOT_FOUND);
    }

}
