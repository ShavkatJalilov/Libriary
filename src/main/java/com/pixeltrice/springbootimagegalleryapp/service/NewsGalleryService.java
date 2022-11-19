package com.pixeltrice.springbootimagegalleryapp.service;

import com.pixeltrice.springbootimagegalleryapp.entity.ImageGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.NewsGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.NewsGalleryByteRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.NewsGalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsGalleryService {
    @Autowired
    NewsGalleryRepository newsGalleryRepository;
     public void saveImage(NewsGallery newsGallery) {
        newsGalleryRepository.save(newsGallery);
    }
    public List<NewsGallery> getAllActiveImages() {
        return newsGalleryRepository.findAll();
    }


}
