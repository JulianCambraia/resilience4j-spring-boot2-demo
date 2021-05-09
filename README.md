# POC - Resilience4j com Springboot2

Esta demonstração mostra como usar a biblioteca de tolerância a falhas resilience4j. Nova biblioteca do Spring boot 2.

#### 1. Pontos Principais

* O Spring ``Cloud Hystrix`` está depreciado. Portanto, novas aplicações não devem utilizar esse projeto;
* O ``Resilience4j`` é uma nova opção para desenvolvedores Spring implementarem o pattern Circuit Breaker;
* O ``Resilience4j`` traz outras funcionalidades como o ``Rate Limiter``, ``Retry``' e ``Bulkhead``, além do pattern Circuit Breaker;
* O ``Resilience4j`` trabalha bem com o spring boot e com ferramentas de monitoramento, permitindo a emissão de métricas;
* Não há uma nova ferramenta para substituir o ``Hystrix Dashboard``, então os usuários precisam utilizar o ``Prometheus`` ou o ``NewRelic`` para o monitoramento.

### 2. Resilience4j
O Resilience4j foi inspirado no ``Hystrix`` da Netflix, porém, foi desenvolvido em Java 8 e utilizando programação funcional. 
É bem leve, pois tem apenas a biblioteca [``Vavr``](https://github.com/vavr-io/vavr) como dependência, já o Hystrix tem 
como dependência o [``Archaius``](https://github.com/Netflix/archaius), que possui várias outras dependências externas, 
como o Guava e o Apache Commons.
Uma nova biblioteca sempre terá vantagem quando comparada com uma biblioteca mais antiga, já que a novata pode aprender 
com os erros do seu antecessor. Além disso, o Resilience4j vem com diversas funcionalidades novas:

#### 2.1. Circuit Breaker
          
Quando um serviço invoca outro serviço, sempre há a possibilidade de que o serviço externo não esteja executando ou a 
latência esteja muito alta. Isso pode levar à exaustão do número de threads, pois as mesmas estarão esperando outras 
requisições terminarem. 
O pattern de [``Circuit Breaker``](https://resilience4j.readme.io/docs/circuitbreaker) funciona de maneira similar a um 
Circuit Breaker elétrico:


* Quando um número de falhas consecutivas ultrapassa determinado limite, o Circuit Breaker se abre;
* Durante certo tempo, todas as requisições invocando este serviço remoto irão falhar imediatamente;
* Após este período, o Circuit Breaker permite que um determinado número de requisições de testes passem;
* Se estas requisições de testes terminarem com sucesso, o Circuit Breaker se fecha e volta a operar normalmente;
* Caso contrário, se continuar havendo falhas, o período sem requisições ao serviço externo é reiniciado.

#### 2.2. Rate Limiter

O [``Rate Limiter``](https://resilience4j.readme.io/docs/ratelimiter) garante que um serviço só aceite determinado número de requisições durante uma janela de tempo, garantindo 
que os recursos sejam utilizados de acordo com os limites desejados e que não sejam utilizados até a sua exaustão.

#### 2.3. Retry

O [``Retry``](https://resilience4j.readme.io/docs/retry) permite que uma aplicação trate falhas momentâneas quando fizerem 
chamadas para serviços externos, garantindo que retentativas sejam feitas por um certo número de vezes. 
Caso não obtenham sucesso após todas as retentativas, a chamada ao método falha e a resposta deve ser tratada normalmente 
pela aplicação.

#### 2.4. Bulkhead
          
O [``Bulkhead``](https://resilience4j.readme.io/docs/bulkhead) garante que a falha em uma parte do sistema não cause a 
falha no sistema todo. 
Ele controla o número de chamadas concorrentes que um componente pode ter. Dessa maneira, o número de recursos esperando 
resposta do componente é limitado. Há dois tipos de implementações do bulkhead:
          
* O Isolamento por semáforo: limita o número de chamadas concorrentes ao serviço, rejeitando imediatamente outras chamadas 
assim que o limite é alcançado;

* O isolamento por thread pool: utiliza um thread pool para separar o serviço dos consumidores e limita cada consumidor a 
um subgrupo dos recursos do sistema.
          
A abordagem por thread pool também provê uma fila de espera, rejeitando requisições apenas quando o pool e a fila estão 
cheias. 
O gerenciamento da thread pool adiciona um pouco de desobrecarga, o que diminui um pouco a performance quando comparado 
ao uso de semáforos, mas permite que threads fiquem suspensas até expirar, caso não sejam executadas.


## 3.0. Construindo uma aplicação Spring Boot com o Resilience4j

Iremos construír dois serviços: o serviço de gerenciamento de livros e, o serviço de gerenciamento de bibliotecas.
Neste sistema, o serviço de bibliotecas chama o serviço de livros. 
Será necessário subir e derrubar o serviço de livros para simular diferentes cenários para o Circuit Breaker, Rate Limiter, Retry e Bulkhead.

##### 3.1. Pré-requisitos

* JDK 8;
* Spring Boot 2.1.x;
* Resilience4j 1.1.x (a última versão do resilience4j é a 1.7.0 mas o resilience4j-spring-boot2 utiliza a versão 1.1.x);
* IDE;
* Gradle;
* NewRelic APM (Ou Prometheus com Grafana).

#### 4.0. Serviço de Gerenciamento de Livros

##### 4.0.1 - Dependências Gradle
Este serviço é uma API REST simples e precisa dos jars padrões do spring boot. 
Também vamos habilitar o swagger para testar a API:

![alt text](https://github.com/JulianCambraia/resilience4j-spring-boot2-demo/blob/main/images/tela-001.png?raw=true)