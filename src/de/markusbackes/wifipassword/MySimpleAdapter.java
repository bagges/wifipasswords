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
