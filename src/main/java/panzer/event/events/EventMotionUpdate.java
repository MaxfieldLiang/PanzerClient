package panzer.event.events;


import panzer.event.Event;

public class EventMotionUpdate extends Event<EventMotionUpdate> {
    private float yaw, pitch;

    public EventMotionUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;

    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
