package br.com.cadastro.cliente.domain.DTO;

import br.com.cadastro.cliente.domain.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
@Getter
@Setter
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String idade;

    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.idade = getIdade(cliente.getDataNascimento());
    }

    public ClienteDTO(Integer id, String nome, LocalDate nascimento){
        this.id = id;
        this.nome = nome;
        this.idade = getIdade(nascimento);
    }

    public String getIdade(LocalDate aniversario) {
        LocalDate hoje = LocalDate.now();
        final Period periodo = Period.between(aniversario, hoje);
        return String.valueOf(periodo.getYears());
    }


}
