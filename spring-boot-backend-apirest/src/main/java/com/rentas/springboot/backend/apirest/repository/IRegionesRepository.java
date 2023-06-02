package com.rentas.springboot.backend.apirest.repository;

import java.util.List;

import com.rentas.springboot.backend.apirest.DTO.RegionDTO;

public interface IRegionesRepository {

	public List<RegionDTO> getRegiones(String Token);
	public RegionDTO saveRegion(RegionDTO reques);
	public RegionDTO updateRegion(RegionDTO reques);
	public void deleteRegion(Long id);
}
