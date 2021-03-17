package br.com.cadastro.cliente.web.rest;

import br.com.cadastro.cliente.domain.Cliente;
import br.com.cadastro.cliente.domain.DTO.ClienteDTO;
import br.com.cadastro.cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Cadastro de cliente",
            description = "No cadastro do cliente é obrigatório informar a data de nascimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Quando não informada a data de nascimento"),
            @ApiResponse(responseCode = "201", description = "Quando o cliente é cadastrado com sucesso")
    })
    @PostMapping
    public ResponseEntity<Cliente> cadastarCliente(@Valid @RequestBody Cliente cliente){
        clienteService.cadastrarCliente(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Exclui o cliente por id",
            description = "Pesquisa o cliente por id e se achar o cadastro, exclui esse cliente do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Quando o cliente não for encontrado na base de dados"),
            @ApiResponse(responseCode = "204", description = "Quando o cliente é excluído com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity exluirCliente(@PathVariable Integer id){
        Cliente cliente = clienteService.buscarCliente(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        clienteService.excluirCliente(cliente.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Altera o cliente",
            description = "Pesquisa o cliente por id e se achar o cadastro, altera os dados desse cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Quando o cliente não for encontrado na base de dados"),
            @ApiResponse(responseCode = "200", description = "Quando o cliente é alterado com sucesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterarCliente(@PathVariable Integer id, @RequestBody Cliente cliente){
        clienteService.buscarCliente(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        clienteService.alterarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Lista todos os clientes",
            description = "Retorna o id, nome e idade de todos os clientes de forma paginada")
    @GetMapping()
    public ResponseEntity<Page<ClienteDTO>> listaPaginada(@RequestParam Integer size, @RequestParam Integer page){
        try {
             return ResponseEntity.ok(clienteService.listaPaginada(page, size));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @Operation(summary = "Busca o cliente por id",
            description = "Retorna um cliente de forma detalhado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Quando o cliente não for encontrado na base de dados"),
            @ApiResponse(responseCode = "200", description = "Quando o cliente é retornado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Integer id){
        Cliente cliente = clienteService.buscarCliente(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return new ResponseEntity(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Inclui foto",
            description = "Inclui uma foto para o cadastro do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Quando o cliente não for encontrado na base de dados"),
            @ApiResponse(responseCode = "200", description = "Quando a foto for incluída")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDTO> incluirFotoCliente(@PathVariable Integer id, @RequestBody MultipartFile foto){
        Cliente cliente = clienteService.buscarCliente(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return ResponseEntity.ok(clienteService.salvarFotoCliente(cliente, foto));
    }

}
