package com.smartstore.menu;

import com.smartstore.model.Categoria;
import com.smartstore.model.Produto;
import com.smartstore.service.CategoriaManager;
import com.smartstore.service.LojaManager;
import com.smartstore.model.TypeCategoria;

import java.util.List;
import java.util.Scanner;

public class MenuProdutos {
    private static String[] options = {"Inserir", "Alterar", "Pesquisar", "Remover", "Listar", "Exibir um", "sair"};
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
                    menuPesquisarProduto();
                    break;
                case "remover":
                case "4":
                    String remover = "remover";
                    exibirProdutos(remover);
                    break;
                case "listar":
                case "5":
                    menuListarProduto();
                    break;
                case "listar por categoria":
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

    private static boolean verificarListaVazia(List<Produto> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("\nNenhum produto encontrado.");
            System.out.println("Pressione Enter para voltar...");
            sc.nextLine();
            return true;
        }
        return false;
    }

    private static void exibirListaPaginada(List<Produto> produtos, String titulo, String modo) {
        if (verificarListaVazia(produtos)) {
            return;
        };
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
            if(modo.equals("remover")) {
                System.out.println("[P] Próxima página | [V] Voltar página | [E] Excluir | [S] Sair");
            } else if(modo.equals("pesquisar")) {
                System.out.println("[P] Próxima página | [V] Voltar página | [A] Acessar o produto | [S] Sair");
            } else {
                System.out.println("[P] Próxima página | [V] Voltar página | [S] Sair");
            }
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
                case "e":
                case "excluir":
                    System.out.print("Digite o id: ");
                    long id = sc.nextLong();
                    sc.nextLine();
                    boolean excluiu = LojaManager.excluirProduto(id);
                    if (excluiu) {
                        System.out.println("Produto excluido");
                        produtos.removeIf(p -> p.getId() == id);
                        if (verificarListaVazia(produtos)) {
                            return;
                        }
                    } else {
                        System.out.println("Produto não excluido");
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

    public static void exibirProdutos(String modo) {
        List<Produto> produtos = LojaManager.listarProdutos();
        exibirListaPaginada(produtos, "LISTA DE PRODUTOS", modo);
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
        System.out.println("4 - LIMPEZA");
        System.out.println("5 - PAPELARIA");

        int opcao = Integer.parseInt(sc.nextLine());

        switch (opcao) {

            case 1:
                return TypeCategoria.ELETRONICOS;

            case 2:
                return TypeCategoria.ALIMENTOS;

            case 3:
                return TypeCategoria.ROUPAS;

            case 4:
                return TypeCategoria.LIMPEZA;

            case 5:
                return TypeCategoria.PAPELARIA;

            default:
                throw new IllegalArgumentException("Categoria inválida");
        }
    }

    private static void listarPorCategoria(String modo) {
        TypeCategoria categoria = escolherCategoria();
        LojaManager lojaManager = new LojaManager();
        List<Produto> produtos = lojaManager.buscarPorCategoria(categoria);
        exibirListaPaginada(produtos, "PRODUTOS DA CATEGORIA " + categoria, modo);
    }

    private static void menuPesquisarProduto() {
        String escolha;
        String pesquisar = "pesquisar";
        while (true) {
            System.out.println("Pesquisar produto por:");
            System.out.println("1. Nome");
            System.out.println("2. Categoria");
            System.out.println("3. Sair");
            System.out.print("Sua opção: ");
            escolha = sc.nextLine().toLowerCase();
            switch (escolha) {
                case "nome":
                case "1":
                    System.out.println("Pesquisar por nome");
                    break;
                case "categoria":
                case "2":
                    listarPorCategoria("pesquisar");
                    System.out.println("pesquisar por categoria");
                    break;
                case "sair":
                case "3":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuListarProduto() {
        String lista = "listar";
        String escolha;
        while (true) {
            System.out.println("Listar produto por:");
            System.out.println("1. todos");
            System.out.println("2. Categoria");
            System.out.println("3. Sair");
            System.out.print("Sua opção: ");
            escolha = sc.nextLine().toLowerCase();
            switch (escolha) {
                case "todos":
                case "1":
                    exibirProdutos(lista);
                    break;
                case "categoria":
                case "2":
                    listarPorCategoria(lista);
                    break;
                case "sair":
                case "3":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
