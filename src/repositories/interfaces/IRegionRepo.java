package repositories.interfaces;

import models.Region;

import java.util.List;

public interface IRegionRepo {
    Region getById(long id);
    List<Region>getAll();
    Region save(Region region);
    Region update(Region region);
    boolean deleteById(long id);
    Region getByName(String name);
    long getLastId();
}
