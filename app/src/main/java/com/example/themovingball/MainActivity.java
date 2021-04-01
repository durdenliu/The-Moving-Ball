package com.example.themovingball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int menuItemThatWasSelected = item.getItemId();
        MovingBallView viewBall = (MovingBallView)findViewById(R.id.view_ball);
        switch (menuItemThatWasSelected){
            case R.id.action_line_move:{
                Context context = MainActivity.this;
                String message = "直线运动!";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                run_line(screenWidth, screenHeight / 2);
                break;
            }
            case R.id.action_circle_move:{
                Context context = MainActivity.this;
                String message = "圆周运动!";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                run_circle(screenWidth / 2, screenHeight / 2, screenWidth / 4);
                break;
            }
            case R.id.action_simple_harmonic:{
                Context context = MainActivity.this;
                String message = "简谐运动!";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                run_SHM(screenWidth, screenWidth / 4, 5);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void run_line(int toX, int toY){
        MovingBallView viewBall = (MovingBallView)findViewById(R.id.view_ball);

        Path path = new Path();
        path.lineTo(toX, toY);

        ObjectAnimator animator = ObjectAnimator.ofFloat(viewBall, View.X, View.Y, path);
        animator.setDuration(2000);
        animator.start();
    }

    private void run_circle(int centerX, int centerY, int radius){
        MovingBallView viewBall = (MovingBallView)findViewById(R.id.view_ball);

        Path path = new Path();
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW);

        ObjectAnimator animator = ObjectAnimator.ofFloat(viewBall, View.X, View.Y, path);
        animator.setDuration(2000);
        animator.start();
    }

    private void run_SHM (int amplitude, int wavelength, int period){

        int waveControl = 60;

        MovingBallView viewBall = (MovingBallView)findViewById(R.id.view_ball);

        Path path = new Path();

        for (int i = -wavelength; i <  period * wavelength; i += wavelength){

            path.rQuadTo(wavelength / 4, amplitude, wavelength / 2, 0);
            path.rQuadTo(wavelength / 4, amplitude, wavelength / 2, 0);
        }

        path.close();

        ObjectAnimator animator = ObjectAnimator.ofFloat(viewBall, View.X, View.Y, path);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000);
        animator.start();
    }

}