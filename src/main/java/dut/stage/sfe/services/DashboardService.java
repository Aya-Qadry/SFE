package dut.stage.sfe.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.OrderRepository;
import dut.stage.sfe.dao.ProductRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.Product;
import dut.stage.sfe.model.User;

@Service
public class DashboardService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository customerRepository;

    @Autowired
    ProductRepository productRepository ; 

    public List<Integer> OrdersByMonth() {
        List<Integer> ordersData = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        Map<Month, Integer> ordersByMonth = new HashMap<>();
    
        for (Order order : orders) {
            Month month = order.getDate().getMonth();
            ordersByMonth.put(month, ordersByMonth.getOrDefault(month, 0) + 1);
        }
    
        for (Month month : Month.values()) {
            int count = ordersByMonth.getOrDefault(month, 0);
            ordersData.add(count);
        }
    
        return ordersData;
    }
    

    public List<Integer> CustomerByMonth() {
        List<Integer> customerData = new ArrayList<>();
        List<User> customers = customerRepository.findByRoleName("CUSTOMER");
        Map<Month, Integer> customersByMonth = new HashMap<>();
        for (User customer : customers) {
            Month month = customer.getDate().getMonth();
            customersByMonth.put(month, customersByMonth.getOrDefault(month, 0) + 1);
        }
    
        for (Month month : Month.values()) {
            int count = customersByMonth.getOrDefault(month, 0);
            customerData.add(count);
        }
        return customerData;
    }
     
    public Map<String, Double> getProductCategoryPercentages() {
        List<Product> products = productRepository.findAll();
        Map<String, Integer> categoryCounts = new HashMap<>();
        int totalProducts = products.size();

        for (Product product : products) {
            String category = product.getCategory_id().getName();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        Map<String, Double> categoryPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            double percentage = (count * 100.0) / totalProducts;
            double round = Math.round(percentage);
            categoryPercentages.put(category,round);
        }
        return categoryPercentages;
    }

}
