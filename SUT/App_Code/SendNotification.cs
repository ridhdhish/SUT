using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.Script.Serialization;

/// <summary>
/// Summary description for SendNotification
/// </summary>
public class SendNotification
{
    string applicationID, senderId;
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    public SendNotification(string _applicationId, string _senderId)
    {
        applicationID = _applicationId;
        senderId = _senderId;
    }
    public void SendPushNotification(string applicationID, string senderId, string Message, string Title, List<string> listRegistrationID, int ID, string _type)
    {
        try
        {
            WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");

            tRequest.Method = "post";
            tRequest.ContentType = "application/json";
            var data = new
            {
                registration_ids = listRegistrationID,
                notification = new
                {
                    body = Message,
                    title = Title,
                    sound = "Enabled"
                },
                data = new
                {
                    type = _type,
                    typeid = ID
                }
            };

            var serializer = new JavaScriptSerializer();
            var json = serializer.Serialize(data);
            Byte[] byteArray = Encoding.UTF8.GetBytes(json);
            tRequest.Headers.Add(string.Format("Authorization: key={0}", applicationID));
            tRequest.Headers.Add(string.Format("Sender: id={0}", senderId));
            tRequest.ContentLength = byteArray.Length;

            using (Stream dataStream = tRequest.GetRequestStream())
            {
                dataStream.Write(byteArray, 0, byteArray.Length);
                using (WebResponse tResponse = tRequest.GetResponse())
                {
                    using (Stream dataStreamResponse = tResponse.GetResponseStream())
                    {
                        using (StreamReader tReader = new StreamReader(dataStreamResponse))
                        {
                            String sResponseFromServer = tReader.ReadToEnd();
                            string str = sResponseFromServer;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            string str = ex.Message;
        }
    }

    public void GetNotificationData(string Title, string Message, int Id, string Type)
    {
        List<string> lst = new List<string>();
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT DeviceToken FROM AndroidDeviceTokens", con);
        da.Fill(dt);
        for (int i = 0; i < dt.Rows.Count; i++)
        {
            string str = dt.Rows[i]["DeviceToken"].ToString();
            lst.Add(str);
        }
        SendPushNotification(applicationID, senderId, Message, Title, lst, Id, Type);
    }
}