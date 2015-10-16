package util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lao on 2015/9/25.
 */
public class Joda {
    private static final Logger logger = LoggerFactory.getLogger(Joda.class);

    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        //format
        logger.info(dateTime.toString());
        logger.info(dateTime.toString("yyyy-MM-dd HH:mm:ss"));

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime dateTime2 = DateTime.parse("2015-9-25 11:00",formatter);
        logger.info("增加2分钟："+dateTime2.plusMinutes(2).toString("yyyy-MM-dd HH:mm"));
    }
}
