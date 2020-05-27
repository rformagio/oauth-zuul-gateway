package br.com.rformagio.cep.domain;

import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
//@RedisHash("address")
@Entity
public class Address implements Serializable {

    @Id
    private String idCep;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH-mm-ss")
    private LocalDateTime date;
}
