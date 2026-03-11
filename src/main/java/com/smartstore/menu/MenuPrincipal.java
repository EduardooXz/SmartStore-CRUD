package com.smartstore.menu;
import com.smartstore.dto.RelatorioCategoria;
import com.smartstore.service.LojaManager;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private static String[] options = {"Gerenciar Produtos", "Relatório", "Sair"}; // Lista de Opções do Menu
    private static Scanner sc = new Scanner(System.in);

    public static void exibirMenu() { // Exibe o menu e suas opções
        System.out.println("======Bem-Vindo ao Smart Store======");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public static void selecionarOptions() {
        while (true) {
            exibirMenu();
            System.out.print("\nSelecionar opção: ");
            String escolha = sc.nextLine().toLowerCase();
            switch (escolha) {
                case "produtos":
                case "1":
                    System.out.println("Entrando no Menu Produtos...");
                    MenuProdutos.iniciar();
                    break;
                case "relatório":
                case "relatorio":
                case "2":
                    exibirRelatorio();
                    break;
                case "sair":
                case "3":
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
    }

    public static void exibirRelatorio() {
        System.out.println("=========== RELATÓRIO DE ESTOQUE ===========\n");
        int totalProdutos = 0, totalEstoque = 0;
        double totalValor = 0;
        List<RelatorioCategoria> relatorioCategorias = LojaManager.gerarRelatorioPorCategoria();
        for(RelatorioCategoria relatorio : relatorioCategorias) {
            System.out.println("Categoria: " + relatorio.getCategoria());
            System.out.println("Produtos cadastrados: " + relatorio.getTotalProduto());
            System.out.println("Quantidade em estoque: " + relatorio.getTotalEstoque());
            System.out.printf("Valor total: R$ %.2f\n", relatorio.getValorTotal());
            System.out.println("----------------------");
            totalProdutos += relatorio.getTotalProduto();
            totalEstoque += relatorio.getTotalEstoque();
            totalValor += relatorio.getValorTotal();
        }
        System.out.println("\n====== RESUMO GERAL ======");
        System.out.println("Total de produtos cadastrados: " + totalProdutos);
        System.out.println("Total em estoque: " + totalEstoque);
        System.out.printf("Valor total do estoque: R$ %.2f\n", totalValor);
    }
}