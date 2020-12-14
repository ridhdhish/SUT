using System;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;

public partial class Maintanance : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        getData();
    }
    public void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        //SqlDataAdapter da = new SqlDataAdapter("SELECT MaintenanceId, CONVERT(date, MaintenancePostDate, 105) AS MaintenancePostDate, MaintenanceAmount, MaintenancePenaltyAmount, MaintenanceDueDate FROM Maintenances WHERE MaintenanceAdminId='" + Session["AdminId"] + "' ORDER BY MaintenancePostDate", con);
        SqlDataAdapter da = new SqlDataAdapter("SELECT MaintenanceId, MaintenancePostDate, MaintenanceAmount, MaintenancePenaltyAmount, MaintenanceDueDate FROM Maintenances WHERE MaintenanceAdminId='" + Session["AdminId"] + "' ORDER BY MaintenancePostDate", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        repViewMaintenance.DataSource = dt;
        repViewMaintenance.DataBind();
        repViewMaintenance.DataSource = dt;
        repViewMaintenance.DataBind();
    }
    protected void rep_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        if (e.CommandName == "lnkbtnEdit")
        {
            int id = int.Parse(e.CommandArgument.ToString());
            Response.Redirect("AddMaintenance.aspx?id=" + id);
        }
        if (e.CommandName == "lnkbtnDelete")
        {
            int id = int.Parse(e.CommandArgument.ToString());
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("DELETE FROM Maintenances WHERE MaintenanceId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            getData();
        }
    }
    protected void btnAddMaintenance_Click(object sender, EventArgs e)
    {
        Response.Redirect("AddMaintenance.aspx");
    }
}