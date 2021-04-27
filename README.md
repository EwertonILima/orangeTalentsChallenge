Desenvolvendo uma API REST para com Spring Boot.

Nesse post iremos criar uma API REST para cadastro de usuário e endereço utilizando framework da linguagem Java, o Spring, que dispõe de diversas tecnologias para simplificar o desenvolvimento da nossa estrutura de código, e pelo ganho de manutenção por ter interdependência mínima entre os objetos.

API REST
O que é API REST?

A sigla API provém do inglês Application Programming Interface(Interface de Programação de Aplicações), é um conjunto de padrões e rotinas para acesso de um aplicativo ou plataforma Web.
O REST significa Representational State Transfer(Transferência de Estado Representacional) consiste em regras e restrições que permitem a criação de projetos com interfaces. Essas regras/restrições permitem o uso dos métodos de requisição HTTP, responsáveis por indicar a ação a ser executada para um dado recurso.

Conforme seguirmos com esse post, essas informações farão mais sentido.

Sobre a API

Vamos criar um Usuário por meio do método POST, depois cadastrar Endereços para esse Usuário, e por fim consultar as informações deste Usuário e seus Endereços pelo método GET.

Construindo a API

Para  realizar o básico que foi proposto, iniciaremos o projeto com algumas dependências do Spring Boot.

Tecnologias

Spring Web 

O Spring Web é utilizado para criar aplicações Web utilizando o Spring MVC. Com essa dependência não precisamos instalar um servidor web, pois utiliza o Apache Tomcat  como servidor web java. 

H2 Database

O H2 é uma base de dados de memória integrada do Spring, escolhemos o H2 pela praticidade de instalação e testes na fase inicial do projeto. 
Quando for feito o deploy da API na web, podemos instalar uma dependência para conexão com qualquer outro banco de dados relacional.

Validation 

O Validation fará a parte de validação dos dados da aplicação, por exemplo  um CPF válido ou formato de email correto.

Spring Data JPA

Essa dependência nos ajudará com recursos como JPA e Hibernate, que farão a comunicação com o banco de dados, fazendo o mapeamento de objeto relacional.

Spring Boot DevTools

O DevTools auxiliará no reinício mais rápido da aplicação e LiveReload, que a cada alteração no projeto, com a aplicação rodando, o Spring altera e recompila o código de maneira rápida.


Iniciando o Projeto

Existem diversas formas de iniciar o projeto, uma das práticas e rápidas, é pelo Spring Initializr, porém como eu já tenho o Spring Tool Suite instalado na minha máquina, vamos direto por ele, que é uma forma bem rápida também.

Vamos preencher com as informações do projeto. Usaremos o tipo Maven, que é a ferramenta que fará a gerência das nossas dependências mencionadas antes de iniciar o nosso projeto. E a versão do Java 8, por questões de compatibilidade.


E depois disso selecionamos a versão do Spring e as dependências  que iremos utilizar.



Com o projeto criado, dependências baixadas pelo Maven, já temos nossa primeira classe criada, a OrangetalentsApplication que será a responsável por rodar a nossa API.





- Iremos utilizar o padrão de camadas para separar e organizar as classes e responsabilidades de cada parte da aplicação, isso faz com que a nossa API tenha uma melhor manutenibilidade:

Entities será onde vai ficar as classes que servirão de modelo para a criação das tabelas no banco e o relacionamento entre as tabelas: Usuario e Endereco.



O Diagrama acima representa os atributos obrigatórios das classes e qual o formato de relacionamento que vamos utilizar.



Repositories ficará as classes que farão as transações com o banco de dados:   UsuarioRepository e EnderecoRepository.

Services teremos as classes responsáveis pelas regras de negócio do projeto: UsuarioService e EnderecoService.

Controllers é a camada onde estarão as classes que farão a comunicação com o Front-end, através dos métodos de requisição HTTP: UsuarioController e EnderecoController.




Configurando a primeira Entidade e o Acesso a base de dados H2

Classe Usuario, onde a anotação @Entity é usada para informar que a classe também é uma entidade.



Em application.properties, iremos inserir as configurações básicas para utilizar o H2.

Aplicação rodando,com as primeiras alterações, com o servidor - executando na porta 8080: 

Acessando a base de dados H2:
http://localhost:8080/h2-console/

E a primeira tabela criada: 

Seguimos criando as demais Camadas e Classes para o Usuario.
Repositories → UsuarioRepository,  aqui a poderosa classe JpaRepository vai entrar em ação.

Services → UsuarioService, nessa camada já vamos começar a fazer um tratamento de exceções padrão, pensando em uma mensagem retorno de erro, amigável.
 

 

Classe para a nossa exceção de Objeto Não Encontrado.

Controllers → UsuarioController, agora podemos fazer os primeiros testes de funcionalidades da API.

Vou usar o POSTMAN para interagir com os Endpoints que criamos. 
Usuário cadastrado com sucesso, retornando status 201

Usuário encontrado com sucesso, retornando status 200
 

Continuando com o processo, iremos seguir quase todos os passos para a Entidade Endereco, então não vou demonstrar o passo-a-passo para não ficar repetitivo, porém para esse projeto só criaremos o Endpoint para cadastro, então não utilizaremos o método para encontrar o endereço em EnderecoService. Dito isso, caso tenham dúvidas nesse ponto do projeto, podem consultar o link do projeto no GitHub que eu deixei no final da postagem.
Relacionamento de entidades e Validation.

Com a classe Endereco pronta, faremos o relacionamento entre as Entidades, que conforme está no diagrama, será um usuários para muitos endereços. Colocando o trechos de código a seguir:

Classe Usuario:



Aqui colocamos a anotação @JsonIgnoreProperties para não ter problemas de referência cíclica.

Classe Endereco:



Esse é o comportamento esperado para nossa API com o relacionamento das entidades:

Endereço cadastrado com sucesso, retornando status 201

Usuário e Endereço encontrados com sucesso, retornando status 200


E usaremos as anotações para validar os dados e deixá los obrigatórios:

@NotBlank = Fazendo com que o usuário insira os dados obrigatoriamente.
@Email = Validar que os dados estejam no formato de email.
@CPF  = Validar que os dados estejam no formato de CPF.
@Column(unique=true) = Para que os dados sejam únicos na base de dados.

Classe Usuario:


Classe Endereco:


Vamos testar  o funcionamento delas:

Email com formato incorreto, retornando status 400



CPF cadastrado com sucesso, retornando status 400


E por fim terminaremos de implementar a nossa exceção de usuário não encontrado, para retornar o status adequado e a nossa mensagem amigável.

Criando a Classe StandardError e ResourceExceptionHandler




Vamos ao último teste, ao tentar buscar um usuário que não está cadastrado em nosso banco de dados:



Usuário não encontrado, retornando status 404 com mensagem amigável.
Finalizamos  o projeto proposto!!!
Implementação do sistema na Web
Pensando em uma API que lida com dados pessoais de usuários, uma abordagem é prover uma segurança para a aplicação. Podemos usar no Spring, o Spring Security  e o JWT que são uma dependências que vão fornecer a autenticação, autorização e proteção contra ataques maliciosos. E considerando o ambiente  para deploy podemos implementar na Web através do Heroku. O Heroku é uma plataforma na nuvem que faz deploy de várias aplicações back-end, seja para hospedagem, testes em produção ou escalar as suas aplicações.
 
Repositório com o código completo está disponível no link:
https://github.com/EwertonILima/orangeTalentsChallenge
 
Escrito por Ewerton Lima
