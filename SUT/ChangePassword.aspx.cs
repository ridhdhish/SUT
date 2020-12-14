using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;

public partial class ChangePassword : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void btnChangePassword_Click(object sender, EventArgs e)
    {
        string currentPassword = txtCurrentPassword.Text.ToLower();
        string newPassword = txtNewPassword.Text.ToLower();
        string confirmNewPassword = txtConfirmNewPassword.Text.ToLower();
        if (currentPassword == "" || newPassword == "" || confirmNewPassword == "")
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Fields can't be empty\")", true);
        }
        else
        {
            SqlConnection con = new SqlConnection(strCon);
            SqlDataAdapter da = new SqlDataAdapter("SELECT TOP 1 AdminPassword FROM Admins WHERE AdminId='" + Session["AdminId"] + "'", con);
            DataTable dt = new DataTable();
            da.Fill(dt);
            string passwordInDB = dt.Rows[0]["AdminPassword"].ToString().ToLower();
            if (passwordInDB.Equals(currentPassword))
            {
                if (newPassword.Equals(confirmNewPassword))
                {
                    if (newPassword.Equals(currentPassword))
                    {
                        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Current password and new password can't be same\")", true);
                    }
                    else
                    {
                        con.Open();
                        new SqlCommand("UPDATE Admins SET AdminPassword='" + newPassword + "' WHERE AdminId='" + Session["AdminId"] + "'", con)
                            .ExecuteNonQuery();
                        con.Close();
                        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Password changed successfully\")", true);
                    }
                }
                else
                {
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"New password and confirm password didn't match\")", true);
                }
            }
            else
            {
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Current password is wrong\")", true);
            }
        }
    }
}