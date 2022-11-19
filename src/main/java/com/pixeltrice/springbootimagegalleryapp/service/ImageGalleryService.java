package com.pixeltrice.springbootimagegalleryapp.service;

import java.util.List;
import java.util.Optional;

import com.pixeltrice.springbootimagegalleryapp.repository.ImageGalleryRepositoryByte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixeltrice.springbootimagegalleryapp.entity.ImageGallery;
import com.pixeltrice.springbootimagegalleryapp.repository.ImageGalleryRepository;



@Service
public class ImageGalleryService {

	@Autowired
	ImageGalleryRepository imageGalleryRepository;
	@Autowired
	ImageGalleryRepositoryByte imageGalleryRepositoryByte;
	
	public void saveImage(ImageGallery imageGallery) {
		imageGalleryRepository.save(imageGallery);	
	}

	public List<ImageGallery> getAllActiveImages() {
		return imageGalleryRepository.findAll();
	}

//	public Optional<ImageGallery> getImageById(Long id) {
//		Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
//		if (byId.isPresent()){
//			ImageGallery imageGallery = byId.get();
//		return 	imageGallery.getImageGalleryByte().getImageByte();
//		}
//		 imageGalleryRepositoryByte.findById(id);
//	}

}

