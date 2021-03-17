package br.com.cadastro.cliente.repository;

import br.com.cadastro.cliente.domain.Cliente;
import br.com.cadastro.cliente.domain.DTO.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer> {

    @Query("select new br.com.cadastro.cliente.domain.DTO.ClienteDTO(c.id, c.nome, c.dataNascimento) from Cliente c")
    Page<ClienteDTO> listarClientes(PageRequest pageRequest);


}
