package br.com.cadastro.cliente.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_cliente_generator")
    private Integer id;
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String foto;

    @ElementCollection
    @CollectionTable(name = "cliente_documentos")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "tipo_documento")
    @Column(name = "numero_documento")
    private Map<TipoDocumento, String> documentos;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Telefone> telefones;

    @Embedded
    private Endereco endereco;


}
