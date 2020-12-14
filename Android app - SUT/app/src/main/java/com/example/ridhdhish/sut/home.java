package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class home extends Fragment {
    ScrollView scrollView;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_Announcement = "";
    private static String METHOD_NAME_Announcement = "Users_Announcement_Select";
    private static String SOAP_ACTION_Meeting = "";
    private static String METHOD_NAME_Meeting = "Users_Meetings_Select";
    private static String imgUrl = "";
    String img = "siteimages/announcements/";
    SharedPreferences sharedPreferences;
    String apartmentId, adminId;
    View v;
    TextView txtAnnouncementView, txtHomeMeetingView;
    ProgressDialog dialog;

    //SwipeRefreshLayout HomesMeetingwiperefresh, HomesAnnouncementwiperefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        v = inflater.inflate(R.layout.activity_home, container, false);

        dialog = new ProgressDialog(getActivity());

        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_Announcement = NAMESPACE + METHOD_NAME_Announcement;
        SOAP_ACTION_Meeting = NAMESPACE + METHOD_NAME_Meeting;
        url = getString(R.string.url);

        txtHomeMeetingView = (TextView) v.findViewById(R.id.txtHomeMeetingView);
        txtAnnouncementView = (TextView) v.findViewById(R.id.txtAnnouncementView);

        scrollView = (ScrollView) v.findViewById(R.id.scrViewHome);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                if (scrollView.getScrollY() > 200) {//
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                } else//
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            }
        });
            sharedPreferences = getActivity().getSharedPreferences("Pref", Context.MODE_PRIVATE);
            adminId = sharedPreferences.getString("adminId", null).toString();
            apartmentId = sharedPreferences.getString("apartmentId", null).toString();

