package br.giulia.bank.management;

import org.junit.jupiter.api.*;

class BankManagementTest {

    @Nested
    class CPFValidatorTest {
        
        @Test
        @DisplayName("Validar CPF com pontos e hífen retorna verdadeiro")
        void givenCPFWithDotsAndHyphen_whenIsValidCalled_thenReturnsTrue() {
            Assertions.assertTrue(BankManagement.isValid("123.456.789-11"));
        }

        @Test
        @DisplayName("Validar CPF apenas com números retorna verdadeiro")
        void givenCPFWithOnlyNumbers_whenIsValidCalled_thenReturnsTrue() {
            Assertions.assertTrue(BankManagement.isValid("12345678911"));
        }

        @Test
        @DisplayName("Validar CPF contendo letras retorna falso")
        void givenCPFWithLetters_whenIsValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isValid("1231a4578b1"));
        }

        @Test
        @DisplayName("Validar CPF contendo espaços em branco retorna falso")
        void givenCPFWithWhiteSpaces_whenIsValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isValid("123 456 789 61"));
        }

        @Test
        @DisplayName("Validar CPF contendo menos que 11 caracteres retorna falso")
        void givenCPFWithLessThan11Characters_whenIsValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isValid("123456789"));
        }

        @Test
        @DisplayName("Validar CPF contendo mais que 11 caracteres retorna falso")
        void givenCPFWithMoreThan11Characters_whenIsValidCalled_thenReturnsFalse() {
            Assertions.assertFalse(BankManagement.isValid("1234567891011"));
        }
    }
}