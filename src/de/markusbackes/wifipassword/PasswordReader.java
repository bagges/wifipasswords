package de.markusbackes.wifipassword;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: markus
 * Date: 02.01.12
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
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
            String ssid = "";
            String key = "";
            while(line != null) {
                if(line.contains("network")){
                    while(line != null && !line.contains("}")){
                        if(line.contains("ssid=\"")){
                            ssid = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                        }
                        if(line.contains("psk")){
                            key = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                        }
                        line = is.readLine();
                    }
                    map = new HashMap<String, String>();
                    map.put("line1", ssid);
                    map.put("line2", key);
                    list.add(map);
                }
                line = is.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }
}
