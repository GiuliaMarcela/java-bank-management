package br.giulia.bank.management;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.exit;

/**
 * Classe desenvolvida para obtenção de nota para a disciplina de Linguagem Orientada a Objetos
 *
 * @author Giulia Marcela Mendes de Arruda
 * @version 1.0
 */
@SuppressWarnings("java:S106") // disable warning from sonarlint
public class BankManagement {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;

            System.out.println("Bem-vindo(a) ao Unopar Benqui");

            System.out.println("Informe o seu nome");
            String name = sc.nextLine();

            if (!isNameValid(name)) {
                System.out.println("O nome é um campo obrigatório! Encerrando o programa.");
                return;
            }
            
            System.out.println("Informe o seu sobrenome");
            String lastname = sc.nextLine();

            if (!isNameValid(lastname)) {
                System.out.println("O sobrenome é um campo obrigatório! Encerrando o programa.");
                return;
            }

            System.out.println("Informe o seu CPF");
            String cpf = sc.nextLine();

            if (!isCPFValid(cpf)) {
                System.out.println("""
                        CPF com formato inválido!
                        Formatos suportados são:
                          - 12345678912
                          - 123.456.789-12
                        Encerrando o programa.""");
                return;
            }

            Account currAccount = new Account(name, lastname, cpf);

            do {
                displayMenu();
                Option selectedOption = getSelectedOption(sc);

                switch (selectedOption) {
                    case CHECK_BALANCE -> currAccount.showBalance();
                    case MAKE_DEPOSIT -> {
                        System.out.println("Digite o valor a depositar: ");
                        BigDecimal depositValue = sc.nextBigDecimal();
                        currAccount.deposit(depositValue);
                    }
                    case MAKE_WITHDRAWAL -> {
                        System.out.println("Digite o valor a sacar: ");
                        BigDecimal withdrawValue = sc.nextBigDecimal();
                        currAccount.withdraw(withdrawValue);
                    }
                    case EXIT -> {
                        running = false;
                        System.out.println("Encerrando a aplicação. Obrigada!");
                    }
                    default -> System.out.println("Opção inexistente. Tente novamente.");
                }
            } while (running);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            exit(1);
        }
    }

    public static boolean isCPFValid(String cpf) {
        Pattern pattern = Pattern.compile("\\b\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\\b|\\b\\d{11}\\b");
        return pattern.matcher(cpf).matches();
    }

    public static boolean isNameValid(String name) {
        return !name.isEmpty() && !name.isBlank();
    }

    public static Option getSelectedOption(Scanner sc) {
        int optionValue = sc.nextInt();
        return Option.getByValue(optionValue);
    }

    public static void displayMenu() {
        System.out.println("Escolha uma opção:");
        for (Option option : Option.values()) {
            System.out.println(option.getValue() + " - " + option.getLabel());
        }
    }

    public enum Option {
        CHECK_BALANCE(1, "Consultar Saldo"),
        MAKE_DEPOSIT(2, "Realizar Depósito"),
        MAKE_WITHDRAWAL(3, "Realizar Saque"),
        EXIT(4, "Sair");

        private final int value;
        private final String label;

        Option(int value, String label) {
            this.value = value;
            this.label = label;
        }

        public static Option getByValue(int value) {
            for (Option option : Option.values()) {
                if (option.getValue() == value) {
                    return option;
                }
            }

            throw new IllegalArgumentException("Opção inválida");
        }

        public int getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class Account {
        private final String name;
        private final String lastname;
        private final String cpf;
        private BigDecimal balance;

        public Account(String name, String lastname, String cpf) {
            this.name = name;
            this.lastname = lastname;
            this.cpf = cpf;
            this.balance = BigDecimal.ZERO;
        }

        @Override
        public String toString() {
            return String.format("{ %n \"name\": \"%s\",%n \"lastname\": \"%s\", %n \"cpf\": \"%s\",%n \"balance\": %s %n}", this.name, this.lastname, this.cpf, this.balance);
        }

        public void showBalance() {
            System.out.printf("%nOlá %s %s!%n", this.name, this.lastname);
            System.out.printf("Saldo atual: R$ %.2f%n", this.balance);
        }

        public void deposit(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Não foi possível completar esta ação. Apenas valores maiores que 0 são permitidos.");
                return;
            }

            System.out.println("Por favor, aguarde.. Estamos realizando o depósito!");
            this.balance = this.balance.add(amount);
            System.out.printf("O depósito de R$ %.2f foi realizado com sucesso.%n", amount);
            showBalance();
        }

        public void withdraw(BigDecimal amount) {
            if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(this.balance) <= 0) {
                this.balance = this.balance.subtract(amount);
                System.out.printf("O saque de R$ %.2f foi realizado com sucesso.%n", amount);
                showBalance();
                return;
            }

            System.out.println("Não foi possível completar esta ação. Saldo insuficiente.");
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public String getName() {
            return name;
        }
        
        public String getLastname() {
            return lastname;
        }

        public String getCpf() {
            return cpf;
        }
    }
}
