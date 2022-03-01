package com.sh.app.data;

import com.sh.app.data.SupersDaoDB.SupersMapper;
import com.sh.app.models.Organization;
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
public class OrganizationDaoDB implements OrganizationDao{
    
    @Autowired
    JdbcTemplate jdbc;
    
    private List<Supers> getSupersForOrganization(int id) {
        final String SELECT_SUPERS_FOR_ORGANIZATION = "SELECT s.* FROM super s JOIN super_organization so ON so.Superid = s.id WHERE so.Superid = ?";
        return jdbc.query(SELECT_SUPERS_FOR_ORGANIZATION, new SupersMapper(), id);
    }

     @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORG_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization org =  jdbc.queryForObject(GET_ORG_BY_ID, new OrganizationMapper(), id);
            org.setSupers(getSupersForOrganization(id));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATION = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(GET_ALL_ORGANIZATION, new OrganizationMapper());
        
        for(Organization org : orgs) {
            org.setSupers(getSupersForOrganization(org.getId()));
        }
        return orgs;
    }
    
    private void insertOrganizationSupers(Organization org) {
        final String INSERT_SUPERS_ORGANIZATION = "INSERT INTO " + "super_organization(Orgid, Superid) VALUES (?,?)";
        
        for(Supers supers : org.getSupers()) {
            jdbc.update(INSERT_SUPERS_ORGANIZATION, org.getId(), supers.getId());
        }
    }
    
    @Override
    public List<Organization> getOrganizationForSupers(Supers supers) {
        final String SELECT_ORGANIZATION_FOR_SUPERS = "SELECT o.* FROM organization o JOIN super_organization so ON so.Orgid = o.id WHERE so.Superid = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGANIZATION_FOR_SUPERS, new OrganizationMapper(), supers.getId());
        
        for(Organization org : orgs) {
            org.setSupers(getSupersForOrganization(org.getId()));
        }
        return orgs;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(Organization_name, Description, Address_or_Contact)" + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORGANIZATION, org.getName(), org.getDescription(), org.getAddress());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertOrganizationSupers(org);
        return org;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization org) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET Organization_name = ?, Description = ?, Address_or_Contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION, org.getName(), org.getDescription(), org.getAddress(), org.getId());
        
        final String DELETE_ORGANIZATION_SUPERS = "DELETE FROM super_organization WHERE Orgid = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERS, org.getId());
        insertOrganizationSupers(org);
    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("Organization_name"));
            org.setDescription(rs.getString("Description"));
            org.setAddress(rs.getString("Address_or_Contact"));
            
            return org;
        }
        
    }
    
}
