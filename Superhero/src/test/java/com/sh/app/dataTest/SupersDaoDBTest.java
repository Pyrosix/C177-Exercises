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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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
public class SupersDaoDBTest {
    
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
    
    public SupersDaoDBTest() {
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
    public void testGetAllSupers() {
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        Supers super2 = new Supers();
        super2.setName("Test Name 2");
        super2.setClassification("TClass2");
        super2.setDescription("Test Descr 2");
        super2 = supersDao.addSuper(super2);
        
        List<Supers> supers = supersDao.getAllSupers();
        
        assertEquals(2, supers.size());
        assertTrue(supers.contains(super1));
        assertTrue(supers.contains(super2));
    }
    
    public void testUpdateSupers() {
        Supers super1 = new Supers();
        super1.setName("Test Name");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        Supers fromDao = supersDao.getSuperById(super1.getId());
        assertEquals(super1, fromDao);
        
        super1.setName("New Test Name");
        supersDao.updateSupers(super1);
        
        assertNotEquals(super1, fromDao);
        
        fromDao = supersDao.getSuperById(super1.getId());
        
        assertEquals(super1, fromDao);
    }

    @Test
    public void testDeleteSupersById() {
        Supers super1 = new Supers();
        super1.setName("Test First");
        super1.setClassification("TClass");
        super1.setDescription("Test Descr");
        super1 = supersDao.addSuper(super1);
        
        
        Supers fromDao = supersDao.getSuperById(super1.getId());
        assertEquals(super1, fromDao);
        
        supersDao.deleteSupersById(super1.getId());
        
        fromDao = supersDao.getSuperById(super1.getId());
        assertNull(fromDao);
    }
}
