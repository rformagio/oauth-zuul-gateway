package br.com.rformagio.person.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String doc;
    private String cep;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Address address;

}
