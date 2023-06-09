package com.rentas.springboot.backend.apirest.logistica.controllers;

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

import com.rentas.springboot.backend.apirest.logistica.models.Subcategoria;
import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.SubcategoriaSearchCriteria; 
import com.rentas.springboot.backend.apirest.logistica.service.SubcategoriaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class SubcategoriaRestController {

	private final SubcategoriaService subcategoriaService;

	public SubcategoriaRestController(SubcategoriaService subcategoriaService) {
		this.subcategoriaService = subcategoriaService;
	}

	@GetMapping("/subcategorias/pagina")
	public ResponseEntity<Page<Subcategoria>> getSubcategorias(SubcategoriaPage subcategoriaPage,SubcategoriaSearchCriteria subcategoriaSearchCriteria) {
		return new ResponseEntity<>(subcategoriaService.getSubcategorias(subcategoriaPage, subcategoriaSearchCriteria), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/subcategorias/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Subcategoria subcategoria = null;
		Map<String, Object> response = new HashMap<>();
		try {
			subcategoria = subcategoriaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (subcategoria == null) {
			response.put("mensaje", "El subcategoria ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Subcategoria>(subcategoria, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/subcategorias")
	public ResponseEntity<?> create(@Valid @RequestBody Subcategoria subcategoria, BindingResult result) {

		Subcategoria subcategoriaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {			 
			subcategoria.setCreationdate( LocalDateTime.now());			
			subcategoria.setLastdate( LocalDateTime.now());
			subcategoriaNew = subcategoriaService.save(subcategoria);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El subcategoria ha sido creado con éxito!");
		response.put("subcategoria", subcategoriaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/subcategorias/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Subcategoria subcategoria, BindingResult result,
			@PathVariable Long id) {

		Subcategoria subcategoriaActual = subcategoriaService.findById(id);
		Subcategoria subcategoriaUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (subcategoriaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el subcategoria ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
						
			subcategoriaActual.setSubcategoriacodigo(subcategoria.getSubcategoriacodigo());	
			subcategoriaActual.setSubcategoriadescripcion(subcategoria.getSubcategoriadescripcion());
			subcategoriaActual.setSubcategoriaabreviatura(subcategoria.getSubcategoriaabreviatura());
			
			subcategoriaActual.setEstado(subcategoria.getEstado()); 
			subcategoriaActual.setLastuser(subcategoria.getLastuser());
			subcategoriaActual.setLastdate( LocalDateTime.now());		
			subcategoriaUpdated = subcategoriaService.save(subcategoriaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el subcategoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El subcategoria ha sido actualizado con éxito!");
		response.put("subcategoria", subcategoriaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
 /*
	@GetMapping("/subcategorias/select-tiposubcategorias")
	public List<TipoSubcategoria> listarTipoSubcategoriaa() {
		return subcategoriaService.findAllTipoSubcategoria();
	}
 */
  
}
