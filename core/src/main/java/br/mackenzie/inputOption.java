package br.mackenzie;

public class inputOption {
    String nome;
    Runnable acao;

    public inputOption(String nome, Runnable acao) {
        this.nome = nome;
        this.acao = acao;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
