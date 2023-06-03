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
@Table(name="lg_categoria")
public class Categoria implements Serializable {
  
	private static final long serialVersionUID = 5368006744839683776L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(length = 6,nullable = false)
	private String categoriacodigo;
	
	@Column(length = 50)
	private String categoriadescripcion;
	@Column(length = 50)
	private String categoriaabreviatura;
	
	@NotNull(message = "la linea no puede ser vacia")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "linea_id")
	private Linea lineaid;
	 
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
 
