package com.rentas.springboot.backend.apirest.logistica.models;

public class SubcategoriaSearchCriteria {
	private String subcategoriacodigo;
    private String subcategoriadescripcion;
    private String estado;
    private Integer categoriaid;
    
	public String getSubcategoriacodigo() {
		return subcategoriacodigo;
	}
	public void setSubcategoriacodigo(String subcategoriacodigo) {
		this.subcategoriacodigo = subcategoriacodigo;
	}
	public String getSubcategoriadescripcion() {
		return subcategoriadescripcion;
	}
	public void setSubcategoriadescripcion(String subcategoriadescripcion) {
		this.subcategoriadescripcion = subcategoriadescripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getCategoriaid() {
		return categoriaid;
	}
	public void setCategoriaid(Integer categoriaid) {
		this.categoriaid = categoriaid;
	}
 
      
 
   
}
