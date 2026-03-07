package com.smartstore.menu;
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
                    System.out.println("Entrando no Menu Relatório...");
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
}