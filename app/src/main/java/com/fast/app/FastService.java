package com.fast.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;

public class FastService extends AccessibilityService {

    private final int targetColor = 17035;
    private final int targetX = 1766;
    private final int targetY = 571;

    private Handler handler;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        handler = new Handler(Looper.getMainLooper());
        startPixelLoop();
    }

    private void startPixelLoop() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = captureScreen(); // заглушка
                if (bmp != null) {
                    int color = bmp.getPixel(targetX, targetY);
                    if (color == targetColor) {
                        tapSequence();
                    }
                }
                handler.postDelayed(this, 1);
            }
        });
    }

    private void tapSequence() {
        int[][] coords = {
                {2159,580},{1238,787},{2159,580},{1238,787},
                {2159,580},{1238,787},{2159,580},{1238,787},
                {2272,112},{985,455}
        };
        for (int[] c : coords) {
            tap(c[0], c[1]);
        }
    }

    private void tap(int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 1));
        dispatchGesture(builder.build(), null, null);
    }

    private Bitmap captureScreen() {
        return null; // заглушка
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
}
