package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.entity.BooksGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.BooksGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.BooksGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksGalleryService {
    @Autowired
    BooksGalleryRepository booksGalleryRepository;
    @Autowired
    BooksGalleryByteRepository booksGalleryByteRepository;
    public void saveImage(BooksGallery booksGallery) {
        booksGalleryRepository.save(booksGallery);
    }
    public List<BooksGallery> getAllActiveImages() {
        return booksGalleryRepository.findAll();
    }
}
