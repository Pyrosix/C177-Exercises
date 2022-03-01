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
public class PowersDaoDBTest {
    
    @Autowired
    SupersDao supersDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    PowersDao powsDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public PowersDaoDBTest() {
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
        
        List<Powers> pows = powsDao.getAllPowers();
        for (Powers pow : pows) {
            powsDao.getPowersById(pow.getId());
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
    public void testGetAllPowers() {
        Powers pows1 = new Powers();
        pows1.setName("Test Name");
        pows1 = powsDao.addPowers(pows1);
        
        Powers pows2 = new Powers();
        pows2.setName("Test Name 2");
        pows2 = powsDao.addPowers(pows2);
        
        List<Powers> pows = powsDao.getAllPowers();
        
        assertEquals(2, pows.size());
        assertTrue(pows.contains(pows1));
        assertTrue(pows.contains(pows2));
    }
    
    public void testUpdatePowers() {
        Powers pows1 = new Powers();
        pows1.setName("Test Name");
        pows1 = powsDao.addPowers(pows1);
        
        Powers fromDao = powsDao.getPowersById(pows1.getId());
        assertEquals(pows1, fromDao);
        
        pows1.setName("New Test Name");
        powsDao.updatePowers(pows1);
        
        assertNotEquals(pows1, fromDao);
        
        fromDao = powsDao.getPowersById(pows1.getId());
        
        assertEquals(pows1, fromDao);
    }

    @Test
    public void testDeletePowersById() {
        Powers pows1 = new Powers();
        pows1.setName("Test First");
        pows1 = powsDao.addPowers(pows1);
        
        
        Powers fromDao = powsDao.getPowersById(pows1.getId());
        assertEquals(pows1, fromDao);
        
        powsDao.deletePowersById(pows1.getId());
        
        fromDao = powsDao.getPowersById(pows1.getId());
        assertNull(fromDao);
    }
}
