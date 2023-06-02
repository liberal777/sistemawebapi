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

import com.rentas.springboot.backend.apirest.logistica.models.TipoPage;
import com.rentas.springboot.backend.apirest.logistica.models.TipoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Tipo;
import com.rentas.springboot.backend.apirest.logistica.service.TipoService;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class TipoRestController {

	private final TipoService tipoService;

	public TipoRestController(TipoService tipoService) {
		this.tipoService = tipoService;
	}
 
	@GetMapping("/tipoproductos/pagina")
	public ResponseEntity<Page<Tipo>> getTipos(TipoPage tipoPage,TipoSearchCriteria tipoSearchCriteria) {
		return new ResponseEntity<>(tipoService.getTipos(tipoPage, tipoSearchCriteria), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/tipoproductos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Tipo tipo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			tipo = tipoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tipo == null) {
			response.put("mensaje", "El tipo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tipo>(tipo, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/tipoproductos")
	public ResponseEntity<?> create(@Valid @RequestBody Tipo tipo, BindingResult result) {

		Tipo tipoNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			tipo.setTipoproductocodigo(tipo.getTipoproductocodigo().toUpperCase());
			tipo.setTipoproductoabreviatura(tipo.getTipoproductoabreviatura().toUpperCase());			
			tipo.setCreationdate( LocalDateTime.now());
			tipo.setLastdate( LocalDateTime.now());
			tipoNew = tipoService.save(tipo);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tipo ha sido creado con éxito!");
		response.put("tipo", tipoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/tipoproductos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Tipo tipo, BindingResult result,
			@PathVariable Long id) {

		Tipo tipoActual = tipoService.findById(id);
		Tipo tipoUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (tipoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			tipoActual.setTipoproductocodigo(tipo.getTipoproductocodigo().toUpperCase());
			tipoActual.setTipoproductodescripcion(tipo.getTipoproductodescripcion());
			tipoActual.setTipoproductoabreviatura(tipo.getTipoproductoabreviatura().toUpperCase());
			tipoActual.setEstado(tipo.getEstado()); 
			tipoActual.setLastuser(tipo.getLastuser());
			tipoActual.setLastdate( LocalDateTime.now());			

			tipoUpdated = tipoService.save(tipoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El tipo ha sido actualizado con éxito!");
		response.put("tipo", tipoUpdated);

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
