package com.vikash.httpMethods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikash.httpMethods.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Long> {

}
