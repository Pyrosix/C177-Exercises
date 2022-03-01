package com.sh.app.controllers;

import com.sh.app.data.SupersDao;
import com.sh.app.models.Supers;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupersController {
    
    @Autowired
    SupersDao supersDao;
    
    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        return "supers";
    }
    
    @PostMapping("addSuper")
    public String addSupers(HttpServletRequest request) {
        String name = request.getParameter("name");
        String classifier = request.getParameter("classification");
        String description = request.getParameter("description");
        
        Supers supers = new Supers();
        supers.setName(name);
        supers.setClassification(classifier);
        supers.setDescription(description);
        
        supersDao.addSuper(supers);
        
        return "redirect:/supers";
    }
    
    @GetMapping("deleteSuper")
    public String deleteSupers(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        supersDao.deleteSupersById(id);
        
        return "redirect:/supers";
    }
    
    @GetMapping("editSuper")
    public String editSupers(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Supers supers = supersDao.getSuperById(id);
        
        model.addAttribute("super", supers);
        
        return "editSuper";
    }
    
    @PostMapping("editSuper")
    public String performEditSupers(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Supers supers = supersDao.getSuperById(id);
        String name = request.getParameter("name");
        String classifier = request.getParameter("classification");
        String description = request.getParameter("description");
        
        supers.setName(name);
        supers.setClassification(classifier);
        supers.setDescription(description);
        
        supersDao.updateSupers(supers);
        
        return "redirect:/supers";
    }
}


