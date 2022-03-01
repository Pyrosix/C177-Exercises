package com.sh.app.controllers;

import com.sh.app.data.LocationDao;
import com.sh.app.data.SightingDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Location;
import com.sh.app.models.Sighting;
import com.sh.app.models.Supers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class SightingController {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    LocationDao localDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("sightings")
    public String displaySighting(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locals = localDao.getAllLocations();
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locals);
        model.addAttribute("supers", supers);
        return "sightings";
        
    }
    
    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) throws ParseException {
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        String date = request.getParameter("date");
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d1 = sdf1.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(d1.getTime());
        
        sighting.setDate(sqlDate);
        
        sighting.setLocation(localDao.getLocationById(Integer.parseInt(locationId)));
        
        List<Supers> supers = new ArrayList<>();
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        sighting.setSupers(supers);
        sightingDao.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Location> locals = localDao.getAllLocations();
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locals);
        model.addAttribute("supers", supers);
        
        return "editSighting";
    }
     
    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        String[] superIds = request.getParameterValues("superId");
        
        sighting.setLocation(localDao.getLocationById(Integer.parseInt(locationId)));
        List<Supers> supers = new ArrayList<>();
        
        for(String superId : superIds) {
            supers.add(supersDao.getSuperById(Integer.parseInt(superId)));
        }
        
        sighting.setSupers(supers);
        sightingDao.updateSighting(sighting);
        
        return "redirect:/sightings";
    }
}
