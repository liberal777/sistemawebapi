package com.rentas.springboot.backend.apirest.logistica.service;

 
import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.rentas.springboot.backend.apirest.logistica.models.TipoPage;
import com.rentas.springboot.backend.apirest.logistica.models.TipoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Tipo;
import com.rentas.springboot.backend.apirest.logistica.repository.TipoCriteriaRepository;
import com.rentas.springboot.backend.apirest.logistica.repository.TipoRepository;

@Service
public class TipoService {

	private final TipoRepository tipoRepository;
	private final TipoCriteriaRepository tipoCriteriaRepository;

	public TipoService(TipoRepository tipoRepository,TipoCriteriaRepository tipoCriteriaRepository) {
		this.tipoRepository = tipoRepository;
		this.tipoCriteriaRepository = tipoCriteriaRepository;
	}
	
 	
	public Page<Tipo> getTipos(TipoPage tipoPage, TipoSearchCriteria tipoSearchCriteria) {
		return tipoCriteriaRepository.findAllWithFilters(tipoPage, tipoSearchCriteria);
	}

	@Transactional(readOnly = true)
	public Tipo findById(Long id) {
		return tipoRepository.findById(id).orElse(null);
	}

	@Transactional
	public Tipo save(Tipo tipo) {
		return tipoRepository.save(tipo);
	}

	@Transactional
	public void delete(Long id) {
		tipoRepository.deleteById(id);
	}  

}