package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VolatilLoteRepositoryTest {

    @Autowired
    VolatilLoteRepository driver;

    Lote lote;
    Lote resultado;
    Produto produto;

    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }

    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }

    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);

        assertEquals(driver.findAll().size(), 1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);

        resultado = driver.save(loteExtra);

        assertEquals(driver.findAll().size(), 2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);

    }

    @Test
    @DisplayName("Buscar um Lote pelo seu ID")
    void buscarLotePeloId() {
        driver.save(lote);

        resultado = driver.find(lote.getId());

        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Buscar todos os Lotes")
    void buscarTodosLotes() {
        driver.save(lote);

        assertEquals(driver.findAll().size(), 1);
    }
    
    @Test
    @DisplayName("Deletar um Lote")
    void deletarLotePeloId() {
        driver.save(lote);

        driver.delete(lote);

        assertEquals(driver.findAll().size(), 0);
    }

    @Test
    @DisplayName("Deletar todos os Lotes")
    void deletarTodosLotes() {
        driver.save(lote);

        driver.deleteAll();

        assertEquals(driver.findAll().size(), 0);
    }

    @Test
    @DisplayName("Atualizar um Lote")
    void atualizarLote() {
        driver.save(lote);

        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();

        resultado = driver.update(loteExtra);

        assertEquals(driver.findAll().size(), 1);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);
    }


}