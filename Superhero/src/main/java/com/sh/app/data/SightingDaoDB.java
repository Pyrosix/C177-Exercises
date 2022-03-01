
package com.sh.app.data;

import com.sh.app.data.LocationDaoDB.LocationMapper;
import com.sh.app.data.SupersDaoDB.SupersMapper;
import com.sh.app.models.Location;
import com.sh.app.models.Sighting;
import com.sh.app.models.Supers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SightingDaoDB implements SightingDao {

   @Autowired
    JdbcTemplate jdbc;
    
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l JOIN sighting s ON s.Locationid = l.id WHERE l.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }
    
    private List<Supers> getSupersForSighting(int id) {
        final String SELECT_SUPERS_FOR_SIGHTING = "SELECT s.* FROM super s JOIN super_sighting ss ON ss.Superid = s.id WHERE ss.Superid = ?";
        return jdbc.query(SELECT_SUPERS_FOR_SIGHTING, new SupersMapper(), id);
    }

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sight =  jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sight.setLocation(getLocationForSighting(id));
            sight.setSupers(getSupersForSighting(id));
            return sight;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    public void associateLocationAndSupers(List<Sighting> sightings) {
        for(Sighting sight : sightings) {
            sight.setLocation(getLocationForSighting(sight.getId()));
            sight.setSupers(getSupersForSighting(sight.getId()));
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTING = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTING, new SightingMapper());
        associateLocationAndSupers(sightings);        
        return sightings;
    }
    
    
    @Override
    public List<Sighting> getSightingsForLocation(Location local) {
        final String SELECT_SIGHTING_FOR_LOCATION = "SELECT * FROM sighting WHERE Locationid = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTING_FOR_LOCATION, new SightingMapper(), local.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsForSupers(Supers supers) {
        final String SELECT_SIGHTING_FOR_SUPERS = "SELECT s.* FROM sighting s JOIN super_sighting ss ON ss.Powerid = s.id WHERE ss.Superid = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTING_FOR_SUPERS, new SightingMapper(), supers.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }
    
    private void insertSuperSighting(Sighting sight) {
        final String INSERT_SUPERS_SIGHTING = "INSERT INTO super_sighting(Sightingid, Superid) VALUES (?,?)";
        
        for(Supers supers : sight.getSupers()) {
            jdbc.update(INSERT_SUPERS_SIGHTING, sight.getId(), supers.getId());
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sight) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(Date, locationid) VALUES(?,?)";
        jdbc.update(INSERT_SIGHTING, sight.getDate(), sight.getLocation().getId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setId(newId);
        insertSuperSighting(sight);
        return sight;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sight) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET Date = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING, sight.getDate(), sight.getId());
        
        final String DELETE_SIGHTING_SUPERS = "DELETE FROM super_sighting WHERE Powerid = ?";
        jdbc.update(DELETE_SIGHTING_SUPERS, sight.getId());
        insertSuperSighting(sight);
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sight = new Sighting();
            sight.setId(rs.getInt("id"));
            sight.setDate(rs.getDate("Date"));
            return sight;
        }
        
    }
}
