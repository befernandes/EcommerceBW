package com.ecombw.EcommerceBW.controller;

import com.ecombw.EcommerceBW.dto.ProductDTO;
import com.ecombw.EcommerceBW.model.Product;
import com.ecombw.EcommerceBW.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertPathValidatorException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productDTOS = productService.findAll();
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        Optional<ProductDTO> productDTO = productService.findById(id);
        return productDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        ProductDTO newProductDTO = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        try {
            ProductDTO updatedProductDTO = productService.update(id, productDTO);
            return ResponseEntity.ok(updatedProductDTO);
        }catch (ConfigDataResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (VerifyError e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (ConfigDataResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
