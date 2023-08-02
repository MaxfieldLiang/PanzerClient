package panzer.event.events;

import net.minecraft.client.util.math.MatrixStack;
import panzer.event.Event;

public class EventRender extends Event<EventRender> {
    private float partialTicks;
    private MatrixStack matrixStack;

    public EventRender(float partialTicks, MatrixStack matrixStack) {
        this.partialTicks = partialTicks;
        this.matrixStack = matrixStack;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }
}
