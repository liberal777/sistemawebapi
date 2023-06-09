package com.rentas.springboot.backend.apirest.logistica.service;

 

import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentas.springboot.backend.apirest.logistica.models.Subcategoria;
import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaSearchCriteria; 
import com.rentas.springboot.backend.apirest.logistica.repository.SubcategoriaCriteriaRepository;
import com.rentas.springboot.backend.apirest.logistica.repository.SubcategoriaRepository; 


@Service
public class SubcategoriaService {

	private final SubcategoriaRepository subcategoriaRepository; 
	private final SubcategoriaCriteriaRepository subcategoriaCriteriaRepository;
	
	public SubcategoriaService(SubcategoriaRepository subcategoriaRepository,SubcategoriaCriteriaRepository subcategoriaCriteriaRepository ) {
		this.subcategoriaRepository = subcategoriaRepository;
		this.subcategoriaCriteriaRepository = subcategoriaCriteriaRepository;
	}
 	
	public Page<Subcategoria> getSubcategorias(SubcategoriaPage subcategoriaPage, SubcategoriaSearchCriteria subcategoriaSearchCriteria) {
		return subcategoriaCriteriaRepository.findAllWithFilters(subcategoriaPage, subcategoriaSearchCriteria);
	}
	 
	@Transactional(readOnly = true)
	public Subcategoria findById(Long id) {
		return subcategoriaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Subcategoria save(Subcategoria subcategoria) {
		return subcategoriaRepository.save(subcategoria);
	}

	@Transactional
	public void delete(Long id) {
		subcategoriaRepository.deleteById(id);
	}  
 
 
 
}