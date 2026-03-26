import model.*;
import service.CategoriaService;
import service.ProdutoService;
import service.PedidoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static CategoriaService categoriaService = new CategoriaService();
    static ProdutoService produtoService = new ProdutoService();
    static PedidoService pedidoService = new PedidoService();;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n=== SISTEMA ESTOQUE ===");
            System.out.println("1 - Criar categoria");
            System.out.println("2 - Criar produto");
            System.out.println("3 - Criar pedido");
            System.out.println("4 - Ver pedidos");
            System.out.println("0 - Sair");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarCategoria();
                case 2 -> criarProduto();
                case 3 -> criarPedido();
                case 4 -> listarPedidos();
            }

        } while (opcao != 0);
    }

    // ✅ agora usa o service
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

        List<Categoria> categorias = categoriaService.listarCategorias();

        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(i + " - " + categorias.get(i).getNome());
        }

        System.out.print("Escolha a categoria: ");
        int index = sc.nextInt();
        sc.nextLine();

        Categoria categoria = categorias.get(index);

        produtoService.criarProduto(nome, categoria, unidade);

        System.out.println("Produto criado!");
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

        if (pedidoService.listarPedidos().isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        for (Pedido pedido : pedidoService.listarPedidos()) {
            System.out.println("\n------------------");
            pedido.mostrarPedido();
        }
    }
}