package threading.types;


import threading.exceptions.ThreadJobException;

public class ThreadedJobResult {
    private Object result;
    private volatile boolean hasFailed;

    public ThreadedJobResult(Object result) {
        this.result = result;
    }

    public ThreadedJobResult(Exception e, boolean hasFailed) {
        result = e;
        hasFailed = true;
    }

    public void throwException(Throwable t) {
        hasFailed = true;
        result = t;
    }

    /**
     * @return
     * @throws ThreadJobException
     */
    public Object getResult() throws ThreadJobException {
        if (hasFailed) {
            throw new ThreadJobException((Throwable) result);
        }
        return result;

    }

    /**
     * automatically calls finish after setting result
     *
     * @param o
     */
    public void setResult(Object o) {
        if (!hasFailed) {
            result = o;
        }
    }

    public boolean hasFailed() {
        return hasFailed;
    }
}
