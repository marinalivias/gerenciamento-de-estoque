package util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import model.ItemPedido;
import model.Pedido;

public class PdfService {

    public static void gerarPdfPedido(Pedido pedido) {
        try {
            String nomeArquivo = "pedido_" + System.currentTimeMillis() + ".pdf";

            PdfWriter writer = new PdfWriter(nomeArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            try {
                Image logo = new Image(ImageDataFactory.create("assets/logo.png"));
                logo.setWidth(120);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo não encontrada, seguindo sem logo...");
            }
            document.add(new Paragraph("\n"));

            document.add(
                    new Paragraph("PEDIDO DE COMPRA")
                            .setBold()
                            .setFontSize(18)
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
            );

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Data: " + pedido.getDataPedido()));
            document.add(new Paragraph("Previsão: " + pedido.getPrevisaoEntrega()));

            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("Produto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Qtd").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Unidade").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Condição").setBold()));

            for (ItemPedido item : pedido.getItens()) {
                table.addCell(item.getProduto().getNome());
                table.addCell(String.valueOf(item.getQuantidade()));
                table.addCell(item.getProduto().getUnidade());
                table.addCell(item.getCondicao());
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            document.add(
                    new Paragraph("Total de itens: " + pedido.getItens().size())
                            .setBold()
            );

            document.close();

            System.out.println("PDF gerado com sucesso: " + nomeArquivo);

        } catch (Exception e) {
            System.out.println("Erro ao gerar PDF");
            e.printStackTrace();
        }
    }
}