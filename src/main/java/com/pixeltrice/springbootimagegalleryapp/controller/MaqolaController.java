package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.Maqolalar;
import com.pixeltrice.springbootimagegalleryapp.repository.MaqolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MaqolaController {
    @Autowired
    MaqolaRepository maqolaRepository;

    @GetMapping("/maqolalar")
    String showMaqola(Model map){
        List<Maqolalar> all = maqolaRepository.findAll();
        map.addAttribute("maqola",all);
        return "About/maqolalar";
    }

    @GetMapping("/Maqolalar")
    String showMaqola1(Model map) {
        List<Maqolalar> all = maqolaRepository.findAll();
        map.addAttribute("All", all);
        return "AdminPanel/Maqolalar";
    }
    @DeleteMapping("/deleteMaqola/{id}")
    public ResponseEntity<?> deleteMaqola(@PathVariable Long id){
        Optional<Maqolalar> byId = maqolaRepository.findById(id);
        if (byId.isPresent()){
            maqolaRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi");
        }
        return ResponseEntity.ok("Xatolik");
    }

    @PostMapping("/saveMaqola")
    public @ResponseBody
    ResponseEntity<?> createMaqola(@RequestParam("sarlavha") String sarlavha, @RequestParam("sana") String sana, @RequestParam("matni") String matni) throws IOException {
        Maqolalar maqolalar=new Maqolalar();
        maqolalar.setSarlavha(sarlavha);
        maqolalar.setMatni(matni);
        maqolalar.setSana(sana);
        maqolaRepository.save(maqolalar);
        return new ResponseEntity<>("Maqola ma'lumotlari joylandi - ", HttpStatus.OK);
    }

    @PutMapping("/updateMaqola/{id}")
    public @ResponseBody ResponseEntity<?> updateMaqola(@PathVariable Long id, @RequestParam("sarlavha1") String sarlavha, @RequestParam("sana1") String sana1, @RequestParam("matni1") String matni1 ) throws IOException {
        Optional<Maqolalar> byId = maqolaRepository.findById(id);
        if (byId.isPresent()){
            Maqolalar maqolalar=byId.get();
            maqolalar.setSarlavha(sarlavha);
            maqolalar.setMatni(matni1);
            maqolalar.setSana(sana1);
            maqolaRepository.save(maqolalar);
            return new ResponseEntity<>("Maqola ma'lumotlari taxrirlandi - ", HttpStatus.OK);
        }

        return new ResponseEntity<>("Maqola ma'lumotlari topilmadi - ", HttpStatus.NOT_FOUND);
    }
}
