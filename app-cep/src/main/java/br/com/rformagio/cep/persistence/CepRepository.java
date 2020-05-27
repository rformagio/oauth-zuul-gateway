package br.com.rformagio.cep.persistence;

import br.com.rformagio.cep.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CepRepository extends JpaRepository<Address, String> {

    Address findAddressByIdCep(String cep);
}
