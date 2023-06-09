package com.rentas.springboot.backend.apirest.logistica.service;
 

import java.util.List;

import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentas.springboot.backend.apirest.logistica.dto.SelectCategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectLinea;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.models.Categoria; 
import com.rentas.springboot.backend.apirest.logistica.models.CategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.CategoriaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.repository.CategoriaRepository;
import com.rentas.springboot.backend.apirest.logistica.repository.CategoriaCriteriaRepository; 


@Service
public class CategoriaService {

	private final CategoriaRepository categoriaRepository; 
	private final CategoriaCriteriaRepository categoriaCriteriaRepository;
	
	public CategoriaService(CategoriaRepository categoriaRepository,CategoriaCriteriaRepository categoriaCriteriaRepository ) {
		this.categoriaRepository = categoriaRepository;
		this.categoriaCriteriaRepository = categoriaCriteriaRepository;
	}
 	
	public Page<Categoria> getCategorias(CategoriaPage categoriaPage, CategoriaSearchCriteria categoriaSearchCriteria) {
		return categoriaCriteriaRepository.findAllWithFilters(categoriaPage, categoriaSearchCriteria);
	}
	 
	@Transactional(readOnly = true)
	public Categoria findById(Long id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Transactional
	public void delete(Long id) {
		categoriaRepository.deleteById(id);
	}  
 
	@Transactional(readOnly = true)
	public List<SelectLinea> findAllLineas() {
		return categoriaRepository.findAllLineas();
	}
	
	@Transactional(readOnly = true)
	public List<SelectCategoria> findByIdCategorias(Long linea_id ) {
		return categoriaRepository.findByIdCategorias(linea_id);
	}
	@Transactional(readOnly = true)
	public List<SelectSubcategoria> findByIdSubcategorias(Long linea_id,Long categoria_id) {
		return categoriaRepository.findByIdSubcategorias(linea_id,  categoria_id);
	}
}