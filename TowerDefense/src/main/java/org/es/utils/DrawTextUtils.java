package org.es.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;

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

    public static Rect drawText(String text, Canvas canvas, int x, int y, HorizontalAlign hAlign, VerticalAlign vAlign, Paint paint) {

        // Save paint align.
        Paint.Align initialAlign = paint.getTextAlign();

        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);

        if (hAlign == HorizontalAlign.CENTER) {
            paint.setTextAlign(Paint.Align.CENTER);

        } else if (hAlign == HorizontalAlign.LEFT) {
            paint.setTextAlign(Paint.Align.RIGHT);

        } else if (hAlign == HorizontalAlign.RIGHT) {
            paint.setTextAlign(Paint.Align.LEFT);
        }

        if (vAlign == VerticalAlign.CENTER) {
           y -= textBounds.height() / 2f;

        } else if (vAlign == VerticalAlign.TOP) {
            y -= textBounds.height();

        } else if (vAlign == VerticalAlign.BOTTOM) {
            y += textBounds.height();
        }

        canvas.drawText(text, x, y, paint);

        // Restore paint align.
        paint.setTextAlign(initialAlign);

        return textBounds;
    }
}
