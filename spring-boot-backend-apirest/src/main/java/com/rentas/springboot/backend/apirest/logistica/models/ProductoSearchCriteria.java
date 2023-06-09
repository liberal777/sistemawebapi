package com.rentas.springboot.backend.apirest.logistica.models;

public class ProductoSearchCriteria {
	private String productocodigo;
    private String descripcion;
    private String estado;
    private Integer marcaid;
    private Integer tipoproductoid;
    private Integer lineaid;
    private Integer categoriaid;
    private Integer subcategoriaid;
    
   
	public String getProductocodigo() {
		return productocodigo;
	}
	public void setProductocodigo(String productocodigo) {
		this.productocodigo = productocodigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	 
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getMarcaid() {
		return marcaid;
	}
	public void setMarcaid(Integer marcaid) {
		this.marcaid = marcaid;
	}
	public Integer getTipoproductoid() {
		return tipoproductoid;
	}
	public void setTipoproductoid(Integer tipoproductoid) {
		this.tipoproductoid = tipoproductoid;
	}
	
	
	public Integer getLineaid() {
		return lineaid;
	}
	public void setLineaid(Integer lineaid) {
		this.lineaid = lineaid;
	}
	public Integer getCategoriaid() {
		return categoriaid;
	}
	public void setCategoriaid(Integer categoriaid) {
		this.categoriaid = categoriaid;
	}
	public Integer getSubcategoriaid() {
		return subcategoriaid;
	}
	public void setSubcategoriaid(Integer subcategoriaid) {
		this.subcategoriaid = subcategoriaid;
	}
 
	
    
	 
	 
	 
	 

   
}
