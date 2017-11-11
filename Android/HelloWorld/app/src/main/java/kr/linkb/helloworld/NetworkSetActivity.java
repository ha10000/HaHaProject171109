package kr.linkb.helloworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_set);
       }
    public void ButtonNetworkSave(View view){
        Log.i("result","ha________________________________");
        String ip[] ={"192", "168", "1", "1"};
        String port = "80";
        String db_ip[] = {"192", "168", "1", "1"};
        String db_port = "27017";

        EditText editText_ip1 = (EditText)findViewById(R.id.ip1); editText_ip1.setText(ip[0]);
        EditText editText_ip2 = (EditText)findViewById(R.id.ip2); editText_ip2.setText(ip[1]);
        EditText editText_ip3 = (EditText)findViewById(R.id.ip3); editText_ip3.setText(ip[2]);
        EditText editText_ip4 = (EditText)findViewById(R.id.ip4); editText_ip4.setText(ip[3]);
        EditText editText_port = (EditText)findViewById(R.id.port); editText_port.setText(port);

        EditText editText_db_ip1 = (EditText)findViewById(R.id.db_ip1); editText_db_ip1.setText(db_ip[0]);
        EditText editText_db_ip2 = (EditText)findViewById(R.id.db_ip2); editText_db_ip2.setText(db_ip[1]);
        EditText editText_db_ip3 = (EditText)findViewById(R.id.db_ip3); editText_db_ip3.setText(db_ip[2]);
        EditText editText_db_ip4 = (EditText)findViewById(R.id.db_ip4); editText_db_ip4.setText(db_ip[3]);
        EditText editText_db_port = (EditText)findViewById(R.id.db_port); editText_db_port.setText(db_port);
        Log.d(this.getClass().getName(), "result------ButtonNetworkSave--------");
        Toast.makeText(NetworkSetActivity.this, "ButtonNetworkSave", Toast.LENGTH_LONG).show();
        new SaveNetworkInfo().execute(editText_ip1.getText().toString());
    }



    class SaveNetworkInfo extends AsyncTask<String,String,String>{

        ProgressDialog dialog = new ProgressDialog(NetworkSetActivity.this);
        @Override
        protected String doInBackground(String... params) {
//            String clientId = "xLRbl1XQVibmNOpsWj2q";//애플리케이션 클라이언트 아이디값";
//            String clientSecret = "6IGNOqbp_L";//애플리케이션 클라이언트 시크릿값";
/*
            String ipAddress = params[0]; // 연결하고자 하는 서버 IP";
            String portNo = params[1]; // 연결하고자 하는 서버의 포트번호
            String MongoIP = params[2]; // 연결하고자 하는 몽고DB IP";
            String MongoPortNo = params[3]; // 연결하고자 하는 서버의 몽고DB트번호
 */
            String ipAddress = "192.1.1.1"; // 연결하고자 하는 서버 IP";
            String portNo = "80"; // 연결하고자 하는 서버의 포트번호
            String MongoIP = "192.1.1.1"; // 연결하고자 하는 몽고DB IP";
            String MongoPortNo = "27017"; // 연결하고자 하는 서버의 몽고DB트번호

            StringBuffer response = new StringBuffer();
            try {
                String text = URLEncoder.encode(params[0], "UTF-8");
                String apiURL = "http://192.168.0.24:3000/networks/"+params[0]+"/"+params[1]+params[2]+"/"+params[3];
//                String apiURL = "https://openapi.naver.com/v1/search/blog.json?query="+ text; // json 결과
                //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-ipAddress", ipAddress);
                con.setRequestProperty("X-Naver-portNo", portNo);
                con.setRequestProperty("X-Naver-MongoIP", MongoIP);
                con.setRequestProperty("X-Naver-MongoPortNo", MongoPortNo);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
//                System.out.println(response.toString());
            } catch (Exception e) {
//                System.out.println(e);
                e.printStackTrace();
            }
            return response.toString();
        }
        // <ctrl + o> 로 onPreExecute, onPostExecute 불러옴...
        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            dialog.setMessage("Network 정보 보관중...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            dialog.dismiss();
            Log.i("json",s);
            Toast.makeText(NetworkSetActivity.this, s, Toast.LENGTH_LONG).show();
            try{
/*
                // JSON 문자열 --> JSON객체로 변환
                JSONObject json = new JSONObject(s);
                // JSON객체에서  items 키값의 배열을 추출 --> 여기에만 해당
                JSONArray items = json.getJSONArray("items");

                itemList.clear(); // 동적 배열 초기화
//                Toast.makeText(NaverOpenAPIActivity.this, items.length() + "", Toast.LENGTH_LONG).show();
                for( int i = 0; i < items.length();i++){ // items 배열 안의 객체 정보 개별 추출
                    JSONObject obj = items.getJSONObject(i);
                    String title = obj.getString("title");
                    String link = obj.getString("link");
                    String description = obj.getString("description");
                    String bloggername = obj.getString("bloggername");
                    String bloggerlink = obj.getString("bloggerlink");
                    String postdate = obj.getString("postdate");
                    itemList.add(new NaverOpenAPIActivity.Item(title, link, description, bloggername, bloggerlink, postdate));
//                    Log.i("title", title);
                }
                NaverOpenAPIActivity.BlogAdapter adapter = new NaverOpenAPIActivity.BlogAdapter(NaverOpenAPIActivity.this);
                ListView listView = (ListView)findViewById(R.id.listview);
                listView.setAdapter(adapter);
//                int display = json.getInt("display");

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(itemList.get(position).link));
                        startActivity(intent);
                    }
                });
*/
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
