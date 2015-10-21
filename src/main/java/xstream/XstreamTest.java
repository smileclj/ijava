//package xstream;
//
//import com.thoughtworks.xstream.XStream;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wana on 2015/10/21.
// */
//public class XstreamTest {
//    public static void main(String[] args) {
//        XStream xStream = new XStream();
//        xStream.alias("Student", StudentXmlBean.class);
//        xStream.alias("person",Map);
//        StudentXmlBean student = new StudentXmlBean();
//        student.setId("1");
//        student.setAge(20);
//        List<Map<String,String>> params = new ArrayList<>();
//        Map<String,String> p1 = new HashMap<>();
//        p1.put("id","1");
//        p1.put("name", "小明");
//        params.add(p1);
//
//        Map<String,String> p2 = new HashMap<>();
//        p2.put("id","2");
//        p2.put("name", "小红");
//        params.add(p2);
//
//        student.setParams(params);
//        List<AddressXmlBean> addresses = new ArrayList<>();
//        AddressXmlBean a1 = new AddressXmlBean();
//        a1.setCode(1);
//        a1.setName("杭州");
//        a1.setRemark("备注");
//        addresses.add(a1);
//
//        AddressXmlBean a2 = new AddressXmlBean();
//        a1.setCode(2);
//        a1.setName("绍兴");
//        a1.setRemark("备注");
//        addresses.add(a2);
//        student.setAddresses(addresses);
//        System.out.println(xStream.toXML(student));
//    }
//}
