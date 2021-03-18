package br.com.cadastro.cliente.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Telefone {
    @Id
    @SequenceGenerator(name = "telefone_generator", initialValue = 1, allocationSize = 1, sequenceName = "seq_telefone")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="telefone_generator")
    private Integer id;
    private String numero;
    private String ddd;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

}
