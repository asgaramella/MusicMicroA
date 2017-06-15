package it.polito.tdp.music.model;

public class ArtistAndNum {
	private Artist artist;
	private int ascolti;
	
	public ArtistAndNum(Artist artist, int ascolti) {
		super();
		this.artist = artist;
		this.ascolti = ascolti;
	}
	
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	public int getAscolti() {
		return ascolti;
	}
	public void setAscolti(int ascolti) {
		this.ascolti = ascolti;
	}
	
	

}
