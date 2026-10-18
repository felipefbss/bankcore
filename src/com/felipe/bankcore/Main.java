package com.felipe.bankcore;
import com.felipe.bankcore.exception.InsufficientBalanceException;
import com.felipe.bankcore.exception.SavingsAccountAlreadyExistsException;
import com.felipe.bankcore.model.*;
import com.felipe.bankcore.service.Bank;
import com.felipe.bankcore.service.FraudDetector;

import java.util.Collections;
import java.util.Scanner;

/**
 * Ponto de entrada do sistema de cartão de crédito.
 * Exibe o menu interativo e direciona para as operações do sistema.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        FraudDetector detector = new FraudDetector();
        int option = 1;

        while(option != 0){
            System.out.println("=== SISTEMA DE CARTÃO DE CRÉDITO ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Cadastrar cartão para um cliente");
            System.out.println("3 - Cadastrar compra");
            System.out.println("4 - Ver compras aprovadas");
            System.out.println("5 - Ver compras recusadas");
            System.out.println("6 - Ver histórico completo (ordenado por data)");
            System.out.println("7 - Ver maior compra");
            System.out.println("8 - Ver menor compra");
            System.out.println("9 - Ver valor total gasto por cliente");
            System.out.println("10 - Ver limite disponível de um cartão");
            System.out.println("--- Conta Bancária ---");
            System.out.println("11 - Ver saldo da conta corrente");
            System.out.println("12 - Criar conta poupança");
            System.out.println("13 - Aplicar rendimento na conta corrente");
            System.out.println("14 - Aplicar rendimento na conta poupança");
            System.out.println("15 - Fazer uma transferência para outra conta");
            System.out.println("16 - Ver a lisa de transferências");
            System.out.println("17 - Depositar em uma conta");
            System.out.println("18 - Sacar de uma conta");
            System.out.println("0 - Sair");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1: {
                    System.out.println("Digite o nome do cliente:");
                    String name = scanner.nextLine();
                    Customer customer = bank.addCustomer(name);
                    System.out.println("Cliente cadastrado com sucesso!");
                    System.out.println(customer);
                    break;
                }
                case 2: {
                    System.out.println("Qual o Id do cliente:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Cliente não existe.");
                    }else{
                        Card card = new Card(customer);
                        System.out.println("Cartão criado com sucesso.");
                        System.out.println(card);
                    }
                    break;
                }
                case 3: {
                    System.out.println("Qual o Id do cliente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Cliente não existe");
                    }else{
                       if(customer.getCards().isEmpty()){
                           System.out.println("Este cliente não tem cartão.");
                       }else{
                           for(Card card: customer.getCards()){
                               System.out.println(card);
                           }
                           System.out.println("Qual número de cartão o usuário quer usar ? ");
                           int number = scanner.nextInt();
                           scanner.nextLine();
                           Card selectedCard = customer.findCardByNumber(number);
                           if(selectedCard == null){
                               System.out.println("Cartão não encontrado.");
                           }else{
                               System.out.println("Qual o valor da compra ? ");
                               double value = scanner.nextDouble();
                               scanner.nextLine();
                               System.out.println("Qual a descrição da compra ? ");
                               String description = scanner.nextLine();
                               System.out.println("Qual o número de parcelas ?");
                               int installments = scanner.nextInt();
                               scanner.nextLine();
                               Purchase purchase = new Purchase(value,description,installments);
                               selectedCard.addPurchase(purchase);
                               System.out.println(purchase);
                           }
                       }
                    }
                    break;
                }
                case 4: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if (customer == null) {
                        System.out.println("Cliente não existe.");
                    } else {
                        if (customer.getCards().isEmpty()) {
                            System.out.println("Este cliente não tem cartão.");
                        } else {
                            for (Card card : customer.getCards()) {
                                System.out.println(card);
                            }
                            System.out.println("Qual número de cartão o usuário quer usar ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if (selectedCard == null) {
                                System.out.println("Cartão não encontrado");
                            } else {
                                if (selectedCard.getApprovedPurchases().isEmpty()) {
                                    System.out.println("Não existe compras aprovadas nesse cartão");
                                } else {
                                    for (Purchase approvedPurchase : selectedCard.getApprovedPurchases()) {
                                        System.out.println(approvedPurchase);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if (customer == null) {
                        System.out.println("Cliente não existe.");
                    } else {
                        if (customer.getCards().isEmpty()) {
                            System.out.println("Este cliente não tem cartão.");
                        } else {
                            for (Card card : customer.getCards()) {
                                System.out.println(card);
                            }
                            System.out.println("Qual número de cartão o usuário quer usar ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if (selectedCard == null) {
                                System.out.println("Cartão não encontrado");
                            } else {
                                if (selectedCard.getDeclinedPurchases().isEmpty()) {
                                    System.out.println("Não existe compras recusadas nesse cartão");
                                } else {
                                    for (Purchase declinedPurchase : selectedCard.getDeclinedPurchases()) {
                                        System.out.println(declinedPurchase);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 6: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Cliente não existe.");
                    }else{
                        if(customer.getCards().isEmpty()){
                            System.out.println("Este cliente não tem cartão.");
                        }else{
                            for(Card card: customer.getCards()){
                                System.out.println(card);
                            }
                            System.out.println("Qual cartão o usuário quer ver o histórico completo (ordenado por data) ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if(selectedCard == null){
                                System.out.println("Cartão não encontrado");
                            }else{
                                if(selectedCard.getShoppingList().isEmpty()){
                                    System.out.println("Não existe compras nesse cartão");
                                }else{
                                    Collections.sort(selectedCard.getShoppingList());
                                    for(Purchase purchase: selectedCard.getShoppingList()){
                                        System.out.println(purchase);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case 7: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Cliente não existe.");
                    }else{
                        if(customer.getCards().isEmpty()){
                            System.out.println("Este cliente não tem cartão.");
                        }else{
                            for(Card card: customer.getCards()){
                                System.out.println(card);
                            }
                            System.out.println("Qual cartão o usuário quer ver a maior compra ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if(selectedCard == null){
                                System.out.println("Cartão não encontrado");
                            }else{
                                Purchase purchase = Collections.max(selectedCard.getShoppingList(), new PurchaseValueComparator());
                                System.out.println(purchase);
                            }
                        }
                    }
                    break;
                }
                case 8: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Cliente não existe.");
                    }else{
                        if(customer.getCards().isEmpty()){
                            System.out.println("Este cliente não tem cartão.");
                        }else{
                            for(Card card: customer.getCards()){
                                System.out.println(card);
                            }
                            System.out.println("Qual cartão o usuário quer ver a menor compra ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if(selectedCard == null){
                                System.out.println("Cartão não encontrado");
                            }else{
                                Purchase purchase = Collections.min(selectedCard.getShoppingList(), new PurchaseValueComparator());
                                System.out.println(purchase);
                            }
                        }
                    }
                    break;
                }
                case 9: {
                    System.out.println("Qual o ID do cliente ?");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe.");
                    }else{
                        double selectedCustomer = customer.getTotalSpent();
                        System.out.println("Valor total gasto do cliente: " + formatarMoeda(selectedCustomer));
                    }
                    break;
                }
                case 10: {
                    System.out.println("Qual o Id do cliente ? ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe.");
                    }else{
                        if(customer.getCards().isEmpty()){
                            System.out.println("Este cliente não tem cartão.");
                        }else{
                            for(Card card: customer.getCards()){
                                System.out.println(card);
                            }
                            System.out.println("Qual cartão o usuário quer ver o limite disponível ? ");
                            int number = scanner.nextInt();
                            scanner.nextLine();
                            Card selectedCard = customer.findCardByNumber(number);
                            if(selectedCard == null){
                                System.out.println("Cartão não encontrado.");
                            }else{
                                System.out.println("Limite disponível: " + formatarMoeda(selectedCard.getAvailableLimit()) );
                            }
                        }
                    }
                    break;
                }
                case 11: {
                    System.out.println("Digite o Id do cliente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe.");
                    }else{
                        System.out.println("Saldo da conta corrente: " + formatarMoeda(customer.getCheckingAccount().getBalance()));
                    }
                    break;
                }
                case 12: {
                    System.out.println("Digite o Id do cliente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe");
                    }else{
                        try {
                            customer.abrirPoupanca();
                            System.out.println("Conta poupança criada com sucesso!");
                        } catch (SavingsAccountAlreadyExistsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 13: {
                    System.out.println("Digite o Id do cliente que deseja Aplicar rendimento na conta corrente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe");
                    }else{
                        double resultadoDoRendimento = customer.getCheckingAccount().aplicarRendimento();
                        System.out.println("Rendimento aplicado: " + formatarMoeda(resultadoDoRendimento));
                        System.out.println("Saldo atual: " + formatarMoeda(customer.getCheckingAccount().getBalance()));
                    }
                    break;
                }
                case 14: {
                    System.out.println("Digte o Id do cliente que deseja Aplicar rendimento na conta poupança:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe");
                    }else if(customer.getSavingsAccount() == null) {
                        System.out.println("Este cliente ainda não possui conta poupança.");
                    }else{
                        double resultadoDoRendimento = customer.getSavingsAccount().aplicarRendimento();
                        System.out.println("Rendimento aplicado: " + formatarMoeda(resultadoDoRendimento));
                        System.out.println("Saldo atual: " + formatarMoeda(customer.getSavingsAccount().getBalance()));
                    }
                    break;
                }
                case 15: {
                    System.out.println("Digite o Id do cliente que deseja fazer essa transferência: ");
                    int idOrigem = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o Id do cliente que vai receber essa transferência: ");
                    int idDestino = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer1 = bank.findCustomerById(idOrigem);
                    Customer customer2 = bank.findCustomerById(idDestino);
                    if (customer1 == null) {
                        System.out.println("Este cliente não existe");
                    } else if (customer2 == null) {
                        System.out.println("Este cliente não existe");
                    } else {
                        Account contaOrigem = escolherConta(customer1, scanner);
                        Account contaDestino = escolherConta(customer2, scanner);
                        if (contaOrigem == null || contaDestino == null) {
                            System.out.println("Uma das contas escolhidas não existe.");
                        } else {
                            System.out.println("Qual o valor que deseja transferir ? ");
                            double amount = scanner.nextDouble();

                            try {
                                contaOrigem.transfer(contaDestino, amount);
                            } catch (InsufficientBalanceException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                }
                case 16: {
                    System.out.println("Qual o Id do cliente que deseja ver o histórico ?");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null){
                        System.out.println("Este cliente não existe.");
                    }else {
                        System.out.println("Qual conta deseja ver a lista de transferências ?");
                        Account contaEscolhida = escolherConta(customer, scanner);
                        if(contaEscolhida == null){
                            System.out.println("Uma das contas escolhidas não existe.");
                        }else{
                            for(Transaction transacoes: contaEscolhida.getTransactionList()){
                                System.out.println(transacoes);
                            }
                        }
                    }
                    break;
                }
                case 17: {
                    System.out.println("Digite o Id do cliente que deseja fazer um depósito: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if(customer == null) {
                        System.out.println("Este cliente não existe.");
                    }else{
                        Account conta = escolherConta(customer, scanner);
                        if(conta == null){
                            System.out.println("Uma das contas escolhidas não existe.");
                        }else {
                            System.out.println("Qual o valor que deseja depositar ?");
                            double amount = scanner.nextDouble();
                            scanner.nextLine();
                            conta.deposit(amount);
                            System.out.println("Depósito realizado com sucesso!");
                        }
                    }
                    break;
                }
                case 18: {
                    System.out.println("Digite o Id do cliente que deseja fazer um saque: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if (customer == null) {
                        System.out.println("Este cliente não existe.");
                    } else {
                        Account conta = escolherConta(customer, scanner);
                        if (conta == null) {
                            System.out.println("Uma das contas escolhidas não existe.");
                        } else {
                            try {
                                System.out.println("Qual o valor que deseja sacar ?");
                                double amount = scanner.nextDouble();
                                scanner.nextLine();
                                if (confirmHighValue(amount ,detector ,scanner)) {
                                    conta.withdraw(amount);
                                    System.out.println("Saque realizado com sucesso!");
                                }
                            }catch (InsufficientBalanceException e){
                                System.out.println(e.getMessage());
                            }

                        }
                    }
                    break;
                }
                default: {
                    System.out.println("Número inválido.");
                    break;
                }
            }

        }
    }

    /**
     * Verifica se uma operação pode prosseguir, considerando o valor informado.
     * Valores normais são liberados sem perguntar nada. Se o valor for considerado
     * suspeito, pede confirmação ao usuário antes de liberar a operação.
     *
     * @param value valor da operação a ser verificado.
     * @param detector detector de fraude usado para avaliar se o valor é suspeito.
     * @param scanner leitor usado para receber a resposta do usuário (1 para sim, 2 para não)
     * @return {@code true} se o valor for normal ou o usuário confirmar a operação;
     *         {@code false} se o usuário recusar ou digitar uma opção inválida
     */
    private static boolean confirmHighValue(double value, FraudDetector detector ,Scanner scanner) {
        boolean valorSuspeito = detector.isSuspicious(value);
        if(!valorSuspeito) {
            return true;
        } else {
            System.out.println("Este valor está acima do normal, deseja continuar mesmo assim ?");
            System.out.println("Se SIM pressione 1, se NÃO pressione 2.");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if(escolha == 2) {
                System.out.println("Operação cancelada!");
                return false;
            }else if(escolha == 1) {
                return true;
            } else {
                System.out.println("Número incorreto, Operação cancelada.");
                return false;
            }
        }
    }

    /**
     * pergunta ao usuário se ele deseja usar a conta corrente,
     * ou a poupança do cliente informado.
     * @param customer o cliente escolhido.
     * @param scanner o Scanner já em uso na main, reaproveitando para ler a escolha do usuário.
     * @return uma conta do cliente escolhido.
     */
    private static Account escolherConta(Customer customer, Scanner scanner) {
        System.out.println("Conta corrente ou poupança?");
        String tipo = scanner.nextLine();
        if (tipo.equals("corrente")) {
            return customer.getCheckingAccount();
        } else {
            return customer.getSavingsAccount(); // pode ser null, se não tiver
        }
    }

    /**
     * formata a String para mostrar cifrão nos valores,
     * e para mostrar somente duas casas decimais.
     * @param valor o valor monetário a ser formatado.
     * @return A string formatada.
     */
    private static String formatarMoeda(double valor) {
        return String.format("R$%.2f", valor);
    }
}
