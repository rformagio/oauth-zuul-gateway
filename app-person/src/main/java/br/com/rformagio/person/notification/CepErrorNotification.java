package br.com.rformagio.person.notification;

import org.springframework.stereotype.Component;

@Component
public class CepErrorNotification {

    public void notifyError(String message) {
        System.out.println(" #####################################################################");
        System.out.println("            APP-PERSON                                                ");
        System.out.println(" ###############  " + message );
        System.out.println(" ");
        System.out.println(" ############### ");
        System.out.println(" #####################################################################");
    }
}
