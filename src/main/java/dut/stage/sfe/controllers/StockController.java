package dut.stage.sfe.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    StockServicesImpl stockServicesImpl;

    @Autowired
    UnitServicesImpl unitServicesImpl;

    @GetMapping("/addToStock/{id}")
    public String getId(Model model, @PathVariable("id") int id) {
        model.addAttribute("product_id", id);
        model.addAttribute("stocklist", stockServicesImpl.findAllStocks());
        model.addAttribute("measurementUnits", unitServicesImpl.findAllUnits());
        model.addAttribute("stock", stockServicesImpl.findById(id));
        return "addstock";
    }

    @PostMapping("/addToStock")
    public String add(@RequestParam("product_id") int product, Stock stock) {
        System.out.println("product lol " + product);
        stock.setProduct_id(product);
        stockServicesImpl.addToStock(stock);
        return "redirect:/adminhome/products/listproducts";
    }

    @GetMapping("/showstock")
    public String showstock(Model model) {
        // Assuming you have a list of stocks named "stocks
        model.addAttribute("liststock", stockServicesImpl.findAllStocks());
        
        return "stock";
    }

    @GetMapping("/getID")
    public String getID(@RequestParam("stock_id") String id){
        System.out.println("stocks id"+id);
        return "stock";
    }

    // @GetMapping("/deletefromstock/{id}")
    // public String deleteCategory(@PathVariable("id") int id){
    //     System.out.println("the category's id" +id );
    //     stockServicesImpl.deleteByid(id);
    //     return "redirect:/adminhome/products/categories";
    // }
    
}