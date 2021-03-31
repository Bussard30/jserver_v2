package de.bussard30.jserverv2.networking.server.protocol.session;

import java.util.HashMap;

public class SessionState {

    private final Class<? extends Session> type;
    private final HashMap<String, Object> values;

    public SessionState(Session session, HashMap<String, Object> hashMap)
    {
        type = session.getClass();
        values = hashMap;
    }

    public Class<? extends Session> getType()
    {
        return type;
    }

    public HashMap<String, Object> getValues()
    {
        return values;
    }

}