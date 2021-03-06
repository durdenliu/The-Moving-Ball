package com.example.themovingball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HandshakeCompletedListener;

public class MainActivity extends AppCompatActivity {
    private MovingBallView mViewBall;
    private int screenWidth;
    private int screenHeight;
    private Button mButtonRadius;
    private Button mButtonColor;
    private Button mButtonPause;

    private enum Direction{
        RIGHT (0),
        DOWN (1);
        Direction(int ni) {
            nativeInt = ni;
        }
        final int nativeInt;
    }
    private Direction direction;
    private int choice;

    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mViewBall = findViewById(R.id.view_ball);

        animator = new ObjectAnimator();

        mButtonRadius = findViewById(R.id.button_radius);
        mButtonRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText = new EditText(MainActivity.this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this).setTitle("?????????").setView(editText).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(mViewBall.getLayoutParams());
                        lp.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.valueOf(editText.getText().toString()), getApplicationContext().getResources().getDisplayMetrics());
                        lp.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.valueOf(editText.getText().toString()), getApplicationContext().getResources().getDisplayMetrics());
                        mViewBall.setLayoutParams(lp);
                    }
                });
                dialog.show();

            }
        });

        mButtonColor = findViewById(R.id.button_color);
        mButtonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"??????", "??????", "??????", "??????", "??????"};
                choice = -1;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this).setTitle("??????")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                choice = which;
                            }
                        }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (choice != -1) {
                                    switch (items[choice].toString()) {
                                        case "??????": {
                                            mViewBall.setColor(Color.RED);
                                            mViewBall.invalidate();
                                            break;
                                        }
                                        case "??????": {
                                            mViewBall.setColor(Color.YELLOW);
                                            mViewBall.invalidate();
                                            break;
                                        }
                                        case "??????": {
                                            mViewBall.setColor(Color.WHITE);
                                            mViewBall.invalidate();
                                            break;
                                        }
                                        case "??????": {
                                            mViewBall.setColor(Color.BLACK);
                                            mViewBall.invalidate();
                                            break;
                                        }
                                        case "??????": {
                                            mViewBall.setColor(Color.CYAN);
                                            mViewBall.invalidate();
                                            break;
                                        }
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
                dialog.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        int menuItemThatWasSelected = item.getItemId();
        mViewBall = (MovingBallView)findViewById(R.id.view_ball);

        switch (menuItemThatWasSelected){
            case R.id.action_line_move:{

                final String[] items = {"??????", "??????"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("?????????????????????");
                dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                });
                dialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[choice] + "?????????", Toast.LENGTH_LONG).show();
                        if(choice == 1){
                            runLine(Direction.DOWN);
                        } else {
                            runLine(Direction.RIGHT);
                        }
                    }
                });
                dialog.show();
                break;
            }

            case R.id.action_circle_move:{

                final EditText editText = new EditText(this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("??????????????????").setView(editText)
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final int radius;
                                if(editText.getText().toString().length() > 0) {
                                    radius = (int) Integer.valueOf(editText.getText().toString());
                                } else {
                                    radius = screenWidth / 4;
                                }
                                Context context = MainActivity.this;
                                String message = "????????????!";
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                runCircle(screenWidth / 2, screenHeight / 2, radius);
                            }
                        });
                dialog.create().show();
                break;
            }
            case R.id.action_simple_harmonic:{

                Context context = MainActivity.this;
                String message = "????????????!";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                runSHM(screenWidth, screenWidth / 3, 2);
                break;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void runLine(Direction direction){
        mViewBall = (MovingBallView)findViewById(R.id.view_ball);

        Path path = new Path();
        path.setLastPoint(0, 0);
        if (direction == Direction.DOWN){
            path.rLineTo(0, screenHeight);
        } else if (direction == Direction.RIGHT){
            path.rLineTo(screenWidth, 0);
        }

        animator.cancel();
        animator = ObjectAnimator.ofFloat(mViewBall, mViewBall.X, mViewBall.Y, path);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        mButtonPause = findViewById(R.id.button_pause);
        ObjectAnimator finalAnimator = animator;
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonPause.getText().toString() == "??????"){
                    finalAnimator.pause();
                    mButtonPause.setText("??????");
                } else {
                    finalAnimator.resume();
                    mButtonPause.setText("??????");
                }

            }
        });
    }

    private void runCircle(int centerX, int centerY, int radius){
        mViewBall = (MovingBallView)findViewById(R.id.view_ball);
        mViewBall.animate().cancel();

        Path path = new Path();
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW);

        animator = ObjectAnimator.ofFloat(mViewBall, mViewBall.X, mViewBall.Y, path);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        mButtonPause = findViewById(R.id.button_pause);
        ObjectAnimator finalAnimator = animator;
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonPause.getText().toString() == "??????"){
                    finalAnimator.pause();
                    mButtonPause.setText("??????");
                } else {
                    finalAnimator.resume();
                    mButtonPause.setText("??????");
                }

            }
        });

    }

    private void runSHM (int amplitude, int wavelength, int period){

        mViewBall = (MovingBallView)findViewById(R.id.view_ball);


        Path path = new Path();
        path.setLastPoint(0, 0);
        for (int i = -wavelength; i <  period * wavelength; i += wavelength) {

            path.rQuadTo(wavelength / 4, amplitude, wavelength / 2, 0);
            path.rQuadTo(wavelength / 4, amplitude, wavelength / 2, 0);
        }

        animator = ObjectAnimator.ofFloat(mViewBall, mViewBall.X, mViewBall.Y, path);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        mButtonPause = findViewById(R.id.button_pause);
        ObjectAnimator finalAnimator = animator;
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonPause.getText().toString() == "??????"){
                    finalAnimator.pause();
                    mButtonPause.setText("??????");
                } else {
                    finalAnimator.resume();
                    mButtonPause.setText("??????");
                }

            }
        });
    }

}
