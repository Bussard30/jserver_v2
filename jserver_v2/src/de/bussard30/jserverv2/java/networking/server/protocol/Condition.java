package networking.server.protocol;


import networking.types.Packet;

import java.util.HashMap;
import java.util.Map;

public class Condition {

    private final HashMap<Class<? extends Packet>, Boolean> packages;
    private final Class<? extends Packet>[] p;
    private final boolean and;
    private final PacketState packetState;
    private boolean conditionMet;

    /**
     * @param packetState 0 = received, 1 = successful
     * @param and         : if true Condition fulfilled when at least one of all package
     *                    types were received<br>
     *                    if not true Condition fulfilled when at least one package of
     *                    all package types were received
     * @param p
     */
    @SafeVarargs
    public Condition(PacketState packetState, boolean and, Class<? extends Packet>... p) {
        this.packetState = packetState;
        this.p = p;
        this.and = and;
        packages = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            packages.put(p[i], false);
        }
    }

    public Class<? extends Packet>[] getPackets() {
        return p;
    }

    /**
     * this method requires the packet to be one of the in the constructor
     * specified ones, this method doesnt check anything
     *
     * @param p
     * @return
     */
    public boolean check(Packet p, PacketState packetState) {
        if (packetState != this.packetState)
            return conditionMet;
        packages.put(p.getClass(), true);
        if (!and) {
            conditionMet = true;
            return true;
        }
        boolean b = false;
        boolean firstTime = true;
        for (Map.Entry<Class<? extends Packet>, Boolean> m : packages.entrySet()) {
            if (firstTime) {
                firstTime = false;
                if (and && m.getValue())
                    b = true;
                else
                    return false;
            } else {
                if (and && m.getValue())
                    b = true;
                else
                    return false;
            }
        }
        return (conditionMet = b);
    }

    public boolean isConditionMet() {
        return conditionMet;
    }

    public PacketState getPacketState() {
        return packetState;
    }

}
