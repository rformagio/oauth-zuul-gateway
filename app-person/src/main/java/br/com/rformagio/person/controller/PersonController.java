package br.com.rformagio.person.controller;

import br.com.rformagio.person.data.PersonData;
import br.com.rformagio.person.data.PersonPresentationData;
import br.com.rformagio.person.exception.CepNotFoundException;
import br.com.rformagio.person.exception.NoPersonFoundException;
import br.com.rformagio.person.exception.PersonNotFoundException;
import br.com.rformagio.person.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@Validated
public class PersonController {

    @Autowired
    PersonService personService;

    @ApiOperation(value = "Retorna uma pessoa pelo id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!"),
            @ApiResponse(code = 404, message = "Pessoa não encontrada!")
    }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonPresentationData findById(@PathVariable("id")
                                   @NotEmpty(message = "Id é obrigatório")
                                   @Pattern(regexp = "[0-9]+", message = "Id deve ser um número")
                                           String id) throws PersonNotFoundException {

        PersonPresentationData personPresentationData = personService.findById(Long.valueOf(id));
        return personPresentationData;
    }

    @ApiOperation(value = "Atualiza uma pessoa e retonra os dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!"),
            @ApiResponse(code = 404, message = "CEP não encontrado!")
    }
    )
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PersonPresentationData update(@RequestBody @Valid PersonData personData) throws CepNotFoundException {
        return personService.update(personData);
    }

    @ApiOperation(value = "Cria uma nova pessoa e retorna os dados salvos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!"),
            @ApiResponse(code = 404, message = "CEP não encontrado!")
    }
    )
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public PersonPresentationData save(@RequestBody @Valid PersonData personData) throws CepNotFoundException {
        return personService.save(personData);
    }

    @ApiOperation(value = "Deleta uma pessoa.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 400, message = "Problema nos dados enviados!")
    }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id")
                           @NotEmpty(message = "Id é obrigatório")
                           @Pattern(regexp = "[0-9]+", message = "Id deve ser um número")
                                   String id) {
        personService.delete(Long.valueOf(id));
    }

    @ApiOperation(value = "Retorna a lista de pessoas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 404, message = "Não há pessoas cadastradas até o momento!")
    }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonPresentationData> findAll() throws NoPersonFoundException {
        List<PersonPresentationData> personDataList = personService.findAll();
        if(personDataList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma pessoa encontrada!");
        }

        return personDataList;
    }

}
