package com.market.wingy.service;

import com.market.wingy.model.Product;
import com.market.wingy.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(ObjectId id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllByShopId(ObjectId shopId) {
        return productRepository.findAllByShopId(shopId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(ObjectId id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> update(ObjectId id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setImage(updatedProduct.getImage());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setCategory(updatedProduct.getCategory());
                    existingProduct.setShop(updatedProduct.getShop());
                    existingProduct.setOptions(updatedProduct.getOptions());
                    return productRepository.save(existingProduct);
                });
    }
}