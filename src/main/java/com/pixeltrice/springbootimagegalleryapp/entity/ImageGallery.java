package com.pixeltrice.springbootimagegalleryapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class ImageGallery {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	

	private String name;
	

	private String muallif;

	private String category;

	private String janri;


	@OneToOne
	ImageGalleryByte imageGalleryByte;


}


