package cn.dengyongsheng.smartcar_control;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView textState, textDirection, textSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textState = findViewById(R.id.textConnectionState);
        this.textDirection = findViewById(R.id.textDirection);
        this.textSpeed = findViewById(R.id.textSpeed);
        GameRockerView rockerView = (GameRockerView) findViewById(R.id.RockerView);
        rockerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (rockerView.strength > 0) {
                    String speed = String.format("%.2f", rockerView.strength * 100);
                    textSpeed.setText(speed + "%");
                } else {
                    textSpeed.setText("停止");
                }
                if (rockerView.direction != 0) {
                    String deg = String.format("%.2f", Math.toDegrees(rockerView.direction));
                    textDirection.setText(deg + "°");
                } else {
                    textDirection.setText("停止");
                }
                return false;
            }
        });
    }


}