package com.myaudiolibrary.apirest.controller;

import com.myaudiolibrary.apirest.model.Albums;
import com.myaudiolibrary.apirest.model.Artists;
import com.myaudiolibrary.apirest.repository.AlbumsRepository;
import com.myaudiolibrary.apirest.repository.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/albums")
public class AlbumsController {

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    private ArtistsRepository artistsRepository;
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json",
            value = ""
    )

    public Albums albumCreation(@RequestBody Albums albums){
        Albums album = albumsRepository.findByTitle(albums.getTitle());
        if(album != null)
            throw new IllegalArgumentException("L'album " +
                    albums.getTitle() + " existe déjà");
        Artists artist = artistsRepository.findById(albums.getArtistId());
        if(artist == null)
            throw new IllegalArgumentException("L'artiste " +
                    albums.getArtistId() + " n'existe pas");
        return albumsRepository.save(albums);
    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            consumes = "application/json",
            produces = "application/json",
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public Albums albumDelete(@PathVariable Long id, @RequestBody Albums albums){
        Albums album = albumsRepository.findOne(id);
        if(album == null)
            throw new EntityNotFoundException("L'album d'id " + id + " n'existe pas");
        albumsRepository.delete(albums);
        return albums;
    }
}
