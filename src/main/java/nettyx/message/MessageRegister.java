/*
 * @(#)MessageRegister.java    Created on 2013年9月29日
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package nettyx.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class MessageRegister {

    private static final Logger logger = LoggerFactory.getLogger(MessageRegister.class);

    private static final String MESSAGE_PACKAGE = "nettyx.message";

    private MessageRegister() {
    }

    public static void init() throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = CommandConstants.class.getFields();
        for (Field f : fields) {
            String name = f.getName();
            String command = f.get(null).toString();
            String clazzName = standardClassName(name);
//            try {
//                Class<? extends AbstractMessage> messageClazz = (Class<? extends AbstractMessage>) Class
//                        .forName(clazzName);
//                AbstractMessage.registerMessageClass(Integer.valueOf(command), messageClazz);
//            }
//            catch (ClassNotFoundException e) {
//                throw new RuntimeException("注册消息" + clazzName
//                        + "失败,请检查CommadConstants中的命令与对应消息类名的拼写是否正确，或者添加未实现的消息,如果以上都正确请检查你的消息类的包名是否为：" + MESSAGE_PACKAGE);
//            }
        }
    }

    private static String standardClassName(String filedName) {
        logger.info(filedName);
        String[] str = filedName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s.charAt(0));
            sb.append(s.substring(1, s.length()).toLowerCase());
        }
        sb.append("Message");
        sb.insert(0, MESSAGE_PACKAGE + ".");
        return sb.toString();
    }

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        MessageRegister.init();
    }
}
