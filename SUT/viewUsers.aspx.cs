using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class bs4admin_light_EditUser : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        getData();
    }
    public void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT UserId, UserName, UserMobile, UserFlatNumber, UserProfilePicture FROM Users WHERE UserApartmentId='" + Session["ApartmentId"] + "' ORDER BY UserFlatNumber", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        repViewUser.DataSource = dt;
        repViewUser.DataBind();
    }
    protected void rep_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        if (e.CommandName == "lnkbtnEdit")
        {
            int id = int.Parse(e.CommandArgument.ToString());
            Response.Redirect("AddUser.aspx?id=" + id);
        }
        if (e.CommandName == "lnkbtnDelete")
        {
            int id = int.Parse(e.CommandArgument.ToString());
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("DELETE FROM Users WHERE UserId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            getData();
        }
    }

    protected void btnAddUser_Click(object sender, EventArgs e)
    {
        Response.Redirect("AddUser.aspx");

    }
}