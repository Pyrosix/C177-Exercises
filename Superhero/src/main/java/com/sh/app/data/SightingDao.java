package com.sh.app.data;

import com.sh.app.models.Location;
import com.sh.app.models.Sighting;
import com.sh.app.models.Supers;
import java.util.List;

public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sight);
    void updateSighting(Sighting sight);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForLocation(Location location);
    List<Sighting> getSightingsForSupers(Supers supers);
}
