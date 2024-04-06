# Bank Management

Este projeto consiste na criação de uma aplicação Java para gerenciamento bancário, implementada em um único arquivo
Java
(main class), como proposto no enunciado da disciplina de Linguagem Orientada a Objetos.

## Enunciado do Relatório

Utilizando os principais conceitos do paradigma de Orientação a Objetos, crie uma pequena aplicação de gerenciamento
bancário que possibilite ao usuário informar seu nome, sobrenome e CPF.

Além disso, a aplicação deverá possibilitar ao
usuário consultar saldo, realizar depositos e saques. Esses procedimentos devem se repetir até que o usuário escolha
encerrar o uso da aplicação.

- Contrua a aplicação em um único arquivo java
- No arquivo criado, você deverá construir:
    - A classe principal
    - Classe para dados pessoais e operações bancárias
    - Método para exibição do menu
- Exibição de um menu com opções para consulta de saldo, depósito, retirada e encerramento da aplicação.
- Utilização de estruturas de decisão (switch...case, do...while) para tratar as escolhas do usuário.
- Encerramento da execução da aplicação com uma mensagem de despedida.

### Checklist

- [x] Instalação do NetBeans.
- [x] Ambiente de desenvolvimento NetBeans e JDK devidamente configurado e funcionando.
- [x] Criação de um projeto Java Application em Java com Maven.
- [x] Implementação do código em Java utilizando conceitos da programação orientada a objetos.

## Projeto

### Estrutura do Projeto

![Captura de tela da IDE Apache NetBeans com o console expandido, exibindo as mensagens de saída da aplicação e realização de um depósito de 100 reais](./assets/images/net-beans-application.png)

O projeto Java Application contém os seguintes arquivos:

- [BankManagement](./src/main/java/br/giulia/bank/management/BankManagement.java): Projeto principal que contém o
  arquivo principal contendo main e a lógica da aplicação.
- [README.md](README.md): Documentação do projeto.

### Tecnologias utilizadas

- Java 21
- Apache NetBeans IDE

### Como usar

Para usar o projeto de gerenciamento bancário implementado em Java descrito no README, siga as instruções abaixo para
configurar e executar a aplicação:

#### Pré-requisitos

- Java Development Kit 21
- IDE Java, como o NetBeans, Eclipse ou IntelliJ IDEA, instalada em seu sistema para facilitar o desenvolvimento e
  execução do código.

#### Passos para execução

1. Clone este repositório em sua máquina
2. Abra o projeto na IDE de sua preferência
3. Localize o arquivo `BankManagement.java`, abra-o e clique em "Run" na barra de ferramentas ou menu de contexto da sua
   IDE.
4. Quando a aplicação iniciar, siga as instruções no console para inserir seu nome, sobrenome e CPF.
    - Após inserir seus dados, um menu será exibido com opções como consulta de saldo, depósito, retirada e encerramento
      da aplicação.
    - Escolha a opção desejada digitando o número correspondente no console e pressionando Enter.
    - Siga as instruções no console para inserir valores para depósito, saque, etc., quando solicitado.

Ao seguir esses passos, você será capaz de configurar, compilar e executar a aplicação de gerenciamento bancário em Java
conforme descrito no README.
