package de.bussard30.jserverv2.networking.server;

/**
 * Rework required.
 *
 * @author Jonas
 */
@Deprecated
public class ActiveTime {
    private final AdvancedArray<Long> markTimes;
    private final AdvancedArray<Boolean> values;
    private final long creationTime;

    private int index = 0;

    public ActiveTime() {
        markTimes = new AdvancedArray<>(Long.class, 100);
        values = new AdvancedArray<>(Boolean.class, 100);
        creationTime = System.currentTimeMillis();
    }

    public void setMark(boolean b) {
        markTimes.put(System.currentTimeMillis(), index);
        values.put(b, index);
        index++;
    }

    public void setMark(long time, boolean b) {
        markTimes.put(time, index);
        values.put(b, index);
        index++;
    }

    public void update(long threshold) {
        while ((System.currentTimeMillis() - markTimes.get(0)) >= threshold) {
            markTimes.removeFirst();
            values.removeFirst();
        }
    }

    public long getActiveTime(long threshold) {
        long l = 0;
        l += markTimes.get(0) - threshold;
        for (int i = 1; i < markTimes.getLength(); i++) {
            if (!values.get(i))
                l += markTimes.get(i) - markTimes.get(i - 1);
        }
        return l;
    }

    public long getAliveTime() {
        return System.currentTimeMillis() - creationTime;
    }
}
