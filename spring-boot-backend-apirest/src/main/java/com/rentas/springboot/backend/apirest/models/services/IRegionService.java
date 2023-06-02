package com.rentas.springboot.backend.apirest.models.services;

import java.util.List;

import com.rentas.springboot.backend.apirest.DTO.RegionDTO;

public interface IRegionService {
	List<RegionDTO> getRegiones(String token);
	public RegionDTO saveRegion(RegionDTO reques);
	public RegionDTO updateRegion(RegionDTO reques);
	public void deleteRegion(Long id);
}
