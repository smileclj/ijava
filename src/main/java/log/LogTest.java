package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wana on 2015/12/24.
 */
public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {
        for(int i = 0;i<50;i++){
            logger.info("info");
        }

    }
}
