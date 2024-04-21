package com.vikash.httpMethods;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikash.httpMethods.controller.HomeController;
import com.vikash.httpMethods.entity.Country;
import com.vikash.httpMethods.repository.CountryRepo;
import com.vikash.httpMethods.serviceImpl.CountryServiceImpl;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
class HttpMethodsApplicationTests {

	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryServiceImpl nationalityService;
    
    
    @MockBean
    private CountryRepo countryRepo;
    
    @InjectMocks
    private HomeController countryController;

    @Test
    public void testGetNationality() throws Exception {
        // Mock service response
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "USA"));
        countries.add(new Country(2L, "France"));
        when(nationalityService.getListOfCountry()).thenReturn(countries);


        // Perform GET request
        mockMvc.perform(get("/getNationality"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("USA")))
                .andExpect(jsonPath("$[1].name", is("France")));
    }
    
    @Test
    public void testSaveCountry() throws Exception {
        Country country = new Country();
        country.setName("Test Country");

        when(countryRepo.save(any())).thenReturn(country);

        mockMvc.perform(MockMvcRequestBuilders.post("/saveNationality")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(country)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdateCountry() throws Exception {
        Long id = 1L;
        Country existingCountry = new Country();
        existingCountry.setId(id);
        existingCountry.setName("Existing Country");

        Country updatedCountry = new Country();
        updatedCountry.setId(id);
        updatedCountry.setName("Updated Country");

        when(countryRepo.findById(id)).thenReturn(Optional.of(existingCountry));
        when(countryRepo.save(any())).thenReturn(updatedCountry);

        mockMvc.perform(MockMvcRequestBuilders.put("/updateNationality/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedCountry)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPatchCountry() throws Exception {
        Long id = 1L;
        Country existingCountry = new Country();
        existingCountry.setId(id);
        existingCountry.setName("Existing Country");

        Country patchedCountry = new Country();
        patchedCountry.setId(id);
        patchedCountry.setName("Patched Country");

        when(countryRepo.findById(id)).thenReturn(Optional.of(existingCountry));
        when(countryRepo.save(any())).thenReturn(patchedCountry);

        mockMvc.perform(MockMvcRequestBuilders.patch("/patchNationality/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(patchedCountry)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testDeleteNationality() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/deleteNationality/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully deleted"));
    }

}
