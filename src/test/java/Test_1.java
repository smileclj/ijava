import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
