using System;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Net;
using System.Net.Mail;
using System.Web;

public partial class ForgotPassword : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    string otp;

    protected void Page_Load(object sender, EventArgs e)
    {
        lblError.Visible = false;
        displayResetPasswordControls(false);
        displayVerifyOTPControls(false);
        lblSuccess.Visible = false;
        lnkbtnSignIn.Visible = false;
    }
    private void SendEmail(string EmailId)
    {
        otp = Session["GeneratedOTP"].ToString();
        using (MailMessage mm = new MailMessage("sutofficialindia@gmail.com", EmailId))
        {
            string htmlFile = File.ReadAllText(HttpContext.Current.Server.MapPath("~/emailOTPTemplate.html"));
            htmlFile = htmlFile.Replace("$['txtHeader']", "Forgot Password? Use the following OTP to confirm your email address and reset your password");
            htmlFile = htmlFile.Replace("$['txtOTP']", otp);
            htmlFile = htmlFile.Replace("$['txtFooter']", "We've received your forgot password request. To reset your password use above OTP code to website. If you didn't request forgot password, ignore this email.");
            mm.Subject = "Email Varification";
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

    private void displayResetPasswordControls(Boolean visibility)
    {
        btnConfirmPassword.Visible = visibility;
        txtPassword.Visible = visibility;
        txtConfirmPassword.Visible = visibility;
        divResetPassword.Visible = visibility;
    }

    private void displayEmailIdControls(Boolean visibility)
    {
        btnSendOTP.Visible = visibility;
        divEmailId.Visible = visibility;
    }

    private void displayVerifyOTPControls(Boolean visibility)
    {
        divVerify.Visible = visibility;
        btnVerify.Visible = visibility;
        lnklblResendOTP.Visible = visibility;
    }

    protected void lnklblResendOTP_Click(object sender, EventArgs e)
    {
            displayEmailIdControls(false);
            displayVerifyOTPControls(true);
            displayResetPasswordControls(false);
            SendEmail(Session["AdminEmailId"].ToString());
    }

    protected void btnConfirmPassword_Click(object sender, EventArgs e)
    {
        if (txtPassword.Text == txtConfirmPassword.Text)
        {
            Console.Write(Session["AdminEmailId"].ToString());

            string query = "UPDATE Admins SET AdminPassword='" + txtPassword.Text + "' WHERE AdminEmailId='" + Session["AdminEmailId"] + "'";
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand(query, con);
            con.Open();
            if(cmd.ExecuteNonQuery() > 0)
            {
                Console.Write(cmd.ExecuteNonQuery());

                lblSuccess.Text = "Password changed";
                lblSuccess.Visible = true;
                lnkbtnSignIn.Visible = true;
                divFooter.Visible = false;
            }
            else
            {
                lblError.Text = "Something went wrong";
                lblError.Visible = true;
            }
            con.Close();
        }
        else
        {
            displayResetPasswordControls(true);
            lblError.Text = "Confirm password didn't match with password";
            lblError.Visible = true;
            //passwords didn't match
        }
    }

    protected void btnVerify_Click(object sender, EventArgs e)
    {
        if (txtOTP.Text == Session["GeneratedOTP"].ToString())
        {
            displayVerifyOTPControls(false);
            displayResetPasswordControls(true);
        }
        else
        {
            displayVerifyOTPControls(true);
            lblError.Text = "OTP didn't match";
            lblError.Visible = true;
            // wrong OTP
        }
    }

    protected void btnSendOTP_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT AdminEmailId FROM Admins",con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        if ((dt.Rows.Count > 0))
        {
            displayEmailIdControls(false);
            displayVerifyOTPControls(true);
            otp = GenerateOTP();
            Session["GeneratedOTP"] = otp;
            Session["AdminEmailId"] = txtEmail.Text;
            SendEmail(txtEmail.Text);
            lblEmailSent.Text = "An email is sent to " + txtEmail.Text;
        }
        else
        {
            lblError.Text = "No account associated with " + txtEmail.Text;
            lblError.Visible = true;
            //No account associated with email 
        }
    }

    protected void lnkbtnSignIn_Click(object sender, EventArgs e)
    {
        Response.Redirect("SignIn.aspx");
    }
}