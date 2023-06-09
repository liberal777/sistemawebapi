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

import com.rentas.springboot.backend.apirest.logistica.dto.SelectCategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectLinea;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.models.Categoria; 
import com.rentas.springboot.backend.apirest.logistica.models.CategoriaPage;
import com.rentas.springboot.backend.apirest.logistica.models.CategoriaSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.service.CategoriaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
public class CategoriaRestController {

	private final CategoriaService categoriaService;

	public CategoriaRestController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@GetMapping("/categorias/pagina")
	public ResponseEntity<Page<Categoria>> getCategoria(CategoriaPage categoriaPage,CategoriaSearchCriteria categoriaSearchCriteria) {
		return new ResponseEntity<>(categoriaService.getCategorias(categoriaPage, categoriaSearchCriteria), HttpStatus.OK);
	}


	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/categorias/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Categoria categoria = null;
		Map<String, Object> response = new HashMap<>();
		try {
			categoria = categoriaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (categoria == null) {
			response.put("mensaje", "El categoria ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/categorias")
	public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult result) {

		Categoria categoriaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) { // valida los campos del fomulario

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {			 
			categoria.setCreationdate( LocalDateTime.now());			
			categoria.setLastdate( LocalDateTime.now());
			categoriaNew = categoriaService.save(categoria);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El categoria ha sido creado con éxito!");
		response.put("categoria", categoriaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/categorias/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult result,
			@PathVariable Long id) {

		Categoria categoriaActual = categoriaService.findById(id);
		Categoria categoriaUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (categoriaActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el categoria ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
						
			categoriaActual.setCategoriacodigo(categoria.getCategoriacodigo());	
			categoriaActual.setCategoriadescripcion(categoria.getCategoriadescripcion());
			categoriaActual.setCategoriaabreviatura(categoria.getCategoriaabreviatura());
			
			categoriaActual.setEstado(categoria.getEstado()); 
			categoriaActual.setLastuser(categoria.getLastuser());
			categoriaActual.setLastdate( LocalDateTime.now());		
			categoriaUpdated = categoriaService.save(categoriaActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el categoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El categoria ha sido actualizado con éxito!");
		response.put("categoria", categoriaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	//@Secured("ROLE_ADMIN")
	@GetMapping("/categorias/select-lineas")
	public List<SelectLinea> listarLineas() {
		return categoriaService.findAllLineas();
	}
 
	@GetMapping("/categorias/select-categorias/{linea_id}")
	public List<SelectCategoria> listarCategorias(@PathVariable Long linea_id) {
		return categoriaService.findByIdCategorias(linea_id);
	}
 
	@GetMapping("/categorias/select-subcategorias/{linea_id}/{categoria_id}")
	public List<SelectSubcategoria> listarSubcategorias(@PathVariable Long linea_id,@PathVariable Long categoria_id) {
		return categoriaService.findByIdSubcategorias(linea_id,categoria_id);
	}
  
}
