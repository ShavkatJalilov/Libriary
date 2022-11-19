package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.entity.BooksGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.QRbookGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.QRbookGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.QRbookGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QRbooksGalleryService {
    @Autowired
    QRbookGalleryRepository qRbookGalleryRepository;
    @Autowired
    QRbookGalleryByteRepository qRbookGalleryByteRepository;
    public void saveImage(QRbookGallery qRbookGallery) {
       qRbookGalleryRepository.save(qRbookGallery);
    }
    public List<QRbookGallery> getAllActiveImages() {
        return qRbookGalleryRepository.findAll();
    }
}
