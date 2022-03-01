package com.sh.app.controllers;

import com.sh.app.data.LocationDao;
import com.sh.app.models.Location;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {
    
    @Autowired
    LocationDao locationDao;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locals = locationDao.getAllLocations();
        model.addAttribute("locations", locals);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String coords = request.getParameter("coordinates");
        
        Location local = new Location();
        local.setName(name);
        local.setDescription(description);
        local.setAddress(address);
        local.setCoordinates(coords);
        
        locationDao.addLocation(local);
        
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);
        
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location local = locationDao.getLocationById(id);
        
        model.addAttribute("location", local);
        
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location local = locationDao.getLocationById(id);
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String coords = request.getParameter("coordinates");
        
        local.setName(name);
        local.setDescription(description);
        local.setAddress(address);
        local.setCoordinates(coords);
        
        locationDao.updateLocation(local);
        
        return "redirect:/locations";
    }
}


