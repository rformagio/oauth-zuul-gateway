package br.com.rformagio.cep.notification;

import org.springframework.stereotype.Component;

@Component
public class CepErrorNotification {

    public void notifyError(String message) {
        System.out.println(" #####################################################################");
        System.out.println("           APP-CEP                                                    ");
        System.out.println(" ###############  " + message );
        System.out.println(" ");
        //System.out.println(" ############### " + e.getMessage());
        System.out.println(" #####################################################################");
    }
}
