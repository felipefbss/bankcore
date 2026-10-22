package com.felipe.bankcore.model;

import com.felipe.bankcore.exception.InsufficientBalanceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * representa uma conta bancária.
 * possui saldo, dono e uma lista de transações.
 * possui métodos deposito, saque e calcular rendimento.
 */

public abstract class Account {
    private double balance;
    private Customer owner;
    private List<Transaction> transactionList = new ArrayList<>();

    public abstract double calcularRendimento();

    /**
     * cria um depósito
     * @param amount é o valor do depósito.
     * soma o valor do saldo com o valor do depósito.
     * cria uma transação.
     * é adicionado na lista de transações.
     */
    public Transaction deposit(double amount){
        balance += amount;
        Transaction transaction = new Transaction(amount, LocalDate.now(), TransactionType.DEPOSITO);
        transactionList.add(transaction);
        return transaction;
    }

    /**
     * Realiza um saque na conta.
     * Se o saldo for maior ou igual ao valor do saque, o valor é subtraído do saldo,
     * uma transação de saque é criada e adicionada à lista de transações.
     * @param amount valor do saque
     * @return a transação de saque registrada
     * @throws InsufficientBalanceException se o saldo for menor que o valor do saque
     */
    public Transaction withdraw(double amount){
        if( amount <= balance){
            balance -= amount;
            Transaction transaction = new Transaction(amount, LocalDate.now(), TransactionType.SAQUE);
            transactionList.add(transaction);
            return transaction;
        }else {
            throw new InsufficientBalanceException("O valor do saque é maior do que o saldo!");
        }
    }

    /**
     * faz uma transferência de uma conta para a outra,
     * reaproveitando os métodos Withdraw e deposit para entrada e saída.
     * @param destinationAccount a conta que vai receber o depósito.
     * @param amount o valor da transferência
     */
    public void transfer(Account destinationAccount, double amount) {
        Transaction trasacaoSaida = this.withdraw(amount);
        Transaction trasacaoEntrada = destinationAccount.deposit(amount);
        trasacaoSaida.setDescription("Transferência enviada para " + destinationAccount.getOwner().getName());
        trasacaoEntrada.setDescription("Transferência recebida de " + getOwner().getName());
        System.out.println("Transferência no valor de: " + String.format("R$%.2f", amount) + " para o " + destinationAccount.getOwner().getName() + " Realizado com sucesso!");
    }

    /**
     * Calcula o rendimento da conta e aplica ele diretamente no saldo, via deposit().
     * adiciona uma descrição desse rendimento.
     * @return o valor do rendimento calculado.
     */
    public double aplicarRendimento() {
        double rendimento = calcularRendimento();
        Transaction transacaoRendimento = deposit(rendimento);
        transacaoRendimento.setDescription("Rendimento aplicado");
        return rendimento;
    }

    /**
     * Cria uma nova conta, vinculando o cliente dono no momento da criação.
     * @param owner o cliente dono da conta.
     */
    public Account(Customer owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
