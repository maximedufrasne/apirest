package com.myaudiolibrary.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artists {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public  Artists(){

    }

    @OneToMany( mappedBy = "Artists", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("artists")
    private List<Albums> albums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbums(List<Albums> albums) {
        this.albums = albums;
    }

    public List<Albums> getAlbums() {
        return albums;
    }
}
