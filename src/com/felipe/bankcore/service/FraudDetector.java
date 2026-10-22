package com.felipe.bankcore.service;

import com.felipe.bankcore.model.Transaction;

import java.util.List;

/**
 * Detecta trasações suspeitas com base no valor.
 * Uma trasação é considerada suspeita quando seu valor é maior ou igual a R$ 5.000,00.
 */
public class FraudDetector {
    /**
     * Valor limite a partir do qual uma transação é considerada suspeita.
     * Declarado como constante para que a regra fique em um único lugar.
     */
    private static final double HIGH_VALUE_LIMIT = 5000.0;

    /**
     * Verifica se uma transação é suspeita.
     * Delega a verificação ao método que recebe apenas o valor,
     * mantendo a regra de suspeita em um único lugar.
     *
     * @param transaction transação a ser verificada.
     * @return {@code true} se o valor for maior ou igual ao limite; {@code false} caso contrário.
     */
    public boolean isSuspicious(Transaction transaction) {
        return this.isSuspicious(transaction.getValue());
    }

    /**
     * Verifica se um valor é suspeito, aplicando a regra do limite.
     * Permite checar uma operação antes de a transação ser criada,
     * quando só o valor informado está disponível.
     *
     * @param value valor da operação a ser verificado.
     * @return {@code true} se o valor for maior ou igual ao limite; {@code false} caso contrário.
     */
    public boolean isSuspicious(double value) {
        return value >= HIGH_VALUE_LIMIT;
    }

    /**
     * Filtra uma lista de transações e devolve apenas as suspeitas.
     *
     * @param transactions Lista de Transações a ser analisada.
     * @return Nova lista contendo somente as transações suspeitas (vazia se não houver nenhuma)
     */
    public List<Transaction> detect(List<Transaction> transactions) {
        return transactions.stream()
                .filter(this::isSuspicious)
                .toList();
    }
}
