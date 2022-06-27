package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private Artist a1;
	private Artist a2;
	private int opereComune;
	
	public Adiacenza(Artist a1, Artist a2, int opereComune) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.opereComune = opereComune;
	}
	
	public Artist getA1() {
		return a1;
	}
	public Artist getA2() {
		return a2;
	}
	public int getOpereComune() {
		return opereComune;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return -this.opereComune+o.getOpereComune();
	}

	@Override
	public String toString() {
		return a1.getArtistId() +" - "+a2.getArtistId()+" = "+ opereComune+"\n";
	}
	
}
