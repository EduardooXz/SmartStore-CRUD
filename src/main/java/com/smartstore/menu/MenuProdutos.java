package com.smartstore.menu;

import com.smartstore.model.Categoria;
import com.smartstore.model.Produto;
import com.smartstore.service.CategoriaManager;
import com.smartstore.service.LojaManager;

import java.util.Scanner;

public class MenuProdutos {
    private static String[] options = {"Inserir", "Alterar", "Pesquisar", "Remover", "Listar Todos", "Exibir um", "sair"};
    private static Scanner sc = new Scanner(System.in);

    public static void exibirMenu() {
        System.out.println("======Gerenciar Produto======");
        for(int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public static void iniciar() {
        while (true) {
            exibirMenu();
            System.out.print("\nSelecionar opção: ");
            String escolha = sc.nextLine().toLowerCase();
            switch (escolha) {
                case "inserir":
                case "1":
                    menuCadastrarProduto();
                    System.out.println("\nProduto cadastrado");
                    break;
                case "alterar":
                case "2":
                    System.out.println("Alterar Produto");
                    break;
                case "pesquisar":
                case "3":
                    System.out.println("Pesquisar Produto");
                    break;
                case "remover":
                case "4":
                    System.out.println("Remover Produto");
                    break;
                case "listar":
                case "listar todos":
                case "5":
                    System.out.println("Listar todos os produtos");
                    break;
                case "exibir um":
                case "6":
                    System.out.println("Exibir um produto");
                    break;
                case "sair":
                case "7":
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
    }

    public static void menuCadastrarProduto() {
        System.out.println("\n====== CADASTRAR PRODUTO ======");
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("Preço: ");
        double preco = Double.parseDouble(sc.nextLine());
        System.out.print("Estoque: ");
        int estoque = Integer.parseInt(sc.nextLine());
        int categoriaId;
        while (true) {
            CategoriaManager.listarCategorias();
            System.out.print("Escolha o ID da categoria: ");
            categoriaId = Integer.parseInt(sc.nextLine());
            if (CategoriaManager.categoriaExiste(categoriaId)) {
                break;
            }
            System.out.println("Categoria inválida.");
        }
        Categoria categoria = CategoriaManager.buscarCategoriaPorId(categoriaId);
        Produto produto = new Produto(nome, preco, categoria, estoque);
        LojaManager.cadastrarProduto(produto);
    }
}
