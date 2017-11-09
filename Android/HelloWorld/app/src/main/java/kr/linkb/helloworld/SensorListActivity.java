package kr.linkb.helloworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SensorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        final String sensors[] = {"sen1", "sen2", "sen3", "sen4", "sen5"};
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(
                SensorListActivity.this, android.R.layout.simple_list_item_1, sensors);
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SensorListActivity.this, position+"",
                        Toast.LENGTH_LONG).show();
                new GetSensorLists().execute("arduino", sensors[position]);
                Log.d(this.getClass().getName(), "haha---------before 0");
            }
        });
//        new GetSensorLists().execute(sensors[0]);;
        new GetSensorLists().execute(sensors[0]);;
    }


    class SensorItem {
//        int temp, humidity; String created_at;
//        SensorItem(int temp, int humidity, String created_at) {
//            this.temp = temp; this.humidity = humidity; this.created_at = created_at;
//        }
        int id, user_id; String mac_address; String created_at;
        SensorItem(int id, int user_id, String mac_address, String created_at) {
            this.id = id; this.user_id = user_id; this.mac_address = mac_address; this.created_at = created_at ;
        }
    }
    ArrayList<SensorItem> sensor_items = new ArrayList<SensorItem>();
    class SensorItemAdapter extends ArrayAdapter {
        public SensorItemAdapter(Context context) {
            super(context, R.layout.list_sensor_item, sensor_items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_sensor_item, null);
            }
            TextView tempText = (TextView)view.findViewById(R.id.temp);
            TextView humidityText = (TextView)view.findViewById(R.id.humidity);
            TextView createdAtText = (TextView)view.findViewById(R.id.created_at);
            tempText.setText(sensor_items.get(position).id+"");
            humidityText.setText(sensor_items.get(position).user_id+"");
            createdAtText.setText(sensor_items.get(position).mac_address+"");
            createdAtText.setText(sensor_items.get(position).created_at);
            return view;
        }
    }


    public void clickSensorAddButton(View view) {
//        new SensorAdd().execute("sensorName","SensorDevice");
    }
    class GetSensorLists extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(SensorListActivity.this);

        @Override
        protected String doInBackground(String... params) {
            StringBuffer response = new StringBuffer();
            try {
                Log.d(this.getClass().getName(), "haha---------0");
//                String urlString = "http://192.168.0.27:3000/devices/"+params[0]+"/"+params[1];
                String urlString = "http://192.168.0.27:3000/devices/";

//                String urlString = "http://192.168.0.35:3000/devices/"+params[0]+"/"+params[1];
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
//                con.setRequestMethod("POST");
                con.setRequestMethod("GET");
                //con.setDoInput(true); con.setDoOutput(true);
                int responseCode = con.getResponseCode();
                BufferedReader br;

                if(responseCode==200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.d(this.getClass().getName(), "haha-----getInputStream----");

                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    Log.d(this.getClass().getName(), "haha-----getErrorStream----");
                }
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                    params[0] = inputLine; //haha
                    Log.d(this.getClass().getName(), inputLine);
                }
                br.close();
            } catch (Exception e) { e.printStackTrace(); }

            return response.toString();
        }
        @Override
        protected void onPreExecute() {
            dialog.setMessage("센서정보 수신 중...");
            dialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            try {
                JSONArray array = new JSONArray(s);//JSON 문자열 -> JSON 객체로 변환
                sensor_items.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
//                    if (obj.getString("id").equals("dht11")) {
//                        sensor_items.add(new SensorItem(obj.getInt("user_id"), obj.getInt("mac_address"),
//                                obj.getString("created_at")));
//                    } else {
//                        sensor_items.add(new SensorItem(obj.getInt("user_id"), obj.getInt("analog"),
//                                obj.getString("created_at")));
//                    }
                    sensor_items.add(new SensorItem(obj.getInt("id"), obj.getInt("usr_id"),
                            obj.getString("mac_address"),obj.getString("created_at")));
                }
                SensorItemAdapter adapter = new SensorItemAdapter(SensorListActivity.this);
                ListView listView = (ListView)findViewById(R.id.listview);
                listView.setAdapter(adapter);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}