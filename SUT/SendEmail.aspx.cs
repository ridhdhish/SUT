using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class SendEmail : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if(Request.QueryString["emailTo"] != null)
        {
            SendEmailWithMessage(Request.QueryString["emailTo"]);
        }
    }
    private void SendEmailWithMessage(string EmailId)
    {
        //otp = Session["GeneratedOTP"].ToString();
        using (MailMessage mm = new MailMessage("sutofficialindia@gmail.com", EmailId))
        {
            string htmlFile = File.ReadAllText(HttpContext.Current.Server.MapPath("~/emailOTPTemplate.html"));
            htmlFile = htmlFile.Replace("$['txtHeader']", "Welcome! Secretary use this OTP to login. This is just a time process");
            htmlFile = htmlFile.Replace("$['txtOTP']", "TEst");
            htmlFile = htmlFile.Replace(" $['txtFooter']", "You received this email because you requested to log in to SUT account.Don't share this code with anyone, otherwise he/she shall be to get access to your account.");
            mm.Subject = "Email Verification";
            mm.Body = htmlFile;
            mm.IsBodyHtml = true;
            SmtpClient smtp = new SmtpClient();
            smtp.Host = "smtp.gmail.com";
            smtp.EnableSsl = true;
            NetworkCredential NetworkCred = new NetworkCredential("sutofficialindia@gmail.com", "sutadmin@123");
            smtp.UseDefaultCredentials = true;
            smtp.Credentials = NetworkCred;
            smtp.Port = 587;
            smtp.Send(mm);
        }

    }
}