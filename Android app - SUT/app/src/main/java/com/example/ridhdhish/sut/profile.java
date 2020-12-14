package com.example.ridhdhish.sut;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static maes.tech.intentanim.CustomIntent.customType;

public class profile extends AppCompatActivity {

    Toolbar toolbar;
    TextView lblProfileName, lblProfileEmailId, lblProfileFlatNumber, lblProfileMobile, lblProfileGender;
    private static String METHOD_NAME = "Users_Profile_Select";
    private static String METHOD_NAME_Update = "Users_Profile_Update";
    private static String SOAP_ACTION_Update = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String url = "";
    private static String METHOD_NAME_UploadImage = "uploadimages";
    private static String SOAP_ACTION_UplaodImage = "";
    private static String imgUrl = "";
    private static String METHOD_NAME_Delete = "Users_ProfilePicture_Delete";
    private static String SOAP_ACTION_Delete = "";
    Button btnedit, btnconfirm;
    EditText edtProfileName, edtProfileEmailId, edtProfileMobile;
    RadioGroup rdoGender;
    ImageView profile_image, imgProfileEditImage;
    RadioButton rdbProfileGenderMale, rdbProfileGenderFemale;
    String USER_ID = "", encoded = "";
    private Uri mCropImageUri;

    ImageView imgProfileDeleteImage;

    TextView txtImage;

    RelativeLayout rlUserPicture;
    LinearLayout llUserProfile;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        USER_ID = sharedPreferences.getString("Userid", null);

        // Toast.makeText(this, "" + USER_ID, Toast.LENGTH_SHORT).show();

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SOAP_ACTION_UplaodImage = NAMESPACE + METHOD_NAME_UploadImage;
        SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        SOAP_ACTION_Delete = NAMESPACE + METHOD_NAME_Delete;
        url = getResources().getString(R.string.url);
        imgUrl = getResources().getString(R.string.WEBSERVER_IP) + "siteimages/profilePictures/";

        txtImage = (TextView) findViewById(R.id.txtImage);
        rlUserPicture = (RelativeLayout) findViewById(R.id.rlUserPicture);
        llUserProfile = (LinearLayout) findViewById(R.id.llUserProfile);

        toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");
        profile_image = (ImageView) findViewById(R.id.profile_image);
        lblProfileEmailId = (TextView) findViewById(R.id.lblProfileEmailId);
        lblProfileName = (TextView) findViewById(R.id.lblProfileName);
        lblProfileFlatNumber = (TextView) findViewById(R.id.lblProfileFlatNumber);
        lblProfileMobile = (TextView) findViewById(R.id.lblProfileMobile);
        lblProfileGender = (TextView) findViewById(R.id.lblProfileGender);
        btnedit = (Button) findViewById(R.id.btnedit);
        btnconfirm = (Button) findViewById(R.id.btnconfirm);
        edtProfileName = (EditText) findViewById(R.id.edtProfileName);
        edtProfileEmailId = (EditText) findViewById(R.id.edtProfileEmailId);
        edtProfileMobile = (EditText) findViewById(R.id.edtProfileMobile);
        rdoGender = (RadioGroup) findViewById(R.id.rdoGender);
        rdbProfileGenderMale = (RadioButton) findViewById(R.id.rdbProfileGenderMale);
        rdbProfileGenderFemale = (RadioButton) findViewById(R.id.rdbProfileGenderFemale);
        imgProfileEditImage = (ImageView) findViewById(R.id.imgProfileEditImage);
        imgProfileDeleteImage = (ImageView) findViewById(R.id.imgProfileDeleteImage);

        //lblProfileGender.setVisibility(View.GONE);
        //lblProfileEmailId.setWidth(50);

        new AsyncProfile().execute(USER_ID);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnedit.setVisibility(View.GONE);
                btnconfirm.setVisibility(View.VISIBLE);

                lblProfileName.setVisibility(View.GONE);
                edtProfileName.setVisibility(View.VISIBLE);

                lblProfileEmailId.setVisibility(View.GONE);
                edtProfileEmailId.setVisibility(View.VISIBLE);

                lblProfileMobile.setVisibility(View.GONE);
                edtProfileMobile.setVisibility(View.VISIBLE);

                lblProfileGender.setVisibility(View.GONE);
                rdoGender.setVisibility(View.VISIBLE);

