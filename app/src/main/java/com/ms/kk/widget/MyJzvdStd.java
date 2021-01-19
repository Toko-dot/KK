package com.ms.kk.widget;

import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;


import com.ms.kk.R;

import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

public class MyJzvdStd extends JzvdStd {
    private long addTime;
    private float preX;
    private ImageView ig_next;
    private OnNextViewListener onNextViewListener;


    public MyJzvdStd(Context context) {
        super(context);
    }

    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_jzvd;
    }

    @Override
    public void init(Context context) {
        super.init(context);

        ig_next = ((ImageView) findViewById(R.id.ig_next));

        ig_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNextViewListener == null)
                    return;
                onNextViewListener.onNextVideo();
            }
        });

        ((CheckBox) findViewById(R.id.speed)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("2X");
                    mediaInterface.setSpeed(2f);
                } else {
                    buttonView.setText("1X");
                    mediaInterface.setSpeed(1f);
                }
            }
        });
    }

    protected void touchActionDown(float x, float y) {
        Log.i(TAG, "onTouch surfaceContainer actionDown [" + this.hashCode() + "] ");
        mTouchingProgressBar = true;
        mDownX = x;
        mDownY = y;
        mChangeVolume = false;
        mChangePosition = false;
        mChangeBrightness = false;
        addTime = 0;
        preX = x;
    }

    protected void touchActionMove(float x, float y) {
        Log.i(TAG, "onTouch surfaceContainer actionMove [" + this.hashCode() + "] ");
        float deltaX = x - mDownX;
        float deltaY = y - mDownY;
        float absDeltaX = Math.abs(deltaX);
        float absDeltaY = Math.abs(deltaY);
        if (screen == SCREEN_FULLSCREEN) {
            //拖动的是NavigationBar和状态栏
            if (mDownX > JZUtils.getScreenWidth(getContext()) || mDownY < JZUtils.getStatusBarHeight(getContext())) {
                return;
            }
            if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                    cancelProgressTimer();
                    if (absDeltaX >= THRESHOLD) {
                        // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                        // 否则会因为mediaplayer的状态非法导致App Crash
                        if (state != STATE_ERROR) {
                            mChangePosition = true;
                            mGestureDownPosition = getCurrentPositionWhenPlaying();
                        }
                    } else {
                        //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                        if (mDownX < mScreenHeight * 0.5f) {//左侧改变亮度
                            mChangeBrightness = true;
                            WindowManager.LayoutParams lp = JZUtils.getWindow(getContext()).getAttributes();
                            if (lp.screenBrightness < 0) {
                                try {
                                    mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                    Log.i(TAG, "current system brightness: " + mGestureDownBrightness);
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mGestureDownBrightness = lp.screenBrightness * 255;
                                Log.i(TAG, "current activity brightness: " + mGestureDownBrightness);
                            }
                        } else {//右侧改变声音
                            mChangeVolume = true;
                            mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        }
                    }
                }
            }
        }
        if (mChangePosition) {
            long totalTimeDuration = getDuration();

            addTime += (x - preX) > 0 ? 1000 : -1000;
            mSeekTimePosition = mediaInterface.getCurrentPosition() + addTime;

            if (mSeekTimePosition > totalTimeDuration)
                mSeekTimePosition = totalTimeDuration;
            String seekTime = JZUtils.stringForTime(mSeekTimePosition);
            String totalTime = JZUtils.stringForTime(totalTimeDuration);

            showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
            preX = x;
        }
        if (mChangeVolume) {
            deltaY = -deltaY;
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
            //dialog中显示百分比
            int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
            showVolumeDialog(-deltaY, volumePercent);
        }

        if (mChangeBrightness) {
            deltaY = -deltaY;
            int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
            WindowManager.LayoutParams params = JZUtils.getWindow(getContext()).getAttributes();
            if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                params.screenBrightness = 1;
            } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                params.screenBrightness = 0.01f;
            } else {
                params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
            }
            JZUtils.getWindow(getContext()).setAttributes(params);
            //dialog中显示百分比
            int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
            showBrightnessDialog(brightnessPercent);
//                        mDownY = y;
        }
    }

    protected void touchActionUp() {
        Log.i(TAG, "onTouch surfaceContainer actionUp [" + this.hashCode() + "] ");
        mTouchingProgressBar = false;
        dismissProgressDialog();
        dismissVolumeDialog();
        dismissBrightnessDialog();
        if (mChangePosition) {
            mediaInterface.seekTo(mSeekTimePosition);
            long duration = getDuration();
            int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
            progressBar.setProgress(progress);
        }
        if (mChangeVolume) {
            //change volume event
        }
        startProgressTimer();
    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();
        batteryTimeLayout.setVisibility(GONE);
    }

    @Override
    public void setScreenTiny() {
        super.setScreenTiny();

    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        batteryTimeLayout.setVisibility(GONE);
    }

    public void setOnNextViewListener(OnNextViewListener onNextViewListener) {
        this.onNextViewListener = onNextViewListener;
    }

    public interface OnNextViewListener {
        void onNextVideo();
    }
}
