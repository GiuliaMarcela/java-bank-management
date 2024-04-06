/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.giulia.bank.management;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author giulia
 */
public class BankManagement {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            System.out.println("Bem-vindo(a) ao Unopar Benqui");

            System.out.println("Informe o seu nome");
            String name = sc.nextLine();

            System.out.println("Informe o seu CPF");
            String cpf = sc.nextLine();

            do {
                if (!isValid(cpf)) {
                    System.out.println("CPF inválido. Tipos suportados são: 12345678912 ou 123.456.789-12");
                    System.out.println("Encerrando o programa");
                    return;
                }

                Account currAccount = new Account(name, cpf);

                System.out.println("Escolha uma opção:");
                System.out.println("1 - Consultar Saldo");
                System.out.println("2 - Realizar Depósito");
                System.out.println("3 - Realizar Saque");
                System.out.println("4 - Encerrar");

                System.out.println("Opção escolhida: ");
                int option;

                try {
                    option = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida! Tente novamente.");
                    sc.nextLine();
                    continue;
                }

                switch (option) {
                    case 1:
                        currAccount.showBalance();
                        break;
                    case 2:
                        System.out.println("Digite o valor a depositar: ");
                        BigDecimal depositValue = sc.nextBigDecimal();
                        currAccount.deposit(depositValue);
                        break;
                    case 3:
                        System.out.println("Digite o valor a sacar: ");
                        BigDecimal withdrawValue = sc.nextBigDecimal();
                        currAccount.withdraw(withdrawValue);
                        break;
                    case 4:
                        running = false;
                        System.out.println("Encerrando a aplicação. Obrigada!");
                        break;
                    default:
                        System.out.println("Opção inexistente. Tente novamente.");
                        break;
                }
            } while (running);
        }
    }

    public static boolean isValid(String cpf) {
        Pattern pattern = Pattern.compile("\\b\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\\b|\\b\\d{11}\\b");

        return pattern.matcher(cpf).matches();
    }

    public static class Account {
        private final String name;
        private final String cpf;
        private BigDecimal balance;

        public Account(String name, String cpf) {
            this.name = name;
            this.cpf = cpf;
            this.balance = BigDecimal.ZERO;
        }

        @Override
        public String toString() {
            return String.format("{ %n \"name\": \"%s\",%n \"cpf\": \"%s\",%n \"balance\": %s %n}", this.name, this.cpf, this.balance);
        }

        public void showBalance() {
            System.out.println("Olá " + this.name);
            System.out.println("O saldo atual é " + this.balance);
        }

        public void deposit(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Não foi possível completar esta ação. Apenas valores maiores que 0 são permitidos.");
                return;
            }

            System.out.println("Por favor, aguarde.. Estamos realizando o depósito!");
            this.balance = this.balance.add(amount);
            System.out.printf("O depósito de R$ %s foi realizado com sucesso.%n", amount);
            showBalance();
        }

        public void withdraw(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(this.balance) <= 0) {
                this.balance = this.balance.subtract(amount);
                System.out.printf("O saque de R$ %s foi realizado com sucesso.%n", amount);
                showBalance();
                return;
            }

            System.out.println("Não foi possível completar esta ação. Saldo insuficiente.");
        }
    }
}