                imgProfileEditImage.setVisibility(View.VISIBLE);
                imgProfileDeleteImage.setVisibility(View.VISIBLE);

            }
        });

        imgProfileEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(profile.this);
            }
        });

        imgProfileDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncProfileDelete().execute(USER_ID);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, mobile, gender = "", profile;

                if (rdbProfileGenderFemale.isChecked()) {
                    gender = rdbProfileGenderFemale.getText().toString();
                } else if (rdbProfileGenderMale.isChecked()) {
                    gender = rdbProfileGenderMale.getText().toString();
                }
                name = edtProfileName.getText().toString();
                email = edtProfileEmailId.getText().toString();
                mobile = edtProfileMobile.getText().toString();

                try {
                    new AsyncProfileUpdate().execute(USER_ID, name, email, mobile, gender);
                    //Thread.sleep(1000);
                    String IsImageUpload = "true";
                    if (encoded.equals("") == true) {
                        IsImageUpload = "false";
                    }
                    new AsyncProfilePictureUpdate().execute(encoded, USER_ID, IsImageUpload);
                    new AsyncProfile().execute(USER_ID);
                } catch (Exception e) {
                }

                btnedit.setVisibility(View.VISIBLE);
                btnconfirm.setVisibility(View.GONE);

                lblProfileName.setVisibility(View.VISIBLE);
                edtProfileName.setVisibility(View.GONE);

                lblProfileEmailId.setVisibility(View.VISIBLE);
                edtProfileEmailId.setVisibility(View.GONE);

                lblProfileMobile.setVisibility(View.VISIBLE);
                edtProfileMobile.setVisibility(View.GONE);

                lblProfileGender.setVisibility(View.VISIBLE);
                rdoGender.setVisibility(View.GONE);

                imgProfileEditImage.setVisibility(View.GONE);
                imgProfileDeleteImage.setVisibility(View.GONE);

                sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Emailid", email);
                editor.commit();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                ActivityCompat.requestPermissions(profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri u = result.getUri();
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                profile_image.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                //txtBase64String.setText(encoded);

                // new AsyncProfilePictureUpdate().execute(encoded, USER_ID);

                //((ImageView) findViewById(R.id.imgViewNew)).setImageURI(result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAllowRotation(false)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(profile.this, setting.class);
                customType(profile.this, "left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class AsyncProfileUpdate extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(profile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            llUserProfile.setVisibility(View.GONE);
            rlUserPicture.setVisibility(View.GONE);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Update);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("name");
            pi.setValue(params[1]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("emailId");
            pi.setValue(params[2]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("mobile");
            pi.setValue(params[3]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("gender");
            pi.setValue(params[4]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_Update, envelope);

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
            dialog.dismiss();
            llUserProfile.setVisibility(View.VISIBLE);
            rlUserPicture.setVisibility(View.VISIBLE);
            afterProfileUpdate(resultXML);
        }
    }

    public void afterProfileUpdate(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(profile.this, "Profile Updated", Toast.LENGTH_LONG).show();
    }

    public class AsyncProfilePictureUpdate extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(profile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            llUserProfile.setVisibility(View.GONE);
            rlUserPicture.setVisibility(View.GONE);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_UploadImage);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("uploadImage");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("userId");
            pi.setValue(params[1]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("isImageUpload");
            pi.setValue(params[2]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_UplaodImage, envelope);

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
            dialog.dismiss();
            llUserProfile.setVisibility(View.VISIBLE);
            rlUserPicture.setVisibility(View.VISIBLE);
            afterProfileUpdate(resultXML);
        }
    }

    public class AsyncProfile extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(profile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            llUserProfile.setVisibility(View.GONE);
            rlUserPicture.setVisibility(View.GONE);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
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
                androidHttpTransport.call(SOAP_ACTION, envelope);

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
            afterAsyncProfile(resultXML);
            dialog.dismiss();
            llUserProfile.setVisibility(View.VISIBLE);
            rlUserPicture.setVisibility(View.VISIBLE);
        }
    }

    public void afterAsyncProfile(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "Users";
        String Emailid = "UserEmailid";
        String Name = "UserName";
        String Mobile = "UserMobile";
        String Gender = "UserGender";
        final String profilePicture = "UserProfilePicture";
        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            table = (SoapObject) resultXML.getProperty(KEY_ITEM);
            String e = table.getProperty(Emailid).toString();
            String n = table.getProperty(Name).toString();
            String m = table.getProperty(Mobile).toString();
            String g = table.getProperty(Gender).toString();
            final String p = table.getProperty(profilePicture).toString();
            String url = imgUrl + p;
            txtImage.setText(p);
          //  Toast.makeText(profile.this, "" + url, Toast.LENGTH_LONG).show();

            lblProfileEmailId.setText(e);
            lblProfileName.setText(n);
            lblProfileMobile.setText(m);
            lblProfileGender.setText(g);


            edtProfileName.setText(n);
            edtProfileEmailId.setText(e);
            edtProfileMobile.setText(m);
            g = g.toLowerCase();
            if (g.equals("male")) {
                rdbProfileGenderMale.setChecked(true);
            }
            if (g.equals("female")) {
                rdbProfileGenderFemale.setChecked(true);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap b = getBitmapFromURL(imgUrl + p);
                    profile_image.post(new Runnable() {
                        @Override
                        public void run() {
                            profile_image.setImageBitmap(b);
                            profile_image.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }).start();
        }

    }

    public class AsyncProfileDelete extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(profile.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            /*llUserProfile.setVisibility(View.GONE);
            rlUserPicture.setVisibility(View.GONE);*/
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Delete);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
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
                androidHttpTransport.call(SOAP_ACTION_Delete, envelope);

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
            afterDeleteProfilePicture();
            dialog.dismiss();
        }
    }

    public void afterDeleteProfilePicture() {
        encoded = "";
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(profile.this, setting.class);
        customType(profile.this, "left-to-right");
        startActivity(i);
        finish();
    }
}