import model.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static CategoriaService categoriaService = new CategoriaService();
    static ProdutoService produtoService = new ProdutoService();
    static PedidoService pedidoService = new PedidoService();
    static MovimentacaoService movimentacaoService = new MovimentacaoService();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n=== SISTEMA ESTOQUE ===");
            System.out.println("1 - Criar categoria");
            System.out.println("2 - Criar produto");
            System.out.println("3 - Criar pedido");
            System.out.println("4 - Ver pedidos");
            System.out.println("5 - Registrar entrada no estoque");
            System.out.println("6 - Registrar saída do estoque");
            System.out.println("7 - Ver estoque atual");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> criarCategoria();
                case 2 -> criarProduto();
                case 3 -> criarPedido();
                case 4 -> listarPedidos();
                case 5 -> registrarEntrada();
                case 6 -> registrarSaida();
                case 7 -> mostrarEstoque();
            }

        } while (opcao != 0);
    }

    public static void criarCategoria() {
        System.out.print("Nome da categoria: ");
        String nome = sc.nextLine();
        categoriaService.criarCategoria(nome);
    }

    public static void criarProduto() {
        if (categoriaService.listarCategorias().isEmpty()) {
            System.out.println("Crie uma categoria primeiro!");
            return;
        }

        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        System.out.print("Unidade (kg, un, L...): ");
        String unidade = sc.nextLine();

        System.out.print("Estoque mínimo: ");
        double minimo = sc.nextDouble();
        System.out.print("Estoque ideal: ");
        double ideal = sc.nextDouble();
        sc.nextLine();

        List<Categoria> categorias = categoriaService.listarCategorias();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(i + " - " + categorias.get(i).getNome());
        }

        System.out.print("Escolha a categoria: ");
        int index = sc.nextInt();
        sc.nextLine();

        Categoria categoria = categorias.get(index);
        produtoService.criarProduto(nome, categoria, unidade, minimo, ideal);
    }

    public static void criarPedido() {
        if (produtoService.listarProdutos().isEmpty()) {
            System.out.println("Crie produtos primeiro!");
            return;
        }

        System.out.print("Data do pedido: ");
        String data = sc.nextLine();

        System.out.print("Previsão de entrega: ");
        String previsao = sc.nextLine();

        Pedido pedido = pedidoService.criarPedido(data, previsao);

        String continuar;
        do {
            List<Produto> produtos = produtoService.listarProdutos();
            for (int i = 0; i < produtos.size(); i++) {
                System.out.println(i + " - " + produtos.get(i).getNome());
            }

            System.out.print("Escolha o produto: ");
            int index = sc.nextInt();
            sc.nextLine();
            Produto produto = produtos.get(index);

            System.out.print("Quantidade: ");
            double quantidade = sc.nextDouble();
            sc.nextLine();

            System.out.print("Condição: ");
            String condicao = sc.nextLine();

            pedidoService.adicionarItem(pedido, produto, quantidade, condicao);

            System.out.print("Adicionar mais itens? (s/n): ");
            continuar = sc.nextLine();
        } while (continuar.equalsIgnoreCase("s"));

        System.out.println("Pedido criado!");
    }

    public static void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
        for (Pedido pedido : pedidos) {
            System.out.println("\n------------------");
            pedido.mostrarPedido();
        }
    }

    public static void registrarEntrada() {
        List<Produto> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Crie produtos primeiro!");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(i + " - " + produtos.get(i).getNome());
        }

        System.out.print("Escolha o produto: ");
        int index = sc.nextInt();
        sc.nextLine();
        Produto produto = produtos.get(index);

        System.out.print("Quantidade da entrada: ");
        double qtd = sc.nextDouble();
        sc.nextLine();

        System.out.print("Data (dd/mm/yyyy): ");
        String data = sc.nextLine();

        movimentacaoService.registrarEntrada(produto, qtd, data);
    }

    public static void registrarSaida() {
        List<Produto> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Crie produtos primeiro!");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(i + " - " + produtos.get(i).getNome());
        }

        System.out.print("Escolha o produto: ");
        int index = sc.nextInt();
        sc.nextLine();
        Produto produto = produtos.get(index);

        System.out.print("Quantidade da saída: ");
        double qtd = sc.nextDouble();
        sc.nextLine();

        System.out.print("Data (dd/mm/yyyy): ");
        String data = sc.nextLine();

        movimentacaoService.registrarSaida(produto, qtd, data);
    }

    public static void mostrarEstoque() {
        List<Produto> produtos = produtoService.listarProdutos();
        System.out.println("\n=== Estoque Atual ===");
        for (Produto p : produtos) {
            double estoque = movimentacaoService.calcularEstoque(p);
            String alerta = movimentacaoService.precisaRepor(p) ? " <<< REPOSIÇÃO NECESSÁRIA! >>>" : "";
            System.out.println(p.getNome() + " | Atual: " + estoque + " " + p.getUnidade() + alerta);
        }
    }
}