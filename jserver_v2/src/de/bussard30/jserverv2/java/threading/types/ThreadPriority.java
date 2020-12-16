package threading.types;

public enum ThreadPriority {
    LOW(0), NORMAL(1), HIGH(2);

    private final int priority;

    ThreadPriority(int i) {
        this.priority = i;
    }

    public int getPriority() {
        return priority;
    }
}
