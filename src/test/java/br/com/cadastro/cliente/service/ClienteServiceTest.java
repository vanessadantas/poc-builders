package br.com.cadastro.cliente.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClienteServiceTest {

    private final static String FOTO_ORIGINAL = "src/test/resources/exemplo/gato.jpeg";

    @Autowired
    private ClienteService clienteService;

    @Test
    public void verificarExistenciaFoto() throws IOException {
        String nomeFoto = "cliente_" + "200";
        File file = FileUtils.getFile(FOTO_ORIGINAL);
        FileInputStream input = new FileInputStream(file);
        MultipartFile foto = new MockMultipartFile("file", nomeFoto, "text/plain", IOUtils.toByteArray(input));

        clienteService.salvarArquivoFileSystem(foto, nomeFoto);
        File fileNovo = FileUtils.getFile(System.getProperty("java.io.tmpdir") + "/" + nomeFoto);
        assertThat(fileNovo).exists() ;
    }


}
