package com.rentas.springboot.backend.apirest.logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rentas.springboot.backend.apirest.logistica.models.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
	 
}
