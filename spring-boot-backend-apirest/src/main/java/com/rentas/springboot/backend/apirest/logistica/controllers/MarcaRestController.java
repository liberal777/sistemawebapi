package com.rentas.springboot.backend.apirest.logistica.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
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

import com.rentas.springboot.backend.apirest.logistica.models.MarcaPage;
import com.rentas.springboot.backend.apirest.logistica.models.MarcaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Marca;
import com.rentas.springboot.backend.apirest.logistica.service.MarcaService;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class MarcaRestController {

	private final MarcaService marcaService;

	public MarcaRestController(MarcaService marcaService) {
		this.marcaService = marcaService;
	}
 
	@GetMapping("/marcaproductos/pagina")
	public ResponseEntity<Page<Marca>> getMarcas(MarcaPage marcaPage,MarcaSearchCriteria marcaSearchCriteria) {
		return new ResponseEntity<>(marcaService.getMarcas(marcaPage, marcaSearchCriteria), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/marcaproductos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Marca marca = null;
		Map<String, Object> response = new HashMap<>();
		try {
			marca = marcaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (marca == null) {
			response.put("mensaje", "El marca ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Marca>(marca, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/marcaproductos")
	public ResponseEntity<?> create(@Valid @RequestBody Marca marca, BindingResult result) {

		Marca marcaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {			
			marca.setMarcaabreviatura(marca.getMarcaabreviatura().toUpperCase());			
			marca.setCreationdate( LocalDateTime.now());			
			marca.setLastdate( LocalDateTime.now());
			marcaNew = marcaService.save(marca);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El marca ha sido creado con éxito!");
		response.put("marca", marcaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/marcaproductos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Marca marca, BindingResult result,
			@PathVariable Long id) {

		Marca marcaActual = marcaService.findById(id);
		Marca marcaUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (marcaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el marca ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
						
			marcaActual.setMarcadescripcion(marca.getMarcadescripcion().toUpperCase());
			marcaActual.setMarcaabreviatura(marca.getMarcaabreviatura().toUpperCase());
			marcaActual.setEstado(marca.getEstado()); 
			marcaActual.setLastuser(marca.getLastuser());
			marcaActual.setLastdate( LocalDateTime.now());		
			marcaUpdated = marcaService.save(marcaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el marca en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El marca ha sido actualizado con éxito!");
		response.put("marca", marcaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	public String fecha()
	{
	    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	    long miliseconds = System.currentTimeMillis();
	    java.sql.Date date = new Date(miliseconds);
	    String dateFormateada = formato.format(date);
	    return dateFormateada;
	}
 
}
