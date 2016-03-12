/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.okada.com.flowerbuttons;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * This example shows how to use property animations, specifically ObjectAnimator, to perform
 * various view animations. Compare this approach to that of the ViewAnimations demo, which
 * shows how to achieve similar effects using the pre-3.0 animation APIs.
 *
 * Watch the associated video for this demo on the DevBytes channel of developer.android.com
 * or on YouTube at https://www.youtube.com/watch?v=3UbJhmkeSig.
 */
public class FlowerButtons extends FrameLayout implements View.OnTouchListener {

    private final static String TAG = FlowerButtons.class.getName();

    private Button mCamera;
    private Button mBusiness;
    private Button mBus;
    private Button mCandy;
    private Button mCandle;
    private boolean mSpreadFlag = false;
    private static final int SPREAD_BUTTON_COUNT = 4;
    private static final int SPREAD_DELAY_TIME = 100;
    private static final int SPREAD_DURATION = 200;
    private static final int SPREAD_DISTANCE = 400;
    private static final int SPREAD_START_DIGREE = 180;
    private OnSelectListner mListener = null;
    private static int mSpreadDigree = 90 / (SPREAD_BUTTON_COUNT - 1);

    public FlowerButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.flower_buttons, this);

        mCamera = (Button) layout.findViewById(R.id.camera);
        mBusiness = (Button) layout.findViewById(R.id.business);
        mBus = (Button) layout.findViewById(R.id.bus);
        mCandy = (Button) layout.findViewById(R.id.candy);
        mCandle = (Button) layout.findViewById(R.id.candle);

        mCandle.setOnTouchListener(this);
        mCamera.setOnTouchListener(this);
        mBusiness.setOnTouchListener(this);
        mBus.setOnTouchListener(this);
        mCandy.setOnTouchListener(this);

        mCandle.setFocusable(true);
        mCandle.setFocusableInTouchMode(true);
        mCamera.setFocusable(true);
        mCamera.setFocusableInTouchMode(true);
        mBusiness.setFocusable(true);
        mBusiness.setFocusableInTouchMode(true);
        mBus.setFocusable(true);
        mBus.setFocusableInTouchMode(true);
        mCandy.setFocusable(true);
        mCandy.setFocusableInTouchMode(true);
    }

    private void animatePropertyValuesHolder(Button target, float degree, float distance, int duration) {
        float toX = (float) (distance * Math.cos(Math.toRadians(degree)));
        float toY = (float) (distance * Math.sin(Math.toRadians(degree)));

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0f, toX);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, toY);
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                target, holderX, holderY, holderRotaion);

        objectAnimator.setDuration(duration);

        objectAnimator.start();
    }

    private boolean checkPositionContains(Rect viewRect, MotionEvent event) {
        int[] location = new int[2];
        mCandle.getLocationOnScreen(location);
        return viewRect.contains(location[0] + (int) event.getX(), location[1] + (int) event.getY());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            toggleButtons();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            toggleButtons();
            if (mCandle.isFocused()) {
                Log.d(TAG, "selected candle");
                mListener.onSelected((View) mCandle);
            } else if (mBus.isFocused()) {
                Log.d(TAG, "selected bus");
                mListener.onSelected((View) mBus);
            } else if (mCamera.isFocused()) {
                Log.d(TAG, "selected camera");
                mListener.onSelected((View) mCamera);
            } else if (mCandy.isFocused()) {
                Log.d(TAG, "selected candy");
                mListener.onSelected((View) mCandy);
            } else if (mBusiness.isFocused()) {
                Log.d(TAG, "selected business");
                mListener.onSelected((View) mBusiness);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Rect candleRect = locateView(mCandle);
            Rect busRect = locateView(mBus);
            Rect cameraRect = locateView(mCamera);
            Rect candyRect = locateView(mCandy);
            Rect businessRect = locateView(mBusiness);
            if (checkPositionContains(candleRect, event)) {
                mCandle.requestFocusFromTouch();
            } else if (checkPositionContains(busRect, event)) {
                mBus.requestFocusFromTouch();
            } else if (checkPositionContains(cameraRect, event)) {
                mCamera.requestFocusFromTouch();
            } else if (checkPositionContains(candyRect, event)) {
                mCandy.requestFocusFromTouch();
            } else if (checkPositionContains(businessRect, event)) {
                mBusiness.requestFocusFromTouch();
            }
        }
        return false;
    }

    private void spreadButtons() {
        Handler handler = new Handler();
        int delayTime = 100;
        Runnable busRunnable = new Runnable() {
            @Override
            public void run() {
                final int degree = SPREAD_START_DIGREE;
                animatePropertyValuesHolder(mBus, degree, SPREAD_DISTANCE, SPREAD_DURATION);
            }
        };
        Runnable businessRunnable = new Runnable() {
            @Override
            public void run() {
                final int degree = SPREAD_START_DIGREE + mSpreadDigree;
                animatePropertyValuesHolder(mBusiness, degree, SPREAD_DISTANCE, SPREAD_DURATION);
            }
        };
        Runnable cameraRunnable = new Runnable() {
            @Override
            public void run() {
                final int degree = SPREAD_START_DIGREE + mSpreadDigree * 2;
                animatePropertyValuesHolder(mCamera, degree, SPREAD_DISTANCE, SPREAD_DURATION);
            }
        };
        Runnable candyRunnable = new Runnable() {
            @Override
            public void run() {
                final int degree = SPREAD_START_DIGREE + mSpreadDigree * 3;
                animatePropertyValuesHolder(mCandy, degree, SPREAD_DISTANCE, SPREAD_DURATION);
            }
        };
        Runnable resetRunnable = new Runnable() {
            @Override
            public void run() {
                if (!mSpreadFlag) {
                    resetButtons();
                }
            }
        };
        handler.postDelayed(busRunnable, delayTime);
        handler.postDelayed(businessRunnable, delayTime += SPREAD_DELAY_TIME);
        handler.postDelayed(cameraRunnable, delayTime += SPREAD_DELAY_TIME);
        handler.postDelayed(candyRunnable, delayTime += SPREAD_DELAY_TIME);
        handler.postDelayed(resetRunnable, delayTime += SPREAD_DELAY_TIME * 2);
    }

    private void resetButtons() {
        mCandle.clearFocus();
        animatePropertyValuesHolder(mBus, 0, 0, 0);
        animatePropertyValuesHolder(mBusiness, 0, 0, 0);
        animatePropertyValuesHolder(mCamera, 0, 0, 0);
        animatePropertyValuesHolder(mCandy, 0, 0, 0);
    }

    private void toggleButtons() {
        if (mSpreadFlag) {
            mSpreadFlag = false;
            resetButtons();
        } else {
            mSpreadFlag = true;
            spreadButtons();
        }
    }

    public Rect locateView(View view) {
        Rect rect = new Rect();
        int[] location = new int[2];
        if (view == null) {
            return rect;
        }
        view.getLocationOnScreen(location);
        rect.left = location[0];
        rect.top = location[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        return rect;
    }

    public void setListener(OnSelectListner listener){
        this.mListener = listener;
    }

    public void removeListener(){
        this.mListener = null;
    }
}
