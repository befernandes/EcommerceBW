package com.ecombw.EcommerceBW.service;

import com.ecombw.EcommerceBW.dto.ProductDTO;
import com.ecombw.EcommerceBW.model.Product;
import com.ecombw.EcommerceBW.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductDTO productDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setImageUrl(product.getImageUrl());
        return productDTO;

    }


    public ProductDTO save(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        Product saved = productRepository.save(product);
        return productDTO (saved);
    }

    public List<ProductDTO> findAll() {
       return productRepository.findAll().
               stream().
               map(this::productDTO).
               collect(Collectors.toList());
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(this::productDTO);
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        // Verifica se o produto existe antes de atualizar
        return productRepository.findById(id).map(existingProduct -> {
            // Atualiza os campos da entidade existente com os dados do DTO
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
            existingProduct.setImageUrl(productDTO.getImageUrl());

            Product updatedEntity = productRepository.save(existingProduct); // Salva as alterações
            return productDTO(updatedEntity); // Retorna o DTO atualizado
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id)); // Ou outra exceção mais específica
    }
    public void deleteById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        }else  {
            throw new RuntimeException("Product not found with id " + id);
        }
    }


}
