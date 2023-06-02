package com.rentas.springboot.backend.apirest.logistica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.rentas.springboot.backend.apirest.logistica.dto.SelectMarca;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectMoneda;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectTipo;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.Productox;

public interface ProductoRepository extends JpaRepository<Productox, Long> {
	// @Query("select a from Producto a where a.descripcion like %?1%")
	// public List<Productox> findByDescripcion(String term);		
  
	@Query("select p.id as id,p.monedadescripcion as monedadescripcion from Moneda p ")
	public List<SelectMoneda> findAllMonedas();
	
	@Query("select p.id as id,p.marcadescripcion as marcadescripcion from Marca p ORDER BY p.marcadescripcion")
	public List<SelectMarca> findAllMarcas();
	
	@Query("select p.id as id,p.tipoproductodescripcion as tipoproductodescripcion from Tipo p ORDER BY p.tipoproductodescripcion")
	public List<SelectTipo> findAllTipos();
	
	@Query("select p.id as id,p.unidadmedidadescripcion as unidadmedidadescripcion,p.unidadmedidacodigo as unidadmedidacodigo from Unidad p ORDER BY p.unidadmedidadescripcion")
	public List<SelectUnidad> findAllUnidades();

	@Query("select p.id as id,p.subcategoriadescripcion as subcategoriadescripcion from Subcategoria p ORDER BY p.subcategoriadescripcion")
	public List<SelectSubcategoria> findAllSubcategorias();
	
	@Procedure
	String f_correlativo_producto();
}
