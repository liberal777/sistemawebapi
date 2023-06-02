package com.rentas.springboot.backend.apirest.logistica.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentas.springboot.backend.apirest.logistica.models.TipoUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.Unidad;
import com.rentas.springboot.backend.apirest.logistica.repository.UnidadRepository;


@Service
public class UnidadService {

	private final UnidadRepository unidadRepository; 

	public UnidadService(UnidadRepository unidadRepository) {
		this.unidadRepository = unidadRepository;
	}
	 
	@Transactional(readOnly = true)
	public Page<Unidad> findAll(Pageable pageable) {
		return unidadRepository.findAll(pageable);
	}
	
	  
	@Transactional(readOnly = true)
	public Page<Unidad> findByDescripcion(String term,Pageable pageable) {
		return unidadRepository.findByUnidadmedidadescripcionContainingIgnoreCase(term,pageable);
	}
	
	 
	@Transactional(readOnly = true)
	public Unidad findById(Long id) {
		return unidadRepository.findById(id).orElse(null);
	}

	@Transactional
	public Unidad save(Unidad unidad) {
		return unidadRepository.save(unidad);
	}

	@Transactional
	public void delete(Long id) {
		unidadRepository.deleteById(id);
	}  
 
	@Transactional(readOnly = true)
	public List<TipoUnidad> findAllTipoUnidad() {
		return unidadRepository.findAllTipoUnidad();
	}
 
}