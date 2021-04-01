package com.example.themovingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class MovingBallView extends View {


    public MovingBallView(Context context){
        super(context);
    }

    public MovingBallView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        int radius = getMeasuredWidth() / 2;

        int ballX = getLeft() + radius;

        int ballY = getTop() + radius;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        canvas.drawCircle(ballX, ballY, radius, paint);

    }

}


