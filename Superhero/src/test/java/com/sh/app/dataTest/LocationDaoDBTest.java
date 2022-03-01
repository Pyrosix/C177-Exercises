package com.sh.app.dataTest;

import com.sh.app.data.OrganizationDao;
import com.sh.app.data.SightingDao;
import com.sh.app.data.LocationDao;
import com.sh.app.data.SupersDao;
import com.sh.app.models.Location;
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
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    public LocationDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Location> location = locationDao.getAllLocations();
        for (Location local : location) {
           locationDao.getLocationById(local.getId());
        }
        
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddandGetLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Descr");
        location.setAddress("Test Address");
        location.setCoordinates("TCords");
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }
         
    @Test
    public void testGetAllLocation() {
        Location location1 = new Location();
        location1.setName("Test Name");
        location1.setDescription("Test Descr");
        location1.setAddress("Test Address");
        location1.setCoordinates("TCords");
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("Test Name 2"); 
        location2.setDescription("Test Descr");
        location2.setAddress("Test Address 2");
        location2.setCoordinates("TCords2");
        location2 = locationDao.addLocation(location2);
        
        List<Location> location = locationDao.getAllLocations();
        
        assertEquals(2, location.size());
        assertTrue(location.contains(location1));
        assertTrue(location.contains(location2));
    }
    
    public void testUpdateLocation() {
        Location location1 = new Location();
        location1.setName("Test Name");
        location1.setDescription("Test Descr");
        location1.setAddress("Test Address");
        location1.setCoordinates("TCords");
        location1 = locationDao.addLocation(location1);
        
        Location fromDao = locationDao.getLocationById(location1.getId());
        assertEquals(location1, fromDao);
        
        location1.setName("New Test Name");
        locationDao.updateLocation(location1);
        
        assertNotEquals(location1, fromDao);
        
        fromDao = locationDao.getLocationById(location1.getId());
        
        assertEquals(location1, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        Location location1 = new Location();
        location1.setName("Test Name");
        location1.setDescription("Test Descr");
        location1.setAddress("Test Address");
        location1.setCoordinates("TCords");
        location1 = locationDao.addLocation(location1);
        
        
        Location fromDao = locationDao.getLocationById(location1.getId());
        assertEquals(location1, fromDao);
        
        locationDao.deleteLocationById(location1.getId());
        
        fromDao = locationDao.getLocationById(location1.getId());
        assertNull(fromDao);
    }
}
