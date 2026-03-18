package com.smartstore.menu;

import com.smartstore.model.Categoria;
import com.smartstore.model.Produto;
import com.smartstore.service.CategoriaService;
import com.smartstore.service.ProdutoService;
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
                    exibirProdutos("alterar");
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
                    exibirProduto();
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
            CategoriaService.listarCategorias();
            System.out.print("Escolha o ID da categoria: ");
            categoriaId = Integer.parseInt(sc.nextLine());
            if (CategoriaService.categoriaExiste(categoriaId)) {
                break;
            }
            System.out.println("Categoria inválida.");
        }
        Categoria categoria = CategoriaService.buscarCategoriaPorId(categoriaId);
        Produto produto = new Produto(nome, preco, categoria, estoque);
        ProdutoService.cadastrarProduto(produto);
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
        }

        int itensPorPagina = 5;
        int paginaAtual = 0;
        int totalPaginas = (int) Math.ceil((double) produtos.size() / itensPorPagina);

        while (true) {
            int inicio = paginaAtual * itensPorPagina;
            int fim = Math.min(inicio + itensPorPagina, produtos.size());

            System.out.println("\n==================== " + titulo + " ====================");
            System.out.println("Página " + (paginaAtual + 1) + " de " + totalPaginas);
            System.out.println("+----+----------------------+------------+");
            System.out.printf("| %-2s | %-20s | %-10s |%n", "ID", "NOME", "PREÇO");
            System.out.println("+----+----------------------+------------+");

            for (int i = inicio; i < fim; i++) {
                Produto produto = produtos.get(i);

                System.out.printf("| %-2d | %-20s | %-10.2f |%n",
                        produto.getId(),
                        limitarTexto(produto.getNome(), 20),
                        produto.getPreco());
            }

            System.out.println("+----+----------------------+------------+");

            if (modo.equals("remover")) {
                System.out.println("[P] Próxima | [V] Voltar | [E] Excluir | [X] Exibir | [S] Sair");
            } else {
                System.out.println("[P] Próxima | [V] Voltar | [A] Alterar | [S] Sair");
            }

            System.out.print("Escolha: ");
            String opcao = sc.nextLine().toLowerCase();

            switch (opcao) {

                case "p":
                    if (paginaAtual < totalPaginas - 1) paginaAtual++;
                    else System.out.println("Última página.");
                    break;

                case "v":
                    if (paginaAtual > 0) paginaAtual--;
                    else System.out.println("Primeira página.");
                    break;

                case "a": // 🔥 NOVO: ALTERAR
                    System.out.print("Digite o ID do produto: ");
                    long idAlterar = Long.parseLong(sc.nextLine());

                    Produto produtoSelecionado = null;

                    for (Produto p : produtos) {
                        if (p.getId() == idAlterar) {
                            produtoSelecionado = p;
                            break;
                        }
                    }

                    if (produtoSelecionado == null) {
                        System.out.println("Produto não encontrado.");
                    } else {
                        alterarProduto(produtoSelecionado);
                    }
                    break;

                case "e": // remover
                    System.out.print("Digite o ID: ");
                    long id = Long.parseLong(sc.nextLine());

                    boolean excluiu = ProdutoService.excluirProduto(id);

                    if (excluiu) {
                        System.out.println("Produto excluído!");
                        produtos.removeIf(p -> p.getId() == id);

                        if (verificarListaVazia(produtos)) return;

                        totalPaginas = (int) Math.ceil((double) produtos.size() / itensPorPagina);
                    } else {
                        System.out.println("Erro ao excluir.");
                    }
                    break;

                case "x":
                    exibirProduto();
                    break;

                case "s":
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public static void exibirProdutos(String modo) {
        List<Produto> produtos = ProdutoService.listarProdutos();
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

    private static void listarPorCategoria(String modo) {
        TypeCategoria categoria = escolherCategoria();
        ProdutoService lojaManager = new ProdutoService();
        List<Produto> produtos = lojaManager.buscarPorCategoria(categoria);
        exibirListaPaginada(produtos, "PRODUTOS DA CATEGORIA " + categoria, modo);
    }

    private static void menuPesquisarProduto() {

        while (true) {

            System.out.println("Pesquisar produto por:");
            System.out.println("1. Nome");
            System.out.println("2. Categoria");
            System.out.println("3. Sair");
            System.out.print("Sua opção: ");

            String escolha = sc.nextLine().toLowerCase();

            switch (escolha) {

                case "nome":
                case "1":
                    pesquisarProduto();
                    break;

                case "categoria":
                case "2":
                    listarPorCategoria("pesquisar");
                    break;

                case "sair":
                case "3":
                    return; // volta para o menu anterior

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
            System.out.println("1. Todos");
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

    private static void alterarProduto(Produto produto) {
        ProdutoService lojaManager = new ProdutoService();

        while (true) {
            System.out.println("\n===== EDITANDO PRODUTO =====");
            System.out.println(produto);

            System.out.println("\nO que deseja alterar?");
            System.out.println("1. Nome");
            System.out.println("2. Preço");
            System.out.println("3. Estoque");
            System.out.println("4. Salvar e sair");
            System.out.print("Escolha: ");

            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Novo nome: ");
                    produto.setNome(sc.nextLine());
                    break;

                case "2":
                    System.out.print("Novo preço: ");
                    produto.setPreco(Double.parseDouble(sc.nextLine()));
                    break;

                case "3":
                    System.out.print("Novo estoque: ");
                    produto.setEstoque(Integer.parseInt(sc.nextLine()));
                    break;

                case "4":
                    lojaManager.alterarProduto(produto);
                    System.out.println("✅ Produto atualizado com sucesso!");
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void pesquisarProduto() {

        System.out.print("Digite o nome do produto: ");
        String nome = sc.nextLine();

        ProdutoService lojaManager = new ProdutoService();
        List<Produto> produtos = lojaManager.buscarPorNome(nome);

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }

        exibirListaPaginada(produtos, "RESULTADOS DA PESQUISA", "pesquisar");
    }
    private static void mostrarDetalhesProduto(Produto produto) {
        System.out.println("\n===== DADOS DO PRODUTO =====");
        System.out.println("ID: " + produto.getId());
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Categoria: " + produto.getCategoria().getNome());
        System.out.println("Estoque: " + produto.getEstoque());
    }
    private static void exibirProduto() {
        ProdutoService produtoService = new ProdutoService();

        System.out.print("Digite o ID do produto: ");
        long id = Long.parseLong(sc.nextLine());

        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            System.out.println("Produto não encontrado.");
            System.out.println("Pressione Enter para continuar...");
            sc.nextLine();
            return;
        }

        mostrarDetalhesProduto(produto);

        System.out.println("\nPressione Enter para continuar...");
        sc.nextLine();
    }
}
