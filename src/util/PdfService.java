package util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import model.ItemPedido;
import model.Pedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfService {

    public static void gerarPdfPedido(Pedido pedido) {
        try {
            String nomeArquivo = "pdf/pedido_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm")) +
                    ".pdf";

            PdfWriter writer = new PdfWriter(nomeArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            try {
                Image logo = new Image(ImageDataFactory.create("assets/logo.png"));
                logo.setWidth(120);
                logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo não encontrada...");
            }

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("PEDIDO DE COMPRA")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Data: " + pedido.getDataPedido()));
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell("Produto");
            table.addHeaderCell("Categoria");
            table.addHeaderCell("Qtd");
            table.addHeaderCell("Unidade");

            for (ItemPedido item : pedido.getItens()) {
                table.addCell(item.getProduto().getNome());
                table.addCell(item.getProduto().getCategoria().getNome());
                table.addCell(String.valueOf(item.getQuantidade()));
                table.addCell(item.getProduto().getUnidade());
            }

            document.add(table);
            document.close();

            System.out.println("PDF gerado: " + nomeArquivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}