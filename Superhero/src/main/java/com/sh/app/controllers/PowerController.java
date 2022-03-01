package com.sh.app.controllers;

import com.sh.app.data.PowersDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Powers;
import com.sh.app.models.Supers;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class PowerController {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    PowersDao powDao;
    
    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Powers> pows = powDao.getAllPowers();
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("powers", pows);
        model.addAttribute("supers", supers);
        return "powers";
        
    }
    
    @PostMapping("addPower")
    public String addPowers(Powers pow, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");
        
        List<Supers> supers = new ArrayList<>();
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        pow.setSupers(supers);
        powDao.addPowers(pow);
        
        return "redirect:/powers";
    }
    
    @GetMapping("powerDetail")
    public String powerDetail(Integer id, Model model) {
        Powers pow = powDao.getPowersById(id);
        model.addAttribute("power", pow);
        return "powerDetail";
    }
    
    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        powDao.deletePowersById(id);
        
        return "redirect:/powers";
    }
    
    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        Powers pow = powDao.getPowersById(id);
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("power", pow);
        model.addAttribute("supers", supers);
        
        return "editPowers";
    }
    
    @PostMapping("editPower")
    public String performEditPower(Powers pow, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");
        
        List<Supers> supers = new ArrayList<>();
        
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        pow.setSupers(supers);
        powDao.updatePowers(pow);
        
        return "redirect:/powers";
    }
}
