package com.rentas.springboot.backend.apirest.logistica.repository;

 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository; 

import com.rentas.springboot.backend.apirest.logistica.models.Linea;  

public interface LineaRepository extends JpaRepository<Linea, Long> {
	Page<Linea> findByLineadescripcionContainingIgnoreCase(String term, Pageable pageable);
	 
}
