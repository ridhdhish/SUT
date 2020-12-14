using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Announcement : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            getData();
            //ScriptManager.RegisterClientScriptBlock(this, this.GetType(), Request.Form["error"].ToString(), "alert('Apartment Id: " + Session["ApartmentId"] + "')", true);
            //Page.ClientScript.RegisterStartupScript(this.GetType(), "Error", "showError()");
        }

        //for (int i = 0; i < rptViewAnnouncements.Items.Count; i++)
        //{
        //    CheckBox a = rptViewAnnouncements.Items[i].FindControl("chkImportant") as CheckBox;
        //    a.ID = "chkImportant_" + i.ToString();
        //    a.ClientIDMode = ClientIDMode.Static;
        //}
    }
    void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        //SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Announcements WHERE AnnouncementAdminId='" + Session["AdminId"] + "' ORDER BY CONVERT(VARCHAR(10), CONVERT(date, AnnouncementDate, 105), 23) AS AnnouncementDate DESC, CONVERT(VARCHAR(10), CONVERT(date, AnnouncementTime, 105), 23) AS AnnouncementTime DESC", con);
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Announcements WHERE AnnouncementAdminId='" + Session["AdminId"] + "' ORDER BY CONVERT(VARCHAR(10), CONVERT(date, AnnouncementDate, 105), 23) DESC, CONVERT(time, AnnouncementTime) DESC", con);
        da.Fill(dt);
        rptViewAnnouncements.DataSource = dt;
        rptViewAnnouncements.DataBind();
    }
    protected void rptViewAnnouncements_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        int id = int.Parse(e.CommandArgument.ToString());
        if (e.CommandName == "lnkbtnDelete")
        {
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("DELETE FROM Announcements WHERE AnnouncementId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            getData();
        }
        if (e.CommandName == "lnkbtnEdit")
        {
            Response.Redirect("addAnnouncement.aspx?id=" + id);
        }
    }
    protected void btnAddAnnouncement_Click(object sender, EventArgs e)
    {
        Response.Redirect("addAnnouncement.aspx");
    }
    protected void chkImportant_CheckedChanged(object sender, EventArgs e)
    {
        int id = int.Parse(((CheckBox)sender).Attributes["Value"].ToString());
        SqlConnection con = new SqlConnection(strCon);
        char imp = '0';
        CheckBox cb = (CheckBox)sender;
        if (cb.Checked == true)
        {
            imp = '1';
        }

        SqlCommand cmd = new SqlCommand("UPDATE Announcements SET AnnouncementImportant='" + imp + "' WHERE AnnouncementId='" + id + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();

    }

    protected void btnReadMessage_Click(object sender, EventArgs e)
    {

    }
}