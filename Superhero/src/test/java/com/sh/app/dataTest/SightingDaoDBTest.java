package com.sh.app.dataTest;

import com.sh.app.data.LocationDao;
import com.sh.app.data.OrganizationDao;
import com.sh.app.data.PowersDao;
import com.sh.app.data.SightingDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Location;
import com.sh.app.models.Organization;
import com.sh.app.models.Powers;
import com.sh.app.models.Sighting;
import com.sh.app.models.Supers;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    PowersDao powersDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         List<Supers> supers = supersDao.getAllSupers();
        for (Supers sups : supers) {
           supersDao.getSuperById(sups.getId());
        }
        
        List<Location> locals = locationDao.getAllLocations();
        for (Location local : locals) {
            locationDao.getLocationById(local.getId());
        }
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization org : orgs) {
            orgDao.getOrganizationById(org.getId());
        }
        
        List<Powers> pows = powersDao.getAllPowers();
        for (Powers pow : pows) {
            powersDao.getPowersById(pow.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sight : sightings) {
            sightingDao.getSightingById(sight.getId());
        }
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddAndGettSighting() {
        
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }
    
    @Test
    public void testGetAllSupers() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting1 = new Sighting();
        Date dt1 = new Date(1001-01-01);
        sighting1.setDate(dt1);
        sighting1.setSupers(supers);
        sighting1.setLocation(location);
        sighting1 = sightingDao.addSighting(sighting1);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting1));
    }
    
    public void testUpdateSupers() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        Date dt1 = new Date(1011-01-01);
        sighting.setDate(dt1);
        Supers super2 = new Supers();
        super2.setName("Test Name 2");
        super2.setClassification("TClas2");
        super2.setDescription("Test Descr 2");
        super2 = supersDao.addSuper(super2);
        supers.add(super2);
        sighting.setSupers(supers);
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSupersById() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
    
    @Test
    public void testGetSightingForLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Location location1 = new Location();
        location1.setName("Test Location");
        location1.setDescription("Test Descr");
        location1.setAddress("Test Address");
        location1.setCoordinates("TCords");
        location1 = locationDao.addLocation(location1);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting1 = new Sighting();
        Date dt1 = new Date(1001-11-11);
        sighting1.setDate(dt1);
        sighting1.setSupers(supers);
        sighting1.setLocation(location);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        Date dt2 = new Date(2002-02-02);
        sighting2.setDate(dt2);
        sighting2.setSupers(supers);
        sighting2.setLocation(location1);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getSightingsForLocation(location);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
        
    }
    
    @Test
    public void testGetSightingForSuper() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        Supers super2 = new Supers();
        super2.setName("Test Name 2");
        super2.setClassification("TClas2");
        super2.setDescription("Test Descr2");
        super2 = supersDao.addSuper(super2);
        
        List<Supers> supers = new ArrayList<>();
        supers.add(super1);
        supers.add(super2);
        
        List<Supers> supers2 = new ArrayList<>();
        supers2.add(super2);
        
        Sighting sighting = new Sighting();
        Date dt = new Date(1000-01-01);
        sighting.setDate(dt);
        sighting.setSupers(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting1 = new Sighting();
        Date dt1 = new Date(1001-11-11);
        sighting1.setDate(dt1);
        sighting1.setSupers(supers2);
        sighting1.setLocation(location);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        Date dt2 = new Date(2002-02-02);
        sighting2.setDate(dt2);
        sighting2.setSupers(supers);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getSightingsForLocation(location);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
    }
}
