package br.com.rformagio.person.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonPresentationData {

    private Long id;
    private String name;
    private String doc;
    private AddressData address;
}
