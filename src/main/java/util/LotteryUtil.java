package util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 具体抽奖可以根据业务对此进行封装
 *
 * create by chenlijiang
 */
public class LotteryUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Random random = new Random();
    private List<Award> awards;
    private int[] limits;

    public LotteryUtil(List<Award> awards) {
        this.awards = awards;
        initLimit();
    }

    private void initLimit() {
        limits = new int[awards.size()];
        for (int i = 0; i < awards.size(); i++) {
            if (i == 0) {
                limits[i] = awards.get(i).getRatio();
            } else {
                limits[i] = limits[i - 1] + awards.get(i).getRatio();
            }
        }
        logger.info("limits===" + Arrays.toString(limits));
    }

    public List<Award> getAwards() {
        return awards;
    }

    public Award random() {
        if (limits.length == 0) {
            logger.warn("没有可抽取的奖品");
            return null;
        }
        int r = random.nextInt(limits[limits.length - 1]) + 1;
        logger.info("随机数===" + r);

        for (int i = 0; i < limits.length; i++) {
            if (i == 0) {
                if (r > 0 && r <= limits[0]) {
                    return awards.get(0);
                }
            } else {
                if (r > limits[i - 1] && r <= limits[i]) {
                    return awards.get(i);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<Award> awards = new ArrayList<Award>();
        awards.add(new Award<String>("1", 70));
        awards.add(new Award<String>("2", 15));
        awards.add(new Award<String>("3", 10));
        awards.add(new Award<String>("4", 5));

        LotteryUtil lotteryUtil = new LotteryUtil(awards);
        for (int i = 0; i < 100; i++) {
            System.out.println(JSON.toJSONString(lotteryUtil.random()));
        }
    }
}
