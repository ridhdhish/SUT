package com.example.ridhdhish.sut;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ridhdhish on 01-02-2019.
 */

public class meetingAdapter extends BaseAdapter {

    ArrayList<meetingDataModel> arrayListMeetingDataModel = new ArrayList<>();
    LayoutInflater inf;

    public meetingAdapter(ArrayList<meetingDataModel> dmobj, Context context) {
        this.arrayListMeetingDataModel = dmobj;
        this.inf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayListMeetingDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMeetingDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder v;
        meetingDataModel meetingDM = (meetingDataModel) getItem(position);

        if (convertView == null) {
            convertView = inf.inflate(R.layout.list_meeting, null);
            v = new viewHolder();

            v.txtMeetingInterfaceMessage = (TextView) convertView.findViewById(R.id.txtMeetingInterfaceMessage);
            v.txtMeetingInterfaceDate = (TextView) convertView.findViewById(R.id.txtMeetingInterfaceDate);
            v.txtMeetingInterfaceTime = (TextView) convertView.findViewById(R.id.txtMeetingInterfaceTime);

            convertView.setTag(v);
        } else {
            v = (viewHolder) convertView.getTag();
        }

        v.txtMeetingInterfaceMessage.setText(meetingDM.getTxtMeetingInterfaceMessage().toString());
        v.txtMeetingInterfaceDate.setText(meetingDM.getTxtMeetingInterfaceDate().toString());
        v.txtMeetingInterfaceTime.setText(meetingDM.getTxtMeetingInterfaceTime().toString());

        return convertView;
    }

    public class viewHolder {
        TextView txtMeetingInterfaceMessage;
        TextView txtMeetingInterfaceDate;
        TextView txtMeetingInterfaceTime;
    }
}
