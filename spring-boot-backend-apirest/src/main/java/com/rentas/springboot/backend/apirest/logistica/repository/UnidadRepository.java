package com.rentas.springboot.backend.apirest.logistica.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rentas.springboot.backend.apirest.logistica.models.TipoUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.Unidad;

public interface UnidadRepository extends JpaRepository<Unidad, Long> {
	Page<Unidad> findByUnidadmedidadescripcionContainingIgnoreCase(String term, Pageable pageable);
	 @Query("from TipoUnidad")
	 public List<TipoUnidad> findAllTipoUnidad();
	 
}
