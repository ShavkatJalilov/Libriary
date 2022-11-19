package com.pixeltrice.springbootimagegalleryapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productByte")
public class ImageGalleryByte {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String contentType;
	private String fileContentType;
	private byte[] imageByte;
	private byte[] fileByte;
}


