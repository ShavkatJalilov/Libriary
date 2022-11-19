package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.TadbirlarGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.TadbirlarGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TadbirlarGalleryService {
    @Autowired
    TadbirlarGalleryRepository tadbirlarGalleryRepository;
//    public void saveTadbir(TadbirlarGallery tadbirlarGallery) {tadbirlarGalleryRepository.save(tadbirlarGallery);}
//    public List<TadbirlarGallery> getAllActiveImages() {
//        return tadbirlarGalleryRepository.findAll();

        public void saveImage(TadbirlarGallery tadbirlarGallery) {
            tadbirlarGalleryRepository.save(tadbirlarGallery);
        }
        public List<TadbirlarGallery> getAllActiveImages() {
            return tadbirlarGalleryRepository.findAll();
        }
}
