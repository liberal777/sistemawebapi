package com.rentas.springboot.backend.apirest.logistica.models;

public class CategoriaSearchCriteria {
	private String categoriacodigo;
    private String categoriadescripcion;
    private String estado;
    private Integer lineaid;
    
	public String getCategoriacodigo() {
		return categoriacodigo;
	}
	public void setCategoriacodigo(String categoriacodigo) {
		this.categoriacodigo = categoriacodigo;
	}
	public String getCategoriadescripcion() {
		return categoriadescripcion;
	}
	public void setCategoriadescripcion(String categoriadescripcion) {
		this.categoriadescripcion = categoriadescripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getLineaid() {
		return lineaid;
	}
	public void setLineaid(Integer lineaid) {
		this.lineaid = lineaid;
	}
 
	
	 
   
}
