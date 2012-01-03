package de.markusbackes.wifipassword;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class WiFiPasswords extends ListActivity
{
    final static String TAG = "WiFiPasswords";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

            List<HashMap<String, String>> passwords = new PasswordReader().readPasswords();

        MySimpleAdapter pws = new MySimpleAdapter(
                this,
                passwords,
                R.layout.list_item,
                new String[] { "line1","line2" },
                new int[] { R.id.text1, R.id.text2 } );
        setListAdapter(pws);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.text2);
                if(tv.getText().toString().matches("\\*{20}")){
                    tv.setText(((HashMap<String, String>)adapterView.getItemAtPosition(i)).get("line2"));
                } else {
                    tv.setText("********************");
                }
            }
        });
    }


}
