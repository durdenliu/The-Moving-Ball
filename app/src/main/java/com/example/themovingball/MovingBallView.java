package com.example.themovingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.Constraints;


public class MovingBallView extends View {

    private int mColor = Color.BLACK;

    public MovingBallView(Context context){
        super(context);
    }

    public MovingBallView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MovingBallView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        int mRadius = getWidth() / 2;
        int ballX = mRadius;
        int ballY = mRadius;

        Paint paint = new Paint();

        paint.setColor(mColor);
        canvas.drawCircle(ballX, ballY, mRadius, paint);
    }

    public void setColor(int color){
        mColor = color;

    }



}


