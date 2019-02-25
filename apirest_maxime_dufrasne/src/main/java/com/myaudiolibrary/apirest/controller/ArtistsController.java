package com.myaudiolibrary.apirest.controller;

import com.myaudiolibrary.apirest.model.Albums;
import com.myaudiolibrary.apirest.model.Artists;
import com.myaudiolibrary.apirest.repository.AlbumsRepository;
import com.myaudiolibrary.apirest.repository.ArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/artists")
public class ArtistsController {

    @Autowired
    private ArtistsRepository artistsRepository;

    @Autowired
    private AlbumsRepository albumRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )

    public Artists ArtistsFindById (@PathVariable("id") Long id) {
        Artists artist = artistsRepository.findOne(id);
        if(artist == null)
            throw new EntityNotFoundException("L'artiste d'id " +
                    id + " n'existe pas");
        return artist;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            params = "name"
    )

    public List<Artists> ArtistsFindByName(@RequestParam("name") String name){
        return artistsRepository.findByNameIgnoreCaseContaining(name);
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public Page<Artists> artistsListArtists(@RequestParam("page") Integer page,
                                             @RequestParam("size") Integer size,
                                             @RequestParam("sortProperty") String sortProperty,
                                             @RequestParam("sortDirection") Sort.Direction sortDirection) {

        PageRequest pageRequest = new PageRequest(page, size, sortDirection, sortProperty);
        return artistsRepository.findAll(pageRequest);
    }

    @RequestMapping(
            method = RequestMethod.POST, //Méthode HTTP : GET/POST/PATCH/PUT/DELETE
            consumes = "application/json", //Type MIME des données passées avec la requête : JSON, XML, Texte...
            produces = "application/json")//Type MIME des données fournies dans la réponse
    public Artists createArtists(@RequestBody Artists artists){
        Artists artist = artistsRepository.findByName(artists.getName());
        if(artist != null)
            throw new IllegalArgumentException("L'artiste " +
                    artists.getName() + " existe déjà");
        return artistsRepository.save(artists);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json",
            value = "/{id}"
    )

    public void artistsUpdate(@PathVariable Long id, @RequestBody Artists artists){
        artistsRepository.save(artists);
        if(artists == null)
            throw new EntityNotFoundException("L'artiste d'id " +
                    artists.getName() + " n'existe pas");
        artists.setName(artists.getName());
        artistsRepository.save(artists);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            consumes = "application/json",
            produces = "application/json",
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public Artists deleteArtists (@PathVariable(value = "id") Long id){
        Artists artists = artistsRepository.findOne(id);
        if(artists == null)
            throw new EntityNotFoundException("L'artiste d'id " +
                    id + " n'existe pas");
        for (int i=0; i < artists.getAlbums().size(); i++)
        {
            Albums album = albumRepository.findById(artists.getAlbums().get(i).getId());
            albumRepository.delete(album);
        }
        artistsRepository.delete(artists);
        return artists;
    }
}
