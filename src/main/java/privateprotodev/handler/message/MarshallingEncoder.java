package privateprotodev.handler.message;

import javax.xml.bind.Marshaller;

import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
import com.sun.xml.internal.ws.util.Pool;

/**
 *
 * @author 71972
 * @date 2018/10/8
 * netty消息编码工具类
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    public MarshallingEncoder(){
    }
}
