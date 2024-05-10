package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;

import dut.stage.sfe.model.Category;

public interface CategoryServies {

    void addCategory(Category category);

    Category findById(int id);

    void deleteByid(int id);

    List<Category> findAllCategories();

    void updateCategory(int id, Category category);

    List<Category> searchCategory(String query);

    Page<Category> findPage(int pageNumber);
}
