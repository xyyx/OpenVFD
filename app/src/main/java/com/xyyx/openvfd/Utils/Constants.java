package com.xyyx.openvfd.Utils;

public class Constants {
    public static final boolean DEBUG = false;
    public static final String TAG = "OpenVFD";
    public static final String LED_ON = "/sys/class/leds/openvfd/led_on";
    public static final String LED_OFF = "/sys/class/leds/openvfd/led_off";
	public static final String LED_CMD = "/sys/class/leds/openvfd/led_cmd";
    public static final String PIPE_FILE = "/data/tmp/openvfd_service";
    public static final String TEMP_FILE = "/sys/class/thermal/thermal_zone0/temp";
    public static final String ALARM = "alarm";
    public static final String USB = "usb";
    public static final String PLAY = "play";
    public static final String PAUSE = "pause";
    public static final String COL = "col";
    public static final String ETH = "eth";
    public static final String WIFI = "wifi";
}
