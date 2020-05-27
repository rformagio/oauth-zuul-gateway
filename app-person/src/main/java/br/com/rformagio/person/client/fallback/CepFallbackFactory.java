package br.com.rformagio.person.client.fallback;

import br.com.rformagio.person.client.CepClient;
import br.com.rformagio.person.notification.CepErrorNotification;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CepFallbackFactory implements FallbackFactory<CepClient> {

    @Autowired
    private CepErrorNotification cepErrorNotification;


    @Override
    public CepClient create(Throwable throwable) {
        CepFallback cepFallback = new CepFallback(throwable);
        cepFallback.setCepErrorNotification(cepErrorNotification);
        return cepFallback;
    }
}
