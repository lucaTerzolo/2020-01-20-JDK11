package it.polito.tdp.artsmia.model;

public class Artist {
	private int artistId;
	private String artistName;
	
	public Artist(int artistId, String artistName) {
		super();
		this.artistId = artistId;
		this.artistName = artistName;
	}
	
	public int getArtistId() {
		return artistId;
	}
	
	public String getArtistName() {
		return artistName;
	}
}
