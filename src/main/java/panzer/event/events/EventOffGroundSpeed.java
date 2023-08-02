package panzer.event.events;

import panzer.event.Event;

public class EventOffGroundSpeed extends Event<EventOffGroundSpeed> {
    public float speed;

    public EventOffGroundSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
