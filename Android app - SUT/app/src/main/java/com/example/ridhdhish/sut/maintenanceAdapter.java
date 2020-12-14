package com.example.ridhdhish.sut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Ridhdhish on 25-01-2019.
 */

public class maintenanceAdapter extends BaseAdapter {
    ArrayList<maintenanceDataModel> arrayListMaintenanceDataModel = new ArrayList<maintenanceDataModel>();
    LayoutInflater inf;

    public maintenanceAdapter(ArrayList<maintenanceDataModel> dmobj, Context context) {
        this.arrayListMaintenanceDataModel = dmobj;
        this.inf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayListMaintenanceDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMaintenanceDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder v;
        maintenanceDataModel maintenanceDM = (maintenanceDataModel) getItem(position);
        if (convertView == null) {
            convertView = inf.inflate(R.layout.listview_maintenance, null);
            v = new viewHolder();
            v.txtMaintenanceInterfaceAmount = (TextView) convertView.findViewById(R.id.txtMaintenanceInterfaceAmount);
            v.txtMaintenanceInterfaceMonth = (TextView) convertView.findViewById(R.id.txtMaintenanceInterfaceMonth);
            v.txtMaintenanceInterfacePenalty = (TextView) convertView.findViewById(R.id.txtMaintenanceInterfacePenalty);
            v.txtMaintenanceInterfaceDueDate = (TextView) convertView.findViewById(R.id.txtMaintenanceInterfaceDueDate);
            v.txtMaintenanceInterfacePostedDate = (TextView) convertView.findViewById(R.id.txtMaintenanceInterfacePostedDate);
            convertView.setTag(v);
        } else {
            v = (viewHolder) convertView.getTag();
        }
        v.txtMaintenanceInterfaceAmount.setText("Amount:    " + maintenanceDM.getAmount().toString());
        v.txtMaintenanceInterfaceMonth.setText("Month:       " + maintenanceDM.getMonth().toString());
        v.txtMaintenanceInterfacePenalty.setText("Penalty:     " + maintenanceDM.getPenalty().toString());
        v.txtMaintenanceInterfaceDueDate.setText("Due Date: " + maintenanceDM.getDueDate().toString());
        v.txtMaintenanceInterfacePostedDate.setText(maintenanceDM.getPostDate().toString());
        return convertView;
    }

    public class viewHolder {
        TextView txtMaintenanceInterfaceMonth;
        TextView txtMaintenanceInterfaceAmount;
        TextView txtMaintenanceInterfacePenalty;
        TextView txtMaintenanceInterfaceDueDate;
        TextView txtMaintenanceInterfacePostedDate;
    }
}
