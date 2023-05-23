package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Product;

public interface ProductServices {
    Product addProduct(Product product);
    List<Product> findAllProducts();
    Product findById(int id);
    Long getTotalProducts();
}
