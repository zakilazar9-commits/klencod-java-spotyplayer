package com.spotyplayer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;

public class AudioVisualizerView extends View {
    private Paint paint;
    private byte[] bytes;
    private float[] points;
    private Random random = new Random();

    public AudioVisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(8f);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#1DB954")); // لون نيون جذاب (شبيه بـ Spotify)
    }

    public void updateVisualizer(byte[] byteArray) {
        this.bytes = byteArray;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bytes == null) return;

        if (points == null || points.length < bytes.length * 4) {
            points = new float[bytes.length * 4];
        }

        float width = getWidth();
        float height = getHeight();

        for (int i = 0; i < bytes.length - 1; i++) {
            float x = i * (width / (float) bytes.length);
            float y = height / 2 + ((byte) (bytes[i] + 128)) * (height / 256);
            
            // رسم خطوط الطيف الصوتي المتراقصة
            canvas.drawLine(x, height, x, y, paint);
        }
    }
}