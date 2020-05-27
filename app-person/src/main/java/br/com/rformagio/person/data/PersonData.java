package br.com.rformagio.person.data;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
public class PersonData implements Serializable {

    private Long id;

    @NotEmpty(message = "O nome é obrigatório!")
    private String name;

    @NotEmpty(message = "O documento é obrigatório")
    private String doc;

    @NotEmpty(message = "O CEP é obrigatório")
    @Pattern(regexp = "[0-9]+", message = "CEP deve conter somente números.")
    @Size(min = 8, max = 8, message = "CEP deve conter 8 digitos!")
    private String cep;

}
