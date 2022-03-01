package com.sh.app.data;

import com.sh.app.models.Powers;
import com.sh.app.models.Supers;
import java.util.List;

public interface PowersDao {
    Powers getPowersById(int id);
    List<Powers> getAllPowers();
    Powers addPowers(Powers pow);
    void updatePowers(Powers pow);
    void deletePowersById(int id);
    
    List<Powers> getPowersForSupers(Supers supers);
}
