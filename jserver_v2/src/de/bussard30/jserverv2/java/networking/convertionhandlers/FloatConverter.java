package networking.convertionhandlers;

import networking.types.ConvertionHandling;

import java.security.InvalidParameterException;

@ConvertionHandling(target = Float.class)
public class FloatConverter extends ConvertionHandler {

    @Override
    public String getString(Object o) throws InvalidParameterException {
        if (o instanceof Float) {
            return Float.toString((Float) o);
        }
        throw new InvalidParameterException();
    }

    @Override
    public Object getObject(String s) throws InvalidParameterException {
        try {
            return Float.parseFloat(s);
        } catch (Throwable t) {
            throw new InvalidParameterException();
        }
    }

}
