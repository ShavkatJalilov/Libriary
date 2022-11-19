package com.pixeltrice.springbootimagegalleryapp.repository;

import com.pixeltrice.springbootimagegalleryapp.entity.BooksGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksGalleryRepository extends JpaRepository<BooksGallery, Long> {
}
