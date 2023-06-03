package com.rentas.springboot.backend.apirest.sistema.models;

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
@Table(name="sis_centrocostoclasificaciones")
  
public class Centrocostoclasificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//centrocostoclasificacionid int not null,
	
			
			
			
		@Column(length = 4,nullable = false)
		public String centrocostoclasificacioncodigo;
			//centrocostoclasificacioncodigo char(4) not null,
			
			
			
		@Column(length = 80,nullable = true)
		public String centrocostoclasificaciondescripcion;
			//centrocostoclasificaciondescripcion varchar(80) null,
			
			
			
			@Column(length = 20,nullable = true)
			public String centrocostoclasificacionabreviatura;
			//centrocostoclasificacionabreviatura varchar(20) null,
			
			
			
			@Column(length = 1,nullable = true)
			public String estado;
			//estado char(1) null,
			
			
			
			@Column(length = 20,nullable = true)
			public String creationuser;
			//creationuser varchar(20) null,
			
			
			
			@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
			private LocalDateTime creationdate;
			//creationdate datetime null,
			
			
			
			@Column(length = 40,nullable = true)
			public String creationstation;
			//creationstation varchar(40) null,
			
			
			
			@Column(length = 20,nullable = true)
			public String lastuser;
			//lastuser varchar(20) null,
			
			
			
			@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
			private LocalDateTime lastdate;
			//lastdate datetime null,
			
			
			
			@Column(length = 40,nullable = true)
			public String laststation;
			//laststation varchar(40) null,
}
