package com.huajia.venusdesktop.service.application.guitar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huajia.annotation.Route;
import com.huajia.venusdesktop.framework.router.TRouterPath;
import com.huajia.venusdesktop.R;
import com.huajia.venusdesktop.base.BaseApplication;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * @Author: HuaJ1a
 * @Emal: 821759439@qq.com
 * @Date: 2024/3/3
 * @Description:
 */
@Route(path = TRouterPath.GUITAR, heightPercent = 1)
public class GuitarApplication extends BaseApplication implements View.OnClickListener {
    private static final String TAG = "GuitarApplication";

    private PitchLineView pitchLineView;

    private CircleButton oneButton;

    private CircleButton twoButton;

    private CircleButton threeButton;

    private CircleButton fourButton;

    private CircleButton fiveButton;

    private CircleButton sixButton;

    /**
     * 当前选择的弦 1 ~ 6
     */
    private int currentChord = -1;

    /**
     * 当前选择的弦的音高
     */
    private float currentPitch = 1;

    private TextView pitchView;

    private TextView standardPitchView;

    private LinearLayout layoutControl;

    private GridLayout gridLayout;

    // 校正时正确开始的时间
    private long adjustCorrectStartTime = -1;

    public GuitarApplication(@NonNull Context context) {
        super(context);
        setContentView(R.layout.application_guitar);
        initView();
        initDsp();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        findViewById(R.id.close_button).setOnClickListener( view -> {
            dismiss();
        });
        pitchLineView = findViewById(R.id.pitch_line_view);
        oneButton = findViewById(R.id.one_chord_button);
        twoButton = findViewById(R.id.two_chord_button);
        threeButton = findViewById(R.id.three_chord_button);
        fourButton = findViewById(R.id.four_chord_button);
        fiveButton = findViewById(R.id.five_chord_button);
        sixButton = findViewById(R.id.six_chord_button);
        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        pitchView = findViewById(R.id.pitch_text_view);
        standardPitchView = findViewById(R.id.standard_pitch_view);

        // 动态适配屏幕占比
        pitchLineView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pitchLineView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int marginVertical = pitchLineView.getHeight() / 4;
                int marginHorizontal = pitchLineView.getWidth() / 10;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) pitchLineView.getLayoutParams();
                layoutParams.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);
                pitchLineView.setLayoutParams(layoutParams);
            }
        });

        layoutControl = findViewById(R.id.layout_control);
        gridLayout = findViewById(R.id.layout_grid);
        layoutControl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutControl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) layoutControl.getLayoutParams();
                int marginHorizontal = layoutControl.getWidth() / 10;
                int marginVertical= layoutControl.getHeight() / 5;
                layoutParams.setMargins(marginHorizontal, 0, marginHorizontal, marginVertical);
                layoutControl.setLayoutParams(layoutParams);
                gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int width = gridLayout.getHeight() / 3;
                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            View childAt = gridLayout.getChildAt(i);
                            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                            layoutParams.width = width;
                            layoutParams.height = width;
                            childAt.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        });
    }

    private void initDsp() {
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
                float pitch = pitchDetectionResult.getPitch();
                changePitch(pitch);
            }
        };
        AudioProcessor audioProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pitchDetectionHandler);
        dispatcher.addAudioProcessor(audioProcessor);
        new Thread(dispatcher,"Audio Dispatch").start();
    }

    private void changePitch(float pitch) {
        if (currentChord == -1) {
            return;
        }
        pitchLineView.post(new Runnable() {
            @Override
            public void run() {
                pitchView.setText(String.format("%.2f", pitch));
                float progress = pitch / currentPitch;
                if (progress > GuitarConstants.PITCH_CORRECT_MIN && progress < GuitarConstants.PITCH_CORRECT_MAX) {
                    // 正确，记录当前时间
                    if (adjustCorrectStartTime == -1l) {
                        adjustCorrectStartTime = System.currentTimeMillis();
                    } else {
                        if (System.currentTimeMillis() - adjustCorrectStartTime >= GuitarConstants.PITCH_CORRECT_TIME) {
                            adjustFinish();
                        }
                    }
                } else {
                    adjustCorrectStartTime = -1l;
                }
                pitchLineView.setProgress(progress);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.one_chord_button:
                currentChord = GuitarConstants.ONE_CHORD;
                break;
            case R.id.two_chord_button:
                currentChord = GuitarConstants.TWO_CHORD;
                break;
            case R.id.three_chord_button:
                currentChord = GuitarConstants.THREE_CHORD;
                break;
            case R.id.four_chord_button:
                currentChord = GuitarConstants.FOUR_CHORD;
                break;
            case R.id.five_chord_button:
                currentChord = GuitarConstants.FIVE_CHORD;
                break;
            case R.id.six_chord_button:
                currentChord = GuitarConstants.SIX_CHORD;
                break;
            default:
                break;
        }
        changeChord();
    }

    private void changeChord() {
        oneButton.setClick(currentChord == GuitarConstants.ONE_CHORD);
        twoButton.setClick(currentChord == GuitarConstants.TWO_CHORD);
        threeButton.setClick(currentChord == GuitarConstants.THREE_CHORD);
        fourButton.setClick(currentChord == GuitarConstants.FOUR_CHORD);
        fiveButton.setClick(currentChord == GuitarConstants.FIVE_CHORD);
        sixButton.setClick(currentChord == GuitarConstants.SIX_CHORD);
        currentPitch = GuitarConstants.getStandardPitch(currentChord);
        standardPitchView.setText(String.valueOf(currentPitch));
    }

    private void adjustFinish() {
        oneButton.setAdjustFinish(currentChord == GuitarConstants.ONE_CHORD);
        twoButton.setAdjustFinish(currentChord == GuitarConstants.TWO_CHORD);
        threeButton.setAdjustFinish(currentChord == GuitarConstants.THREE_CHORD);
        fourButton.setAdjustFinish(currentChord == GuitarConstants.FOUR_CHORD);
        fiveButton.setAdjustFinish(currentChord == GuitarConstants.FIVE_CHORD);
        sixButton.setAdjustFinish(currentChord == GuitarConstants.SIX_CHORD);
    }
}
