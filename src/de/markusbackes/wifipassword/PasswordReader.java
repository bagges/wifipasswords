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
            p2 = Runtime.getRuntime().exec("su");
            DataOutputStream d=new DataOutputStream(p2.getOutputStream());
            d.writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            d.writeBytes("exit\n");
            d.flush();
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
