package com.rentas.springboot.backend.apirest.logistica.repository;

  
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rentas.springboot.backend.apirest.logistica.dto.SelectCategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectLinea;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.models.Categoria;  
public interface CategoriaRepository extends JpaRepository<Categoria, Long> { 
	// @Query("select a from Producto a where a.descripcion like %?1%")
	// public List<Productox> findByDescripcion(String term);	
	@Query("select p.id as id,p.lineadescripcion as lineadescripcion from Linea p ORDER BY p.lineadescripcion")
	public List<SelectLinea> findAllLineas(); 
	
	@Query("select p.id as id,p.categoriadescripcion as categoriadescripcion from Categoria p  where p.lineaid.id= ?1 ORDER BY p.categoriadescripcion")
	public List<SelectCategoria> findByIdCategorias(Long linea_id); 
	
	@Query("select p.id as id,p.subcategoriadescripcion as subcategoriadescripcion from Subcategoria p  where p.categoriaid.lineaid.id= ?1 and p.categoriaid.id= ?2 ORDER BY p.subcategoriadescripcion")
	public List<SelectSubcategoria> findByIdSubcategorias(Long linea_id,Long categoria_id); 
	
	
}
