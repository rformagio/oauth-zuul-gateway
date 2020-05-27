package br.com.rformagio.cep.service;

import br.com.rformagio.cep.client.CepClient;
import br.com.rformagio.cep.domain.Address;
import br.com.rformagio.cep.exception.CepNotFoundException;
import br.com.rformagio.cep.persistence.CepRepository;
import br.com.rformagio.cep.data.AddressData;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class CepService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CepClient cepClient;

    @Autowired
    private CepRepository cepRepository;

    public AddressData getAddressByCEP(String cep) throws CepNotFoundException {

        AddressData addressData;

        addressData = cepClient.findAddress(cep);
        if(addressData.isErro()){
            throw new CepNotFoundException(cep, "Cep não encontrado!!");
        }
        addressData.setDate(LocalDateTime.now());
        Address address = modelMapper.map(addressData, Address.class);
        address.setIdCep(cep);
        cepRepository.save(address);

        return addressData;
    }

}
