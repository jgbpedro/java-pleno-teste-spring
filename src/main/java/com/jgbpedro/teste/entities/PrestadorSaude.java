package com.jgbpedro.teste.entities;

import java.io.Serializable;

public class PrestadorSaude implements Serializable{
	public static final long serialVersionUID = 1L;
	
	private String nome;
	private String endereco;
	private double latitude;
	private double longitude;
	private double distanciaEmKm;
	
	public PrestadorSaude() {
		
	}

	public PrestadorSaude(
			String nome, 
			String endereco, 
			double latitudeUsuario,
			double latitudePrestadorServico,
			double longitudeUsuario,
			double longitudePrestadorServico
	){
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.latitude = latitudePrestadorServico;
		this.longitude = longitudePrestadorServico;
		this.distanciaEmKm = this.distance(latitudeUsuario, latitudePrestadorServico, longitudeUsuario, longitudePrestadorServico);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDistanciaEmKm() {
		return distanciaEmKm;
	}

	public void setDistanciaEmKm(double distanciaEmKm) {
		this.distanciaEmKm = distanciaEmKm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private double distance(double lat1, double lat2, double lon1, double lon2) {
		double p = 0.017453292519943295;    // Math.PI / 180
		double a = 
	    		0.5 - Math.cos((lat2 - lat1) * p)/2 + 
	    		Math.cos(lat1 * p) * Math.cos(lat2 * p) * 
	    		(1 - Math.cos((lon2 - lon1) * p))/2;
		double km = 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = X km
	    
		return Math.floor(km * 100) / 100; //2 casas decimais
	}
}
