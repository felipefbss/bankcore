package com.felipe.bankcore.model;

import com.felipe.bankcore.exception.SavingsAccountAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cliente do sistema bancário.
 * Cada cliente pode possuir múltiplos cartões de crédito.
 */
public class Customer {
    //contador compartilhado entre todos os clientes,usado pra gerar IDs únicos automaticamnete;
    private static int nextId = 1;
    private CheckingAccount checkingAccount;
    private String name;
    private int id;
    private SavingsAccount savingsAccount;

    //cliente pode ter múltiplos cartões;lista começa vazia e cresce conforme cartões são adicionados;
    private List<Card> cards = new ArrayList<>();


    /**
     * Cria um novo cliente com o nome informado.
     * O id é gerado automaticamente pela classe e não pode ser definido manualmente.
     *
     * @param name o nome do cliente
     */
    public Customer(String name){
        this.name = name;
        this.checkingAccount = new CheckingAccount(this);

        //pega o valor atual antes de incrementar, pra IDs começarem em 1;
        this.id = nextId;
        nextId++;

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    /**
     * Retorna a lista de cartões deste cliente.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Soma o valor total gasto pelo cliente, somando as compras aprovadas
     * de todos os seus cartões.
     * @return o valor total gasto pelo cliente.
     */
    public double getTotalSpent(){
        double totalSpent = 0;
        for(Card percorreListCards: cards){
            for(Purchase percorreListaAprovada: percorreListCards.getApprovedPurchases()){
                totalSpent += percorreListaAprovada.getValue();
            }
        }
        return totalSpent;
    }

    /**
     * Percorre a lista para achar o cartão com o number informado.
     * @param number number do cartão a ser buscado.
     * @return o cartão encontrado, ou null se nenhum cartão tiver esse number.
     */
    public Card findCardByNumber(int number){
        for(Card card : cards){
            if(card.getNumber() == number){
                return card;
            }
        }
        return null;
    }

    /**
     * Cria uma conta poupança para o cliente.
     * caso ela já tenha uma conta poupança, é lançada uma Exception personalizada.
     * @return uma conta poupança.
     */
    public SavingsAccount abrirPoupanca(){
        if(this.savingsAccount != null){
             throw new SavingsAccountAlreadyExistsException("Não é possivel criar duas contas poupanças.");
        } else{
            this.savingsAccount = new SavingsAccount(this);
            return savingsAccount;
        }
    }

    @Override
    public String toString() {
        return "name: " + getName() + " - " + "ID: " + getId() + " - " + "Cartões: " + getCards().size() + " - " + "total de gastos: " + String.format("R$%.2f", getTotalSpent());
    }
}