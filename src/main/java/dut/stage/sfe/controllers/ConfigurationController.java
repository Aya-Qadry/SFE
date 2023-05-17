package dut.stage.sfe.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.UnitRepository;
import dut.stage.sfe.model.MesurementUnit;
import dut.stage.sfe.model.ShippingOptions;
import dut.stage.sfe.model.ShippingOptions;
import dut.stage.sfe.services.ShippingOptionServiceImpl;
import dut.stage.sfe.services.UnitServicesImpl;

@Controller
@RequestMapping("/adminhome/configuration")
public class ConfigurationController {
    
    @Autowired
    UnitServicesImpl unitServicesImpl ; 

    @Autowired
    ShippingOptionServiceImpl shippingService ; 
    
    @Autowired
    UnitRepository repository ; 

    @GetMapping("/listunits")
    public String listUnits(Model model){
        model.addAttribute("unitslist", unitServicesImpl.findAllUnits());
        return "listunits";
    }

    @GetMapping("/deleteUnit/{id}")
    public String deleteUnit(@PathVariable("id") int id){
        unitServicesImpl.deleteById(id);    
        return "redirect:/adminhome/configuration/listunits";
    }

    @GetMapping("/editUnit/{id}")
    @ResponseBody
    public MesurementUnit editUnit(@PathVariable("id") int id ){
        return unitServicesImpl.findUnitById(id);
    }

    @PostMapping("/saveUnit")
    public String editUnit(MesurementUnit mesurementUnit){
        repository.save(mesurementUnit);
        return "redirect:/adminhome/configuration/listunits";
    }

    @GetMapping("/listoptions")
    public String listOptions(Model model){
        model.addAttribute("listoptions", shippingService.findAllOptions());
        return "listoptions";
    }

    @PostMapping("/addoption")
    public String addOption(ShippingOptions shippingOptions ){
        shippingService.add(shippingOptions);
        return "redirect:/adminhome/configuration/listoptions" ; 
    }

    @GetMapping("/deleteOption/{id}")
    public String deleteOption(@PathVariable("id") int id){
        shippingService.deleteById(id);
        return "redirect:/adminhome/configuration/listoptions";
    }

    @GetMapping("/showOption/{id}")
    @ResponseBody
    public ShippingOptions showCategory(@PathVariable("id") int id) {
        return shippingService.findById(id) ;
    } 
}
