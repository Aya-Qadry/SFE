package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.ProductRepository;
import dut.stage.sfe.model.Product;

@Service
public class ProductServicesImpl implements ProductServices{

    @Autowired
    ProductRepository repository ;

    @Override
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product findById(int id) {
        Product p = repository.findById(id).orElse(null) ; 
        return p;
    }

    @Override
    public Long getTotalProducts() {
        return repository.count();
    }
    
}
