package panzer.utils;

public class TimeUtil {
    private long lastMS = getCurrentMS();

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReach(long milliseconds, boolean autoReset) {
        if (getCurrentMS() - lastMS >= milliseconds) {
            if (autoReset) {
                reset();
            }
            return true;
        }
        return false;
    }

    public void reset() {
        lastMS = getCurrentMS();
    }
}
