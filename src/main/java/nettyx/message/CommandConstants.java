/*
 * @(#)CommandConstants.java    Created on 2013年9月29日
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package nettyx.message;

/**
 * 所有消息指令编码
 */
public class CommandConstants {
    /**
     * 命令添加规则：
     * <p/>
     * 1、命令取值范围：0x100-Integer.MAX；
     * <p/>
     * 2、命名规则用匈牙利命名法，字母都写成大写，并且去掉message字段， 比如：DriverRequestOrderMessage--->DRIVER_REQUEST_ORDER
     */
    public static final int PRIVATE_CHAT = 0x00000100;
    public static final int PUBLIC_CHAT = 0x00000101;
    public static final int SYSTEM = 0x00000102;
}
