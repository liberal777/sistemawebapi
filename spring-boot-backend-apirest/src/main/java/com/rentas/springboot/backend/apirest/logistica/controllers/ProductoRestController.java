package com.rentas.springboot.backend.apirest.logistica.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentas.springboot.backend.apirest.logistica.dto.SelectMarca;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectMoneda;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectSubcategoria;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectTipo;
import com.rentas.springboot.backend.apirest.logistica.dto.SelectUnidad;
import com.rentas.springboot.backend.apirest.logistica.models.ProductoPage;
import com.rentas.springboot.backend.apirest.logistica.models.ProductoSearchCriteria;
import com.rentas.springboot.backend.apirest.logistica.models.Productox;
import com.rentas.springboot.backend.apirest.logistica.service.ProductoService; 

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
 

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "techgeeknext-api")
@Slf4j
public class ProductoRestController {
	
	/* CONFIGURACION DEL SWAGGER OPEN API= http://localhost:8080/swagger-ui/index.html
	y se pega esta url en el explorer= 	/v3/api-docs  */
	private final ProductoService productoService;

	public ProductoRestController(ProductoService productoService) {
		this.productoService = productoService;
	}
  
	@GetMapping("/productos/pagina")
	public ResponseEntity<Page<Productox>> getProductos(ProductoPage productoPage,ProductoSearchCriteria productoSearchCriteria) {
		return new ResponseEntity<>(productoService.getProductos(productoPage, productoSearchCriteria), HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Productox producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = productoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("mensaje", "El producto ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Productox>(producto, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/productos")
	public ResponseEntity<?> create(@Valid @RequestBody Productox producto, BindingResult result) {
		Productox productoNew = null;
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			//con esto elimino los campos repetidos
			Set<String> errNoDoble = new HashSet<>(errors);
			response.put("errors", errNoDoble );
			log.error("descripcion",producto.getDescripcion());
			// log.error("", String.join(";", errors));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			producto.setProductocodigo(productoService.correlativo());
			producto.setDescripcion(producto.getDescripcion());
			producto.setProductoabreviatura(producto.getProductoabreviatura());
			producto.setProductodetalles(producto.getProductoabreviatura());	
			producto.setCreationdate( LocalDateTime.now());			
			producto.setLastdate( LocalDateTime.now());			
			productoNew = productoService.save(producto);
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido creado con éxito!");
		response.put("producto", productoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/productos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Productox producto, BindingResult result,
			@PathVariable Long id) {
		Productox productoActual = productoService.findById(id);
		Productox productoUpdated = null;
		Map<String, Object> response = new HashMap<>();
		log.info("hola");
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo "  + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	 
		if (productoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el producto ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			productoActual.setClaseproducto(producto.getClaseproducto());
			productoActual.setProductocodigo(producto.getProductocodigo());
			productoActual.setDescripcion(producto.getProductoabreviatura());
			productoActual.setProductoabreviatura(producto.getProductoabreviatura());			
			productoActual.setProductodetalles(producto.getProductoabreviatura());

			productoActual.setEspecificaciontecnica(producto.getEspecificaciontecnica());
			productoActual.setCodigointerno(producto.getCodigointerno());
			productoActual.setCodigobarras(producto.getCodigobarras());
			productoActual.setCodigobarrasfabricante(producto.getCodigobarrasfabricante());

			productoActual.setModelofabricante(producto.getModelofabricante());
			productoActual.setMonedacodigo(producto.getMonedacodigo());
			
			productoActual.setTipoproductoid(producto.getTipoproductoid());
			productoActual.setUnidadmedidaid(producto.getUnidadmedidaid());
			
			productoActual.setMarcaid(producto.getMarcaid());
			productoActual.setEstado(producto.getEstado());

			productoActual.setSubcategoriaid(producto.getSubcategoriaid());
			productoUpdated = productoService.save(productoActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto ha sido actualizado con éxito!");
		response.put("producto", productoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<?> anular(@PathVariable Long id) {

		Productox productoActual = productoService.findById(id);

		Productox productoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (productoActual == null) {
			response.put("mensaje", "Error: no se pudo anular, el producto ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			if (productoActual.getEstado()=="A")  
				productoActual.setEstado("I");
			else
				productoActual.setEstado("A");
			
			productoUpdated = productoService.save(productoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto ha sido anulado con éxito!");
		response.put("producto", productoUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/productos/select-monedas")
	public List<SelectMoneda> listarMonedas() {
		return productoService.findAllMonedas();
	}
		
	@Secured("ROLE_ADMIN")
	@GetMapping("/productos/select-tipos")
	public List<SelectTipo> listarTipos() {
		return productoService.findAllTipos();
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/productos/select-marcas")
	public List<SelectMarca> listarMarcas() {
		return productoService.findAllMarcas();
	}
	
	//@Secured("ROLE_ADMIN")
	@GetMapping("/productos/select-unidades")
	public List<SelectUnidad> listarUnidades() {
		return productoService.findAllUnidades();
	}
	
	//@Secured("ROLE_ADMIN")
	@GetMapping("/productos/select-subcategorias")
	public List<SelectSubcategoria> listarSubcategorias() {
		return productoService.findAllSubcategorias();
	}
	
}
