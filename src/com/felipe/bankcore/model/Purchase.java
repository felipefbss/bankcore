package com.felipe.bankcore.model;

import java.time.LocalDate;

/**
 * Representa uma compra realizada no cartão de credito.
 * Possui um valor, uma descrição, números de pareclas e a data em que foi realizada.
 * Toda compra nasce como PENDING e é avaliada pelo Card (aprovada ou recusada).
 */
public class Purchase  implements Comparable<Purchase>{
    private double value;
    private String description;
    private LocalDate date;
    private PurchaseStatus status;
    private int installments;

    /**
     * Cria uma nova compra.
     * O date é gerado automaticamente pela classe localDate.now.
     * status nasce como PENDING.
     * @param value o valor da compra.
     * @param description a descrição da compra.
     * @param installments número de parcelas da compra.
     *
     */
    public Purchase(double value, String description,int installments){
        if(value < 0){
            throw new IllegalArgumentException("Valores negativos são inválidos: " + value);
        }else if(installments <= 0){
            throw new IllegalArgumentException("Números de pareclas inválido: " + installments);
        }
        this.status = PurchaseStatus.PENDING;
        this.value = value;
        this.description = description;
        this.date = LocalDate.now();
        this.installments = installments;
    }

    /**
     * Marca a compra como aprovada.
     */
    public void approve(){
        this.status = PurchaseStatus.APPROVED;
    }

    /**
     * Marca a compra como reprovada.
     */
    public void decline(){
        this.status = PurchaseStatus.DECLINED;
    }

    /**
     * calcula o valor de cada parcela.
     * @return o valor de cada parcela.
     */
    public double getValuePerInstallment(){
        return value / installments;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public int getInstallments() {
        return installments;
    }

    @Override
    public String toString() {
        return getDescription() + " - " + String.format("R$%.2f", getValue()) + " - " +  getDate() + " - Status: " + getStatus() + " -" + " Números de parcelas: " + getInstallments() + "X" + " de R$" + getValuePerInstallment();
    }

    // ordena por data (ordem natural do histórico de compras)
    @Override
    public int compareTo(Purchase other) {
        return this.date.compareTo(other.date);
    }
}
