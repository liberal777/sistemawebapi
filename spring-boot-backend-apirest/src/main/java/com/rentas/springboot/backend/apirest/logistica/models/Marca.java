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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity 
@Table(name="lg_productomarca")
public class Marca implements Serializable {
	
 
	private static final long serialVersionUID = 4825450513900163194L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(length = 50)
	private String marcadescripcion;
	@Column(length = 30)
	private String marcaabreviatura;
	@Column(length = 1)
	private String estado;
	@Column(length = 20, nullable = true)
	private Integer partnerid;
	@Column(length = 20, nullable = false)
	private String creationuser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationdate;
	@Column(length = 20, nullable = false)
	private String lastuser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastdate;

	

}
