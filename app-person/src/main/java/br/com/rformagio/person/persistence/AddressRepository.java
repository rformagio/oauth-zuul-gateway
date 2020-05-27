package br.com.rformagio.person.persistence;

import br.com.rformagio.person.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
