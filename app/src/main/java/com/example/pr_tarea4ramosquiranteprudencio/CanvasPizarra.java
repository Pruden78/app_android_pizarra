package com.example.pr_tarea4ramosquiranteprudencio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CanvasPizarra {

    public static class DrawView extends View {
        private Paint pincelRedondo;
        private String tipoPincel = "redondo"; // valores posibles: "redondo", "estrella" y "cara"
        private List<Point> puntosTocados = new ArrayList<>();
        private int miColor;
        private Path currentPath;
        private Bitmap estrellaBitmap, caraBitmap;

        public DrawView(Context context) {
            super(context);
            init();
        }
        //Inflar el bitmap de la forma de estrella
        private Bitmap obtenerEstrellaBitmap() {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.forma_estrella);
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        //Inflar el bitmap de la forma de cara
        private Bitmap obtenerCaraBitmap(){
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.micara);
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        private void init() {
            miColor = Color.BLACK; // Color inicial del pincel
            pincelRedondo = new Paint();
            pincelRedondo.setColor(miColor);
            pincelRedondo.setStyle(Paint.Style.STROKE);
            pincelRedondo.setStrokeWidth(20); // Ancho del trazo
            pincelRedondo.setStrokeJoin(Paint.Join.ROUND); // Bordes redondeados
            pincelRedondo.setStrokeCap(Paint.Cap.ROUND); // Extremos redondeados
            estrellaBitmap = obtenerEstrellaBitmap();
            caraBitmap = obtenerCaraBitmap();
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (Point punto : puntosTocados) {
                if (punto.path != null) {
                    // Dibuja trazos para el pincel redondo
                    pincelRedondo.setColor(punto.color);
                    canvas.drawPath(punto.path, pincelRedondo);
                } else if ("estrella".equals(punto.tipo)){
                    // Dibuja estrellas
                    float x = punto.x - estrellaBitmap.getWidth() / 2;
                    float y = punto.y - estrellaBitmap.getHeight() / 2;
                    canvas.drawBitmap(estrellaBitmap, x, y, null);
                } else if ("cara".equals(punto.tipo)){
                    //Dibuja la cara
                    float x = punto.x - caraBitmap.getWidth() / 2;
                    float y = punto.y - caraBitmap.getHeight() / 2;
                    canvas.drawBitmap(caraBitmap, x, y, null);
                }
            }
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    if ("estrella".equals(tipoPincel) || "cara".equals(tipoPincel)) {
                        puntosTocados.add(new Point((int) x, (int) y, miColor, tipoPincel));
                    } else if ("redondo".equals(tipoPincel)) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            currentPath = new Path();
                            currentPath.moveTo(x, y);
                            puntosTocados.add(new Point(currentPath, miColor));
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            currentPath.lineTo(x, y);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if ("redondo".equals(tipoPincel)) {
                        currentPath = null;
                    }
                    break;
            }
            invalidate();
            return true;
        }

        public void setTipoPincel(String tipo) {
            this.tipoPincel = tipo;
        }

        public void setColorPincel(int color) {
            miColor = color;
        }

        public void borrarDibujo() {
            puntosTocados.clear();
            invalidate();
        }
    }
}

// Clase Point simplificada para manejar solo trazos continuos
class Point {
    int x, y;
    Path path;
    int color;
    String tipo; // Agregar esto

    Point(Path path, int color) {
        this.path = path;
        this.color = color;
        this.tipo = "redondo"; // Tipo por defecto
    }

    Point(int x, int y, int color, String tipo) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.tipo = tipo;
    }
}




