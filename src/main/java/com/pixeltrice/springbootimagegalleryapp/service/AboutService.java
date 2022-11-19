package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.entity.About;
import com.pixeltrice.springbootimagegalleryapp.entity.BooksGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.AboutByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutService {
    @Autowired
    AboutRepository aboutRepository;
    @Autowired
    AboutByteRepository aboutByteRepository;

    public void saveImage(About about) {
        aboutRepository.save(about);
    }
    public List<About> getAllActiveImages() {
        return aboutRepository.findAll();
    }

}
