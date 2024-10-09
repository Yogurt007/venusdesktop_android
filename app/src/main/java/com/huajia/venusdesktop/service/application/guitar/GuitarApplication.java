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
import com.huajia.venusdesktop.databinding.ApplicationGuitarBinding;
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

    private ApplicationGuitarBinding binding;

    /**
     * 当前选择的弦 1 ~ 6
     */
    private int currentChord = -1;

    /**
     * 当前选择的弦的音高
     */
    private float currentPitch = 1;

    // 校正时正确开始的时间
    private long adjustCorrectStartTime = -1;

    public GuitarApplication(@NonNull Context context) {
        super(context);
        binding = ApplicationGuitarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        binding.oneChordButton.setOnClickListener(this);
        binding.twoChordButton.setOnClickListener(this);
        binding.threeChordButton.setOnClickListener(this);
        binding.fourChordButton.setOnClickListener(this);
        binding.fiveChordButton.setOnClickListener(this);
        binding.sixChordButton.setOnClickListener(this);

        // 动态适配屏幕占比
        binding.pitchLineView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.pitchLineView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int marginVertical = binding.pitchLineView.getHeight() / 4;
                int marginHorizontal = binding.pitchLineView.getWidth() / 10;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.pitchLineView.getLayoutParams();
                layoutParams.setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical);
                binding.pitchLineView.setLayoutParams(layoutParams);
            }
        });

        binding.layoutControl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.layoutControl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.layoutControl.getLayoutParams();
                int marginHorizontal = binding.layoutControl.getWidth() / 10;
                int marginVertical= binding.layoutControl.getHeight() / 5;
                layoutParams.setMargins(marginHorizontal, 0, marginHorizontal, marginVertical);
                binding.layoutControl.setLayoutParams(layoutParams);
                binding.layoutGrid.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        binding.layoutGrid.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int width = binding.layoutGrid.getHeight() / 3;
                        for (int i = 0; i < binding.layoutGrid.getChildCount(); i++) {
                            View childAt = binding.layoutGrid.getChildAt(i);
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
        binding.pitchLineView.post(new Runnable() {
            @Override
            public void run() {
                binding.pitchTextView.setText(String.format("%.2f", pitch));
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
                binding.pitchLineView.setProgress(progress);
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
        binding.oneChordButton.setClick(currentChord == GuitarConstants.ONE_CHORD);
        binding.twoChordButton.setClick(currentChord == GuitarConstants.TWO_CHORD);
        binding.threeChordButton.setClick(currentChord == GuitarConstants.THREE_CHORD);
        binding.fourChordButton.setClick(currentChord == GuitarConstants.FOUR_CHORD);
        binding.fiveChordButton.setClick(currentChord == GuitarConstants.FIVE_CHORD);
        binding.sixChordButton.setClick(currentChord == GuitarConstants.SIX_CHORD);
        currentPitch = GuitarConstants.getStandardPitch(currentChord);
        binding.standardPitchView.setText(String.valueOf(currentPitch));
    }

    private void adjustFinish() {
        binding.oneChordButton.setAdjustFinish(currentChord == GuitarConstants.ONE_CHORD);
        binding.twoChordButton.setAdjustFinish(currentChord == GuitarConstants.TWO_CHORD);
        binding.threeChordButton.setAdjustFinish(currentChord == GuitarConstants.THREE_CHORD);
        binding.fourChordButton.setAdjustFinish(currentChord == GuitarConstants.FOUR_CHORD);
        binding.fiveChordButton.setAdjustFinish(currentChord == GuitarConstants.FIVE_CHORD);
        binding.sixChordButton.setAdjustFinish(currentChord == GuitarConstants.SIX_CHORD);
    }
}
