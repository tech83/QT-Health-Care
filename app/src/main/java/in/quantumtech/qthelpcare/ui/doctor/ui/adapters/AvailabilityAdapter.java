package in.quantumtech.qthelpcare.ui.doctor.ui.adapters;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.ui.database.UserContract;
import in.quantumtech.qthelpcare.ui.doctor.ui.activities.Register2Activity;
import in.quantumtech.qthelpcare.ui.utils.M;


/**
 * Created by quantum on 29/3/17.
 */

public class AvailabilityAdapter extends ResourceCursorAdapter{
    private int[] day_start_h = new int[5];
    private int[] day_start_m = new int[5];
    private int[] day_end_h = new int[5];
    private int[] day_end_m = new int[5];
    private int[] eve_start_h = new int[5];
    private int[] eve_start_m = new int[5];
    private int[] eve_end_h = new int[5];
    private int[] eve_end_m = new int[5];

    public AvailabilityAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, 0);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        ArrayAdapter<CharSequence> daysAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,Register2Activity.daysArray);
        daysAdapter.setDropDownViewResource(R.layout.spinner_layout_list);
        viewHolder.spinner_day_s.setAdapter(daysAdapter);
        viewHolder.spinner_day_e.setAdapter(daysAdapter);
        getInitTime(context,cursor,viewHolder);
        viewHolder.time_morning_start.setText(M.processTime(cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM))));
        viewHolder.time_morning_end.setText(M.processTime(cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO))));
        viewHolder.time_evening_start.setText(M.processTime(cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM))));
        viewHolder.time_evening_end.setText(M.processTime(cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO))));

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Warning");
                alert.setMessage("Do you want to delete this timing?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                        context.getContentResolver().delete(UserContract.DoctorAvailableTable.CONTENT_URI, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                }
        });

        viewHolder.time_morning_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = hourOfDay + ":" + minute;
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM,formattedTime);
                        context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    }
                },day_start_h[0] < 1 ? 10 : day_start_h[0], day_start_m[0],false).show();
            }
        });

        viewHolder.time_morning_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = hourOfDay + ":" + minute;
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO,formattedTime);
                        context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    }
                },day_end_h[0] < 1 ? 14 : day_end_h[0], day_end_m[0],false).show();

            }
        });

        viewHolder.time_evening_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = hourOfDay + ":" + minute;
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM,formattedTime);
                        context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    }
                },eve_start_h[0] < 1 ? 16 : eve_start_h[0], eve_start_m[0],false).show();
            }
        });

        viewHolder.time_evening_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = hourOfDay + ":" + minute;
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO,formattedTime);
                        context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    }
                },eve_end_h[0] < 1 ? 19 : eve_end_h[0], eve_end_m[0],false).show();
            }
        });

        viewHolder.spinner_day_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idx) {
                if (viewHolder.spinner1 != position){
                    String day1 = (String) parent.getSelectedItem();
                    int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_FROM,day1);
                    context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    viewHolder.spinner1 = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.spinner_day_e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idx) {
                if (viewHolder.spinner2 != position){
                    String day1 = (String) parent.getSelectedItem();
                    int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorAvailableTable._ID));
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_TO,day1);
                    context.getContentResolver().update(UserContract.DoctorAvailableTable.CONTENT_URI,contentValues, UserContract.DoctorAvailableTable._ID + "=?", new String[]{String.valueOf(id)});
                    viewHolder.spinner2 = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getInitTime(Context context, Cursor cursor, ViewHolder viewHolder) {
        String string = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_FROM));
        String[] split = string.split(":");
        day_start_h[0] = Integer.parseInt(split[0]);
        day_start_m[0] = Integer.parseInt(split[1]);

        String m_to = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_MORNING_TIME_TO));
        String[] m_tos = m_to.split(":");
        day_end_h[0] = Integer.parseInt(m_tos[0]);
        day_end_m[0] = Integer.parseInt(m_tos[1]);

        String e_f = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_FROM));
        String[] e_fs = e_f.split(":");
        eve_start_h[0] = Integer.parseInt(e_fs[0]);
        eve_start_m[0] = Integer.parseInt(e_fs[1]);

        String e_to = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_EVENING_TIME_TO));
        String[] e_tos = e_to.split(":");
        eve_end_h[0] = Integer.parseInt(e_tos[0]);
        eve_end_m[0] = Integer.parseInt(e_tos[1]);

        String day_s = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_FROM));
        String day_e = cursor.getString(cursor.getColumnIndex(UserContract.DoctorAvailableTable.COLUMN_NAME_DAY_TO));
        int day1 = M.getDayIndex(day_s, Register2Activity.daysArray);
        int day2 = M.getDayIndex(day_e, Register2Activity.daysArray);
        viewHolder.spinner_day_s.setSelection(day1);
        viewHolder.spinner_day_e.setSelection(day2);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.spinner_day_e = (Spinner) view.findViewById(R.id.spinner_day_e);
        viewHolder.spinner_day_s = (Spinner) view.findViewById(R.id.spinner_day_s);
        viewHolder.time_morning_start = (TextView) view.findViewById(R.id.time_morning_start);
        viewHolder.time_morning_end = (TextView) view.findViewById(R.id.time_morning_end);
        viewHolder.time_evening_start = (TextView) view.findViewById(R.id.time_evening_start);
        viewHolder.time_evening_end = (TextView) view.findViewById(R.id.time_evening_end);
        viewHolder.btnDelete = (ImageView) view.findViewById(R.id.btn_delete);
        view.setTag(viewHolder);
        return view;
    }
    

    private static class ViewHolder{
        int spinner1 = -1 ,spinner2 = -1;
        Spinner spinner_day_s,spinner_day_e;
        TextView time_morning_start,time_morning_end,time_evening_start,time_evening_end;
        ImageView btnDelete;
    }
}
