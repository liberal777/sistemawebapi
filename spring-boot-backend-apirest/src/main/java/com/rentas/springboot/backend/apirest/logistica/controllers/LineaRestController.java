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

import com.rentas.springboot.backend.apirest.logistica.models.Linea;
import com.rentas.springboot.backend.apirest.logistica.service.LineaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class LineaRestController {

	private final LineaService lineaService;

	public LineaRestController(LineaService lineaService) {
		this.lineaService = lineaService;
	}
	@GetMapping("/lineas/page/{page}")
	public Page<Linea> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("id").ascending());
		return lineaService.findAll(pageable);
	}
	
	@GetMapping("/lineas/page/{page}/{data}")
	public Page<Linea> index(@PathVariable Integer page,@PathVariable String data) {		
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by("id").ascending());
		return lineaService.findByDescripcion(data, pageable);
	}
	

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/lineas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Linea linea = null;
		Map<String, Object> response = new HashMap<>();
		try {
			linea = lineaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (linea == null) {
			response.put("mensaje", "El linea ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Linea>(linea, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/lineas")
	public ResponseEntity<?> create(@Valid @RequestBody Linea linea, BindingResult result) {

		Linea lineaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {			 
			linea.setCreationdate( LocalDateTime.now());			
			linea.setLastdate( LocalDateTime.now());
			lineaNew = lineaService.save(linea);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El linea ha sido creado con éxito!");
		response.put("linea", lineaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/lineas/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Linea linea, BindingResult result,
			@PathVariable Long id) {

		Linea lineaActual = lineaService.findById(id);
		Linea lineaUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (lineaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el linea ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
						
	 
			lineaActual.setLineacodigo(linea.getLineacodigo());	
			lineaActual.setLineadescripcion(linea.getLineadescripcion());
			lineaActual.setLineaabreviatura(linea.getLineaabreviatura());
			lineaActual.setEstado(linea.getEstado()); 
			lineaActual.setLastuser(linea.getLastuser());
			lineaActual.setLastdate( LocalDateTime.now());		
			lineaUpdated = lineaService.save(lineaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el linea en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El linea ha sido actualizado con éxito!");
		response.put("linea", lineaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
 /*
	@GetMapping("/lineas/select-tipolineas")
	public List<TipoLinea> listarTipoLineaa() {
		return lineaService.findAllTipoLinea();
	}
 */
  
}
