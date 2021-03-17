package br.com.cadastro.cliente.domain;

public enum Genero {

    FEMININO("F"),
    MASCULINO("M");

    private String descricao;

    Genero(String descricao){
        this.descricao=descricao;
    }
    public String getDescricao(){
        return descricao;
    }
}
