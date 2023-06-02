package com.rentas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentas.springboot.backend.apirest.DTO.RegionDTO;
import com.rentas.springboot.backend.apirest.repository.IRegionesRepository;
@Service
public class RegionService implements IRegionService {
	
	@Autowired 
	IRegionesRepository repo;
	
	@Override
	public List<RegionDTO> getRegiones(String token) {
		 return repo.getRegiones(token);
		 
	}

	@Override
	public RegionDTO saveRegion(RegionDTO reques) {
	 
		return repo.saveRegion(reques);
	}

	@Override
	public RegionDTO updateRegion(RegionDTO reques) {
		
		return repo.updateRegion(reques);
	}

	@Override
	public void deleteRegion(Long id) {
		repo.deleteRegion(id);
		
	}

}
