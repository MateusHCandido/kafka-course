# KAFKA
Kafka é uma plataforma de streaming distribuída, projetada para lidar com grandes volumes de dados em tempo real. Ele oferece uma arquitetura robusta e escalável para processamento de dados em tempo real e armazenamento de eventos.


## Por que o serviço de mensageria é necessário?
Mensageria é essencial para sistemas distribuídos. Permite comunicação assíncrona eficiente e confiável entre diferentes partes de um sistema. Em ambientes distribuídos, onde componentes podem estar em servidores diferentes, é vital ter um meio escalável e resiliente de troca de informações.

## Produtores
Produtores em Kafka são responsáveis por enviar mensagens para tópicos. Eles geram dados que precisam ser processados ou compartilhados com outros componentes do sistema.

## Consumidores
Consumidores em Kafka recebem e processam as mensagens dos tópicos. Eles consomem os dados gerados pelos produtores e executam operações específicas com base nessas mensagens.

## Streams
Streams no Kafka permitem criar aplicativos de processamento de dados em tempo real. São usados para transformar, filtrar e agregar dados enquanto são transmitidos através do Kafka.

## Micro Serviço
Em arquiteturas de microserviços, Kafka é frequentemente usado para comunicação assíncrona entre os diferentes serviços. Isso permite escalabilidade, flexibilidade e desacoplamento entre os componentes do sistema.

Kafka é uma solução poderosa para construir sistemas distribuídos que lidam com grandes volumes de dados em tempo real, como análise de streaming, ingestão de logs, processamento de eventos e muito mais. Sua arquitetura distribuída e capacidade de processamento de alto desempenho o tornam uma escolha popular para muitas empresas.

# MÓDULOS CRIADOS PARA O PROJETO:


## [common-kafka](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/tree/main/common-kafka)

Dentro do módulo common-kafka, se encontra toda a estrutura do serviço de mensageria kafka.

## [service-email](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/tree/main/service-email)

Dentro do módulo service-email se encontra o consumidor do serviço de email.

## [service-fraud-detector](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/tree/main/service-fraud-detector)

Dentro do módulo service-fraud-detector se encontra o consumidor do serviço de detecção de fraude. Devido o caráter do curso seja entender com fucionava o consumo se diversos serviços, não há nenhum controle real na detecção de fraude.

## [service-log](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/tree/main/service-log)

Dentro do módulo service-log se encontra o consumidor do serviço de logs

## [service-new-order](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/tree/main/service-new-order)

Dentro do módulo service-new-order se encontra o serviço de ordem de novos pedidos, em que, na aplicação em si está programado para gerar 50 ordens de pedido


### Observações:

O projeto poderá ser executado, após a inicialização do arquivo [kafka-docker-compose.yml](https://github.com/MateusHCandido/kafka-course-producers-consumers-streams/blob/main/docker/kafka-docker-compose.yml). Este arquivo irá gerar uma imagem dos servidores zookeeper e kafka,
responsáveis pelo funcionamento da aplicação, geração dos tópicos, produção e consumo de mensagens.

para executar o arquivo, é necessário que o docker esteja executando normalmente. Após isso, no terminal, dentro do diretório do arquivo, execute o comando: 

```docker-compose -f kafka-docker-compose.yml up```
