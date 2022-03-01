package com.sh.app.data;

import com.sh.app.data.SupersDaoDB.SupersMapper;
import com.sh.app.models.Powers;
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
public class PowersDaoDB implements PowersDao {

    @Autowired
    JdbcTemplate jdbc;
    
    
    private List<Supers> getSupersForPowers(int id) {
        final String SELECT_SUPERS_FOR_POWERS = "SELECT s.* FROM super s JOIN superpower sp ON sp.Superid = s.id WHERE sp.Superid = ?";
        return jdbc.query(SELECT_SUPERS_FOR_POWERS, new SupersMapper(), id);
    }

    @Override
    public Powers getPowersById(int id) {
        try {
            final String GET_POWERS_BY_ID = "SELECT * FROM power WHERE id = ?";
            Powers pow =  jdbc.queryForObject(GET_POWERS_BY_ID, new PowersMapper(), id);
            pow.setSupers(getSupersForPowers(id));
            return pow;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Powers> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM power";
        List<Powers> pows = jdbc.query(GET_ALL_POWERS, new PowersMapper());
        
        for(Powers pow : pows) {
            pow.setSupers(getSupersForPowers(pow.getId()));
        }
        return pows;
    }
    
    private void insertPowersSupers(Powers pow) {
        final String INSERT_SUPERS_POWERS = "INSERT INTO " + "superpower(Powerid, Superid) VALUES (?,?)";
        
        for(Supers supers : pow.getSupers()) {
            jdbc.update(INSERT_SUPERS_POWERS, pow.getId(), supers.getId());
        }
    }
    
    @Override
    public List<Powers> getPowersForSupers(Supers supers) {
        final String SELECT_POWERS_FOR_SUPERS = "SELECT p.* FROM power p JOIN superpower sp ON sp.Powerid = p.id WHERE sp.Superid = ?";
        List<Powers> pows = jdbc.query(SELECT_POWERS_FOR_SUPERS, new PowersMapper(), supers.getId());
        
        for(Powers pow : pows) {
            pow.setSupers(getSupersForPowers(pow.getId()));
        }
        return pows;
    }

    @Override
    @Transactional
    public Powers addPowers(Powers pow) {
        final String INSERT_POWERS = "INSERT INTO power(Power_name) VALUES(?)";
        jdbc.update(INSERT_POWERS, pow.getName());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        pow.setId(newId);
        insertPowersSupers(pow);
        return pow;
    }

    @Override
    @Transactional
    public void updatePowers(Powers pow) {
        final String UPDATE_POWERS = "UPDATE power SET Power_name = ? WHERE id = ?";
        jdbc.update(UPDATE_POWERS, pow.getName(), pow.getId());
        
        final String DELETE_POWERS_SUPERS = "DELETE FROM superpower WHERE Powerid = ?";
        jdbc.update(DELETE_POWERS_SUPERS, pow.getId());
        insertPowersSupers(pow);
    }

    @Override
    public void deletePowersById(int id) {
        final String DELETE_POWERS = "DELETE FROM power WHERE id = ?";
        jdbc.update(DELETE_POWERS, id);
    }
    
    public static final class PowersMapper implements RowMapper<Powers> {

        @Override
        public Powers mapRow(ResultSet rs, int rowNum) throws SQLException {
            Powers pow = new Powers();
            pow.setId(rs.getInt("id"));
            pow.setName(rs.getString("Power_name"));
            return pow;
        }
    }
}