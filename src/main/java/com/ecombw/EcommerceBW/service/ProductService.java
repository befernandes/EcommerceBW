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

}
