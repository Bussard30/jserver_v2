package networking.convertionhandlers;

import networking.types.ConvertionHandling;

import java.security.InvalidParameterException;

@ConvertionHandling(target = Double.class)
public class DoubleConverter extends ConvertionHandler {

    @Override
    public String getString(Object o) throws InvalidParameterException {
        if (o instanceof Double) {
            return Double.toString((Double) o);
        }
        throw new InvalidParameterException();
    }

    @Override
    public Object getObject(String s) throws InvalidParameterException {
        try {
            return Double.parseDouble(s);
        } catch (Throwable t) {
            throw new InvalidParameterException();
        }
    }

}
