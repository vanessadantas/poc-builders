package br.com.cadastro.cliente.domain;

import lombok.Getter;

@Getter
public enum TipoDocumento {

    PASSAPORTE("PASSPORTE"),
    IDENTIDADE("IDENTIDADE"),
    CPF("CPF");


    private String descricao;

    TipoDocumento(String descricao){
        this.descricao=descricao;
    }
}
