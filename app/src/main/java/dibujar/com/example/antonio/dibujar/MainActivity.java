package dibujar.com.example.antonio.dibujar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---- parámetros para las vistas
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //---- creamos el layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //---- creamos el botón de borrar
        Button clear = new Button(this);
        clear.setText(R.string.borrar);       //getResources().getText(R.string.borrar));
        clear.setLayoutParams(params);
        // ----añadimos el botón al layout
        layout.addView(clear);
        //---- creamos el objeto vista
        final miVista miVista = new miVista(this);
        //---- añadimos  la vista al layout
        layout.addView(miVista);
        //---- creamos los parámetros del layout para el layout
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //---- añadimos el layout y sus parámetros al contenedor para visualizarlos
        this.addContentView(layout, layoutParam);
        /* añado el evento onClick del boton para que borre el canvas*/
        clear.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               miVista.setAccion("clear");
               miVista.clear();
            }
        });
    }

    class miVista extends View {
        float x = 50;
        float y = 50;
        String accion = "accion";
        Path path = new Path();
        //Canvas canvas;

        public miVista(Context context) { super(context); }

        public void setAccion(String accion){ this.accion = accion; }

        public String getAccion() { return accion; }

        /**
         * Método para borrar el contenido del path trazado en el canvas
         */
        public void clear() {
            path.reset();   // borra el contenido de lo que se haya trazado en el path
            invalidate();   // se necesita porque si no no borra hasta que se vuelva a dibujar
        }

        public void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6);
            paint.setColor(Color.RED);
            //int ancho = canvas.getWidth();
            if (accion.equals("down")) {
                path.moveTo(x, y);
            }
            if (accion.equals("move")) {
                path.lineTo(x, y);
            }
            canvas.drawPath(path, paint);
        }

        public boolean onTouchEvent(MotionEvent e) {
            x = e.getX();
            y = e.getY();

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                accion = "down";
            }
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                accion = "move";
            }
            invalidate();
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
