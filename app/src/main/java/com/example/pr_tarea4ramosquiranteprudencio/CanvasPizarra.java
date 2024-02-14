package com.example.pr_tarea4ramosquiranteprudencio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CanvasPizarra {


    public static class DrawView extends View {
        private Paint pincelRedondo;
        private List<Point> puntosTocados = new ArrayList<>();

        public DrawView(Context context) {
            super(context);
            init();
        }

        private void init() {
            pincelRedondo = new Paint();
            pincelRedondo.setColor(Color.MAGENTA);
            pincelRedondo.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (Point punto : puntosTocados) {
                float cx = punto.x;   // 50 es la mitad del ancho del cuadrado
                float cy = punto.y;    // 50 es la mitad del alto del cuadrado
                float radius = 30;
                canvas.drawCircle(cx, cy, radius, pincelRedondo);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Dependiendo del tipo de evento
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    // Agrega el punto actual a la lista
                    puntosTocados.add(new Point((int) event.getX(), (int) event.getY()));
                    invalidate(); // Indica que la vista debe ser redibujada
                    break;
            }
            return true;
        }

        public void borrarDibujo(){
            puntosTocados.clear(); // Limpia la lista de puntos
            invalidate(); // Redibuja la vista, que ahora estará vacía
        }

    }
}

// Clase Point fuera de DrawView
class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

