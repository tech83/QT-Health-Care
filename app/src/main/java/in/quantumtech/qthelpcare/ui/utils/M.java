package in.quantumtech.qthelpcare.ui.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by quantum on 28/3/17.
 */

public class M {
    public static boolean isEmailValid(String email) {
        boolean isEmailEntered = !TextUtils.isEmpty(email.trim());
        return isEmailEntered && isValid(email);
    }
    private static boolean isValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, 2);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static int getDayIndex(String string, List<CharSequence> array){
        int index = -1;
        for (int i = 0; i < array.size(); i++) {
            if (string.equalsIgnoreCase((String) array.get(i))){
                index = i;
            }
        }
        return index;
    }

    public static String formatTime(int hourOfDay, int minute){
        int hour = hourOfDay % 12;
        return String.format("%02d:%02d %s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "AM" : "PM");
    }
    public static String processTime(String string){
        String[] split = string.split(":");
        return formatTime(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
