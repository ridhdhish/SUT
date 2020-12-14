using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;

public partial class AddMeeting : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (Request.QueryString["id"] != null)
            {
                btnAddMeeting.Text = "Update";
                getData();
            }
        }
    }
    protected void btnViewMeetings_Click(object sender, EventArgs e)
    {
        Response.Redirect("ViewMeetings.aspx");
    }
    protected void btnAddMeeting_Click(object sender, EventArgs e)
    {
        if (txtTopic.Text == "" || txtMessage.Value == "" || txtDate.Text == "" || txtTime.Text == "" || txtPlace.Text == "")
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Fields can't be empty\")", true);
        }
        else
        {
            if (btnAddMeeting.Text == "Add Meeting")
            {
                char imp = '0';
                if (chkImportant.Checked == true)
                {
                    imp = '1';
                }
                string query = "INSERT INTO Meetings(Meetingtitle, MeetingMessage, MeetingImportant, MeetingDate, MeetingTime, MeetingAddress, MeetingPostDate, MeetingApartmentId, MeetingAdminId) VALUES('" + txtTopic.Text + "', '" + txtMessage.Value + "', '" + imp + "', '" + txtDate.Text + "', '" + txtTime.Text + "', '" + txtPlace.Text + "', '" + DateTime.Now.ToString("dd-MM-yyyy hh:mm:ss") + "', '" + Session["AdminId"] + "', '" + Session["ApartmentId"] + "'); SELECT SCOPE_IDENTITY() AS ID";
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand(query, con);
                SqlDataAdapter da = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                da.Fill(dt);
                new SendNotification("AIzaSyCl2OLf_lI7lhSUZb9UI62KCRjXIwtNW7U", "423159204587")
                    .GetNotificationData(txtTopic.Text + "\n", txtMessage.Value + "\nDate: " + txtDate.Text + "\nTime: " + txtTime.Text, int.Parse(dt.Rows[0]["ID"].ToString()), "meeting");
                Response.Redirect(Request.RawUrl);
            }
            if (btnAddMeeting.Text == "Update")
            {
                char imp = '0';
                if (chkImportant.Checked == true)
                {
                    imp = '1';
                }
                string query = "UPDATE Meetings SET Meetingtitle='" + txtTopic.Text + "', MeetingMessage='" + txtMessage.Value + "', MeetingImportant='" + imp + "', MeetingDate='" + txtDate.Text + "' , MeetingTime='" + txtTime.Text + "', MeetingAddress='" + txtPlace.Text + "' WHERE MeetingId='" + Request.QueryString["id"] + "'";
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand(query, con);
                con.Open();
                if (cmd.ExecuteNonQuery() > 0)
                {
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Updated successfully\")", true);
                }
                else
                {
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Error\")", true);
                }

                con.Close();
                Response.Redirect("ViewMeetings.aspx");
            }
        }
    }
    void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Meetings WHERE MeetingId='" + Request.QueryString["id"] + "'", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        txtTopic.Text = dt.Rows[0]["MeetingTitle"].ToString();
        txtMessage.Value = dt.Rows[0]["MeetingMessage"].ToString();
        txtDate.Text = dt.Rows[0]["MeetingDate"].ToString();
        txtTime.Text = dt.Rows[0]["MeetingTime"].ToString();
        txtPlace.Text = dt.Rows[0]["MeetingAddress"].ToString();
        chkImportant.Checked = (dt.Rows[0]["MeetingImportant"].ToString() == "0" ? false : true);
    }
}