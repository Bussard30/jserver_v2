package networking.convertionhandlers;

import networking.types.ConvertionHandling;

import java.security.InvalidParameterException;

@ConvertionHandling(target = Integer.class)
public class IntegerConverter extends ConvertionHandler {

    @Override
    public String getString(Object o) throws InvalidParameterException {
        if (o instanceof Integer) {
            return Integer.toString((Integer) o);
        }
        throw new InvalidParameterException();
    }

    @Override
    public Object getObject(String s) throws InvalidParameterException {
        try {
            return Integer.parseInt(s);
        } catch (Throwable t) {
            throw new InvalidParameterException();
        }
    }

}
