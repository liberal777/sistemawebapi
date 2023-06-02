package com.rentas.springboot.backend.apirest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rentas.springboot.backend.apirest.DTO.RegionDTO;

@Repository
public class RegionesRepository implements IRegionesRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<RegionDTO> getRegiones(String Token) {
		 List<RegionDTO> listado=null;
		 listado = jdbcTemplate.query("select id,nombre from regionesdto", (rs,rowNum)->
		 new RegionDTO(rs.getLong("ID"),
				 	rs.getString("NOMBRE")
				 	)
	 );
		return listado;
	}

	@Override
	public RegionDTO saveRegion(RegionDTO reques) {
		int cantRegIns=jdbcTemplate.update("insert into regionesdto(id,nombre) values(?,?)",reques.getId(), reques.getNombre() ); 
		System.out.println("registros insertado"+cantRegIns);
		return reques;
	}

	@Override
	public RegionDTO updateRegion(RegionDTO reques) {
		int cantRegIns=jdbcTemplate.update("update regionesdto set nombre = ? where id = ?", reques.getNombre() ,reques.getId() ); 
		System.out.println("registros insertado"+cantRegIns);
		return reques;
	}

	@Override
	public void deleteRegion(Long id) {		
		int cantRegIns = jdbcTemplate.update("delete from regionesdto where id = ?", id ); 
		System.out.println("registros insertado"+cantRegIns);
		
		
	}

	 

}
