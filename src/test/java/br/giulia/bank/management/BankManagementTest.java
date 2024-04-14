package br.giulia.bank.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static br.giulia.bank.management.BankManagement.Option.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankManagementTest {

    @Nested
    class AccountTest {
        @Test
        @DisplayName("Validar conta com saldo inicial quando realizar depósito positivo o saldo deve aumentar")
        void givenAccountWithInitialBalance_whenDepositPositiveAmount_thenBalanceIncreases() {
            BankManagement.Account account = new BankManagement.Account("Alice", "Smith", "12345678912");
            BigDecimal initialBalance = BigDecimal.valueOf(100);
            account.deposit(initialBalance);

            BigDecimal depositAmount = BigDecimal.valueOf(50);
            account.deposit(depositAmount);

            BigDecimal expectedBalance = initialBalance.add(depositAmount);
            Assertions.assertEquals(expectedBalance, account.getBalance());
        }

        @Test
        @DisplayName("Validar que ao tentar realizar depósito com valor negativo mensagem de erro deve ser exibida")
        void givenAccountWithInitialBalance_whenDepositNegativeAmount_thenReturnsErrorMessage() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            BankManagement.Account account = new BankManagement.Account("Alice", "Smith", "12345678912");
            BigDecimal initialBalance = BigDecimal.valueOf(-100);
            account.deposit(initialBalance);

            System.setOut(originalOut);
            String consoleOutput = outputStream.toString().trim();

            String expectedOutput = "Não foi possível completar esta ação. Apenas valores maiores que 0 são permitidos.";

            Assertions.assertEquals(expectedOutput, consoleOutput);
            Assertions.assertEquals(BigDecimal.ZERO, account.getBalance());
        }

        @Test
        @DisplayName("Validar conta com saldo suficiente quando realizar saque o saldo deve diminuir")
        void givenAccountWithSufficientBalance_whenWithdrawValidAmount_thenBalanceDecreases() {
            BankManagement.Account account = new BankManagement.Account("Charlie", "Doe", "45678912345");
            BigDecimal initialBalance = BigDecimal.valueOf(300);
            account.deposit(initialBalance);

            BigDecimal withdrawalAmount = BigDecimal.valueOf(100);
            account.withdraw(withdrawalAmount);

            BigDecimal expectedBalance = initialBalance.subtract(withdrawalAmount);
            Assertions.assertEquals(expectedBalance, account.getBalance());
        }

        @Test
        @DisplayName("Validar conta com saldo quando realizar saque maior do que saldo mensagem de erro deve ser exibida")
        void givenAccountWithInsufficientBalance_whenWithdrawAmount_thenReturnsErrorMessage() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            BankManagement.Account account = new BankManagement.Account("David", "Bowie", "78912345678");
            BigDecimal initialBalance = BigDecimal.valueOf(400);
            account.deposit(initialBalance);

            BigDecimal withdrawalAmount = BigDecimal.valueOf(500);
            account.withdraw(withdrawalAmount);

            System.setOut(originalOut);
            String consoleOutput = outputStream.toString().trim();

            String expectedOutput = "Não foi possível completar esta ação. Saldo insuficiente.";

            Assertions.assertEquals(initialBalance, account.getBalance());
            Assertions.assertTrue(consoleOutput.contains(expectedOutput));
        }

        @Test
        @DisplayName("Validar retorno do método toString()")
        void givenAccount_whenToStringCalled_thenReturnsExpectedFormat() {
            BankManagement.Account account = new BankManagement.Account("Frank", "Sinatra", "55566677700");

            String accountInfo = account.toString();

            String expectedInfo = String.format(
                    "{ %n \"name\": \"%s\",%n \"lastname\": \"%s\", %n \"cpf\": \"%s\",%n \"balance\": %s %n}",
                    account.getName(),
                    account.getLastname(),
                    account.getCpf(),
                    account.getBalance()
            );

            Assertions.assertEquals(expectedInfo, accountInfo);
        }
    }

    @Nested
    class BankUtilityClassesTest {

        @Test
        @DisplayName("Validar que a saída do console deve exibir as opções de menu disponíveis")
        void givenConsoleOutput_whenDisplayMenuCalled_thenPrintsExpectedMenuOptions() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            BankManagement.displayMenu();

            System.setOut(originalOut);
            String consoleOutput = outputStream.toString().trim();

            String expectedOutput = """
                    Escolha uma opção:
                    1 - Consultar Saldo
                    2 - Realizar Depósito
                    3 - Realizar Saque
                    4 - Sair""";

            Assertions.assertEquals(expectedOutput, consoleOutput);
        }

        @Test
        @DisplayName("Validar entrada do usuário igual a 1 deve retornar 'Consultar Saldo'")
        void givenUserInputEquals1_whenGetSelectedOptionCalled_thenReturnsCheckBalanceOption() {
            Scanner scannerMock = mock(Scanner.class);

            when(scannerMock.nextInt()).thenReturn(1);
            BankManagement.Option selectedOption = BankManagement.getSelectedOption(scannerMock);

            Assertions.assertSame(CHECK_BALANCE, selectedOption);
        }

        @Test
        @DisplayName("Validar entrada do usuário igual a 2 deve retornar 'Realizar Depósito'")
        void givenUserInputEquals2_whenGetSelectedOptionCalled_thenReturnsMakeDepositOption() {
            Scanner scannerMock = mock(Scanner.class);

            when(scannerMock.nextInt()).thenReturn(2);
            BankManagement.Option selectedOption = BankManagement.getSelectedOption(scannerMock);

            Assertions.assertSame(MAKE_DEPOSIT, selectedOption);
        }

        @Test
        @DisplayName("Validar entrada do usuário igual a 3 deve retornar 'Realizar Saque'")
        void givenUserInputEquals3_whenGetSelectedOptionCalled_thenReturnsMakeWithdrawalOption() {
            Scanner scannerMock = mock(Scanner.class);

            when(scannerMock.nextInt()).thenReturn(3);
            BankManagement.Option selectedOption = BankManagement.getSelectedOption(scannerMock);

            Assertions.assertSame(MAKE_WITHDRAWAL, selectedOption);
        }

        @Test
        @DisplayName("Validar entrada do usuário igual a 4 deve retornar 'Sair'")
        void givenUserInputEquals4_whenGetSelectedOptionCalled_thenReturnsExitOption() {
            Scanner scannerMock = mock(Scanner.class);

            when(scannerMock.nextInt()).thenReturn(4);
            BankManagement.Option selectedOption = BankManagement.getSelectedOption(scannerMock);

            Assertions.assertSame(EXIT, selectedOption);
        }

        @Test
        @DisplayName("Validar entrada do usuário inválida deve lançar IllegalArgumentException")
        void givenUserInputInvalidOption_whenGetSelectedOptionCalled_thenThrowsIllegalArgumentException() {
            Scanner scannerMock = mock(Scanner.class);

            when(scannerMock.nextInt()).thenReturn(5);

            Exception exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> BankManagement.getSelectedOption(scannerMock)
            );

            String expectedMessage = "Opção inválida";

            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Nested
    class NameValidatorTest {
        @Test
        @DisplayName("Validar que nome com apenas espaços em branco retorna falso")
        void givenNameWithOnlyWhiteSpaces_whenIsNameValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isNameValid(" "));
        }

        @Test
        @DisplayName("Validar que nome vazio retorna falso")
        void givenEmptyName_whenIsNameValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isNameValid(""));
        }
    }

    @Nested
    class CPFValidatorTest {

        @Test
        @DisplayName("Validar CPF com pontos e hífen retorna verdadeiro")
        void givenCPFWithDotsAndHyphen_whenIsCPFValidCalled_thenReturnsTrue() {
            Assertions.assertTrue(BankManagement.isCPFValid("123.456.789-11"));
        }

        @Test
        @DisplayName("Validar CPF apenas com números retorna verdadeiro")
        void givenCPFWithOnlyNumbers_whenIsCPFValidCalled_thenReturnsTrue() {
            Assertions.assertTrue(BankManagement.isCPFValid("12345678911"));
        }

        @Test
        @DisplayName("Validar CPF contendo letras retorna falso")
        void givenCPFWithLetters_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid("1231a4578b1"));
        }

        @Test
        @DisplayName("Validar CPF contendo espaços em branco retorna falso")
        void givenCPFContainsWhiteSpaces_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid("123 456 789 61"));
        }

        @Test
        @DisplayName("Validar CPF contendo menos que 11 caracteres retorna falso")
        void givenCPFWithLessThan11Characters_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid("123456789"));
        }

        @Test
        @DisplayName("Validar CPF contendo mais que 11 caracteres retorna falso")
        void givenCPFWithMoreThan11Characters_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid("1234567891011"));
        }

        @Test
        @DisplayName("Validar CPF em branco retorna falso")
        void givenEmptyCPF_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid(""));
        }

        @Test
        @DisplayName("Validar CPF com apenas espaços em branco retorna falso")
        void givenCPFWithOnlyWhiteSpaces_whenIsCPFValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isCPFValid(" "));
        }
    }
}
