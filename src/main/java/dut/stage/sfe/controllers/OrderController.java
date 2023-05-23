package dut.stage.sfe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    OrderProductRepository oRepository ; 
    @GetMapping("/listorders")
    public String getOrders(Model model) {
        model.addAttribute("orderlist", orderServicesImpl.findAllOrders());
        return "orders";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        // orderProductServicesImpl.deleteByOrderid(id);
        orderServicesImpl.deleteById(id);
        return "redirect:/adminhome/orders/listorders";
    }

    @GetMapping("/showOrder/{id}")
    @ResponseBody
    public OrderProducts showOrder(@PathVariable("id") int id ) {
        Order o = orderServicesImpl.findById(id) ;
        OrderProducts order= oRepository.findByOrder(o);
        System.out.println("test "+order.getPrice());
        // List<OrderProducts> products = orderProductServicesImpl.findAllByOrderId(id);
        // model.addAttribute("products", products );
        // model.addAttribute("order", order);
        return order;
    }

}
