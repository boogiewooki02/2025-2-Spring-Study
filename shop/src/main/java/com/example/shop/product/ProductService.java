package com.example.shop.product;

import com.example.shop.product.dto.ProductCreateRequest;
import com.example.shop.product.dto.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // @Transactional
    public Long createProduct(ProductCreateRequest request) {
        Product existing = productRepository.findByName(request.getName());
        if (existing != null) {
            throw new RuntimeException("Product already exists: " + request.getName());
        }

        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getStock()
        );

        productRepository.save(product);

        return product.getId();
    }

    // @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found: id=" + productId);
        }
        return product;
    }

    // @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new RuntimeException("Product not found: id=" + productId);
        }

        product.updateInfo(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getStock()
        );
    }

    // @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found: id=" + productId);
        }
        productRepository.deleteById(productId);
    }
}
