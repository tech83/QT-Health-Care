package in.quantumtech.qthelpcare.ui.doctor.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.quantumtech.qthelpcare.R;
import in.quantumtech.qthelpcare.ui.model.ClinicTypeModel;

/**
 * Created by quantum on 29/3/17.
 */

public class SpinnerCustomAdapter extends ArrayAdapter<ClinicTypeModel> {
    private List<ClinicTypeModel> objects;
    private LayoutInflater inflater;

    public SpinnerCustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ClinicTypeModel> objects) {
        super(context, resource, objects);
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        if (convertView == null){
            convertView = inflater.inflate(R.layout.spinner_type,parent,false);
        }
        TextView label = (TextView) convertView.findViewById(R.id.text);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(objects.get(position).getTypeName());

        // And finally return your dynamic (or custom) view for each spinner item
        return convertView;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.spinner_type_list,parent,false);
        }
        TextView label = (TextView) convertView.findViewById(R.id.text);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(objects.get(position).getTypeName());

        // And finally return your dynamic (or custom) view for each spinner item
        return convertView;
    }
}
