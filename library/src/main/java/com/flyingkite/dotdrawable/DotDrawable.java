package com.flyingkite.dotdrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.Arrays;

public class DotDrawable extends Drawable {
    // Drawable's max level
    private static final int MAX_LEVEL = 10000;

    // google colors, red, green, blue, yellow
    private static final int RED = 0xFFC93437;
    private static final int BLUE = 0xFF375BF1;
    private static final int GREEN = 0xFF34A350;
    private static final int YELLOW = 0xFFF7D23E;
    private int[] mDotColors = {RED, GREEN, BLUE, YELLOW};

    // points and paints
    private Paint[] mDotPaints = new Paint[4];
    private PointF[] mDotXY = new PointF[4];
    private int topDotIndex = 0;

    // The scale of dot, radius = width / mDotScale
    private float mDotScale = 5;
    private float mDotRadius;

    // pause the drawable and no need to drawn again
    private boolean mPause = false;

    // previous value of dot on the top
    private int prevT;

    // Speed
    private static final int ACCELERATION = 1;
    private int mAcceleration;
    private int mHalfPeriod;
    private int mSide;

    public DotDrawable() {
        init();
    }

    public DotDrawable(int[] colors) {
        setColors(colors);
        init();
    }

    /**
     * The 4 colors of dot, from left-top, clockwise
     * @param colors Colors of dot in left top, right top, right bottom and left bottom
     */
    public void setColors(int[] colors) {
        if (colors == null || colors.length != 4) {
            throw new IllegalArgumentException("Invalid 4 colors, " + Arrays.toString(colors));
        }
        mDotColors = colors;
    }

    private void init() {
        setPaints();
        setAlpha(255);
        setAcceleration(ACCELERATION);
    }

    private void setPaints() {
        for (int i = 0; i < 4; i++) {
            mDotXY[i] = new PointF();
            mDotPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDotPaints[i].setColor(mDotColors[i]);
            mDotPaints[i].setAntiAlias(true);
        }
    }

    public void setPause(boolean pause) {
        mPause = pause;
    }

    public void setAcceleration(int acceleration) {
        mAcceleration = acceleration;
        mHalfPeriod = MAX_LEVEL / acceleration / 2;
    }

    public void setDotScale(float dotScale) {
        mDotScale = dotScale;
        updateRadius(mSide);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        int W = bounds.width();
        int H = bounds.height();
        int min;
        // Dots align at left-top
        if (W > H) { // landscape
            if ((H & 0x1) != 0) { // minus 1 if odd
                H--;
            }
            min = H;
        } else { // portrait
            if ((W & 0x1) != 0) { // minus 1 if odd
                W--;
            }
            min = W;
        }
        mSide = min;
        updateRadius(min);
    }

    private void updateRadius(int width) {
        mDotRadius = width / mDotScale;
        float s = mDotScale - 1;
        mDotXY[0].set(mDotRadius,     mDotRadius);
        mDotXY[1].set(mDotRadius * s, mDotRadius);
        mDotXY[2].set(mDotRadius * s, mDotRadius * s);
        mDotXY[3].set(mDotRadius,     mDotRadius * s);
    }

    @Override
    public void draw(Canvas canvas) {
        // Draw the other 3 non-top dots
        int i;
        for (i = 3; i >= 0; i--) {
            if (i != topDotIndex) {
                canvas.drawCircle(mDotXY[i].x, mDotXY[i].y, mDotRadius, mDotPaints[i]);
            }
        }

        // Draw the top dot
        i = topDotIndex;
        canvas.drawCircle(mDotXY[i].x, mDotXY[i].y, mDotRadius, mDotPaints[i]);
    }

    @Override
    protected boolean onLevelChange(int level) {
        if (mPause) return false;
        //  T = time max, O = (0, 0)
        //  A = (T/4, V), B = (T/2, 0)
        //  C = (3T/4, -V), D = (T, 0)
        //    |   A
        //  V #  /\
        //    | /  \
        //    |/    \
        //  --O-----B------D-----
        //    |      \    /
        //    |       \  /
        // -V #        \/
        //    |        C

        // Velocity = sin(t) => Distance = A - B * cos(t)
        double freq = 2 * Math.PI * mAcceleration / MAX_LEVEL; // frequency = 2*PI / period
        int t = level % (MAX_LEVEL / mAcceleration);
        float A = mDotRadius * mDotScale / 2; // Start point, center of drawable
        float B = mDotRadius * (mDotScale / 2 - 1); // Amplitude, waving distance
        float dx = (float) Math.cos(freq * t);
        float go = A - B * dx;
        float back = A + B * dx;

        // Point order should sync with #updateRadius()
        mDotXY[0].set(go, go);
        mDotXY[1].set(back, go);
        mDotXY[2].set(back, back);
        mDotXY[3].set(go, back);

        // t2 is to treat level 10000 as 9999, so we can know next period has come
        // And to change the dot on the top
        int t2 = (t == 10000) ? 9999 : t;
        int newT = t2 / mHalfPeriod;
        if (prevT != newT) {
            topDotIndex = (topDotIndex + 1) % 4;
        }
        prevT = newT;
        return true; // appearance of the Drawable changed
    }

    @Override
    public void setAlpha(int alpha) {
        for (int i = 0; i < 4; i++) {
            mDotPaints[i].setAlpha(alpha);
        }
    }

    @Override
    public int getAlpha() {
        return mDotPaints[0].getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        for (int i = 0; i < 4; i++) {
            mDotPaints[i].setColorFilter(cf);
        }
    }

    @Override
    public ColorFilter getColorFilter() {
        return mDotPaints[0].getColorFilter();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
