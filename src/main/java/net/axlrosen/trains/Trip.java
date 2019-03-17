package net.axlrosen.trains;

// Represents one trip along a path.
public class Trip {
    public String path;
    public int distance;

    public Trip(String path, int distance) {
        this.path = path;
        this.distance = distance;
    }

    public String getLastStation() {
        return path.substring(path.length() - 1);
    }

    public Trip extend(String newStation, int trackDistance) {
        return new Trip(this.path + newStation, this.distance + trackDistance);
    }

    public String toString() {
        return "[" + path + ", " + distance + "]";
    }
}
