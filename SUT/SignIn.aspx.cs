using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;

public partial class SignIn : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if(Request.Form["AdminVerified"] == "true")
        {
            Page.ClientScript.RegisterStartupScript(this.GetType(), "Error", "showError('Account Verified')", true);

        }
        if (Request.Form["page"] != null)
        {
            hdpage.Value = Request.Form["page"].ToString();
        }
    }
    protected void BtnSignIn_Click(object sender, EventArgs e)
    {
        String query = "SELECT AdminId, AdminEmailid, AdminApartmentId FROM Admins WHERE AdminEmailid='" + txtEmail.Text + "' and AdminPassword='" + txtPassword.Text + "'";
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter(query, con);
        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+query+"')", true);
        DataTable dt = new DataTable();
        da.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            Session["AdminId"] = dt.Rows[0]["AdminId"].ToString();
            Session["ApartmentId"] = dt.Rows[0]["AdminApartmentId"].ToString();
            //string str = Request.Form["page"].ToString();
            if (hdpage.Value != null && hdpage.Value.ToString() != "")
            {
                //Response.Redirect(Request.QueryString["page"].ToString());
                Response.Redirect(hdpage.Value.ToString());
            }
            else
            {
                Response.Redirect("index.aspx");
            }
        }
        else
        {
            lblInvalid.Text = "Invalid emailid or password<br/>";
            txtPassword.Text = "";
        }
    }
}