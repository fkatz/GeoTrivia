package javatp.entities;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.PrecisionModel;
@Entity
@Table(name = "Pois")
public class POI {
    private String name;
    @Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
    @JsonIgnore
    private Point location;
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }
    public Double getLng(){
        return location.getX();
    }
    public Double getLat(){
        return location.getY();
    }
    public void setId(Long id) {
        this.id = id;
    }
    public POI(@JsonProperty("name") String name, 
            @JsonProperty("lat") double lat, 
            @JsonProperty("lng") double lng) {
                GeometryFactory gf = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING),4326);
    this.location = gf.createPoint(new Coordinate(lng,lat));
    this.name = name;
}
}