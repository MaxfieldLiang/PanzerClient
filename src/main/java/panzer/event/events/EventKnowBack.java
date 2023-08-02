package panzer.event.events;

import panzer.event.Event;

public class EventKnowBack extends Event<EventKnowBack> {
    private double x, y, z;
    private final double defaultX;
    private final double defaultY;
    private final double defaultZ;
    public EventKnowBack(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        defaultX = x;
        defaultY = y;
        defaultZ = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getDefaultX() {
        return defaultX;
    }

    public double getDefaultY() {
        return defaultY;
    }

    public double getDefaultZ() {
        return defaultZ;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
