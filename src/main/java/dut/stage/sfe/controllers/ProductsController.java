package dut.stage.sfe.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;

import dut.stage.sfe.dao.CategoryRepository;
import dut.stage.sfe.dao.OrderProductRepository;
import dut.stage.sfe.dao.ProductRepository;
import dut.stage.sfe.model.Category;
import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.model.Product;
import dut.stage.sfe.model.Stock;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.CategoryServicesImpl;
import dut.stage.sfe.services.FileUploadUtil;
import dut.stage.sfe.services.OrderProductServicesImpl;
import dut.stage.sfe.services.ProductServicesImpl;
import dut.stage.sfe.services.StockServicesImpl;
import dut.stage.sfe.services.UnitServicesImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adminhome/products")
public class ProductsController {

    @Autowired
    CategoryServicesImpl categoryServicesImpl;

    @Autowired
    CategoryRepository repository;

    @Autowired
    ProductServicesImpl productServicesImpl;

    @Autowired
    UnitServicesImpl unitServicesImpl;

    @Autowired
    StockServicesImpl stockServicesImpl;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductServicesImpl orderProductServicesImpl;

    @Autowired
    OrderProductRepository orderProductRepository;

    @GetMapping("/categories")
    public String getTable(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/categories/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        // List<User> users = userServicesImpl.findAllUsers() ;

        Page<Category> page = categoryServicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        // total number of all the items in the table
        Long totalItems = page.getTotalElements();
        List<Category> categories = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("categorieslist", categories);
        return "categories";
    }

    @GetMapping("/categories/search")
    @ResponseBody
    public List<Category> searchCategory(@RequestParam("query") String query) {
        List<Category> filteredData = categoryServicesImpl.searchCategory(query);
        return filteredData;
    }

    @PostMapping("/categories/addcategory")
    public String addCategory(@Valid Category category) {
        categoryServicesImpl.addCategory(category);
        return "redirect:/adminhome/products/categories";
    }

    @GetMapping("/categories/deleteCategory/{id}")
    @Transactional
    public String deleteCategory(@PathVariable("id") int id) {
        Category category = categoryServicesImpl.findById(id);
        List<Product> listProducts = productServicesImpl.findAllProducts();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();
        List<Stock> stock = stockServicesImpl.findAllStocks();

        for (Product product : listProducts) {
            if (product.getCategory_id().getCategory_id() == category.getCategory_id()) {

                // Check if the product is in stock
                for (Stock stock2 : stock) {
                    Product product2 = productServicesImpl.findById(stock2.getProduct_id());
                    if (product2.getProduct_id() == product.getProduct_id()) {
                        // Product found in stock

                        // Check if the product is in order products
                        for (OrderProducts orderProduct : orderProducts) {
                            if (orderProduct.getStock().getProduct().getProduct_id() == product.getProduct_id()) {
                                // Product found in order products
                                orderProductRepository.deleteById(orderProduct.getOrderproducts_id());

                                break;
                            }
                        }

                        stockServicesImpl.deleteByid(stock2.getStock_id());
                    }
                }

                productServicesImpl.deleteById(product.getProduct_id());
            }
        }

        categoryServicesImpl.deleteByid(id);
        return "redirect:/adminhome/products/categories";
    }

    @GetMapping("/categories/getCatErrors/{id}")
    @ResponseBody
    public List<String> getCatErrors(@PathVariable("id") int id) {
        List<String> error = new ArrayList<>();
        Category category = categoryServicesImpl.findById(id);
        List<Product> listProducts = productServicesImpl.findAllProducts();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();
        List<Stock> stock = stockServicesImpl.findAllStocks();

        for (Product product : listProducts) {
            if (product.getCategory_id().getCategory_id() == category.getCategory_id()) {

                // Check if the product is in stock
                for (Stock stock2 : stock) {
                    Product product2 = productServicesImpl.findById(stock2.getProduct_id());
                    if (product2.getProduct_id() == product.getProduct_id()) {
                        // Product found in stock

                        // Check if the product is in order products
                        for (OrderProducts orderProduct : orderProducts) {
                            if (orderProduct.getStock().getProduct().getProduct_id() == product.getProduct_id()) {
                                // Product found in order products
                                error.add(
                                        "This category is present in an order , deleting it would result in deleting the prodcut from the order's cart");
                            }
                        }
                        error.add(
                                "This category is present in the app's stock , deleting it would result in deleting its products from the stock ");
                    }
                }
                error.add("This category represents a product , deleting would also delete the product ");
            }
        }
        return error;

    }

