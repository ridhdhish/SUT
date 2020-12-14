package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Ridhdhish on 05-03-2019.
 */

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.MyViewHolder> {

    private ArrayList<complaintDataModel> list;
    private Context mcontext;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_DELETE = "";
    private static String METHOD_NAME_DELETE = "Users_Complaints_Delete";
    SharedPreferences sharedPreferences;


    public complaintAdapter(ArrayList<complaintDataModel> Data, Context _context) {
        list = Data;
        mcontext = _context;
        SOAP_ACTION_DELETE = NAMESPACE + METHOD_NAME_DELETE;
        url = mcontext.getString(R.string.url);
        NAMESPACE = mcontext.getString(R.string.NAMESPACE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_complaint, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final complaintAdapter.MyViewHolder holder, final int position) {
        holder.txtComplaintTitle.setText(list.get(position).getTxtComplaintTitle());
        holder.txtComplaintMessage.setText(list.get(position).getTxtComplaintMessage());
        holder.txtComplaintDate.setText(list.get(position).getTxtComplaintDate());
        holder.txtComplaintId.setText(list.get(position).getTxtComplaintId());

        holder.txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popup = new PopupMenu(mcontext, holder.txtOption);

                popup.inflate(R.menu.complaint_options);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.compaintMenuEdit:
                                //Toast.makeText(mcontext, holder.txtComplaintTitle.getText().toString(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(mcontext, CompaintUpdate.class);
                                i.putExtra("complaintId", holder.txtComplaintId.getText().toString());
                                i.putExtra("title", holder.txtComplaintTitle.getText().toString());
                                i.putExtra("message", holder.txtComplaintMessage.getText().toString());
                                mcontext.startActivity(i);
                                break;
                            case R.id.compaintMenuDelete:
                                new AsyncComplaintDelete().execute(holder.txtComplaintId.getText().toString());
                                list.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });

                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtComplaintTitle;
        TextView txtComplaintMessage;
        TextView txtComplaintDate;
        TextView txtComplaintId;
        ImageView txtOption;
        android.support.v7.widget.CardView cardviewHomeAnnouncement;
        char imp;

        public MyViewHolder(View v) {
            super(v);
            txtComplaintTitle = (TextView) v.findViewById(R.id.txtComplaintTitle);
            txtComplaintMessage = (TextView) v.findViewById(R.id.txtComplaintMessage);
            txtComplaintDate = (TextView) v.findViewById(R.id.txtComplaintDate);
            txtComplaintId = (TextView) v.findViewById(R.id.txtComplaintId);
            txtOption = (ImageView) v.findViewById(R.id.txtOption);
            cardviewHomeAnnouncement = (android.support.v7.widget.CardView) v.findViewById(R.id.layoutComplaintInterface);
        }
    }

    public class AsyncComplaintDelete extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(mcontext);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String[] params) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_DELETE);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("complaintId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_DELETE, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject diffgram = (SoapObject) response.getProperty("diffgram");

                if (diffgram.getPropertyCount() > 0) {
                    documentElement = (SoapObject) diffgram.getProperty("DocumentElement");
                }
            } catch (Exception E) {
                Log.e("Error-->> ", E.toString());
            }

            return documentElement;
        }

        @Override
        protected void onPostExecute(SoapObject resultXML) {
            super.onPostExecute(resultXML);
            sharedPreferences = mcontext.getSharedPreferences("Pref", Context.MODE_PRIVATE);
            //new complaint.AsyncComplaintSelect().execute(sharedPreferences.getString("Userid", null).toString());
            dialog.dismiss();
        }
    }

//    ArrayList<complaintDataModel> arrayListComplaintDataModel = new ArrayList<complaintDataModel>();
//    LayoutInflater inf;
//    Context mcontext;
//
//    complaintAdapter(ArrayList<complaintDataModel> arrayListComplaintDataModel, Context context) {
//        this.arrayListComplaintDataModel = arrayListComplaintDataModel;
//        this.inf = LayoutInflater.from(context);
//        this.mcontext = context;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayListComplaintDataModel.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayListComplaintDataModel.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final viewHolder vh;
//        final complaintDataModel complaintDM = (complaintDataModel) getItem(position);
//
//        if (convertView == null) {
//            convertView = inf.inflate(R.layout.listview_complaint, null);
//            vh = new viewHolder();
//            vh.txtComplaintDate=(TextView) convertView.findViewById(R.id.txtComplaintDate);
//            vh.txtComplaintMessage=(TextView) convertView.findViewById(R.id.txtComplaintMessage);
//            vh.txtComplaintTitle=(TextView) convertView.findViewById(R.id.txtComplaintTitle);
//
//            convertView.setTag(vh);
//        } else {
//            vh = (viewHolder) convertView.getTag();
//        }
//        vh.txtComplaintMessage.setText(complaintDM.getTxtComplaintMessage().toString());
//        vh.txtComplaintDate.setText(complaintDM.getTxtComplaintDate().toString());
//        vh.txtComplaintTitle.setText(complaintDM.getTxtComplaintTitle().toString());
//
//        return convertView;
//
//    }
//
//    public class viewHolder {
//        TextView txtComplaintTitle;
//        TextView txtComplaintMessage;
//        TextView txtComplaintDate;
//    }
}
