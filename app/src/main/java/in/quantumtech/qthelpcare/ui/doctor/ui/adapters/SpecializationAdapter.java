package in.quantumtech.qthelpcare.ui.doctor.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.ui.database.UserContract;


/**
 * Created by quantum on 29/3/17.
 */

public class SpecializationAdapter extends ResourceCursorAdapter{

    public SpecializationAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, 0);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Warning");
                alert.setMessage("Do you want to delete this Specialization?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = cursor.getInt(cursor.getColumnIndex(UserContract.DoctorSpecializationTable._ID));
                        context.getContentResolver().delete(UserContract.DoctorSpecializationTable.CONTENT_URI, UserContract.DoctorSpecializationTable._ID + "=?", new String[]{String.valueOf(id)});
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

        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(UserContract.DoctorSpecializationTable.COLUMN_NAME_SPECIALIZATION_TITLE)));
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView) view.findViewById(R.id.title);
        viewHolder.btnDelete = (ImageView) view.findViewById(R.id.btn_remove);
        view.setTag(viewHolder);
        return view;
    }
    

    private static class ViewHolder{

        TextView title;
        ImageView btnDelete;
    }
}
