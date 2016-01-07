package edu.thomas.draglettertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class Galgeleg_act extends AppCompatActivity {

    HorizontalScrollView hsview = new HorizontalScrollView(this);
    Galgelogik gl = new Galgelogik();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GalgeView gv = new GalgeView(this);
        setContentView(gv);


    }

    class Letter {
        RectF r = new RectF();
        String str;

        public Letter(String s, RectF r) {
            this.r = r;
            str = s;
        }
    }

    class GalgeView extends View {

        PointF finger = new PointF();
        ArrayList<Letter> letters = new ArrayList<>();
        Letter chosenLetter = null;
        Paint textType = new Paint();
        Paint lineType = new Paint();

        private void init() {
            textType.setColor(Color.BLUE);
            textType.setTextSize(40);
            textType.setAntiAlias(true);
            lineType.setColor(Color.BLACK);
            lineType.setStyle(Paint.Style.STROKE);

            TextView atxt = new TextView();
            RectF aRect;
            atxt.getHitRect;

            letters.add(new Letter("A", new RectF(100, 20, 140, 60)));

            /*
            for (int i = 'A'; i <= 'Z'; i++) {
                letters.add(new Letter(""+i, ));
            }
            */

        }

        public GalgeView(Context c) {
            super(c);
            init();
        }

        public GalgeView(Context c, AttributeSet at) {
            super(c, at);
            init();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float screenScale = getWidth()/480f;
            canvas.scale(screenScale, screenScale);

            for(Letter l : letters) {
                if(l == chosenLetter) continue;
                drawLetter(canvas, l.r.left, l.r.top, l);
            }

            canvas.drawCircle(finger.x, finger.y, 10, textType);
            if(chosenLetter != null) drawLetter(canvas, finger.x, finger.y, chosenLetter);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {

            float screenScale = getWidth()/480f;
            finger.x = e.getX() / screenScale;
            finger.y = e.getY() / screenScale;

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                for (Letter l : letters) {
                    if (l.r.contains(finger.x, finger.y)) {
                        chosenLetter = l;
                        Log.d("Galge", "chosen letter is " + l);
                        break;
                    }
                }
            }

            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                if (chosenLetter != null) {
                    Log.d("Galge", "Finger coordinate = " + finger);
                }
            }

            if (e.getAction() == MotionEvent.ACTION_UP) {
                if(chosenLetter != null) {
                    chosenLetter.r.offsetTo(finger.x, finger.y);
                    Log.d("Galge", "chosenLetter.r = " + chosenLetter.r);
                }
                chosenLetter = null;
            }
            invalidate();
            return true;
        }

        private void drawLetter(Canvas c, float x, float y, Letter l) {
            RectF r = new RectF(l.r);
            r.offsetTo(x, y);
            c.drawRect(r, lineType);
            c.drawText(l.str, r.left, r.bottom, textType);
        }
    }
}
