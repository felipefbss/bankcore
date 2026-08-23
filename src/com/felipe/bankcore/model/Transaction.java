package com.felipe.bankcore.model;

import java.time.LocalDate;
/**
 * representa uma movimentação(transaçõe) nas contas.
 * Possui um valor, um id, tipo de transação e a data em que foi realizada.
*/
public class Transaction {
    private static int nextId = 1;
    private int id;
    private double value;
    private LocalDate date;
    private TransactionType type;
    private String description;

    /**
     * Cria uma nova transação.
     * O date é gerado automaticamente pela classe localDate.now.
     * status é definido entre DEPOSITO E SAQUE.
     * @param value o valor da transação.
     * @param date a data da transação.
     * @param type o tipo é definido como DEPOSITO ou SAQUE.
     *
     */
    public Transaction(double value, LocalDate date, TransactionType type) {
        this.value = value;
        this.date = date;
        this.type = type;

        //pega o valor atual antes de incrementar, pra IDs começarem em 1;
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    /**
     * Representa a transação em formato de texto, para exibição no extrato.
     */
    @Override
    public String toString() {
        String resultado = getDescription() != null ? getDescription() : "Nenhuma descrição";
        return "valor: " + getValue() + " - "  + "tipo de transação: " + getType() + " - " + "data: " + getDate() + " - " + "descrição: " + resultado;
    }
}
