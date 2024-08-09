package org.es.engine.hud;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.es.engine.graphics.utils.DrawTextUtils;
import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class Text extends Control {

    private final DrawTextUtils.HorizontalAlign mHorizontalAlign;
    private final DrawTextUtils.VerticalAlign mVerticalAlign;
    private final Paint mPaint;

    private final String mStaticText;

    public Text(float xCoef, float yCoef, String staticText, DrawTextUtils.HorizontalAlign hAlign, DrawTextUtils.VerticalAlign vAlign, Paint paint) {
        super(xCoef, yCoef);

        mHorizontalAlign = hAlign;
        mVerticalAlign = vAlign;
        mPaint = paint;

        mStaticText = staticText;
    }

    /**
     * Draw the HUD element.
     *
     * @param canvas The canvas on which to draw.
     * @param param  Not used for the main HUD. Can not be null for other HUD elements.
     */
    @Override
    public void draw(Canvas canvas, DrawingParam param) {
        setBounds(DrawTextUtils.drawText(mStaticText + getText(), canvas,
                getXCoef() * canvas.getWidth(),
                getYCoef() * canvas.getHeight(),
                mHorizontalAlign, mVerticalAlign, mPaint));
    }

    protected abstract String getText();
}
