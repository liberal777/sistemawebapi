package com.rentas.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentas.springboot.backend.apirest.DTO.RegionDTO;
import com.rentas.springboot.backend.apirest.models.services.IRegionService;

@RestController
@RequestMapping("/jdbc")
public class RegionRestController {
 
	@Autowired
	IRegionService regionService;

	@GetMapping("/regionesdto")
	public List<RegionDTO> index(String token){
		return regionService.getRegiones(token);
	}
	 
	//@Secured("ROLE_ADMIN")
	@PostMapping("/regionesdto")
	public ResponseEntity<?> create(@Valid @RequestBody RegionDTO regionDTO, BindingResult result) {
		
		RegionDTO regionNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			regionNew = regionService.saveRegion(regionDTO);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La region ha sido creado con éxito!");
		response.put("region", regionNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//@Secured("ROLE_ADMIN")
	@PutMapping("/regionesdto")
	public ResponseEntity<?> update(@Valid @RequestBody RegionDTO regionDTO, BindingResult result) {
		
		RegionDTO regionNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			regionNew = regionService.updateRegion(regionDTO);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la actualizacion en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El region ha actualizada con éxito!");
		response.put("entidad", regionNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/regionesdto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {									
		    regionService.deleteRegion(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el region de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La region se ha eliminado con éxito!");		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
