package com.spotyplayer.model;

import android.net.Uri;

public class Song {
    private long id;
    private String title;
    private String artist;
    private String album;
    private Uri uri;
    private Uri albumArtUri;

    public Song(long id, String title, String artist, String album, Uri uri, Uri albumArtUri) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
        this.albumArtUri = albumArtUri;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public Uri getUri() { return uri; }
    public Uri getAlbumArtUri() { return albumArtUri; }
}