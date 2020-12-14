using System;
using System.Collections.Specialized;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Data;
using System.Data.SqlClient;

public partial class SiteMaster : System.Web.UI.MasterPage
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    string profilePicturePath = "~/siteimages/profilePictures/";
    string OTP, verified;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            getData();
            if (Session["AdminId"] == null)
            {
                SignOut();   
            }
            else
            { 
                Response.AddHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
                Response.AddHeader("Pragma", "no-cache");
                NameValueCollection ParametersToPOST = new NameValueCollection();
                ParametersToPOST.Add("error", "Not Verified");
                if (verified.Equals("0"))
                {
                    Session.Abandon();
                    RedirectWithData(ParametersToPOST, "VerifyRegistration.aspx");
                }
            }
        }
    }
    
    void getData()
    {
        string query = "SELECT AdminName, AdminProfilePicture, AdminVerfied FROM Admins WHERE AdminId='" + Session["AdminId"] + "'";
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter(query, con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        if (dt != null)
        {
            if (dt.Rows.Count > 0)
            {
                lblAdminName.Text = dt.Rows[0]["AdminName"].ToString();
                verified = dt.Rows[0]["AdminVerfied"].ToString();
                //lblEmailAddress.Text = txtEmailAddress.Text = dt.Rows[0]["AdminEmailid"].ToString();
                //lblPhoneNumber.Text = txtPhoneNumber.Text = dt.Rows[0]["AdminMobileNumber"].ToString();
                //lblSocietyAddress.Text = txtSocietyAddress.Text = dt.Rows[0]["AdminSocietyAddress"].ToString();
                //lblSocietyName.Text = txtSocietyName.Text = dt.Rows[0]["AdminSocietyName"].ToString();
                //lblSocietyPincode.Text = txtSocietyPincode.Text = dt.Rows[0]["AdminSocietyPincode"].ToString();

                //string gen = dt.Rows[0]["AdminGender"].ToString();
                //if (gen == "male")
                //{
                //    rdbMale.Checked = true;
                //    lblGender.Text = "Male";
                //}
                //else if (gen == "female")
                //{
                //    rdbFemale.Checked = true;
                //    lblGender.Text = "Female";
                //}
                string profilePictureImage = dt.Rows[0]["AdminProfilePicture"].ToString();
                if (profilePictureImage == "")
                {
                    profilePicturePath += "defaultUser.png";
                }
                else
                {
                    profilePicturePath += profilePictureImage.ToString();
                }
                imgProfilePicture.ImageUrl = profilePicturePath;
            }
        }
    }


    public static void RedirectWithData(NameValueCollection data, string url)
    {
        HttpResponse response = HttpContext.Current.Response;
        response.Clear();

        StringBuilder s = new StringBuilder();
        s.Append("<html>");
        s.AppendFormat("<body onload='document.forms[\"form\"].submit()'>");
        s.AppendFormat("<form name='form' action='{0}' method='post'>", url);
        foreach (string key in data)
        {
            s.AppendFormat("<input type='hidden' name='{0}' value='{1}' />", key, data[key]);
        }
        s.Append("</form></body></html>");
        response.Write(s.ToString());
        response.End();
    }


    protected void timer_Tick(object sender, EventArgs e)
    {
        if (Session["AdminId"] == null)
        {
            SignOut();
        }
    }

    private void SignOut()
    {
        NameValueCollection ParametersToPOST = new NameValueCollection();
        ParametersToPOST.Add("page", Request.Url.AbsolutePath.ToString());
        RedirectWithData(ParametersToPOST, "SignIn.aspx");
    }
}
