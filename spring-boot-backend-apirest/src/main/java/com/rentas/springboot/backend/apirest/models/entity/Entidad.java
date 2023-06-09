package com.rentas.springboot.backend.apirest.models.entity;

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
@Table(name = "sis_entidad")
public class Entidad implements Serializable {
	
	private static final long serialVersionUID = 6438687148100244812L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 7, nullable = false)
	private String codigo;
	@Column(length = 11, nullable = false)
	private String ruc;
	@Column(length = 60)
	private String companiadescripcion;
	@Column(length = 80)
	private String companiadescripcioncompleta;
	@Column(length = 30)
	private String companiaabreviatura;
	@Column(length = 100)
	private String direccionfiscal;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String fechainicio;
	@Column(length = 15)
	private String telefono1;
	@Column(length = 15)
	private String telefono2;
	@Column(length = 3)
	private String monedapordefecto;
	@Column(length = 1)
	private String afectoigvflageliminar;
	@Column(length = 1)
	private String afectoretencionigvflag;
	@Column(length = 1)
	private String facturacionelectronicaflag;
	@Column(length = 100)
	private String paginaweb;
	@Column(length = 80)
	private String email;
	@Column(length = 20)
	private String certificadoinscripcion;
	@Column(length = 20)
	private String cuentabancodetraccion;
	@Column(nullable = true)
	private Integer partnerrepresentantelegalid;
	@Column(nullable = true)
	private Integer companiagrupoid;
	@Column(nullable = true)
	private Integer paisid;
	@Column(nullable = true)
	private Integer partnerrelacionadoid;
	@Column(nullable = true)
	private Integer empresaid;
	@Column(length = 100)
	private String logofile;

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
