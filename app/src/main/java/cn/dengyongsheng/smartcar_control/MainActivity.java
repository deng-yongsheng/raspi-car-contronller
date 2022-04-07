package cn.dengyongsheng.smartcar_control;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.dengyongsheng.smartcar_control.Instructions.Move;
import cn.dengyongsheng.smartcar_control.Instructions.Stop;

import java.io.IOException;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    TextView textState, textDirection, textSpeed, textCarIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textState = findViewById(R.id.textConnectionState);
        this.textDirection = findViewById(R.id.textDirection);
        this.textSpeed = findViewById(R.id.textSpeed);
        this.textCarIp = findViewById(R.id.carIp);
        // 输出局域网广播地址
        try {
            textCarIp.setText(ControlUtils.getBroadcast());
        } catch (SocketException e) {
            // 输出局域网地址获取失败信息
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        GameRockerView rockerView = (GameRockerView) findViewById(R.id.RockerView);
        rockerView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        String speed = String.format("%.2f", rockerView.strength * 100);
                        textSpeed.setText(speed + "%");
                        String deg = String.format("%.2f", Math.toDegrees(rockerView.direction));
                        textDirection.setText(deg + "°");
                        // 发送指令
                        Move move = new Move(rockerView.strength, rockerView.direction);
                        try {
                            ControlUtils.putInstrution(move);
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        textSpeed.setText("停止");
                        textDirection.setText("停止");
                        // 发送指令
                        Stop stop = new Stop();
                        try {
                            ControlUtils.putInstrution(stop);
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return false;
            }
        });
    }


}