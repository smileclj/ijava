package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wana on 2015/12/10.
 */
public class TestThread {
    private static final Logger logger = LoggerFactory.getLogger(TestThread.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(1000);

    private static String joinParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private String _getDynamicMessageBackGroundUrl(String uid, String sign, String uname, String thumbnail) {
        logger.info("参数uid:{}-sign:{}-uname:{}-thumbnail:{}", new Object[]{uid, sign, uname, thumbnail});
        sign = sign == null ? "" : sign;
        uname = uname == null ? "" : uname;
        thumbnail = thumbnail == null ? "" : thumbnail;
        String charName = "UTF-8";
        Map<String, String> params = new HashMap<String, String>();
        List<String> ps = new ArrayList<String>();
        ps.add(uid);
        ps.add(sign);
        ps.add(uname);
        ps.add(thumbnail);

        try {
            params.put("uid", URLEncoder.encode(uid, charName));
            params.put("sign", URLEncoder.encode(sign, charName));
            params.put("uname", URLEncoder.encode(uname, charName));
            params.put("thumbnail", URLEncoder.encode(thumbnail, charName));
            String picParamStr = joinParams(params);
            params.put("isnotify", "true");
            String paramStr = joinParams(params);
            logger.info("参数信息==={}", paramStr);

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("paramStr=={}", paramStr);
                }
            });
            logger.info("图文消息图片url==={}", "123" + "?" + picParamStr);
            return "234" + "?" + picParamStr;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
//        TestThread t = new TestThread();
//        for (int i = 0; i < 20; i++) {
//            t._getDynamicMessageBackGroundUrl(i+"","2","3","4");
//        }
        System.out.println((int)Math.round(2.4));
    }
}
