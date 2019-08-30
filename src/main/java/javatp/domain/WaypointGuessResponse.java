package javatp.domain;
public class WaypointGuessResponse{
    boolean correct;
    Waypoint waypoint;
    public WaypointGuessResponse(boolean correct,Waypoint waypoint){
        this.correct = correct;
        this.waypoint = waypoint;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Waypoint getWaypoint() {
        return waypoint;
    }

    public void setWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }
}