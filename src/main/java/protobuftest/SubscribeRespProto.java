package protobuftest;

import java.io.Serializable;

/**
 * @author 71972
 * @date 2018/10/6
 */
public class SubscribeRespProto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int subReqID;
    private int respCode;
    private String desc;

    public SubscribeRespProto() {
    }

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubsrcibeResp [subReqId =" + subReqID + ", respCode = " + respCode + ",desc=" + desc + "]";
    }
}
