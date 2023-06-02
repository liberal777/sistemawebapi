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
@Table(name="sis_unidad")
public class Unidad implements Serializable {
	 
	private static final long serialVersionUID = -2034837897067650841L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 3, nullable = false)
	private String unidadmedidacodigo;
	@Column(length = 20, nullable = false)
	private String unidadmedidadescripcion;
	@Column(length = 40)
	private String unidadmedidaabreviatura; 
		
	@NotNull(message = "tipo unidad no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipounidad_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoUnidad unidadmedidatipo;
	
	//@Column(nullable = true)
	//private Integer tipounidad_id;
		
	@Column(nullable = true)
	private Integer numerodecimales;
	@Column(length = 3)
	private String codigofiscal;

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
