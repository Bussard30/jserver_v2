package de.bussard30.jserverv2.java.networking.server.protocol.session;

import java.util.HashMap;
import java.util.List;

public class SessionState {

    private Class<? extends Session> type;
    private HashMap<String, Object> values;

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