# POC - Resilience4j com Springboot2

Esta demonstração mostra como usar a biblioteca de tolerância a falhas resilience4j. Nova biblioteca do Spring boot 2.

####Pontos Principais

* O Spring ``Cloud Hystrix`` está depreciado. Portanto, novas aplicações não devem utilizar esse projeto;
* O ``Resilience4j`` é uma nova opção para desenvolvedores Spring implementarem o pattern Circuit Breaker;
* O ``Resilience4j`` traz outras funcionalidades como o ``Rate Limiter``, ``Retry``' e ``Bulkhead``, além do pattern Circuit Breaker;
* O ``Resilience4j`` trabalha bem com o spring boot e com ferramentas de monitoramento, permitindo a emissão de métricas;
* Não há uma nova ferramenta para substituir o ``Hystrix Dashboard``, então os usuários precisam utilizar o ``Prometheus`` ou o ``NewRelic`` para o monitoramento.
