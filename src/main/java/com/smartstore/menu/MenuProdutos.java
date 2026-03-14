package com.smartstore.menu;

import com.smartstore.model.Categoria;
import com.smartstore.model.Produto;
import com.smartstore.service.CategoriaManager;
import com.smartstore.service.LojaManager;
import com.smartstore.model.TypeCategoria;

import java.util.List;
import java.util.Scanner;

public class MenuProdutos {
    private static String[] options = {"Inserir", "Alterar", "Pesquisar", "Remover", "Listar Todos", "Lista por Categoria", "Exibir um", "sair"};
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
                    exibirProdutos();
                    break;
                case "listar por categoria":
                case "6":
                    listarPorCategoria();
                    break;
                case "exibir um":
                case "7":
                    System.out.println("Exibir um produto");
                    break;
                case "sair":
                case "8":
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

    private static void exibirListaPaginada(List<Produto> produtos, String titulo) {
        if (produtos.isEmpty()) {
            System.out.println("\nNenhum produto encontrado.");
            System.out.println("Pressione Enter para voltar...");
            sc.nextLine();
            return;
        }

        int itensPorPagina = 5;
        int paginaAtual = 0;
        int totalPaginas = (int) Math.ceil((double) produtos.size() / itensPorPagina);

        while (true) {
            int inicio = paginaAtual * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, produtos.size());

            System.out.println("\n==================== " + titulo + " ====================");
            System.out.println("Página " + (paginaAtual + 1) + " de " + totalPaginas);
            System.out.println("+----+----------------------+------------+----------+----------------+");
            System.out.printf("| %-2s | %-20s | %-10s | %-8s | %-14s |%n",
                    "ID", "NOME", "PREÇO", "ESTOQUE", "CATEGORIA");
            System.out.println("+----+----------------------+------------+----------+----------------+");

            for (int i = inicio; i < fim; i++) {
                Produto produto = produtos.get(i);

                System.out.printf("| %-2d | %-20s | %-10.2f | %-8d | %-14s |%n",
                        produto.getId(),
                        limitarTexto(produto.getNome(), 20),
                        produto.getPreco(),
                        produto.getEstoque(),
                        limitarTexto(produto.getCategoria().getNome(), 14));
            }

            System.out.println("+----+----------------------+------------+----------+----------------+");
            System.out.println("[P] Próxima página | [V] Voltar página | [S] Sair");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine().toLowerCase();

            switch (opcao) {
                case "p":
                case "proxima":
                    if (paginaAtual < totalPaginas - 1) {
                        paginaAtual++;
                    } else {
                        System.out.println("Você já está na última página.");
                    }
                    break;

                case "v":
                case "voltar":
                    if (paginaAtual > 0) {
                        paginaAtual--;
                    } else {
                        System.out.println("Você já está na primeira página.");
                    }
                    break;

                case "s":
                case "sair":
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public static void exibirProdutos() {
        List<Produto> produtos = LojaManager.listarProdutos();
        exibirListaPaginada(produtos, "LISTA DE PRODUTOS");
    }

    public static String limitarTexto(String texto, int limite) {
        if (texto.length() <= limite) {
            return texto;
        }
        return texto.substring(0, limite - 3) + "...";
    }

    private static TypeCategoria escolherCategoria() {

        System.out.println("Escolha a categoria:");

        System.out.println("1 - ELETRONICOS");
        System.out.println("2 - ALIMENTOS");
        System.out.println("3 - ROUPAS");
        System.out.println("4 - REMEDIOS");
        System.out.println("5 - ELETRODOMESTICOS");

        int opcao = Integer.parseInt(sc.nextLine());

        switch (opcao) {

            case 1:
                return TypeCategoria.ELETRONICOS;

            case 2:
                return TypeCategoria.ALIMENTOS;

            case 3:
                return TypeCategoria.ROUPAS;

            case 4:
                return TypeCategoria.REMEDIOS;

            case 5:
                return TypeCategoria.ELETRODOMESTICOS;

            default:
                throw new IllegalArgumentException("Categoria inválida");
        }
    }

    private static void listarPorCategoria() {
        TypeCategoria categoria = escolherCategoria();

        LojaManager lojaManager = new LojaManager();
        List<Produto> produtos = lojaManager.buscarPorCategoria(categoria);

        exibirListaPaginada(produtos, "PRODUTOS DA CATEGORIA " + categoria);
    }
}
