package org.es.engine.graphics.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Cyril Leroux on 27/02/14.
 */
public class DrawTextUtils {

    public static enum VerticalAlign {
        TOP,
        BOTTOM,
        CENTER
    }

    public static enum HorizontalAlign {
        RIGHT,
        LEFT,
        CENTER
    }

    public static RectF drawText(String text, Canvas canvas, float x, float y, HorizontalAlign hAlign, VerticalAlign vAlign, Paint paint) {

        // Save paint align.
        Paint.Align initialAlign = paint.getTextAlign();

        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);

        RectF onScreenBounds = new RectF(textBounds);

        if (hAlign == HorizontalAlign.CENTER) {
            paint.setTextAlign(Paint.Align.CENTER);
            onScreenBounds.left = x - Math.abs(textBounds.width() / 2f);

        } else if (hAlign == HorizontalAlign.LEFT) {
            paint.setTextAlign(Paint.Align.RIGHT);
            onScreenBounds.left = x - Math.abs(textBounds.width());

        } else if (hAlign == HorizontalAlign.RIGHT) {
            paint.setTextAlign(Paint.Align.LEFT);
            onScreenBounds.left = x;
        }

        if (vAlign == VerticalAlign.CENTER) {
            onScreenBounds.top = y - Math.abs(textBounds.height() / 2f);

        } else if (vAlign == VerticalAlign.TOP) {
            onScreenBounds.top = y - Math.abs(textBounds.height());

        } else if (vAlign == VerticalAlign.BOTTOM) {
            onScreenBounds.top = y;
        }

        onScreenBounds.right = onScreenBounds.left + Math.abs(textBounds.width());
        onScreenBounds.bottom = onScreenBounds.top + Math.abs(textBounds.height());

        canvas.drawText(text, x, onScreenBounds.bottom, paint);

        // Restore paint align.
        paint.setTextAlign(initialAlign);

        return onScreenBounds;
    }
}
