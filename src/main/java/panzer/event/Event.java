package panzer.event;

public class Event<T> {
    private boolean cancel;
    private State state;


    public boolean isPre() {
        return this.state == State.PRE;
    }

    public boolean isPost() {
        return this.state == State.POST;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        PRE, POST;
    }
}
