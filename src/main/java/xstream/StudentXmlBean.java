package xstream;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wana on 2015/10/21.
 */
public class StudentXmlBean implements Serializable{
    private static final long serialVersionUID = -4633637331787015086L;
    private String id;
    private int age;
    private List<AddressXmlBean> addresses;
    private List<Map<String,String>> params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Map<String, String>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }

    public List<AddressXmlBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressXmlBean> addresses) {
        this.addresses = addresses;
    }
}
