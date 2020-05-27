package br.com.rformagio.cep.client.fallback;

import br.com.rformagio.cep.client.CepClient;
import br.com.rformagio.cep.notification.CepErrorNotification;
import br.com.rformagio.cep.persistence.CepRepository;
import feign.hystrix.FallbackFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CepFallbackFactory implements FallbackFactory<CepClient> {

    @Autowired
    private CepErrorNotification cepErrorNotification;

    @Autowired
    private CepRepository cepRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CepClient create(Throwable throwable) {
        CepFallback cepFallback = new CepFallback(throwable);
        cepFallback.setCepErrorNotification(cepErrorNotification);
        cepFallback.setCepRepository(cepRepository);
        cepFallback.setModelMapper(modelMapper);
        return cepFallback;
    }
}
