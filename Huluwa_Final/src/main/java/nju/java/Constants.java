package nju.java;

public class Constants {
    public static final int CELLLEN = 50; // 图片的边长   (必须是STEP的整数倍）
    public static final int ATTACK_DIS = 2; // 攻击范围，以“格”为单位
    public static final int TIME_CLOCK = 400; // 线程休眠时间 （毫秒）
    public static final int REPLAY_CLOCK = 1;
    public static final int SCREEN_HEIGHT = 800; // 高度
    public static final int SCREEN_WIDTH = 1200; // 宽度
    public static final int NUMCELLX = SCREEN_WIDTH / CELLLEN;//x轴方向上格子的个数，24个
    public static final int NUMCELLY = SCREEN_HEIGHT / CELLLEN;//x轴方向上格子的个数，16个
}
