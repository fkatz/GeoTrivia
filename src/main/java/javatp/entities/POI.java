package javatp.entities;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "Pois")
public class POI {
    private String name;
    private double lat;
    private double lng;
    public POI(){}
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "POI [lat=" + lat + ", lng=" + lng + ", name=" + name + "]";
    }

    public POI(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}