package com.example.practice_10;

public class Track {
    private String name;
    private String album_name;
    private String author_name;

    public Track(String name, String album_name, String author_name) {
        this.name = name;
        this.album_name = album_name;
        this.author_name = author_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}

