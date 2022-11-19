package com.pixeltrice.springbootimagegalleryapp.repository;

import com.pixeltrice.springbootimagegalleryapp.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages,Long> {
}
