package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wana on 2015/12/28.
 */
public class RegexTest {
    public static boolean checkEmail(String email){
        //123abc@qq.com
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.(com)");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(RegexTest.checkEmail("123abc@qq.co"));
    }
}
