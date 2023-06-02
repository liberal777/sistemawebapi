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
@Table(name = "sis_moneda")
public class Moneda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 3, nullable = false, unique = false)
	private String monedacodigo;
	@Column(length = 20, nullable = true)
	private String monedadescripcion;
	@Column(length = 4, nullable = true)
	private String monedasigla;
	@Column(length = 1, nullable = true)
	private String monedaprincipalflag;
	@Column(length = 3, nullable = true)
	private String codigointernacional;
	@Column(length = 1, nullable = true)
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
