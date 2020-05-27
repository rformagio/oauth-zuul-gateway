package br.com.rformagio.cep.client;

import br.com.rformagio.cep.client.fallback.CepFallbackFactory;
import br.com.rformagio.cep.data.AddressData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "https://viacep.com.br/ws/", fallbackFactory = CepFallbackFactory.class)
public interface CepClient {

    @GetMapping("{cep}/json")
    AddressData findAddress(@PathVariable("cep") String cep);
}
