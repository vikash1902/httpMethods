package com.vikash.httpMethods.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikash.httpMethods.entity.Country;
import com.vikash.httpMethods.repository.CountryRepo;

@Service
public class CountryServiceImpl {

	@Autowired
	private CountryRepo countryRepo;

	public List<Country> getListOfCountry() {
		return countryRepo.findAll();
	}
	
	public Country save(Country country) {
		return countryRepo.save(country);
	}
}
