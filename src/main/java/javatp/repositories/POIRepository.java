package javatp.repositories;

import java.util.Collection;
import java.util.HashMap;

import javatp.entities.POI;

public class POIRepository {
    private static POIRepository instance;
    private HashMap<String, POI> pois = new HashMap<String, POI>();
    
    private POIRepository() {
        this.pois.put("Monumento a la bandera", new POI("Monumento a la bandera",-32.9477132,-60.6304658));
    }

    public static POIRepository get() {
        if (instance == null) {
            instance = new POIRepository();
        }
        return instance;
    }
    public POI getOne(String name) {
        return pois.get(name);
    }
    public Collection<POI> getAll(){
        return pois.values();
    }
    public void save(POI poi) throws Exception{
        if(poi == null || poi.getName() == null) throw new Exception("Object can't be null");
        if(pois.containsKey(poi.getName())) throw new Exception("Object already defined");
        pois.put(poi.getName(),poi);
    }
}