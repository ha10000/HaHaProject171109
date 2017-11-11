package kr.linkb.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InitScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);


    }

    public void clickLoginButton(View view) {

        Toast.makeText(InitScreenActivity.this, "clickLoginButton", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(InitScreenActivity.this,
                SensorListActivity.class);
        intent.putExtra("device_name", "device_name");
        intent.putExtra("sensor_name", "sensor_name");
        startActivity(intent);
    }
//
    public void clickSettingButton(View view) {
        Log.i("result", "--------clickSettingButton");
    Intent intent = new Intent(
            InitScreenActivity.this,
            NetworkSetActivity.class);
        startActivity(intent);
    }

}
