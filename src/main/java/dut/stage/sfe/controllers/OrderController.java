package dut.stage.sfe.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.OrderProductRepository;
import dut.stage.sfe.dao.OrderRepository;
import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.services.OrderProductServicesImpl;
import dut.stage.sfe.services.OrderServicesImpl;

@Controller
@RequestMapping("/adminhome/orders")
public class OrderController {

    @Autowired
    OrderServicesImpl orderServicesImpl;
    @Autowired
    OrderProductServicesImpl orderProductServicesImpl;
    @Autowired
    OrderRepository repo;
    @Autowired
    OrderProductRepository oRepository;

    @GetMapping("/listorders")
    public String getTable(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/listorders/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) { 

        Page<Order> page = orderServicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        // total number of all the items in the table
        Long totalItems = page.getTotalElements();
        List<Order> orders = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("orderlist", orders);
        return "orders";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Order> searchOrder(@RequestParam("query") String query) {
        List<Order> filteredData = orderServicesImpl.searchOrders(query);
        return filteredData;
    }


    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        // orderProductServicesImpl.deleteByOrderid(id);
        orderServicesImpl.deleteById(id);
        return "redirect:/adminhome/orders/listorders";
    }

    @GetMapping("/showOrder/{orderId}")
    @ResponseBody
    public List<OrderProducts> showOrder(@PathVariable("orderId") int id) {
        Order order = orderServicesImpl.findById(id);
        List<OrderProducts> orderProductsList = oRepository.findByOrder(order);
        
        return orderProductsList;
    }


}
