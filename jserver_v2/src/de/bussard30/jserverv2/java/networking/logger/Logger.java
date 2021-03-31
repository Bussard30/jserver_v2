package de.bussard30.jserverv2.java.networking.logger;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 * @author Unknown
 */
public class Logger {
    private static final SimpleDateFormat sdf;
    public static final Vector<LogWrapper> queue = new Vector<>();

    public static final String info = "INFO";
    public static final String warning = "WARNING";
    public static final String error = "ERROR";
    public static final String fatal = "FATAL";

    public static LogWrapper fetchMessage() {
        synchronized (queue) {
            return queue.remove(0);
        }
    }

    static {
        sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    }

    public static void info(@NotNull Object caster, @NotNull String message) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), info, caster));
        }
    }

    public static void info(@NotNull Object caster, @NotNull String message, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), info, ip, caster));
        }
    }

    public static void warning(@NotNull Object caster, @NotNull String message) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), warning, caster));
        }
    }

    public static void warning(@NotNull Object caster, @NotNull String message, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), warning, ip, caster));
        }
    }

    public static void warning(@NotNull Object caster, @NotNull Throwable t) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), warning, caster));
        }
    }

    public static void warning(@NotNull Object caster, @NotNull Throwable t, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), warning, ip, caster));
        }
    }

    public static void warning(@NotNull Object caster, @NotNull String message, @NotNull Throwable t) {
        synchronized (queue) {
            queue.add(new LogWrapper(message + "\n" + getMessage(t), getTime(), warning, caster));
        }
    }

    public static void error(@NotNull Object caster, @NotNull String message) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), error, caster));
        }
    }

    public static void error(@NotNull Object caster, @NotNull String message, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), error, ip, caster));
        }
    }

    public static void error(@NotNull Object caster, @NotNull Throwable t) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), error, caster));
        }
    }

    public static void error(@NotNull Object caster, @NotNull Throwable t, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), error, ip, caster));
        }
    }

    public static void error(@NotNull Object caster, @NotNull String message, Throwable t) {
        synchronized (queue) {
            queue.add(new LogWrapper(message + "\n" + getMessage(t), getTime(), error, caster));
        }
    }

    public static void fatal(@NotNull Object caster, @NotNull String message) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), fatal, caster));
        }
    }

    public static void fatal(@NotNull Object caster, @NotNull String message, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(message, getTime(), fatal, ip, caster));
        }
        // TODO
    }

    public static void fatal(@NotNull Object caster, @NotNull Throwable t) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), fatal, caster));
        }
    }

    public static void fatal(@NotNull Object caster, @NotNull Throwable t, String ip) {
        synchronized (queue) {
            queue.add(new LogWrapper(getMessage(t), getTime(), fatal, ip, caster));
        }
    }

    /**
     * If time is null, this method sets the time.<br>
     * </br>
     * Sets type to info.
     *
     * @param lw
     */
    public static void info(@NotNull LogWrapper lw) {
        if (lw.getTime() == null) {
            lw.setTime(getTime());
        }
        lw.setType(info);
        synchronized (queue) {
            queue.add(lw);
        }
    }

    private static String getTime() {
        return sdf.format(Calendar.getInstance().getTime());
    }

    private static String getMessage(@NotNull Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}
