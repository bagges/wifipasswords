package de.markusbackes.wifipassword;

import android.app.ListActivity;
import android.content.Intent;
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
                new String[] { "ssid","psk" },
                new int[] { R.id.text1, R.id.text2 } );
        setListAdapter(pws);
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ((HashMap<String, String>)adapterView.getItemAtPosition(i)).get("psk"));
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ((HashMap<String, String>)adapterView.getItemAtPosition(i)).get("ssid"));
                startActivity(Intent.createChooser(sharingIntent, "Teile Schlüssel"));
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.text2);
                if(tv.getText().toString().matches("\\*{20}")){
                    tv.setText(((HashMap<String, String>)adapterView.getItemAtPosition(i)).get("psk"));
                } else {
                    tv.setText("********************");
                }
            }
        });
    }


}
