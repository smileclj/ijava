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

    private List<Object> division(List<String> lists,int num,int limit){
        List<Object> os = new ArrayList<>();
        for(int i=0;i<num;i++){
            List<String> subList = null;
            try{
                subList = lists.subList(num*i, limit-1);
            }catch(Exception e){
                subList = lists.subList(num*i, lists.size()-num*i-1);
            }
            os.add(subList);
        }
        return os;
    }

    @Test
    public void t(){
//        System.out.println((int)Math.ceil((double)5/2));
        List<String> lists = new ArrayList<>(450);
        System.out.println(lists.subList(400,599));
    }

    @Test
    public void tt(){
        List<String> lists = new ArrayList<>();
        for(int i = 0;i<450;i++){
            lists.add(""+i);
        }
        List<Object> result = division(lists,3,200);
        List<Object> os = new ArrayList<>();
        int num = 3;
        int limit = 200;  //tasknum
        for(int i=0;i<num;i++){
            List<String> subList = null;
            try{
                subList = lists.subList(limit*i, limit*i+limit);
            }catch(Exception e){
                subList = lists.subList(limit*i, lists.size());
            }
            os.add(subList);
        }
        System.out.println(os);
    }


}
