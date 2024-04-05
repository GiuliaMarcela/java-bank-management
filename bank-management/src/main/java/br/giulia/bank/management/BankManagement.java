/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.giulia.bank.management;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author giulia
 */
public class BankManagement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Bem-vindo(a) ao Unopar Benqui");

        System.out.println("Informe o seu nome");
        String name = sc.nextLine();

        System.out.println("Informe o seu CPF");
        String cpf = sc.nextLine();

        Account currAccount = new Account(name, cpf);

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Consultar Saldo");
            System.out.println("2 - Realizar Depósito");
            System.out.println("3 - Realizar Saque");
            System.out.println("4 - Encerrar");

            System.out.println("Opção escolhida: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    currAccount.showBalance();
                    break;
                case 2:
                    System.out.print("Digite o valor a depositar: ");
                    BigDecimal depositValue = new BigDecimal(sc.next());
                    currAccount.deposit(depositValue);
                    break;
                case 3:
                    System.out.print("Digite o valor a sacar: ");
                    BigDecimal withdrawValue = new BigDecimal(sc.next());
                    currAccount.withdraw(withdrawValue);
                    break;
                case 4:
                    running = false;
                    System.out.println("Encerrando a aplicação. Obrigada!");
                    sc.close();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    sc.close();
                    break;
            }
        } while (running);
    }

    protected static class Account {
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
            System.out.printf("Olá %s! %n", this.name);
            System.out.printf("%n O saldo atual é %s", this.balance);
        }

        public void deposit(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Não foi possível completar esta ação. Apenas valores maiores que 0 são permitidos.");
                return;
            }

            System.out.println("Por favor, aguarde.. Estamos realizando o depósito!");
            this.balance = this.balance.add(amount);
            System.out.printf("O depósito de R$ %s foi realizado com sucesso.", amount);
            showBalance();
        }

        public void withdraw(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(this.balance) <= 0) {
                this.balance = this.balance.subtract(amount);
                System.out.printf("O saque de R$ %s foi realizado com sucesso.", amount);
                showBalance();
                return;
            }

            System.out.println("Não foi possível completar esta ação. Saldo insuficiente.");
        }
    }
}
