package com.rentas.springboot.backend.apirest.sistema.models;

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
@Table(name="sis_bancos")
public class Banco implements Serializable {
	
 	private static final long serialVersionUID = 5906521909064919615L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	 
	@Column(length = 3)
	private String bancocodigo;
	@Column(length = 20)
	private String bancodescripcion;
	@Column(length = 40)
	private String bancoabreviatura;
	@Column(length = 30)
	private String bersonacontacto;
	@Column(length = 1)
	private String conciliacionautomaticaflag;
	@Column(length = 1)
	private String conciliacionformatoflag;
	@Column(length = 1)
	private String formatopropioflag;
	@Column(length = 50)
	private String formatodatawindow;
	
	
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
