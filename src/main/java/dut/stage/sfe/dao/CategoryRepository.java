package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Category;
import dut.stage.sfe.model.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>  {
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE %:query% OR LOWER(c.description) LIKE %:query%")
    List<Category> searchCategory(String query);
} 