    @GetMapping("/categories/editCategory/{id}")
    @ResponseBody
    public Category editCategory(@PathVariable("id") int id) {
        return categoryServicesImpl.findById(id);
    }

    @PostMapping("/categories/editCategory")
    public String editCat(Category category) {
        category.setDate(LocalDateTime.now());
        categoryServicesImpl.updateCategory(category.getCategory_id(), category);
        return "redirect:/adminhome/products/categories";
    }

    @GetMapping("/categories/showCategory/{id}")
    @ResponseBody
    public Optional<Category> showCategory(@PathVariable("id") int id) {
        return repository.findById(id);
    }

    @GetMapping("/addproduct")
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("categories", categoryServicesImpl.findAllCategories());
        model.addAttribute("product", product);
        return "addproduct";
    }

    @PostMapping("/addproduct")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult resultProduct,
            @Valid @RequestParam("image") MultipartFile multipartFile, BindingResult resultImage, Model model)
            throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setPhotos(fileName);
        if (resultProduct.hasErrors() || resultImage.hasErrors()) {
            model.addAttribute("categories", categoryServicesImpl.findAllCategories());
            model.addAttribute("product", product);
            return "addproduct";
        }
        Product savedProduct = productServicesImpl.addProduct(product);
        String uploadDir = "product-photos/" + savedProduct.getProduct_id();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/adminhome/products/listproducts";
    }

    @GetMapping("/listproducts")
    public String showProducts(Model model) {

        model.addAttribute("stocklist", stockServicesImpl.findAllStocks());
        model.addAttribute("productslist", productRepository.findProductsWithoutStock());
        return "listproducts";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam("query") String query) {
        List<Product> filteredData = productServicesImpl.searchProducts(query);
        return filteredData;
    }

    @GetMapping("/deleteProduct/{id}")
    @Transactional
    public String delete(@PathVariable("id") int id, Model model) {
        // User user = userServicesImpl.findById(id);
        Product product = productServicesImpl.findById(id);
        List<Stock> stocklist = stockServicesImpl.findAllStocks();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();
        for (Stock stock : stocklist) {
            if (stock.getProduct_id() == product.getProduct_id()) { 
                for (OrderProducts orderProducts2 : orderProducts) {
                    if (orderProducts2.getStock().getProduct_id() == product.getProduct_id()) {
                        orderProductServicesImpl.deleteByStock(stock);
                    }
                }
            }
            stockServicesImpl.deleteByProduct(product);
            productServicesImpl.deleteById(id);

        }
        return "redirect:/adminhome/products/listproducts";
    }

    @GetMapping("/delete/{id}")
    public String deleteP(@PathVariable("id") int id) {

        productServicesImpl.deleteById(id);

        return "redirect:/adminhome/products/listproducts";
    }

    @GetMapping("/getProductErrors/{id}")
    @ResponseBody
    public List<String> getProductInfo(@PathVariable("id") int id) {
        List<String> error = new ArrayList<>();

        Product product = productServicesImpl.findById(id);
        List<Stock> stocklist = stockServicesImpl.findAllStocks();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();

        for (Stock stock : stocklist) {
            if (stock.getProduct_id() == product.getProduct_id()) {
                error.add(
                        "This product is in stock , deleting it would remove it permanently from the stock           ");
                for (OrderProducts orderProducts2 : orderProducts) {
                    if (orderProducts2.getStock().getProduct_id() == product.getProduct_id()) {
                        error.add(
                                "This product is present in an order , deleting it would result in deleting the entire order");
                    }
                }
            } else {
                System.out.println("not in stock");
            }
        }
        return error;

    }

}
