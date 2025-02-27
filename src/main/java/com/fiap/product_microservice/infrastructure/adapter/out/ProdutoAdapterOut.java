package com.fiap.product_microservice.infrastructure.adapter.out;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

import com.fiap.product_microservice.infrastructure.exception.ProdutoNaoEncontradoException;
import org.springframework.stereotype.Component;
import com.fiap.product_microservice.application.port.out.ProdutoAdapterPortOut;
import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.in.validation.ProdutoValidation;
import com.fiap.product_microservice.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.product_microservice.infrastructure.adapter.out.repository.ProdutoRepository;
import com.fiap.product_microservice.infrastructure.exception.ProdutoInvalidoException;

@Component
public class ProdutoAdapterOut implements ProdutoAdapterPortOut {

    private final ProdutoRepository produtoRepository;

    public ProdutoAdapterOut(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> listarProdutos() {
        List<ProdutoEntity> produtoEntities = produtoRepository.findAll();
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        ProdutoValidation.validate(categoria);
        List<ProdutoEntity> produtoEntities = produtoRepository.findProductByCategoria(categoria);
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Produto criarProduto(Produto produto) {
        ProdutoValidation.validateFields(produto);
        return produtoRepository.save(ProdutoEntity.fromDomain(produto)).toDomain();
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) {
        try {
            Optional<ProdutoEntity> produtoEntityOptional = produtoRepository.findById(id);
            if (produtoEntityOptional.isPresent()) {
                ProdutoEntity produtoEntityExistente = produtoEntityOptional.get();
                boolean isUpdated = false;

                // Valida e Atualiza os campos modificados
                if (ProdutoValidation.validateFields(produto)) {
                    produtoEntityExistente.setNome(produto.getNome());
                    produtoEntityExistente.setCategoria(produto.getCategoria());
                    produtoEntityExistente.setPreco(produto.getPreco());
                    produtoEntityExistente.setDescricao(produto.getDescricao());
                    produtoEntityExistente.setImagemUrl(produto.getImagemUrl());
                    isUpdated = true;
                }

                if (isUpdated) {
                    // Salvar somente se houver mudanças
                    ProdutoEntity salvo = produtoRepository.save(produtoEntityExistente);
                    return salvo.toDomain();
                } else {
                    // Retornar a entidade existente se nada foi atualizado
                    return produtoEntityExistente.toDomain();
                }
            } else {
                throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado");
            }
        } catch (ProdutoNaoEncontradoException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ProdutoInvalidoException(ex.getMessage());
        }
    }

    @Override
    public void deletarPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"))
                .toDomain();
    }


}
