import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by wana on 2015/10/17.
 */
public class Test_1 {

    @Test
    public void test1(){
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        System.out.println(StringUtils.join(list1, ","));

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        System.out.println(StringUtils.join(list2,","));
    }

    @Test
    public void fastjson(){
        Map<String,Object> params = new HashMap<>();
        params.put("date", new Date());
        System.out.println(JSON.toJSONString(params));
    }

    @Test
    public void testUri(){
        try {
            URI uri = new URI("file:/D:/uri.txt");
            File file = new File(uri);
            System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());
//            System.out.println(Test_1.class.getResource("test.txt").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
