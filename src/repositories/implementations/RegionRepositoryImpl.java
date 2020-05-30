package repositories.implementations;

import models.Region;
import repositories.interfaces.IRegionRepo;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RegionRepositoryImpl implements IRegionRepo {

    Path filePath;

    public RegionRepositoryImpl(Path filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public Region getById(long id) {
        Region region=getAll().stream().filter(r->r.getId()==id).findFirst().orElse(null);
        return region;
    }

    @Override
    public List<Region> getAll() {
        List<String>regions=null;
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        try (ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(filePath.toString()))){
            regions= (ArrayList<String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toRegionList(regions);
    }

    @Override
    public Region save(Region region) {
        ArrayList<String>regions= null;
        if (!isFileEmpty()) {
            regions = (ArrayList<String>) getAll().stream().map(Region::toString).collect(Collectors.toList());
            regions.add(region.toString()+"\n");
            cleanFile();
        }else {
            regions=new ArrayList<>();
            regions.add(region.toString()+"\n");
        }
        try (ObjectOutputStream outputStream=new ObjectOutputStream(
                new FileOutputStream(filePath.toString()))){
            cleanFile();
            outputStream.writeObject(regions);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return region;
    }

    @Override
    public Region update(Region region)  {
        List<Region>regions=getAll();
        Region regionToUpdate = regions.stream().filter(r -> r.getId() == region.getId()).findFirst().orElse(null);
        regionToUpdate.setName(region.getName());

        cleanFile();
        ArrayList<String>regionsStr= (ArrayList<String>) regions.stream().map(Region::toString).collect(Collectors.toList());
        
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            outputStream.writeObject(regionsStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    @Override
    public boolean deleteById(long id) {
        List<Region>regions=getAll();
        Region regionToRemove = regions.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        regions.remove(regionToRemove);
        cleanFile();
        ArrayList<String>regionsStr= (ArrayList<String>) regions.stream().map(Region::toString).collect(Collectors.toList());
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            outputStream.writeObject(regionsStr);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return false;
    }

    @Override
    public Region getByName(String name) {
        Region region=getAll().stream().filter(r->r.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return region;
    }

    @Override
    public long getLastId() {
        List<Region>regions=getAll();
        long lastId=0;
        if (regions.isEmpty()){
            return lastId;
        }
        lastId=regions.stream().max(Comparator.comparing(Region::getId)).get().getId();
        return lastId;
    }

    private void cleanFile() {
        try {
            new FileOutputStream(filePath.toString()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isFileEmpty() {
        File file=filePath.toFile();
        return file.length()<10;
    }

    private List<Region> toRegionList(List<String> regions) {
        return regions.stream().map(r->toRegion(r)).collect(Collectors.toList());
    }

    private Region toRegion(String region){
        String[]regionData=region.split(",");
        System.out.println();
        String[]idStr=regionData[0].split("=");
        long id=Long.parseLong(idStr[1]);
        return new Region(id,Region.nameToValue(regionData[1]));
    }
}
