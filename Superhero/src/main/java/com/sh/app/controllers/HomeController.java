package com.sh.app.controllers;

import com.sh.app.data.LocationDao;
import com.sh.app.data.SightingDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Location;
import com.sh.app.models.Sighting;
import com.sh.app.models.Supers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    LocationDao localDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("home")
    public String displaySighting(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locals = localDao.getAllLocations();
        List<Supers> supers = supersDao.getAllSupers();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locals);
        model.addAttribute("supers", supers);
        return "home";
    }
}
