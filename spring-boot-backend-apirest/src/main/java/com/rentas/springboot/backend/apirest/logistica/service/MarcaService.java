package com.rentas.springboot.backend.apirest.logistica.service;

 
import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.rentas.springboot.backend.apirest.logistica.models.MarcaPage;
import com.rentas.springboot.backend.apirest.logistica.models.MarcaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Marca;
import com.rentas.springboot.backend.apirest.logistica.repository.MarcaCriteriaRepository;
import com.rentas.springboot.backend.apirest.logistica.repository.MarcaRepository;

@Service
public class MarcaService {

	private final MarcaRepository marcaRepository;
	private final MarcaCriteriaRepository marcaCriteriaRepository;

	public MarcaService(MarcaRepository marcaRepository,MarcaCriteriaRepository marcaCriteriaRepository) {
		this.marcaRepository = marcaRepository;
		this.marcaCriteriaRepository = marcaCriteriaRepository;
	}
	
 	
	public Page<Marca> getMarcas(MarcaPage marcaPage, MarcaSearchCriteria marcaSearchCriteria) {
		return marcaCriteriaRepository.findAllWithFilters(marcaPage, marcaSearchCriteria);
	}

	@Transactional(readOnly = true)
	public Marca findById(Long id) {
		return marcaRepository.findById(id).orElse(null);
	}

	@Transactional
	public Marca save(Marca marca) {
		return marcaRepository.save(marca);
	}

	@Transactional
	public void delete(Long id) {
		marcaRepository.deleteById(id);
	}  

}