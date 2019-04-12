package logEvent;

import java.net.InetSocketAddress;

/**
 * Created by 71972 on 2018/9/29.
 *
 * @author 71972
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    public LogEvent(String logFile, String msg) {
        this(null, -1, logFile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logFile, String msg) {
        this.source = source;
        this.received = received;
        this.logFile = logFile;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }

    public String getLogFile() {
        return logFile;
    }

    public InetSocketAddress getSource() {
        return source;
    }
}
