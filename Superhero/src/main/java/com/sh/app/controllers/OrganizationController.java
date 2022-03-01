package com.sh.app.controllers;

import com.sh.app.data.OrganizationDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Organization;
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
public class OrganizationController {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = orgDao.getAllOrganizations();
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("organizations", orgs);
        model.addAttribute("supers", supers);
        return "organizations";
        
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");
        
        List<Supers> supers = new ArrayList<>();
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        org.setSupers(supers);
        orgDao.addOrganization(org);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String orgDetail(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("organization", org);
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDao.deleteOrganizationById(id);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("organization", org);
        model.addAttribute("supers", supers);
        
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(Organization org, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");
        
        List<Supers> supers = new ArrayList<>();
        
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        org.setSupers(supers);
        orgDao.updateOrganization(org);
        
        return "redirect:/organizations";
    }
    
}
