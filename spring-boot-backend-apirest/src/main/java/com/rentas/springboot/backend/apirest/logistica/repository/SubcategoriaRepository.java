package com.rentas.springboot.backend.apirest.logistica.repository;

  
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.rentas.springboot.backend.apirest.logistica.models.Subcategoria; 

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
	 
}
