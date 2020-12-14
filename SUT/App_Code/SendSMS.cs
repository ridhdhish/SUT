using System;
using System.IO;
using System.Net;
using System.Text;

/// <summary>
/// Summary description for SendSMS
/// </summary>
public class SendSMS
{
    private static string mTo;
    private static string mMsg;

    public void SendOTP(string sender,string message, string receiver)
    {
        mTo = receiver.Trim();
        mMsg = message.Trim();

        string authKey = "268811AubEgbb415c94d32e";

        //Prepare you post parameters
        StringBuilder sbPostData = new StringBuilder();
        sbPostData.AppendFormat("authkey={0}", authKey);
        sbPostData.AppendFormat("&mobiles={0}", mTo);
        sbPostData.AppendFormat("&message={0}", mMsg);
        sbPostData.AppendFormat("&sender={0}", sender);
        sbPostData.AppendFormat("&route={0}", 4);

        try
        {
            //Call Send SMS API
            string sendSMSUri = "http://api.msg91.com/api/sendhttp.php";
            //Create HTTPWebrequest
            HttpWebRequest httpWReq = (HttpWebRequest)WebRequest.Create(sendSMSUri);
            //Prepare and Add URL Encoded data
            UTF8Encoding encoding = new UTF8Encoding();
            byte[] data = encoding.GetBytes(sbPostData.ToString());
            //Specify post method
            httpWReq.Method = "POST";
            httpWReq.ContentType = "application/x-www-form-urlencoded";
            httpWReq.ContentLength = data.Length;
            using (Stream stream = httpWReq.GetRequestStream())
            {
                stream.Write(data, 0, data.Length);
            }
            //Get the response
            HttpWebResponse response = (HttpWebResponse)httpWReq.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string responseString = reader.ReadToEnd();

            //Close the response
            reader.Close();
            response.Close();
        }
        catch (Exception ex)
        {
            Console.Write(ex);
        }
    }
}