using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;

public partial class AddMaintenance : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (Request.QueryString["id"] != null)
            {
                btnAddMaintenance.Text = "Update";
                divNote.Visible = false;
                getData();
            }
        }
    }
    protected void btnViewMaintenance_Click(object sender, EventArgs e)
    {
        Response.Redirect("ViewMaintenance.aspx");
    }

    void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT MaintenanceDueDate, MaintenanaceMonthYear, MaintenanceAmount, MaintenancePenaltyAmount FROM Maintenances WHERE MaintenanceId='" + Request.QueryString["id"] + "'", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        txtDueDate.Text = dt.Rows[0]["MaintenanceDueDate"].ToString();
        txtMonthYear.Text = dt.Rows[0]["MaintenanaceMonthYear"].ToString();
        txtAmount.Text = dt.Rows[0]["MaintenanceAmount"].ToString();
        txtAmountPenalty.Text = dt.Rows[0]["MaintenancePenaltyAmount"].ToString();
    }

    protected void btnAddMaintenance_Click(object sender, EventArgs e)
    {
        if (txtAmount.Text == "" || txtMonthYear.Text == "" || txtDueDate.Text == "")
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Fields can't be empty\")", true);
        }
        else
        {
            if (btnAddMaintenance.Text == "Add Maintenance")
            {
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand("INSERT INTO Maintenances(MaintenanceDueDate, MaintenanaceMonthYear, MaintenanceAmount, MaintenancePenaltyAmount, MaintenancePostDate, MaintenanceAdminId, MaintenanceApartmentId) VALUES('" + txtDueDate.Text + "','" + txtMonthYear.Text + "','" + txtAmount.Text + "','" + txtAmountPenalty.Text + "','" + DateTime.Now.ToString("dd-mm-yyyy") + "','" + Session["AdminId"] + "', '" + Session["ApartmentId"] + "'); SELECT SCOPE_IDENTITY() AS ID", con);
                SqlDataAdapter da = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                da.Fill(dt);
                new SendNotification("AIzaSyCl2OLf_lI7lhSUZb9UI62KCRjXIwtNW7U", "423159204587").
                   GetNotificationData("Maintenance Added", txtMonthYear.Text, int.Parse(dt.Rows[0]["ID"].ToString()), "maintenance");
                Response.Redirect(Request.RawUrl);
            }
            if (btnAddMaintenance.Text == "Update")
            {
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand("UPDATE Maintenances SET MaintenanceDueDate='" + txtDueDate.Text + "', MaintenanaceMonthYear='" + txtMonthYear.Text + "', MaintenanceAmount='" + txtAmount.Text + "', MaintenancePenaltyAmount='" + txtAmountPenalty.Text + "' WHERE MaintenanceId='" + Request.QueryString["id"] + "'", con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
                Response.Redirect("ViewMaintenance.aspx");
            }
        }
    }
}