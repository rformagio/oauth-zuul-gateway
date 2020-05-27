package br.com.rformagio.cep.controller;

import br.com.rformagio.cep.exception.CepNotFoundException;
import br.com.rformagio.cep.data.AddressData;
import br.com.rformagio.cep.service.CepService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("api/v1/cep")
@Validated
public class CepController {

    @Autowired
    CepService cepService;

    @ApiOperation(value = "Retorna o endereço pelo CEP.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!"),
            @ApiResponse(code = 404, message = "CEP não encontrado!")
    }
    )
    @GetMapping("/{cep}")
    @ResponseStatus(HttpStatus.OK)
    public AddressData getAddressByCEP(@PathVariable("cep")
                                   @NotEmpty(message = "CEP é obrigatório")
                                   @Pattern(regexp = "[0-9]+", message = "Só pode haver números")
                                   @Size(min = 8, max = 8, message = "CEP deve conter 8 digitos!")
                                           String cep) throws CepNotFoundException {

            return cepService.getAddressByCEP(cep);
    }

}
