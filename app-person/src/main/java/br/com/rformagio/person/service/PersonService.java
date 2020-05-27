package br.com.rformagio.person.service;

import br.com.rformagio.person.client.CepFacade;
import br.com.rformagio.person.data.AddressData;
import br.com.rformagio.person.data.PersonData;
import br.com.rformagio.person.data.PersonPresentationData;
import br.com.rformagio.person.domain.Address;
import br.com.rformagio.person.domain.Person;
import br.com.rformagio.person.exception.CepNotFoundException;
import br.com.rformagio.person.exception.PersonNotFoundException;
import br.com.rformagio.person.persistence.AddressRepository;
import br.com.rformagio.person.persistence.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CepFacade cepFacade;

    @Autowired
    private ModelMapper modelMapper;


    public List<PersonPresentationData> findAll(){

        List<Person> personList = personRepository.findAll();
        List<PersonPresentationData> personPresentationDataList = new ArrayList<>();

        personList.forEach(person -> personPresentationDataList
        .add(buildPersonPresentationData(person)));

       return personPresentationDataList;
    }

    public PersonPresentationData findById(Long id) throws PersonNotFoundException {
        PersonPresentationData personPresentationData;
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            personPresentationData = buildPersonPresentationData(person);
        } else {
            throw new PersonNotFoundException(String.valueOf(id),
            "Pessoa não encontrada!");
        }

        return personPresentationData;
    }

    public PersonPresentationData save(PersonData personData) throws CepNotFoundException {
        AddressData addressData = cepFacade.findAddress(personData.getCep());
        Address address =  buildAddress(addressData);
        Person person = buildPerson(personData, address);
        address.setPerson(person);
        Person personSaved = personRepository.save(person);
        return buildPersonPresentationData(personSaved);
    }

    public PersonPresentationData update(PersonData personData) throws CepNotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(personData.getId());
        Person person = null;
        if(optionalPerson.isPresent()){
            person = optionalPerson.get();
            person.setName(personData.getName());
            person.setDoc(personData.getDoc());
            person.setCep(personData.getCep());
            AddressData addressData = cepFacade.findAddress(personData.getCep());
            Address address = person.getAddress();
            modelMapper.map(addressData, address);
            //person.setAddress(address);
            personRepository.flush();
        }

        return buildPersonPresentationData(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    private PersonPresentationData buildPersonPresentationData(Person person){

        return PersonPresentationData.builder()
                .id(person.getId())
                .doc(person.getDoc())
                .name(person.getName())
                .address(buildAddressData(person.getAddress()))
                .build();
    }

    private Person buildPerson(PersonData personData, Address address){

        Person person = new Person();
        person.setCep(personData.getCep());
        person.setDoc(personData.getDoc());
        person.setName(personData.getName());
        person.setAddress(address);
        return person;
    }

    private Address buildAddress(AddressData addressData){
        return modelMapper.map(addressData, Address.class);
    }

    private AddressData buildAddressData(Address address){
        return modelMapper.map(address, AddressData.class);
    }
}
