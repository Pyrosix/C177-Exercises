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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
    public void testGetAllOrganization() {
        Organization org1 = new Organization();
        org1.setName("Test Name");
        org1.setDescription("Test Descr");
        org1.setAddress("Test Address");
        org1 = orgDao.addOrganization(org1);
        
        Organization org2 = new Organization();
        org2.setName("Test Name 2");
        org2.setDescription("Test Descr 2");
        org2 = orgDao.addOrganization(org2);
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org1));
        assertTrue(orgs.contains(org2));
    }
    
    public void testUpdateOrganization() {
        
        Organization org1 = new Organization();
        org1.setName("Test Name");
        org1.setDescription("Test Descr");
        org1.setAddress("Test Address");
        org1 = orgDao.addOrganization(org1);
        
        Organization fromDao = orgDao.getOrganizationById(org1.getId());
        assertEquals(org1, fromDao);
        
        org1.setName("New Test Name");
        orgDao.updateOrganization(org1);
        
        assertNotEquals(org1, fromDao);
        
        fromDao = orgDao.getOrganizationById(org1.getId());
        
        assertEquals(org1, fromDao);
    }

    @Test
    public void testDeleteOrganizationById() {
        
        Organization org1 = new Organization();
        org1.setName("Test Name");
        org1.setDescription("Test Descr");
        org1.setAddress("Test Address");
        org1 = orgDao.addOrganization(org1);
        
        
        Organization fromDao = orgDao.getOrganizationById(org1.getId());
        assertEquals(org1, fromDao);
        
        orgDao.deleteOrganizationById(org1.getId());
        
        fromDao = orgDao.getOrganizationById(org1.getId());
        assertNull(fromDao);
    }
}
