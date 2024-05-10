package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p.* FROM products p LEFT JOIN stock s ON p.product_id = s.product_id WHERE s.product_id IS NULL", nativeQuery = true)
    List<Product> findProductsWithoutStock();

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE %:query%")
    List<Product> searchProducts( String query);

}
