package com.rentas.springboot.backend.apirest.logistica.service;

 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.rentas.springboot.backend.apirest.logistica.models.Linea;
import com.rentas.springboot.backend.apirest.logistica.repository.LineaRepository;


@Service
public class LineaService {

	private final LineaRepository lineaRepository; 

	public LineaService(LineaRepository lineaRepository) {
		this.lineaRepository = lineaRepository;
	}
	 
	@Transactional(readOnly = true)
	public Page<Linea> findAll(Pageable pageable) {
		return lineaRepository.findAll(pageable);
	}
	
	  
	@Transactional(readOnly = true)
	public Page<Linea> findByDescripcion(String term,Pageable pageable) {
		return lineaRepository.findByLineadescripcionContainingIgnoreCase(term,pageable);
	}
	
	 
	@Transactional(readOnly = true)
	public Linea findById(Long id) {
		return lineaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Linea save(Linea linea) {
		return lineaRepository.save(linea);
	}

	@Transactional
	public void delete(Long id) {
		lineaRepository.deleteById(id);
	}  
 
 
 
}