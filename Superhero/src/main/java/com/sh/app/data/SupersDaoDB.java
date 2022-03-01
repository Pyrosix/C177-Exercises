
package com.sh.app.data;

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
public class SupersDaoDB implements SupersDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Supers getSuperById(int id) {
        try {
            final String GET_SUPER_BY_ID = "SELECT * FROM super WHERE id = ?";
            return jdbc.queryForObject(GET_SUPER_BY_ID, new SupersMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Supers> getAllSupers() {
        final String GET_ALL_SUPERS = "SELECT * FROM super";
        return jdbc.query(GET_ALL_SUPERS, new SupersMapper());
    }

    @Override
    @Transactional
    public Supers addSuper(Supers supers) {
        final String INSERT_SUPERS = "INSERT INTO super(Super_name, Classification, Description)" + "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERS, supers.getName(), supers.getClassification(), supers.getDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        supers.setId(newId);
        return supers;
    }

    @Override
    public void updateSupers(Supers supers) {
        final String UPDATE_SUPERS = "UPDATE super SET Super_name = ?, Classification = ?, Description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERS, supers.getName(), supers.getClassification(), supers.getDescription(), supers.getId());
    }

    @Override
    public void deleteSupersById(int id) {
        final String DELETE_SUPERS = "DELETE FROM super WHERE id = ?";
        jdbc.update(DELETE_SUPERS, id);
    }

    public static final class SupersMapper implements RowMapper<Supers> {

        @Override
        public Supers mapRow(ResultSet rs, int rowNum) throws SQLException {
            Supers supers = new Supers();
            supers.setId(rs.getInt("id"));
            supers.setName(rs.getString("Super_name"));
            supers.setClassification(rs.getString("Classification"));
            supers.setDescription(rs.getString("Description"));
            
            return supers;
        }
    }
}
