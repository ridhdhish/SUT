using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;

public partial class ViewMeetings : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        getViewMeetings();
    }
    void getViewMeetings()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Meetings WHERE MeetingAdminId='" + Session["AdminId"] + "' ORDER BY MeetingDate DESC, MeetingTime DESC", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        repViewMeetings.DataSource = dt;
        repViewMeetings.DataBind();
    }
    protected void btnAddMeeting_Click(object sender, EventArgs e)
    {
        Response.Redirect("AddMeeting.aspx");
    }
    protected void repViewMeetings_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        int id = int.Parse(e.CommandArgument.ToString());
        if (e.CommandName == "lnkbtnEdit")
        {
            Response.Redirect("AddMeeting.aspx?id=" + id);
        }

        if (e.CommandName == "lnkbtnDelete")
        {
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("DELETE FROM Meetings WHERE MeetingId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            getViewMeetings();
        }
    }
}