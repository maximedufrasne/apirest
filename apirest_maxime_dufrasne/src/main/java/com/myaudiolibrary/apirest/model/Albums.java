package com.myaudiolibrary.apirest.model;

import javax.persistence.*;


@Entity
@Table (name = "album")
public class Albums {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Long artistId;

    public  Albums(){

    }

    @ManyToOne
    @JoinColumn(name = "artistId")
    private Artists artists;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }
    
}
