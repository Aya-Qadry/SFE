package dut.stage.sfe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dut.stage.sfe.dao.StockRepository;
import dut.stage.sfe.model.Product;
import dut.stage.sfe.model.Stock;
import dut.stage.sfe.services.ProductServicesImpl;
import dut.stage.sfe.services.StockServices;
import dut.stage.sfe.services.StockServicesImpl;
import dut.stage.sfe.services.UnitServicesImpl;

@Controller
@RequestMapping("/adminhome/stock")
public class StockController {
    
    @Autowired
    StockServicesImpl stockServicesImpl ; 

    @Autowired
    UnitServicesImpl unitServicesImpl ; 

    @Autowired
    ProductServicesImpl productServicesImpl ; 

    @Autowired
    StockRepository repository ; 
    
    @GetMapping("/addToStock/{id}")
    public String getId( Model model , @PathVariable("id") int id){
        Product p = productServicesImpl.findById(id) ; 
        model.addAttribute("product_id", p);
        model.addAttribute("measurementUnits", unitServicesImpl.findAllUnits());
        return "addstock";
    }

    @PostMapping("/addToStock")
    public String add(@ModelAttribute("product_id") Product product , Stock stock ){
        // Product p = product ; 
        stock.setProduct_id(product);
        System.out.println("product "+product.getName());
        // repository.save(stock);
        stockServicesImpl.addToStock(stock);
        return "redirect:/adminhome/products/listproducts";
    }
}
