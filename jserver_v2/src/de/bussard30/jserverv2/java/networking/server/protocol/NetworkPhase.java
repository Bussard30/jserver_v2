package networking.server.protocol;

public interface NetworkPhase {

    default String getName() {
        return this.getClass().getTypeName();
    }
}
