package main.java.accountFile.repository.ioImpl;

import main.java.accountFile.model.Region;
import main.java.accountFile.repository.interfaces.GenericRepository;
import main.java.accountFile.utils.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RegionRepositoryImpl implements GenericRepository<Region,Long> {

    static final File regionFile=new File("resources/regions.txt");

    
    @Override
    public Region getById(Long id) {
        Region region=getAll().stream().filter(r->r.getId()==id).findFirst().orElse(null);
        return region;
    }

    @Override
    public List<Region> getAll() {
        List<String>regions=new ArrayList<>();
        if (isFileEmpty()){
            return new ArrayList<>();
        }
        try {
            String text= FileManager.read(regionFile);
            String[] regionArr = text.split("REGION_DATA");
            for (int i = 1; i < regionArr.length; i++) {
               regions.add(regionArr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toRegionList(regions);
    }

    @Override
    public Region add(Region region) {
        List<String>regions= null;
        if (!isFileEmpty()) {
            regions = (ArrayList<String>) getAll().stream().map(r->toString(r)).collect(Collectors.toList());
            regions.add(toString(region));
            deleteAll();
        }else {
            regions=new ArrayList<>();
            regions.add(toString(region));
        }
        try{
            FileManager.write(regions,regionFile);
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
        deleteAll();
        ArrayList<String>regionsStr= (ArrayList<String>) regions.stream().map(r->toString(r)).collect(Collectors.toList());
        
        try{
            FileManager.write(regionsStr,regionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Region>regions=getAll();
        Region regionToRemove = regions.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        regions.remove(regionToRemove);
        deleteAll();
        ArrayList<String>regionsStr= (ArrayList<String>) regions.stream().map(r->toString(r)).collect(Collectors.toList());
        try{
            FileManager.write(regionsStr,regionFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return false;
    }
    
    public Region getByName(String name) {
        Region region=getAll().stream().filter(r->r.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return region;
    }

    
    public long getLastId() {
        List<Region>regions=getAll();
        long lastId=0;
        if (regions.isEmpty()){
            return lastId;
        }
        lastId=regions.stream().max(Comparator.comparing(Region::getId)).get().getId();
        return lastId;
    }

    @Override
    public boolean deleteAll() {
        try {
            new FileOutputStream(regionFile).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    private boolean isFileEmpty() {
        return regionFile.length()<10;
    }

    private List<Region> toRegionList(List<String> regions) {
        return regions.stream().map(r->toRegion(r)).collect(Collectors.toList());
    }

    private Region toRegion(String region){
        String[]regionData=region.split(",");
        return new Region(idToValue(regionData[0]),nameToValue(regionData[1]));
    }

    private String toString(Region region) {
        return "REGION_DATA{ " +
                "id= " + region.getId() +
                ", name=" + region.getName() + "}\n";
    }
    private  long idToValue(String str){
        String[]arr=str.split("=");
        return Long.parseLong(arr[1].trim());
    }
    private String nameToValue(String str){
        String[]arr=str.split("=");
        String val=arr[1];
        return val.substring(0,val.length()-1);
    }
}
