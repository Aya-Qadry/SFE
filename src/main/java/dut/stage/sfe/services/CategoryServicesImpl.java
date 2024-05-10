package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.CategoryRepository;
import dut.stage.sfe.model.Category;
import dut.stage.sfe.model.User;

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

    @Override
    public void updateCategory(int id, Category category) {
       Category savedCat = repository.findById(id).orElse(null);
        savedCat.setCategory_id(id);
        savedCat.setDate(savedCat.getDate());
        savedCat.setDescription(category.getDescription());
        savedCat.setName(category.getName());
        savedCat.setEnabled(savedCat.getEnabled());
        repository.save(savedCat);
    }

    @Override
    public List<Category> searchCategory(String query) {
        return repository.searchCategory(query);
    }
    
    @Override
    public Page<Category> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findAll(pageable);
    }
}
