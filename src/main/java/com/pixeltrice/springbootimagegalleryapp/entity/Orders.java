package com.pixeltrice.springbootimagegalleryapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomi;
    private String muallif;
    private String ismi;
    private String telRaqam;
    private String pasport;
    private boolean olibKetdi;
    private String berilganSana;
    private boolean qabulQilindi;
    private String muddati;
    private String olibKelishMudadti;


}
