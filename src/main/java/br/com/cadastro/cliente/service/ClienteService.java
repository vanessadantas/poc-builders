package br.com.cadastro.cliente.service;

import br.com.cadastro.cliente.domain.Cliente;
import br.com.cadastro.cliente.domain.DTO.ClienteDTO;
import br.com.cadastro.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Service
public class ClienteService {


    @Autowired
    ClienteRepository clienteRepository;

    public Page<ClienteDTO> listaPaginada(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.unsorted());
        return clienteRepository.listarClientes(pageRequest);
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public void excluirCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    public void alterarCliente(Cliente cliente){
        clienteRepository.save(cliente);

    }

    @Transactional
    public ClienteDTO salvarFotoCliente(Cliente cliente, MultipartFile foto) {
        String nomeFoto = "cliente_" + cliente.getId().toString();
        salvarArquivoFileSystem(foto, nomeFoto);
        cliente.setFoto(System.getProperty("java.io.tmpdir") + "/" + nomeFoto);
        clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }

    protected void salvarArquivoFileSystem(MultipartFile arquivo, String nome) {
        String storageArquivo = System.getProperty("java.io.tmpdir");
        Path diretorio = Paths.get(storageArquivo);
        Path arquivoPath = diretorio.resolve(nome);
        try {
            Files.createDirectories(diretorio);
            arquivo.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao tentar salvar foto no diretório");
        }
    }


    public Optional<Cliente> buscarCliente(Integer id) {
        return clienteRepository.findById(id);
    }
}
