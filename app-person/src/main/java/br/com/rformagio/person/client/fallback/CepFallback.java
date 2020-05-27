package br.com.rformagio.person.client.fallback;

import br.com.rformagio.person.client.CepClient;
import br.com.rformagio.person.data.AddressData;
import br.com.rformagio.person.notification.CepErrorNotification;
import feign.FeignException;
import lombok.Setter;

public class CepFallback implements CepClient {

    @Setter
    CepErrorNotification cepErrorNotification;

    private final Throwable throwable;

    public CepFallback(Throwable throwable){
        this.throwable = throwable;
    }

    @Override
    public AddressData findAddress(String cep){

        AddressData addressData = new AddressData();
        if(throwable instanceof FeignException.NotFound){
            addressData.setErro(true);
        } else {
            cepErrorNotification.notifyError("Executando serviço de FALLBACK de busca CEP!!");
        }
        addressData.setCep(cep);
        return addressData;
    }

}
