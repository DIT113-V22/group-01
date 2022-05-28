package com.example.smartcarmqttapp.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import org.eclipse.paho.client.mqttv3.MqttException;

public class JoystickView extends SurfaceView implements View.OnTouchListener, SurfaceHolder.Callback {

    private float centerX;
    private float centerY;
    private float hatRadius;
    private float baseRadius;
    private JoystickListener joyStickCallback;

    private void setupDimensions() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 5;
    }

    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joyStickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joyStickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributes) {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener){
            joyStickCallback = (JoystickListener) context;
        }
    }

    public void drawJoystick(float newX, float newY) {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            colors.setColor(Color.GRAY);
            canvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setColor(Color.BLUE);
            canvas.drawCircle(newX, newY, hatRadius, colors);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view.equals(this)) {
            if (event.getAction() != event.ACTION_UP) {
                float displacement = (float) Math.sqrt((Math.pow(event.getX() - centerX, 2)) + Math.pow(event.getY() - centerY, 2));
                if (displacement < baseRadius) {
                    drawJoystick(event.getX(), event.getY());
                    if (view instanceof JoystickListener) {
                        try {
                            joyStickCallback.onJoystickMoved((event.getX() - centerX) / baseRadius, (event.getY() - centerY) / baseRadius, getId());
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (event.getX() - centerX) * ratio;
                    float constrainedY = centerY + (event.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    try {
                        joyStickCallback.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                drawJoystick(centerX, centerY);
                try {
                    joyStickCallback.onJoystickMoved(0, 0, getId());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent, int source) throws MqttException;
    }
}
