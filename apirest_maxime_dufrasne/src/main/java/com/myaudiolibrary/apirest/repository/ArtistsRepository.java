package com.myaudiolibrary.apirest.repository;

import com.myaudiolibrary.apirest.model.Albums;
import com.myaudiolibrary.apirest.model.Artists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistsRepository extends JpaRepository<Artists, Long> {
    List<Artists> findByNameIgnoreCaseContaining(String name);
    Artists findByName(String name);
    Artists findById(Long id);
}
