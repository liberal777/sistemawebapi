package com.rentas.springboot.backend.apirest.logistica.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Entity 
@Table(name="lg_subcategoria")
public class Subcategoria implements Serializable {
   
	private static final long serialVersionUID = -191073220962691752L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	 
	@Column(length = 6,nullable = false)
	private String subcategoriacodigo;
		
	@Column(length = 50)
	private String subcategoriadescripcion;
	@Column(length = 50)
	private String subcategoriaabreviatura; 
	
	@NotNull(message = "la categoria no puede ser vacia")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	private Categoria categoriaid; 	

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
  
 
