package br.com.rformagio.person.client;

import br.com.rformagio.person.client.fallback.CepFallbackFactory;
import br.com.rformagio.person.data.AddressData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "${person.app-cep.url}", fallbackFactory = CepFallbackFactory.class)
public interface CepClient {

    @GetMapping("{cep}")
    AddressData findAddress(@PathVariable("cep") String cep);

}


