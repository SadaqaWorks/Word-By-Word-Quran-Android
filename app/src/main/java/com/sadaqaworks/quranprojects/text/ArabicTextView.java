package com.sadaqaworks.quranprojects.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

public class ArabicTextView extends TextView {

    final private ArrayList<Line> mLayout = new ArrayList<Line>();
    final private Paint mPaint = new Paint();
    final private StringBuffer mBuffer = new StringBuffer();
    private String[] mWords = new String[0];
    private int mTopOverflow;
    private int mBottomOverflow;
    private int mFontHeight;
    private int mFontAscend;
    private int mTotalWidth;
    private int mTotalHeight;
    private FontCache mCache;

    public ArabicTextView(Context context) {
        super(context);
        setGravity(Gravity.RIGHT);
        mCache = FontCache.getInstance();
    }

    public ArabicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.RIGHT);
        mCache = FontCache.getInstance();
    }

    public static String[] split(String text) {
        ArrayList<String> words = new ArrayList<String>();
        String last = null;
        for (String s : text.trim().split(" +")) {
            if (s.length() > 0 && s.charAt(0) >= '\u06d6') {
                if (last == null) {
                    last = s;
                } else {
                    last = last + ' ' + s;
                    words.remove(words.size() - 1);
                }
            } else {
                last = s;
            }
            words.add(last);
        }
        return words.toArray(new String[words.size()]);
    }

    private String joinWords(StringBuffer sb, int start, int end) {
        sb.setLength(0);
        for (int i = start; i <= end; i++) {
            if (i > start) {
                sb.append(" ");
            }
            sb.append(mWords[i]);
        }
        return sb.toString();
    }

    private Bitmap getTextBitmapCached(String text, int size) {
        Bitmap bitmap = mCache.getBitmap(text);
        if (bitmap == null) {
            bitmap = NativeRenderer.renderText(text, size);
            mCache.putBitmap(text, bitmap);
        }
        return bitmap;
    }

    private int[] getTextExtentCached(String text, int size) {
        int[] extent = mCache.getExtent(text);
        if (extent == null) {
            extent = NativeRenderer.getTextExtent(text, size);
            mCache.putExtent(text, extent);
        }
        return extent;
    }


    private int consumeLine(int start, int width) {
        int realStart = start;
        int end = mWords.length - 1;

        // Scan full words first
        String text = joinWords(mBuffer, start, end);
        int textSize = (int) getTextSize();
        int goodExt[] = getTextExtentCached(text, textSize);
        if (width < goodExt[0] && start < end) {
            int badWidth = goodExt[0];
            int goodWidth = 0;
            end = end - 1;
            while (end > start) {
                // Guess mid using ratio
                int mid = (end - start) * (width - goodWidth) / (badWidth - goodWidth);
                mid += start + 1;

                text = joinWords(mBuffer, realStart, mid);
                int[] ext = getTextExtentCached(text, textSize);
                if (width < ext[0]) {
                    end = mid - 1;
                    badWidth = ext[0];
                } else {
                    start = mid;
                    goodExt = ext;
                    goodWidth = ext[0];
                }
            }
        }

        Line line = new Line();
        line.index = mLayout.size();
        line.start = realStart;
        line.end = end;
        line.width = goodExt[0];
        line.height = goodExt[1];
        line.ascend = mFontAscend - goodExt[2];
        mLayout.add(line);
        return end + 1;
    }

    private void createLayout(int width) {
        mLayout.clear();
        for (int start = 0; start < mWords.length; ) {
            start = consumeLine(start, width);
        }

        // Calculate overflow
        mTopOverflow = 0;
        mBottomOverflow = 0;
        if (mLayout.size() > 0) {
            Line top = mLayout.get(0);
            if (top.ascend < 0) {
                mTopOverflow = -top.ascend;
            }
            Line bottom = mLayout.get(mLayout.size() - 1);
            if (bottom.ascend + bottom.height > mFontHeight) {
                mBottomOverflow = (bottom.ascend + bottom.height) - mFontHeight;
            }
        }
        mTotalHeight = mTopOverflow + mBottomOverflow + mFontHeight * mLayout.size();
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        requestLayout();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        mWords = split(text.toString());
        requestLayout();
    }

    @Override
    public int getLineHeight() {
        return mFontHeight;
    }

    @Override
    public int getLineCount() {
        return mLayout.size();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        String text = joinWords(mBuffer, 0, mWords.length - 1);
        int[] ext = getTextExtentCached(text, (int) getTextSize());
        mTotalWidth = ext[0];
        mFontHeight = ext[3];
        mFontAscend = ext[4];

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mTotalWidth + getCompoundPaddingLeft() + getCompoundPaddingRight();
            width = Math.max(width, getSuggestedMinimumWidth());
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        int usableWidth = width - getCompoundPaddingLeft() - getCompoundPaddingRight();
        createLayout(usableWidth);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mTotalHeight + getCompoundPaddingTop() + getCompoundPaddingBottom();
            height = Math.max(height, getSuggestedMinimumHeight());
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int usableWidth = getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight();
        int usableHeight = getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom();
        int color = getCurrentTextColor();
        float[] matrix = {
                0, 0, 0, 0, Color.red(color),
                0, 0, 0, 0, Color.green(color),
                0, 0, 0, 0, Color.blue(color),
                0, 0, 0, 1, 0
        };
        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        Rect clip = canvas.getClipBounds();
        int size = (int) getTextSize();
        for (Line line : mLayout) {
            int x = getCompoundPaddingLeft();
            int gravity = getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
            if (gravity == Gravity.RIGHT) {
                x += usableWidth - line.width;
            } else if (gravity == Gravity.CENTER_HORIZONTAL) {
                x += (usableWidth - line.width) / 2;
            }

            int y = getCompoundPaddingTop() + mTopOverflow + line.index * mFontHeight + line.ascend;
            gravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
            if (gravity == Gravity.BOTTOM) {
                y += usableHeight - mTotalHeight;
            } else if (gravity == Gravity.CENTER_VERTICAL) {
                y += (usableHeight - mTotalHeight) / 2;
            }

            if (!clip.intersects(x, y, x + line.width, y + line.height)) {
                continue;
            }

            String text = joinWords(mBuffer, line.start, line.end);
            Bitmap bitmap = getTextBitmapCached(text, size);

            Rect src = new Rect(0, 0, line.width, line.height);
            Rect dst = new Rect(x, y, x + line.width, y + line.height);
            canvas.drawBitmap(bitmap, src, dst, mPaint);
        }
    }

    private static class Line {
        int index;
        int start;
        int end;
        int width;
        int height;
        int ascend;
    }
}
