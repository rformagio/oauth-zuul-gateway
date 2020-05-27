package br.com.rformagio.person.persistence;

import br.com.rformagio.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
