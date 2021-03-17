package br.com.cadastro.cliente.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_telefone_generator")
    private Integer id;
    private String numero;
    private String ddd;
    @ManyToOne
    private Cliente cliente;

}
