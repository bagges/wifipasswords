/**
This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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

/**
 * Created by IntelliJ IDEA.
 * User: markus
 * Date: 02.01.12
 * Time: 22:05
 */
public class WiFiPasswords extends ListActivity
{
    final static String TAG = "WiFiPasswords";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		//create new password reader and load passwords from wpa_supplicant.conf
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
			//Share password
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
			//Used to switch between stars and plaintext password
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
