package networking.server.protocol;

public class ProtocolCondition {

    private final NetworkPhase target;
    private final ConditionWrapper[] conditions;

    public ProtocolCondition(NetworkPhase target, ConditionWrapper... conditions) {
        this.target = target;
        this.conditions = conditions;
    }

    public NetworkPhase getTargetPhase() {
        return target;
    }

    public ConditionWrapper[] getConditions() {
        return conditions;
    }
}
