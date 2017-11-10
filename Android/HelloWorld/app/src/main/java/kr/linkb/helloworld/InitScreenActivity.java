package kr.linkb.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class InitScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);
    }

    public void clickLoginButton(View view) {
        Intent intent = new Intent(InitScreenActivity.this,
                SensorListActivity.class);
//        intent.putExtra("user_id", String.valueOf(1));
        intent.putExtra("user_name", "user_name");
        intent.putExtra("device_name", "device_name");
        intent.putExtra("sensor_name", "sensor_name");
        startActivity(intent);

    }
//
//    public void clickSettingButton(View view) {
//    }

}
