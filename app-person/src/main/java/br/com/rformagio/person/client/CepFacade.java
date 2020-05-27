package br.com.rformagio.person.client;

import br.com.rformagio.person.data.AddressData;
import br.com.rformagio.person.exception.CepNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CepFacade {

    @Autowired
    private CepClient cepClient;

    public AddressData findAddress(String cep) throws CepNotFoundException{
        AddressData addressData = cepClient.findAddress(cep);
        if(addressData.isErro()){
            throw new CepNotFoundException(cep, "Cep não encontrado!");
        }
        return addressData;
    }
}
