package model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}