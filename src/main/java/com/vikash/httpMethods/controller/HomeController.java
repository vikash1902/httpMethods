package com.vikash.httpMethods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vikash.httpMethods.entity.Country;
import com.vikash.httpMethods.repository.CountryRepo;
import com.vikash.httpMethods.serviceImpl.CountryServiceImpl;

@RestController
public class HomeController {

	@Autowired
	private CountryServiceImpl countryServiceImpl;

	@Autowired
	private CountryRepo countryRepo;

//	The GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
	@GetMapping("/getNationality")
	public ResponseEntity<List<Country>> getCountry() {
		System.out.println("hello from get nationality");
		return new ResponseEntity<List<Country>>(countryServiceImpl.getListOfCountry(), HttpStatus.OK);
	}

// The POST method submits an entity to the specified resource, often causing a change in state or side effects on the server.	
	@PostMapping("/saveNationality")
	public ResponseEntity<Country> saveCountry(@RequestBody Country country) {
		try {
			System.out.println("hello from save nationality");
			Country savedCountry = countryServiceImpl.save(country);
			return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	The PUT method replaces all current representations of the target resource with the request payload.
	@PutMapping("/updateNationality/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable("id") Long id,@RequestBody Country country) {
		System.out.println("hello from update nationality");
		Country existingCountry = countryRepo.findById(id).orElse(null);
		if (existingCountry != null) {
			existingCountry.setName(country.getName());
			Country updatedCountry = countryRepo.save(existingCountry);
			return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
//	The PATCH method applies partial modifications to a resource.
	 @PatchMapping("/patchNationality/{id}")
	    public ResponseEntity<Country> patchCountry(@PathVariable("id") Long id, @RequestBody Country country) {
	        Country existingCountry = countryRepo.findById(id).orElse(null);
	        if (existingCountry != null) {
	            if (country.getName() != null) {
	                existingCountry.setName(country.getName());
	            }
	            // Add more fields to update as needed

	            Country patchedCountry = countryRepo.save(existingCountry);
	            return new ResponseEntity<>(patchedCountry, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
//	 The DELETE method deletes the specified resource.
	  
	 @DeleteMapping("/deleteNationality/{id}")
	 public ResponseEntity<String> deleteNationality(@PathVariable("id") Long id){
		 try {
				System.out.println("hello from delete nationality");
				countryRepo.deleteById(id);
				return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
	 }
}
