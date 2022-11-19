package com.pixeltrice.springbootimagegalleryapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TadbirlarGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sarlavha;
    private String sana;
    private String matn;

    @OneToOne
    private TadbirlarGalleryByte tadbirlarGalleryByte;
}
