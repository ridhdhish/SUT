using System;
using System.Collections.Specialized;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Web;
using System.Web.UI;

public partial class VerifyRegistration : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    string otp = "";

    protected void Page_Load(object sender, EventArgs e)
    {

        if (!IsPostBack)
        {
            Response.ClearHeaders();
            Response.AddHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
            Response.AddHeader("Pragma", "no-cache");

            if (Request.Form["error"] != null)
            {
                Page.ClientScript.RegisterStartupScript(this.GetType(), "Error", "showError('" + Request.Form["error"].ToString() + "')", true);
                // ScriptManager.RegisterClientScriptBlock(this, this.GetType(), Request.Form["error"].ToString(), "alert('" + Request.Form["error"].ToString() + "')", true);
            }
            if (Session["OTPVerification"] != null)
            {
                displayOTPSection();
            }
            else
            {
                displayLoginSection();
            }
        }
    }

    protected void displayLoginSection()
    {
        divVerify.Visible = false;
        btnVerify.Visible = false;
        lblEmailSent.Visible = false;
        lnklblResendOTP.Visible = false;
    }

    protected void btnSend_Click(object sender, EventArgs e)
    {
        lblEmailSent.Visible = true;
        lblEmailSent.Text = "";
        Session["AdminEmailId"] = txtEmail.Text.ToLower();
        Session["AdminPassword"] = txtPassword.Text.ToLower();
        if (txtEmail.Text != String.Empty &&  txtPassword.Text != String.Empty) {

            otp = GenerateOTP();
            Session["GeneratedOTP"] = otp;
            string query = "UPDATE Admins SET AdminOTP='" + Session["GeneratedOTP"].ToString() + "' WHERE AdminEmailid='" + txtEmail.Text + "' AND AdminPassword='" + txtPassword.Text + "'";
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand(query, con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            query = "SELECT AdminEmailId FROM admins WHERE AdminEmailId='" + txtEmail.Text + "'";
            SqlDataAdapter da = new SqlDataAdapter(query, con);
            DataTable dt = new DataTable();
            da.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                query = "SELECT AdminName, AdminEmailId, AdminPassword FROM admins WHERE AdminEmailId='" + txtEmail.Text + "' AND AdminPassword='" + txtPassword.Text + "'";
                con = new SqlConnection(strCon);
                da = new SqlDataAdapter(query, con);
                dt = new DataTable();
                da.Fill(dt);
                if (dt.Rows.Count > 0)
                {
                    Session["OTPVerification"] = "SendClicked";
                    SendEmail(Session["AdminEmailId"].ToString());
                }
                else
                {
                    lblEmailSent.Text = "Wrong email or password";
                    txtEmail.Text = "";
                    txtPassword.Text = "";
                }
                displayOTPSection();
            }
            else
            {
                lblEmailSent.CssClass = "text-danger";
                lblEmailSent.Text = "No account is associated with " + txtEmail.Text + "<br> <a href='SignUp.aspx'>Register</a>";
                txtEmail.Text = "";
                txtPassword.Text = "";
            }
        }
        else{
            divEmailId.Attributes.Add("class", "input-group hatch");
            divPassword.Attributes.Add("class", "input-group hatch");
            lblEmailSent.CssClass = "text-danger";
            lblEmailSent.Text = "EmailId and password can't be empty";
        }
        
    }

    protected void btnVerify_Click(object sender, EventArgs e)
    {
        lblEmailSent.Text = "";
        if (txtOTP.Text != String.Empty) {
            string query = "SELECT AdminId, AdminOTP FROM Admins WHERE AdminEmailId='" + Session["AdminEmailId"] + "' AND AdminPassword='" + Session["AdminPassword"] + "'";
            SqlConnection con = new SqlConnection(strCon);
            SqlDataAdapter da = new SqlDataAdapter(query, con);
            DataTable dt = new DataTable();
            da.Fill(dt);
            if (dt.Rows.Count > 0)
            {
                if (dt.Rows[0]["AdminOTP"].ToString().Equals(txtOTP.Text))
                {
                    //Update the OTP everytime when Admin generates/resends new OTP
                    query = "UPDATE Admins SET AdminVerfied='1' WHERE AdminEmailId='" + Session["AdminEmailId"] + "' AND AdminPassword='" + Session["AdminPassword"] + "' AND AdminOTP='" + txtOTP.Text + "'";
                    SqlCommand cmd = new SqlCommand(query, con);
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                    NameValueCollection ParametersToPOST = new NameValueCollection();
                    ParametersToPOST.Add("AdminVerified", "true");
                    RedirectWithData(ParametersToPOST, "SignIn.aspx");
                    Response.Redirect("SignIn.aspx");
                }
                else
                {
                    lblEmailSent.CssClass = "text-danger";
                    lblEmailSent.Text = "Incorrect OTP";
                }
            }
            else
            {
                lblEmailSent.Text = "Something went wrong!";
            }
        }
        else
        {
            lblEmailSent.Visible = false;
            divVerify.Attributes.Add("class", "input-group hatch");
            lblEmailSent.CssClass = "text-danger";
            lblEmailSent.Text = "Enter OTP";
        }
    }

    private void SendEmail(string EmailId)
    {
        otp = Session["GeneratedOTP"].ToString();
        using (MailMessage mm = new MailMessage("sutofficialindia@gmail.com", EmailId))
        {
            string htmlFile = File.ReadAllText(HttpContext.Current.Server.MapPath("~/emailOTPTemplate.html"));
            htmlFile = htmlFile.Replace("$['txtHeader']", "Welcome! Secretary use this OTP to login. This is just a time process");
            htmlFile = htmlFile.Replace("$['txtOTP']", otp);
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

    private string GenerateOTP()
    {
        string alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string numbers = "1234567890";

        string characters = numbers;

        characters += alphabets + numbers;
        string otp = string.Empty;
        for (int i = 0; i < 6; i++)
        {
            string character = string.Empty;
            do
            {
                int index = new Random().Next(0, characters.Length);
                character = characters.ToCharArray()[index].ToString();
            } while (otp.IndexOf(character) != -1);
            otp += character;
        }
        return otp;
    }

    void displayOTPSection()
    {
        divSend.Visible = false;
        btnSend.Visible = false;
        divVerify.Visible = true;
        btnVerify.Visible = true;
        lnklblResendOTP.Visible = true;
        txtEmail.Text = "";
        txtPassword.Text = "";
    }

    protected void lnklblResendOTP_Click(object sender, EventArgs e)
    {
        displayOTPSection();
        SendEmail(Session["AdminEmailId"].ToString());
    }

    protected void txtOTP_TextChanged(object sender, EventArgs e)
    {
        //if () { }
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
}


/* References 
 * 
 * 
 * GenerateOTP = https://www.aspsnippets.com/Articles/Generate-Unique-Random-OTP-One-Time-Password-in-ASPNet-using-C-and-VBNet.aspx 
 * 
 * 
 * Email Template = {
                        Author: W3layouts
                        Author URL: http://w3layouts.com
                        License: Creative Commons Attribution 3.0 Unported
                        License URL: http://creativecommons.org/licenses/by/3.0/
                    }
 *
 * 
 */
