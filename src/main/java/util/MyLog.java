package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lao on 2015/9/25.
 */
public class MyLog {
//    private static final Logger logger = Logger.getLogger(Logger.class.getName());
private static final Logger logger = LoggerFactory.getLogger(MyLog.class);

    public static void main(String[] args) {
        logger.info("开启日志");
    }
}
