package util;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import model.Pedido;
import model.ItemPedido;

public class PdfService {

    public static void gerarPdfPedido(Pedido pedido) {
        try {
            String nomeArquivo = "pedido_" + System.currentTimeMillis() + ".pdf";

            PdfWriter writer = new PdfWriter(nomeArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("=== PEDIDO ==="));
            document.add(new Paragraph("Data: " + pedido.getDataPedido()));
            document.add(new Paragraph("Previsão: " + pedido.getPrevisaoEntrega()));
            document.add(new Paragraph(" "));

            for (ItemPedido item : pedido.getItens()) {
                document.add(new Paragraph(
                        item.getProduto().getNome() +
                                " | Quantidade: " + item.getQuantidade() +
                                " | Condição: " + item.getCondicao()
                ));
            }

            document.close();

            System.out.println("PDF gerado com sucesso: " + nomeArquivo);

        } catch (Exception e) {
            System.out.println("Erro ao gerar PDF");
            e.printStackTrace();
        }
    }
}