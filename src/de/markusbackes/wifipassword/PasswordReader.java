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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: markus backes
 * Date: 02.01.12
 * Time: 21:35
 */
public class PasswordReader {
    public  List<HashMap<String, String>> readPasswords() {
        Process p2= null;
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
			//obtain root rights
            p2 = Runtime.getRuntime().exec("su");
            DataOutputStream d=new DataOutputStream(p2.getOutputStream());
			//Hack to read the contents of wpa_supplicant.conf
            d.writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            d.writeBytes("exit\n");
            d.flush();
			
			//parse the wpa_supplicant.conf
            DataInputStream is = new DataInputStream(p2.getInputStream());
            String line = is.readLine();
            while(line != null) {
                if(line.contains("network")){
                    line = is.readLine();
                    map = new HashMap<String, String>();
                    while(line != null && !line.contains("}")){
                        line = line.replace("\n", "").replace("\r", "").replace("\t", "").replace("\"", "");
                        if(line.matches(".+=.+")){
                            map.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1, line.length()));
                        }
                        line = is.readLine();
                    }
                    list.add(map);
                }
                line = is.readLine();
            } // end parse
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
