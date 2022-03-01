package com.sh.app.data;

import com.sh.app.models.Supers;
import java.util.List;

public interface SupersDao {
    Supers getSuperById(int id);
    List<Supers> getAllSupers();
    Supers addSuper(Supers supers);
    void updateSupers(Supers supers);
    void deleteSupersById(int id);
    
    
}
