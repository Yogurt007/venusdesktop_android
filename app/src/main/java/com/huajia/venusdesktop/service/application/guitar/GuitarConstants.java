package com.huajia.venusdesktop.service.application.guitar;

/**
 * @Author: HuaJ1a
 * @Email: 821759439@qq.com
 * @Date: 2024/4/21
 * @Description:
 */
public class GuitarConstants {

    public static final int ONE_CHORD = 1;
    public static final int TWO_CHORD = 2;
    public static final int THREE_CHORD = 3;
    public static final int FOUR_CHORD = 4;
    public static final int FIVE_CHORD = 5;
    public static final int SIX_CHORD = 6;

    // 正确的区间范围
    public static final float PITCH_CORRECT_MIN = 0.95f;
    public static final float PITCH_CORRECT_MAX = 1.05f;
    // 正确持续的时间
    public static final long PITCH_CORRECT_TIME = 2000l;


    // 弦的标准音高
    private  static final float[] STANDARD_PITCH = { 329.628f, 246.942f, 196f, 146.832f, 110f, 82.407f };

    /**
     * 获取弦的标准音高
     * @param chord 第几根弦
     * @return 音高
     */
    public static float getStandardPitch(int chord) {
        if (chord < 0 || chord > SIX_CHORD) {
            throw new RuntimeException("chord over");
        }
        return STANDARD_PITCH[chord - 1];
    }

}
