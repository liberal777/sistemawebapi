package com.rentas.springboot.backend.apirest.logistica.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table; 

import com.fasterxml.jackson.annotation.JsonFormat; 

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Entity 
@Table(name="lg_linea")
public class Linea implements Serializable {
  
	private static final long serialVersionUID = -2572173151487230198L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	 
	@Column(length = 6,nullable = false)
	private String lineacodigo;
	@Column(length = 30)
	private String lineadescripcion;
	@Column(length = 30)
	private String lineaabreviatura; 		
	@Column(length = 4)
	private String claseproducto;
	@Column(nullable = true)
	private Integer gourmetorden; 
	@Column(length = 100)
	private String gourmeticono;
	@Column(length = 1)
	private String estado;
	@Column(length = 20, nullable = false)
	private String creationuser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationdate;
	@Column(length = 20, nullable = false)
	private String lastuser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastdate;
} 
 