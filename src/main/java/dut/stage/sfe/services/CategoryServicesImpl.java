package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.CategoryRepository;
import dut.stage.sfe.model.Category;

@Service
public class CategoryServicesImpl implements CategoryServies{

    @Autowired
    CategoryRepository repository ;

    @Override
    public void addCategory(Category category) {
        repository.save(category);
    }

    @Override
    public Category findById(int id) {
        Category c = repository.findById(id)
                    .orElse(null); 
        return c ;
    }

    @Override
    public void deleteByid(int id) {
       repository.deleteById(id);
    }

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }
    
}
