package xstream;

import java.io.Serializable;

/**
 * Created by wana on 2015/10/21.
 */
public class AddressXmlBean implements Serializable{
    private static final long serialVersionUID = -1027672771458410444L;

    private int code;
    private String name;
    private String remark;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
