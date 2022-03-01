
package com.sh.app.data;

import com.sh.app.models.Organization;
import com.sh.app.models.Supers;
import java.util.List;

public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganizationById(int id);
    
    List<Organization> getOrganizationForSupers(Supers supers);
}
