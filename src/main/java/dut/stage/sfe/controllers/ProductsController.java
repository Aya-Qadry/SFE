package dut.stage.sfe.controllers;

import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dut.stage.sfe.dao.CategoryRepository;
import dut.stage.sfe.model.Category;
import dut.stage.sfe.model.Product;
import dut.stage.sfe.services.CategoryServicesImpl;
import dut.stage.sfe.services.FileUploadUtil;
import dut.stage.sfe.services.ProductServicesImpl;
import dut.stage.sfe.services.UnitServicesImpl;

@Controller
@RequestMapping("/adminhome/products")
public class ProductsController {
    
    @Autowired
    CategoryServicesImpl categoryServicesImpl ; 

    @Autowired
    CategoryRepository repository ; 

    @Autowired
    ProductServicesImpl productServicesImpl ;
    
    @Autowired
    UnitServicesImpl unitServicesImpl ; 

    @GetMapping("/categories")
    public String getTable(Model model){
        model.addAttribute("categorieslist",categoryServicesImpl.findAllCategories());
        return "categories";
    } 

    @PostMapping("/categories/addcategory")
    public String addCategory(Category category ){
        categoryServicesImpl.addCategory(category);
        return "redirect:/adminhome/products/categories" ; 
    }

    @GetMapping("/categories/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        System.out.println("the category's id" +id );
        categoryServicesImpl.deleteByid(id);
        return "redirect:/adminhome/products/categories";
    }

    @GetMapping("/categories/showCategory/{id}")
    @ResponseBody
    public Optional<Category> showCategory(@PathVariable("id") int id) {
        return repository.findById(id) ;
    }

    @GetMapping("/addproduct")
    public String addProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "addproduct"; 
    }
    
    @PostMapping("/addproduct")
    public String addProduct(Product product , @RequestParam("image") MultipartFile multipartFile) throws IOException{
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setPhotos(fileName);
        Product savedProduct = productServicesImpl.addProduct(product);
        String uploadDir = "product-photos/" + savedProduct.getProduct_id();
        FileUploadUtil.saveFile(uploadDir , fileName , multipartFile);

        return "redirect:/adminhome/products/listproducts";
    }

    @GetMapping("/listproducts")
    public String showProducts(Model model){
        model.addAttribute("productslist", productServicesImpl.findAllProducts());
        return "listproducts";
    }

}
