using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
public partial class SendNoti : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    
    protected void Page_Load(object sender, EventArgs e)
    {
    }

    protected void btnSend_Click(object sender, EventArgs e)
    {
        setRegIDs("Hi.. It me from ASP.NET", "ASP.NET notification");
    }
    public void setRegIDs(string Message, string Title)
    {
        string applicationID = "AIzaSyCl2OLf_lI7lhSUZb9UI62KCRjXIwtNW7U";
        string senderId = "423159204587";

        List<string> lst = new List<string>();
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT DeviceToken FROM AndroidDeviceTokens", con);
        da.Fill(dt);
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            string str = dt.Rows[i]["DeviceToken"].ToString();
            Response.Write(str);
            lst.Add(str);
        }
      //  objSN.SendPushNotification(applicationID, senderId, Message, Title, lst, 1011);

    }

}