package br.com.rformagio.cep.client.fallback;

import br.com.rformagio.cep.client.CepClient;
import br.com.rformagio.cep.data.AddressData;
import br.com.rformagio.cep.domain.Address;
import br.com.rformagio.cep.notification.CepErrorNotification;
import br.com.rformagio.cep.persistence.CepRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class CepFallback implements CepClient {

    @Setter
    CepErrorNotification cepErrorNotification;

    @Setter
    CepRepository cepRepository;

    @Setter
    private ModelMapper modelMapper;

    private final Throwable throwable;

    public CepFallback(Throwable throwable){
        this.throwable = throwable;
    }

    @Override
    public AddressData findAddress(String cep){

        cepErrorNotification.notifyError("Executando serviço de FALLBACK de busca CEP!! (" +  cep + ")" );

        AddressData addressData;
        Optional<Address> optionalAddress = cepRepository.findById(cep);
        if (optionalAddress.isPresent()) {
            addressData = modelMapper.map(optionalAddress.get(), AddressData.class);
        } else {
            addressData = new AddressData();
            addressData.setCep(cep);
        }

        return addressData;
    }
}
