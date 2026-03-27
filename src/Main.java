import model.*;
import service.*;
import util.PdfService;

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
            System.out.println("8 - Ver categorias");
            System.out.println("9 - Ver produtos");
            System.out.println("10 - Ver produtos para reposição");
            System.out.println("11 - Gerar pedido automático");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarCategoria();
                case 2 -> criarProduto();
                case 3 -> criarPedido();
                case 4 -> listarPedidos();
                case 5 -> registrarEntrada();
                case 6 -> registrarSaida();
                case 7 -> mostrarEstoque();
                case 8 -> listarCategorias();
                case 9 -> listarProdutos();
                case 10 -> mostrarReposicao();
                case 11 -> gerarPedidoAutomatico();
            }

        } while (opcao != 0);
    }

    public static void gerarPedidoAutomatico() {
        List<Produto> produtos = produtoService.listarProdutos();

        System.out.print("Data do pedido: ");
        String data = sc.nextLine();

        System.out.print("Previsão de entrega: ");
        String previsao = sc.nextLine();

        Pedido pedido = pedidoService.criarPedido(data, previsao);

        boolean temItem = false;

        for (Produto p : produtos) {

            if (movimentacaoService.precisaRepor(p)) {

                double qtd = movimentacaoService.calcularQuantidadeReposicao(p);

                pedidoService.adicionarItem(pedido, p, qtd, "AUTO");

                System.out.println("Adicionado: " + p.getNome() + " | " + qtd);

                temItem = true;
            }

        }

        if (!temItem) {
            System.out.println("Nenhum produto precisa reposição.");
        } else {
            System.out.println("Pedido automático criado com sucesso!");
            PdfService.gerarPdfPedido(pedido);
        }
    }

    public static void criarCategoria() {
        System.out.print("Nome da categoria: ");
        String nome = sc.nextLine();
        categoriaService.criarCategoria(nome);
    }

    public static void criarProduto() {
        List<Categoria> categorias = categoriaService.listarCategorias();

        if (categorias.isEmpty()) {
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

        if (minimo < 0 || ideal < 0) {
            System.out.println("Valores inválidos!");
            return;
        }

        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(i + " - " + categorias.get(i).getNome());
        }

        System.out.print("Escolha a categoria: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index < 0 || index >= categorias.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Categoria categoria = categorias.get(index);
        produtoService.criarProduto(nome, categoria, unidade, minimo, ideal);
    }

    public static void criarPedido() {
        List<Produto> produtos = produtoService.listarProdutos();

        if (produtos.isEmpty()) {
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
            for (int i = 0; i < produtos.size(); i++) {
                System.out.println(i + " - " + produtos.get(i).getNome());
            }

            System.out.print("Escolha o produto: ");
            int index = sc.nextInt();
            sc.nextLine();

            if (index < 0 || index >= produtos.size()) {
                System.out.println("Opção inválida!");
                return;
            }

            Produto produto = produtos.get(index);

            System.out.print("Quantidade: ");
            double quantidade = sc.nextDouble();
            sc.nextLine();

            if (quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                return;
            }

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

        if (index < 0 || index >= produtos.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Produto produto = produtos.get(index);

        System.out.print("Quantidade da entrada: ");
        double qtd = sc.nextDouble();
        sc.nextLine();

        if (qtd <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        System.out.print("Data: ");
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

        if (index < 0 || index >= produtos.size()) {
            System.out.println("Opção inválida!");
            return;
        }

        Produto produto = produtos.get(index);

        System.out.print("Quantidade da saída: ");
        double qtd = sc.nextDouble();
        sc.nextLine();

        if (qtd <= 0) {
            System.out.println("Quantidade inválida!");
            return;
        }

        double estoqueAtual = movimentacaoService.calcularEstoque(produto);

        if (qtd > estoqueAtual) {
            System.out.println("Erro: estoque insuficiente!");
            return;
        }

        System.out.print("Data: ");
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

    public static void listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("\n=== CATEGORIAS ===");

        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(i + " - " + categorias.get(i).getNome());
        }
    }

    public static void listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n=== PRODUTOS ===");

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);

            double estoque = movimentacaoService.calcularEstoque(p);

            System.out.println(i + " - " + p.getNome() +
                    " | Categoria: " + p.getCategoria().getNome() +
                    " | Estoque: " + estoque + " " + p.getUnidade());
        }
    }
    public static void mostrarReposicao() {
        List<Produto> produtos = produtoService.listarProdutos();

        System.out.println("\n=== SUGESTÃO DE REPOSIÇÃO ===");

        boolean encontrou = false;

        for (Produto p : produtos) {
            double estoque = movimentacaoService.calcularEstoque(p);

            if (movimentacaoService.precisaRepor(p)) {

                double sugerido = movimentacaoService.calcularQuantidadeReposicao(p);

                System.out.println(p.getNome() +
                        " | Atual: " + estoque +
                        " | Comprar: " + sugerido + " " + p.getUnidade());

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Estoque saudável 😎");
        }
    }

}