package com.pixeltrice.springbootimagegalleryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixeltrice.springbootimagegalleryapp.entity.ImageGallery;

import java.util.List;
import java.util.Optional;


@Repository
public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long>{
    List<ImageGallery> findByCategory(String category);
}

