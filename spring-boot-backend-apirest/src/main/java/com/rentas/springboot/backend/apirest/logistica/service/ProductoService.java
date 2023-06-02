package com.rentas.springboot.backend.apirest.logistica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentas.springboot.backend.apirest.logistica.dto.SelectMarca;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectMoneda;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectTipo;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.ProductoPage;
import com.rentas.springboot.backend.apirest.logistica.models.ProductoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Productox;
import com.rentas.springboot.backend.apirest.logistica.repository.ProductoCriteriaRepository;
import com.rentas.springboot.backend.apirest.logistica.repository.ProductoRepository;

@Service
public class ProductoService {

	private final ProductoRepository productoRepository;
	private final ProductoCriteriaRepository productoCriteriaRepository;

	public ProductoService(ProductoRepository productoRepository,ProductoCriteriaRepository productoCriteriaRepository) {
		this.productoRepository = productoRepository;
		this.productoCriteriaRepository = productoCriteriaRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Productox> findAll() {
		return (List<Productox>) productoRepository.findAll();
	}
	

	@Transactional(readOnly = true)
	public Page<Productox> findAll(Pageable pageable) {
		return productoRepository.findAll(pageable);
	}
	
	public Page<Productox> getProductos(ProductoPage productoPage, ProductoSearchCriteria productoSearchCriteria) {
		return productoCriteriaRepository.findAllWithFilters(productoPage, productoSearchCriteria);
	}

	@Transactional(readOnly = true)
	public Productox findById(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	@Transactional
	public Productox save(Productox producto) {
		return productoRepository.save(producto);
	}

	@Transactional
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<SelectMoneda> findAllMonedas() {
		return productoRepository.findAllMonedas();
	}
	
	@Transactional(readOnly = true)
	public List<SelectTipo> findAllTipos() {
		return productoRepository.findAllTipos();
	}
	
	@Transactional(readOnly = true)
	public List<SelectMarca> findAllMarcas() {
		return productoRepository.findAllMarcas();
	}
	
	@Transactional(readOnly = true)
	public List<SelectUnidad> findAllUnidades() {
		return productoRepository.findAllUnidades();
	}
	
	@Transactional(readOnly = true)
	public List<SelectSubcategoria> findAllSubcategorias() {
		return productoRepository.findAllSubcategorias();
	}

	@Transactional
	public String correlativo() {
		return productoRepository.f_correlativo_producto();
	}

}