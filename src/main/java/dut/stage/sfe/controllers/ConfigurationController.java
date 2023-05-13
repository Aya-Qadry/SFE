package dut.stage.sfe.controllers;

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
import dut.stage.sfe.services.UnitServicesImpl;

@Controller
@RequestMapping("/adminhome/configuration")
public class ConfigurationController {
    
    @Autowired
    UnitServicesImpl unitServicesImpl ; 

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

}
