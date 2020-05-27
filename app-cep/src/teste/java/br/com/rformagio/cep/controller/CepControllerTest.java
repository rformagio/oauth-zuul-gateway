package br.com.rformagio.cep.controller;

import br.com.rformagio.cep.exception.CepNotFoundException;
import br.com.rformagio.cep.service.CepService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CepController.class)
public class CepControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CepService cepService;

    @Test
    public void givenInvelidCEP_whenFind_thenReturnBadRequest() throws Exception {

        String INVALID_CEP = "QWE";

        given(cepService.getAddressByCEP(anyString())).willReturn(null);

        mvc.perform(get("/api/v1/cep/QWE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
