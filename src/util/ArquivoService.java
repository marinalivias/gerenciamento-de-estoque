package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoService {

    public static <T> void salvar(String nomeArquivo, List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + nomeArquivo);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregar(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}