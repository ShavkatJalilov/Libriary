package com.pixeltrice.springbootimagegalleryapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pixeltrice.springbootimagegalleryapp.entity.ImageGalleryByte;
import com.pixeltrice.springbootimagegalleryapp.repository.ImageGalleryRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.ImageGalleryRepositoryByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pixeltrice.springbootimagegalleryapp.entity.ImageGallery;
import com.pixeltrice.springbootimagegalleryapp.service.ImageGalleryService;


@Controller
public class ImageGalleryController {
	
	@Autowired
	ImageGalleryService imageGalleryService;
	@Autowired
	ImageGalleryRepositoryByte imageGalleryRepositoryByte;
	@GetMapping(value = {"/", "/home"})
	public String addProductPage() {
		return "index";
	}

	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name,
			@RequestParam("muallif") String muallif, @RequestParam("janri") String janri
			,@RequestParam("image") MultipartFile image,@RequestParam("file") MultipartFile file, @RequestParam("category") String category) throws IOException {
		ImageGallery infor = new ImageGallery();
        infor.setName(name);
		infor.setMuallif(muallif);
		infor.setCategory(category);
		infor.setJanri(janri);

		ImageGalleryByte imageGalleryByte = new ImageGalleryByte();
		imageGalleryByte.setFileByte(file.getBytes());
		imageGalleryByte.setImageByte(image.getBytes());
		imageGalleryByte.setContentType(image.getContentType());
		imageGalleryByte.setFileContentType(file.getContentType());
		ImageGalleryByte galleryByte = imageGalleryRepositoryByte.save(imageGalleryByte);
		infor.setImageGalleryByte(galleryByte);
        imageGalleryService.saveImage(infor);
		return new ResponseEntity<>("Maxsulot joylandi - ", HttpStatus.OK);
	}

	@Autowired
	ImageGalleryRepository imageGalleryRepository;
//	@GetMapping("/image/display/{id}")
//	@ResponseBody
//	void showImage(@PathVariable("id") Long id, HttpServletResponse response)
//			throws ServletException, IOException {
//		Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
//		if (byId.isPresent()){
//			ImageGallery imageGallery = byId.get();
//			response.setContentType(imageGallery.getImageGalleryByte().getContentType());
//			response.getOutputStream().write(imageGallery.getImageGalleryByte().getImageByte());
//			response.getOutputStream().close();
//		}
//	}


		@GetMapping("/kitoblar")
	String show(Model map) {
		List<ImageGallery> images = imageGalleryService.getAllActiveImages();

		map.addAttribute("images", images);
		return "AdminPanel/kitoblar";
	}
	@GetMapping("/boshSahifa")
	public String firstPage(){
		return "crud";
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteKitob(@PathVariable Long id){
		Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
		if (byId.isPresent()){
			imageGalleryRepository.deleteById(id);
			imageGalleryRepositoryByte.deleteById(id);
			return ResponseEntity.ok("O'chirildi");
		}
		return ResponseEntity.ok("Xatolik");
	}

	@PutMapping("/update/{id}")
	public @ResponseBody ResponseEntity<?> updateKitob(@PathVariable Long id,@RequestParam("name") String name, @RequestParam("muallif") String muallif, @RequestParam("janri") String janri
			,@RequestParam("image") MultipartFile image,@RequestParam("file") MultipartFile file, @RequestParam("category") String category ) throws IOException {
		Optional<ImageGallery> byId = imageGalleryRepository.findById(id);
		Optional<ImageGalleryByte> byId1 = imageGalleryRepositoryByte.findById(id);
		if (byId1.isPresent()){
				if (byId.isPresent()){
				ImageGallery imageGallery=byId.get();
				imageGallery.setJanri(janri);
				imageGallery.setName(name);
				imageGallery.setCategory(category);
				imageGallery.setMuallif(muallif);

				ImageGalleryByte imageGalleryByte=byId1.get();

				imageGalleryByte.setImageByte(image.getBytes());
				imageGalleryByte.setFileByte(file.getBytes());
				imageGalleryByte.setContentType(image.getContentType());
				imageGalleryByte.setFileContentType(file.getContentType());
				ImageGalleryByte galleryByte = imageGalleryRepositoryByte.save(imageGalleryByte);
				imageGallery.setImageGalleryByte(galleryByte);
				imageGalleryService.saveImage(imageGallery);
				return new ResponseEntity<>("Kitob taxrirlandi - ", HttpStatus.OK);
				}
		}
		return new ResponseEntity<>("Kitob topilmadi - ", HttpStatus.NOT_FOUND);
	}

}

