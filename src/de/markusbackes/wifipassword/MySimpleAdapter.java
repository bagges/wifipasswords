package de.markusbackes.wifipassword;

import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: markus
 * Date: 02.01.12
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class MySimpleAdapter extends SimpleAdapter {
    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public void setViewText(TextView v, String text) {
        if (v.getId() == R.id.text1) {
            super.setViewText(v, text);
        } else {
            super.setViewText(v, "********************");
        }
    }
}
