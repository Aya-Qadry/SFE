package dut.stage.sfe.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.ShippingRepository;
import dut.stage.sfe.dao.UnitRepository;
import dut.stage.sfe.model.MesurementUnit;
import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.model.Product;
import dut.stage.sfe.model.ShippingOptions;
import dut.stage.sfe.model.Stock;
import dut.stage.sfe.model.ShippingOptions;
import dut.stage.sfe.services.OrderProductServicesImpl;
import dut.stage.sfe.services.OrderServicesImpl;
import dut.stage.sfe.services.ShippingOptionServiceImpl;
import dut.stage.sfe.services.StockServicesImpl;
import dut.stage.sfe.services.UnitServicesImpl;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/adminhome/configuration")
public class ConfigurationController {

    @Autowired
    UnitServicesImpl unitServicesImpl;

    @Autowired
    ShippingOptionServiceImpl shippingService;

    @Autowired
    UnitRepository repository;

    @Autowired
    ShippingRepository shippingRepository;

    @Autowired
    OrderServicesImpl orderServicesImpl;

    @Autowired
    OrderProductServicesImpl orderProductServicesImpl;

    @Autowired
    StockServicesImpl stockServicesImpl;

    @GetMapping("/listunits")
    public String listUnits(Model model) {
        return getOnePageUnit(model, 1);
    }

    @GetMapping("/listunits/{pageNumber}")
    public String getOnePageUnit(Model model, @PathVariable("pageNumber") int currentPage) {

        Page<MesurementUnit> page = unitServicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        Long totalItems = page.getTotalElements();
        List<MesurementUnit> unitslist = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("unitslist", unitslist);
        return "listunits";
    }

    @GetMapping("/units/search")
    @ResponseBody
    public List<MesurementUnit> searchUnits(@RequestParam("query") String query) {
        List<MesurementUnit> filteredData = unitServicesImpl.searchUnit(query);
        return filteredData;
    }

    @GetMapping("/deleteUnit/{id}")
    @Transactional
    public String deleteUnit(@PathVariable("id") int id) {
        // unitServicesImpl.deleteById(id);
        MesurementUnit unit = unitServicesImpl.findUnitById(id);
        List<Stock> stock = stockServicesImpl.findAllStocks();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();

        for (Stock stock2 : stock) {
            if (stock2.getUnit_id().getUnit_id() == unit.getUnit_id()) {
                for (OrderProducts orderProducts2 : orderProducts) {
                    if (orderProducts2.getStock().getUnit_id().getUnit_id() == unit.getUnit_id()) {
                        orderProductServicesImpl.deleteByOrderid(orderProducts2.getOrderid());
                    }
                }
                stockServicesImpl.deleteByid(stock2.getStock_id());
            }
        }
        unitServicesImpl.deleteById(id);
        return "redirect:/adminhome/configuration/listunits";
    }

    @GetMapping("/getUnitErrors/{id}")
    @ResponseBody
    public List<String> getUnitErrors(@PathVariable("id") int id) {
        MesurementUnit unit = unitServicesImpl.findUnitById(id);
        List<Stock> stock = stockServicesImpl.findAllStocks();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();

        List<String> error = new ArrayList<>();

        for (Stock stock2 : stock) {
            if (stock2.getUnit_id().getUnit_id() == unit.getUnit_id()) {
                for (OrderProducts orderProducts2 : orderProducts) {
                    if (orderProducts2.getStock().getUnit_id().getUnit_id() == unit.getUnit_id()) {
                        error.add(
                                "This unit is a part of an order , deleting it would delete the products of the order ");
                    }
                }
                error.add("This unit describes a product in stock , deleting it would remove the product from stock ");
            }
        }
        return error;
    }

    @GetMapping("/editUnit/{id}")
    @ResponseBody
    public MesurementUnit editUnit(@PathVariable("id") int id) {
        return unitServicesImpl.findUnitById(id);
    }

    @PostMapping("/saveUnit")
    public String editUnit(MesurementUnit mesurementUnit) {
        mesurementUnit.setDate(LocalDateTime.now());
        repository.save(mesurementUnit);
        return "redirect:/adminhome/configuration/listunits";
    }

    @GetMapping("/listoptions")
    public String listOptions(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/listoptions/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {

        Page<ShippingOptions> page = shippingService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        Long totalItems = page.getTotalElements();
        List<ShippingOptions> listoptions = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("listoptions", listoptions);
        return "listoptions";
    }

    @GetMapping("/options/search")
    @ResponseBody
    public List<ShippingOptions> searchShippingOptions(@RequestParam("query") String query) {
        List<ShippingOptions> filteredData = shippingService.searchOptions(query);
        return filteredData;
    }

    @PostMapping("/addoption")
    public String addOption(ShippingOptions shippingOptions) {
        // shippingService.add(shippingOptions);
        shippingOptions.setDate(LocalDateTime.now());
        shippingRepository.save(shippingOptions);
        return "redirect:/adminhome/configuration/listoptions";
    }

    @GetMapping("/deleteOption/{id}")
    @Transactional
    public String deleteOption(@PathVariable("id") int id) {
        // shippingService.deleteById(id);
        ShippingOptions option = shippingService.findById(id);
        List<Order> orders = orderServicesImpl.findAllOrders();
        List<OrderProducts> orderProducts = orderProductServicesImpl.findAll();
        for (Order order : orders) {
            if (order.getShipping_id() == option.getShipping_id()) {
                for (OrderProducts orderProducts2 : orderProducts) {
                    if (orderProducts2.getOrderid() == order.getOrder_id()) {
                        orderProductServicesImpl.deleteByOrderid(order.getOrder_id());
                    }
                }
                orderServicesImpl.deleteById(order.getOrder_id());
            }
        }
        shippingService.deleteById(id);

        return "redirect:/adminhome/configuration/listoptions";
    }

    @GetMapping("/getOptionErrors/{id}")
    @ResponseBody
    public String getOptionErrors(@PathVariable("id") int id) {
        String errors = "";
        ShippingOptions option = shippingService.findById(id);
        List<Order> orders = orderServicesImpl.findAllOrders();

        for (Order order : orders) {
            if (order.getShipping_id() == option.getShipping_id()) {
                errors = "This shipping option is related to a couple of orders , deleting it would result in deleting the orders";
            }
        }
        return errors;
    }
 
    @GetMapping("/editOption/{id}")
    @ResponseBody
    public ShippingOptions editOption(@PathVariable("id") int id) {
        return shippingService.findById(id);
    }

    @GetMapping("/showOption/{id}")
    @ResponseBody
    public ShippingOptions showCategory(@PathVariable("id") int id) {
        return shippingService.findById(id);
    }
}
