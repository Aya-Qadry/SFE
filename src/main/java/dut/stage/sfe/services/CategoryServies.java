package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Category;

public interface CategoryServies {
    
    void addCategory(Category category);
    Category findById(int id);
    void deleteByid(int id);
    List<Category> findAllCategories();
    
}
