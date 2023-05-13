package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>  {
    
}
