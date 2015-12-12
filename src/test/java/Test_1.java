import com.alibaba.fastjson.JSON;
import entity.JsonEntity;
import entity.MessageEntity;
import entity.Student;
import entity.Student2;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.junit.Test;
import util.DateUtils;
import util.ReflectionUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wana on 2015/10/17.
 */
public class Test_1 {

    @Test
    public void test1() {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        System.out.println(StringUtils.join(list1, ","));

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        System.out.println(StringUtils.join(list2, ","));
    }

    @Test
    public void fastjson() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", new Date());
        System.out.println(JSON.toJSONString(params));
    }

    @Test
    public void testUri() {
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

    private List<Object> division(List<String> lists, int num, int limit) {
        List<Object> os = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            List<String> subList = null;
            try {
                subList = lists.subList(num * i, limit - 1);
            } catch (Exception e) {
                subList = lists.subList(num * i, lists.size() - num * i - 1);
            }
            os.add(subList);
        }
        return os;
    }

    @Test
    public void t() {
//        System.out.println((int)Math.ceil((double)5/2));
        List<String> lists = new ArrayList<>(450);
        System.out.println(lists.subList(400, 599));
    }

    @Test
    public void tt() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 450; i++) {
            lists.add("" + i);
        }
        List<Object> result = division(lists, 3, 200);
        List<Object> os = new ArrayList<>();
        int num = 3;
        int limit = 200;  //tasknum
        for (int i = 0; i < num; i++) {
            List<String> subList = null;
            try {
                subList = lists.subList(limit * i, limit * i + limit);
            } catch (Exception e) {
                subList = lists.subList(limit * i, lists.size());
            }
            os.add(subList);
        }
        System.out.println(os);
    }

    @Test
    public void ttt() {
        System.out.println((int) (Math.random() * 100000));
    }

    @Test
    public void tttt() {
//        System.out.println(Double.toString(Math.random()));
//        System.out.println((Double.toString(Math.random())).substring(2, (2 + 6)));
        System.out.println("oyv8Qt1x84Ta_NAWhKMl8nUnF9Z0".length());
        System.out.println("oyv8Qt1X8_JiMcI1d5rS8C3zPGHo".length());

    }

    @Test
    public void now() {
        System.out.println(new Date().getTime());
//
//        System.out.println(UUID.randomUUID().toString());
//
//        System.out.println(UUID.randomUUID().toString().length());
//
//        System.out.println(false & true);

//        System.out.println(new Random().nextInt(-1));
        System.out.println("".contains("a"));

    }

    @Test
    public void testJson() {
        JsonEntity entity = new JsonEntity();
        entity.setName("小明");
        entity.setAge(20);
        entity.setCreateTime(new Date());
        entity.setFlag(true);
        System.out.println(JSON.toJSON(entity));
    }

    @Test
    public void testReflect() {
        Student test = new Student();
        test.setName("aaa");

        Student2 test2 = new Student2();
        try {
            ReflectionUtils.copyPorperties(test2, test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(test2));
    }

    @Test
    public void jsonTest() {
        MessageEntity message = new MessageEntity();
        message.setContent("\\\\\"'\\\"\\'");
        System.out.println(message.getContent());
        System.out.println(JSON.toJSONString(message));
    }

    @Test
    public void testDate() {
//        long startTime = DateUtils.string2Date("2015-12-11 08:00:00","yyyy-MM-dd HH:mm:ss").getTime();
//        long endTime = new Date().getTime();
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(endTime - startTime);
//        StringBuilder sb = new StringBuilder();
//        sb.append("总耗时：").append(c.get(Calendar.HOUR) + "时").append(c.get(Calendar.MINUTE) + "分").append(c.get(Calendar.SECOND) + "秒");
//        System.out.println(sb.toString());

        DateTime begin = new DateTime("2015-12-11 08:00:00");
        DateTime end = DateTime.now();

        //计算区间毫秒数
        Duration d = new Duration(begin, end);
        long time = d.getMillis();

        //计算区间天数
        Period p = new Period(begin, end, PeriodType.millis());

        System.out.println(p.getDays());


    }
}
