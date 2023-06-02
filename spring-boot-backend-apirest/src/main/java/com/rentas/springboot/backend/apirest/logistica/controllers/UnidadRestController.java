package com.rentas.springboot.backend.apirest.logistica.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentas.springboot.backend.apirest.logistica.models.TipoUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.Unidad;
import com.rentas.springboot.backend.apirest.logistica.service.UnidadService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class UnidadRestController {

	private final UnidadService unidadService;

	public UnidadRestController(UnidadService unidadService) {
		this.unidadService = unidadService;
	}
	@GetMapping("/unidades/page/{page}")
	public Page<Unidad> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("id").ascending());
		return unidadService.findAll(pageable);
	}
	
	@GetMapping("/unidades/page/{page}/{data}")
	public Page<Unidad> index(@PathVariable Integer page,@PathVariable String data) {		
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("id").ascending());
		return unidadService.findByDescripcion(data, pageable);
	}
	

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/unidades/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Unidad unidad = null;
		Map<String, Object> response = new HashMap<>();
		try {
			unidad = unidadService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (unidad == null) {
			response.put("mensaje", "El unidad ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Unidad>(unidad, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/unidades")
	public ResponseEntity<?> create(@Valid @RequestBody Unidad unidad, BindingResult result) {

		Unidad unidadNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {	
			 
			unidad.setUnidadmedidacodigo(unidad.getUnidadmedidacodigo().toUpperCase());	
			unidad.setUnidadmedidaabreviatura(unidad.getUnidadmedidaabreviatura().toUpperCase());
			unidad.setUnidadmedidatipo(unidad.getUnidadmedidatipo());	
			unidad.setCreationdate( LocalDateTime.now());			
			unidad.setLastdate( LocalDateTime.now());
			unidadNew = unidadService.save(unidad);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El unidad ha sido creado con éxito!");
		response.put("unidad", unidadNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/unidades/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Unidad unidad, BindingResult result,
			@PathVariable Long id) {

		Unidad unidadActual = unidadService.findById(id);
		Unidad unidadUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (unidadActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el unidad ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
						
			unidadActual.setUnidadmedidacodigo(unidad.getUnidadmedidacodigo().toUpperCase());	
			unidadActual.setUnidadmedidaabreviatura(unidad.getUnidadmedidaabreviatura().toUpperCase());
			unidadActual.setUnidadmedidatipo(unidad.getUnidadmedidatipo());	
			unidadActual.setEstado(unidad.getEstado()); 
			unidadActual.setLastuser(unidad.getLastuser());
			unidadActual.setLastdate( LocalDateTime.now());		
			unidadUpdated = unidadService.save(unidadActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el unidad en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El unidad ha sido actualizado con éxito!");
		response.put("unidad", unidadUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
 
	@GetMapping("/unidades/select-tipounidades")
	public List<TipoUnidad> listarTipoUnidada() {
		return unidadService.findAllTipoUnidad();
	}
 
 
	/*
	// @Secured("ROLE_ADMIN")
		@GetMapping("/unidades/select-tipounidades")
		public List<TipoUnidad> listarTipoUnidad() {
			List<TipoUnidad> rubro = new ArrayList<>();
			rubro.add(new TipoUnidad("P", "Peso"));
			rubro.add(new TipoUnidad("T", "Tiempo"));
			rubro.add(new TipoUnidad("V", "Volumen"));
			rubro.add(new TipoUnidad("M", "Medida"));
			rubro.add(new TipoUnidad("C", "Tecnica"));
			rubro.add(new TipoUnidad("", "Ninguna"));
			return rubro;
		}
 */
}
