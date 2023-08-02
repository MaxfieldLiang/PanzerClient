package panzer.event.events;

import panzer.event.Event;
import net.minecraft.client.gui.DrawContext;

public class EventGui extends Event<EventGui> {
    private final DrawContext drawContext;

    public EventGui(DrawContext dc){
        drawContext = dc;
    }

    public DrawContext getDrawContext() {
        return drawContext;
    }
}