//        HomesMeetingwiperefresh=(SwipeRefreshLayout) v.findViewById(R.id.HomeMeetingswiperefresh);
//        HomesMeetingwiperefresh.setColorSchemeResources(R.color.colorPrimary);
//        HomesMeetingwiperefresh.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        loadRecyclerViewData();
//                        new AsyncAnnouncement().execute(adminId);
//                        new AsyncMeeting().execute(apartmentId);
//                        HomesMeetingwiperefresh.setRefreshing(false);
//                    }
//                }
//        );
//
//        HomesAnnouncementwiperefresh=(SwipeRefreshLayout) v.findViewById(R.id.HomesAnnouncementwiperefresh);
//        HomesAnnouncementwiperefresh.setColorSchemeResources(R.color.colorPrimary);
//        HomesAnnouncementwiperefresh.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        new AsyncAnnouncement().execute(adminId);
//                        new AsyncMeeting().execute(apartmentId);
//                        HomesAnnouncementwiperefresh.setRefreshing(false);
//                    }
//                }
//        );

            new AsyncAnnouncement().execute(adminId);
            new AsyncMeeting().execute(apartmentId);
        //  Toast.makeText(getActivity(), ""+ adminId, Toast.LENGTH_SHORT).show();
        //Recycler ended

        txtHomeMeetingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), meeting.class);
                startActivity(i);
            }
        });

        txtAnnouncementView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
            Intent i = new Intent(getActivity(), announcement.class);
            startActivity(i);
        }
                                               }
        );

        return v;
    }

    public ArrayList<HomeMeetingDataModel> listarrayMeeting(SoapObject resultXML) {

        ArrayList<HomeMeetingDataModel> dmlist = new ArrayList<>();
        HomeMeetingDataModel meetingDM;

        String KET_ITEM = "Meetings";
        String message = "MeetingMessage";
        String address = "MeetingAddress";
        String important="MeetingImportant";

        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            int counts = 4 < resultXML.getPropertyCount() ? 4 : resultXML.getPropertyCount();
            for (int i = 0; i < counts; i++) {
                table = (SoapObject) resultXML.getProperty(i);
                meetingDM = new HomeMeetingDataModel();
                //meetingDM.setTxtHomeMeetingText("Test data " + i);
                //dmlist.add(meetingDM);

                if (table.getProperty(message).toString().length() > 120) {
                    meetingDM.setTxtHomeMeetingText(table.getProperty(message).toString().substring(0, 120) + "...");
                } else {
                    meetingDM.setTxtHomeMeetingText(table.getProperty(message).toString());
                }

                meetingDM.setImp(table.getProperty(important).toString());
                dmlist.add(meetingDM);
            }
        }
        else
        {

        }
        return dmlist;
    }

    public ArrayList<HomeAnnouncementDataModel> listarrayAnnouncement(SoapObject resultXML) {
        ArrayList<HomeAnnouncementDataModel> data = new ArrayList<HomeAnnouncementDataModel>();
        HomeAnnouncementDataModel announcementDM;

        String KEY_ITEM = "Announcements";
        String title = "AnnouncementTitle";
        String message = "AnnouncementMessage";
        String time = "AnnouncementTime";
        String date = "AnnouncementDate";
        String image = "AnnouncementImage";
        String important = "AnnouncementImportant";

        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            int counts = 4 < resultXML.getPropertyCount() ? 4 : resultXML.getPropertyCount();
            for (int i = 0; i < counts; i++) {
                table = (SoapObject) resultXML.getProperty(i);

                announcementDM = new HomeAnnouncementDataModel();
//                announcementDM.setImgAnnouncement(imgUrl + table.getProperty(image).toString());
//                announcementDM.setTxtAnnouncementMessage(table.getProperty(message).toString());
//                announcementDM.setTxtAnnouncementDateTime(table.getProperty(date).toString() + " " + table.getProperty(time).toString());
//                announcementDM.setTxtAnnouncementTitle(table.getProperty(title).toString());
                /*
                if(imp) then do something
                * */
                if (table.getProperty(message).toString().length() > 120) {
                    announcementDM.setTxtHomeAnnouncementText(table.getProperty(message).toString().substring(0, 120) + "...");
                } else {
                    announcementDM.setTxtHomeAnnouncementText(table.getProperty(message).toString());
                }
                announcementDM.setImp(table.getProperty(important).toString());

                data.add(announcementDM);
            }
        }
        return data;
    }

    public class AsyncAnnouncement extends AsyncTask<String, Void, SoapObject> {

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Announcement);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("adminId");
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
                androidHttpTransport.call(SOAP_ACTION_Announcement, envelope);

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
            afterAsyncAnnouncement(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncAnnouncement(SoapObject resultXML) {
        RecyclerView listViewHomeAnnouncement = (RecyclerView) v.findViewById(R.id.listViewHomeAnnouncement);
        listViewHomeAnnouncement.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerAnnouncement = new LinearLayoutManager(getActivity());
        MyLayoutManagerAnnouncement.setOrientation(LinearLayoutManager.HORIZONTAL);
        ArrayList<HomeAnnouncementDataModel> announcementDM = listarrayAnnouncement(resultXML);
        listViewHomeAnnouncement.setAdapter(new HomeAnnouncementAdapter(announcementDM, getActivity()));
        listViewHomeAnnouncement.setLayoutManager(MyLayoutManagerAnnouncement);
    }

    public class AsyncMeeting extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected SoapObject doInBackground(String[] params) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Meeting);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("apartmentId");
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
                androidHttpTransport.call(SOAP_ACTION_Meeting, envelope);

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
            afterAsyncMeeting(resultXML);
        }
    }

    public void afterAsyncMeeting(SoapObject resultXML) {
        RecyclerView listViewHomeMeeting = (RecyclerView) v.findViewById(R.id.listViewHomeMeeting);
        listViewHomeMeeting.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerMeeting = new LinearLayoutManager(getActivity());
        MyLayoutManagerMeeting.setOrientation(LinearLayoutManager.HORIZONTAL);
        ArrayList<HomeMeetingDataModel> meetingDM = listarrayMeeting(resultXML);
        listViewHomeMeeting.setAdapter(new HomeMeetingAdapter(meetingDM, getActivity()));
        listViewHomeMeeting.setLayoutManager(MyLayoutManagerMeeting);
    }

}
