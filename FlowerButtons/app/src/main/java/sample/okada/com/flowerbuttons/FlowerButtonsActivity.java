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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * This example shows how to use property animations, specifically ObjectAnimator, to perform
 * various view animations. Compare this approach to that of the ViewAnimations demo, which
 * shows how to achieve similar effects using the pre-3.0 animation APIs.
 *
 * Watch the associated video for this demo on the DevBytes channel of developer.android.com
 * or on YouTube at https://www.youtube.com/watch?v=3UbJhmkeSig.
 */
public class FlowerButtonsActivity extends Activity implements OnSelectListner {

    private final static String TAG = FlowerButtonsActivity.class.getName();
    private FlowerButtons mFlowerButtons = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_buttons);

        mFlowerButtons = (FlowerButtons) findViewById(R.id.flower_buttons);
        mFlowerButtons.setListener(this);
    }

    @Override
    public void onSelected(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bus:
                break;
            case R.id.camera:
                break;
            case R.id.business:
                break;
            case R.id.candle:
                break;
            case R.id.candy:
                break;
            default:
                break;
        }
    }
}
