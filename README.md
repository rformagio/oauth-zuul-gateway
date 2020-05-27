# oauth-zuul-gateway

Esta prova de conceito (POC) consiste em:
1. Criar um API REST que acessa uma segunda API externa (neste caso será feita uma requisição para ViaCEP)
2. Disponibilizar a API criada através de um Gateway (API Gateway. Neste caso será utilizado o Zuul, da Neteflix)
3. Proteger a API com OAUTH2

### Tecnologias utilizadas

* Java 8
* Maven 3.6.0
* IDE Intellij IDEA
* Spring-boot
* Eureka Server
* Zuul Gateway (Netflix)
* Java Feign
* Hystrix
* OAUTH2
* Docker
* Swagger
* Lombok

### Implementação

Foram criadas duas APIs:
1. App-Person: Esta API contempla o domínio de **Pessoa**. 
```json
{
  "id": 1,
  "name": "Teste",
  "doc": "9999999",
  "address": {
    "cep": "01421001",
    "logradouro": null,
    "complemento": null,
    "bairro": null,
    "localidade": null,
    "uf": null,
    "unidade": null,
    "ibge": null,
    "gia": null,
    "date": null
  }
}
```

![Person MER](/docs/Pessoa.png)

2. App-Cep: Esta API contempla o domínio do CEP, com a entidade **Address**

Também foi implementado um servidor de autorização bastante simples, visto que não era o foco do POC a sua implementação.
Este servidor foi criado com apenas um usuário *in memory*:
- username: teste
- password: password

O método de autenticação é o *Password Grant*. Ou seja, ao informar usuário e senha, o servidor faz a validação e se estiver ok retorna um token.

Para fazer uma requisição ao servidor é necessário identificar o cliente, passando o client_id e o secret do cliente por *Authorization Basic*
- client_id: app-person
- secret: secret

A url para solicitar um token:

- **http://localhost:8090/oauth/token**

A url para validar o token:

- **http://localhost:8090/validateUser**

O Eureka-Server não necessita de implementação adicional. Ele atua como *Service Discoverer*  onde os serviços **App-Person** e **App-Cep** irão se registrar.

O Zuul atua como Gateway. É aporta de entrada de todas as requisições. Ou seja, qualquer requisição externa para os demais serviçoes devem passar por ele. Ele responde na porta 9090, ou seja http://localhost:9090
O roteamento do **Zuul** foi configurado da seguinte forma:

- requisições para o path /oauth/* serão redirecionadas para o **Auth-Server**
- requisições para o path /api/v1/** serão redirecionadas para o **App-Person**

Também foi configurado um filtro com a seguinte regra:

-  requisições par ao path  /oauth/** estão com *permitAll*, ou seja, estão liberadas. O **Zuul** simplesmente repassará a requisição conforme regrar de roteamento;
- requisições para o path /** necessitarão de validação de token e, neste caso, antes de verificar as regras de roteamento, o **Zuul** faz um requisição ao **Auth-Server** para validar o token enviado na requisição. Se a validação do token for "ok" o **Zuul** segue para o roteamento. Do contrário bloqueia o acesso.


### Diagrama

Este projeto possui 5 módulos:
* App-Person: aplicação de pessoas;
* App-Cep: aplicação de busca CEP;
* Eureka-Server: servidor Eureka que implementa o Service Discoverer, permitindo o registro dos serviços.
* API-Gateway: Zuul, que funionará como Gateway. Será a porta de entrada do sistema. Toda requisição passará por ele. Será reposnável por direcionar a requisição externa para a aplicação correta.
* Auth-Server: Servidor de autenticação e autorização. Foi construído da forma mais simples possível somente para atender este projeto:
  * Carrega usuário e senha *in memory*. Neste caso específico, apenas um usuário: username:teste; password:password
  * implementa o método: *password grant*
  * cliente_id:app-person, secret:secret para o Authorization Basic
  
  
![Person MER](/docs/OAUTH_ZUUL-Diagrama01.png)


### Funcionamento Geral

#### Fluxo par obtenção de token

![Token flow](/docs/Token.png)


#### Fluxo geral para utilização do Person
![Person flow](/docs/OAUTH-PERSON.png)

#### Interação App-Person vs App-Cep

![Person-CEP flow](/docs/person-cep.png)

A aplicação **App-Cep** não possui rota configurada no **Zuul**. A intenção é que apenas a aplicação **App-Person** ou outras aplicações internas façam requisições para **App-Cep**.

Esta aplicação só é chamada no momento da criação ou atualização de uma *pessoa*. Neste dois momento a **App-Person** faz uma requisição para a **App-Cep**:

- **http://localhost:9092/api/v1/cep/{cet}  GET**

A aplicação **App-Cep** então faz uma requisição esterna à API do ViaCEP. Esta, por sua vez, retorna o obejto de endereço que é então retornado para a **App-Person**. Esta última, complementa o objeto *Person* com o endereço *Address* e salva na base *H2*.

###### Tratamento de erros
Aplicação **App-Cep** também possui uma base dedos, pois a cada retorno do ViaCEP ela armazena os dados do endereço localmente. Assim, em caso de falha do ViaCEP, o **App-Cep** faz uma tentativa de encontrar o endereço na base local
Em casos de falha da ViaCEP, uma classe de notificação é chamada e uma mensagem é gravada no log. O ideal é implementar uma ntificação via e-mail, mensagem ou ainda um WebHook. 

No caso de falha interna e falha na aplicação **App-Cep**, o *pattern Circuit Breaker* dispara uma chamada para uma classe de fallback na aplicação **App-Person**. Neste caso um endereço vazio é retornado e uma notificação também gerada em log. Da mesma forma que anteriormente, o ideial seria o envio de e-mail ou utilização de um mecanismo de WebHook.




### Como executar

Para executar e verificar o funcionamento do projeto:

1. Clone o projeto do GIT;
2. Acesse o diretório /oauth-gateway-challenge/
3. Execute o script build.sh
 * Este script fará o build de todos os módulos e criará as imagens docker de cada um.
4. Neste mesmo diretório, execute o comando: 

```script
docker-compose up
```
* Este comando "subirá" todas imagens

5. No diretório oauth-gateway-challenge/docs/soupUI está o projeto para testes com o **SoapUI**


Também é possível fazer os testes individualmente dos serviços **App-Person** e **App-Cep** acessando as urls do **Swagger**

- App-Person
https://localhost:9091/swagger-ui.html

- App-Cep
http://localhost:9092/swagger-ui.html


### Melhorias

1. Criar Testes;
2. Utilizar o **Spring Cloud Gateway** em vez do **Zuul**
3. Utilizar o **REDIS** apartado em substituição do **H2** na aplicação **App-Cep**
4. Utilizar o **MongoDB** apartado em substituição ao **H2** na aplicação **App-Person**
5. Melhorar os logs
6. Melhorar a implementação do **Auth-Server**
7. Utilizar um servidor de configuração central, **Spring Cloud Config**.


*Rodrigo Formagio*
