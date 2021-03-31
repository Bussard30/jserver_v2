package threading.types;

public enum ThreadProcessingSpeed {
    /**
     * FAST : does not create a new job to a executed on another thread.
     */
    FAST,

    /**
     * SLOW : creates a new job that eventually get executed(< 30 ms) on another thread
     */
    SLOW
}
