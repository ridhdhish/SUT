using System;
using System.Data;
using System.Data.SqlClient;

public partial class viewMaintenanceRecord : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";

    protected void Page_Load(object sender, EventArgs e)
    {
        getData();
    }
    public void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Maintenances WHERE MaintenanceAdminId='" + Session["AdminId"] + "'", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        repViewMaintenanceRecord.DataSource = dt;
        repViewMaintenanceRecord.DataBind();
        repViewMaintenanceRecord.DataSource = dt;
        repViewMaintenanceRecord.DataBind();
    }
}