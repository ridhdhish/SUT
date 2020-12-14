using System;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Web.UI;

public partial class guestUsers : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            getGuestUsers();
        }
    }
    void getGuestUsers()
    {
        try
        {
            SqlConnection con = new SqlConnection(strCon);
            SqlDataAdapter da = new SqlDataAdapter("SELECT GUId, GUName, GUMobile, GUEmailid FROM GuestUser WHERE GUApartmentId='" + Session["ApartmentId"].ToString() + "'", con);
            DataTable dt = new DataTable();
            da.Fill(dt);
            rptViewGuestUsers.DataSource = dt;
            rptViewGuestUsers.DataBind();
        }
        catch(Exception e)
        {
        }
    }
    protected void rptViewGuestUsers_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        int id = int.Parse(e.CommandArgument.ToString());
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("DELETE FROM GuestUser WHERE GUId='" + id + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        getGuestUsers();
    }
}