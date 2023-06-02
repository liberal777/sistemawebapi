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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
@Entity
@Table(name = "lg_productos")
public class Productox implements Serializable {
	 
	private static final long serialVersionUID = -4851444789931866731L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 4)
	private String claseproducto;

	@NotEmpty(message = "no puede estar vacio")
	@Size(max = 20, message = "el tamaño tiene que estar menor o igual a 20")
	@Column(nullable = false)
	private String productocodigo;
	@NotNull(message = "Descripcion no puede ser vacia")
	@Column(name = "productodescripcion", length = 100)
	private String descripcion;
	@NotEmpty(message = "Abreviatura no puede ser vacia")
	@Column(length = 60)
	private String productoabreviatura;
	@Column(length = 250)
	private String productodetalles;
	@Column(length = 255)
	private String especificaciontecnica;
	@Column(length = 15)
	private String codigointerno;
	@Column(length = 30)
	private String codigobarras;
	@Column(length = 30)
	private String codigobarrasfabricante;
	@Column(length = 40)
	private String modelofabricante;
	@Column(length = 150)
	private String productofoto;
 
	@NotNull(message = "Moneda no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moneda_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Moneda monedacodigo;

	@Column(length = 1)
	private String disponibleventaflag;
	@Column(length = 1)
	private String manejoxloteflag;
	@Column(length = 1)
	private String manejoxserieflag;
	@Column(length = 1)
	private String manejoxunidadflag;
	@Column(length = 1)
	private String manejoxkitflag;
	@Column(length = 1)
	private String controlcalidadflag;
	@Column(length = 1)
	private String afectoimpuestoventasflag;
	@Column(length = 1)
	private String impuestoventastipo;
	@Column(length = 1)	
	private String afectoimpuestoiscflag;
	@Column( nullable = true)
	private Integer isctasaid;
	@Column(length = 1)
	private String afectoimpuestoicbperflag;
	@Column( nullable = true)
	private Integer icbperid;
	@Column(length = 1)
	private String disponibleconsumoflag;
	@Column(length = 1)
	private String isoaplicableflag;
	@Column(length = 15)
	private String isonormainterna;
	@Column(length = 1)
	private String cantidaddobleflag;
	@Column(length = 1)
	private String cantidaddoblefactorflag;
	@Column(columnDefinition="Decimal(20,10) default '0.0000000000'")
	private Double cantidaddoblefactor;
	@Column(columnDefinition="Decimal(19,4)")
	private Double lotecompra;
	@Column(columnDefinition="Decimal(19,4)")
	private Double lotedespacho;
	@Column(columnDefinition="Decimal(16,6)")
	private Double lotedecompram3;// numeric(16,6),
	@Column(columnDefinition="Decimal(16,6)")
	private Double lotedecomprakg;// numeric(16,6),
	@Column(length = 1)
	private String detraccionflag;
	@Column(length = 4)
	private String detraccioncodigo;
	@Column(columnDefinition="Decimal(19,4)")
	private Double largo;// numeric(19,4),
	@Column(columnDefinition="Decimal(19,4)")
	private Double ancho;// numeric(19,4),
	@Column(columnDefinition="Decimal(19,4)")
	private Double altura;// numeric(19,4),
	@Column(columnDefinition="Decimal(19,4)")
	private Double espesor;// numeric(19,4),
	@Column(columnDefinition="Decimal(19,4)")
	private Double pesobruto;// numeric(19,4),
	@Column(columnDefinition="Decimal(19,4)")
	private Double pesoneto;// numeric(19,4),
	@Column( nullable = true)
	private Integer pesoum;
	@Column(columnDefinition="Decimal(19,4)")
	private Double volumen;// numeric(19,4),
	@Column( nullable = true)
	private Integer volumenum;
	@Column( nullable = true)
	private Integer modeloid;
		 	
	@NotNull(message = "Marca no puede ser vacia")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "marca_id",nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Marca marcaid;
		
	@NotNull(message = "Subcategoria no puede ser vacia")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "subcategoria_id",nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)	
	private Subcategoria subcategoriaid;
	
	@NotNull(message = "Tipo producto no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Tipo tipoproductoid;
	
	@Column( nullable = true)
	private Integer procedenciaid;
	@Column( nullable = true)
	private Integer colorid;
	
	@NotNull(message = "Unidad no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidad_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Unidad unidadmedidaid;
	
	@Column( nullable = true)
	private Integer unidadcompraid;
	@Column( nullable = true)
	private Integer unidadembalajeid;
	@Column( nullable = true)
	private Integer unidadmedidadobleid;
	@Column( nullable = true)
	private Integer tallaid;
	@Column( nullable = true)
	private Integer detraccionid;
	@Column( nullable = true)
	private Integer companiaid;
	@Column(length = 1)
	private String companiatodosflag;
	@Column(length = 1)
	private String precioeditableflag;
	@Column( nullable = true)
	private Integer gourmetorden;
	@Column( nullable = true)
	private Integer gourmetseccionid;
	@Column(length = 1)
	private String fechavencimientoflag;
	@Column(length = 1)
	private String disponiblewebflag;
	@Column(columnDefinition="Decimal(18,0)")
	private Double webproductid; 
	@Column(length = 1)
	private String webenviadoflag;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime webenviadofecha;// timestamp with time zone,
	@Column(length = 40)
	private String webenviadousuario;
	@Column(columnDefinition="Decimal(19,4)")
	private Double webstockactual; 
	@Column(columnDefinition="Decimal(19,4)")
	private Double webpreciounitario; 
	@Column(length = 1)
	private String estado;
	@Column(length = 20, nullable = false)
	private String creationuser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationdate;
	@Column(length = 20, nullable = false)
	private String lastuser;
	//@Column(columnDefinition = "timestamp default current_timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastdate;
	 
	
}
