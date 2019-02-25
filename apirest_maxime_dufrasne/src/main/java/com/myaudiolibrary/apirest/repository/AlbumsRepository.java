package com.myaudiolibrary.apirest.repository;

import com.myaudiolibrary.apirest.model.Albums;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlbumsRepository extends JpaRepository<Albums, Long> {

    Albums findById(Long id);

    Albums findByTitle(String title);

}
