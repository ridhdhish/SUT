using System;
using System.Data;
using System.Data.SqlClient;
using System.Web;
using System.Web.UI;

public partial class addannouncement : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            imgPreview.Visible = false;
            if (Request.QueryString["id"] != null)
            {
                getEditData();
            }
        }
    }

    protected void btnAdd_Click(object sender, EventArgs e)
    {
        if (txtMessage.Value == "")
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Message field can't be empty\")", true);
        }
        else
        {
            addAnnouncement();
        }
    }

    void addAnnouncement()
    {
        if (Session != null)
        {
            char imp = '0';
            if (chkImportant.Checked == true)
            {
                imp = '1';
            }

            string adminid = Session["AdminId"].ToString();
            string apartmentId = Session["ApartmentId"].ToString();
            string message = Server.HtmlEncode(txtMessage.Value);
            string topic = Server.HtmlEncode(txtTopic.Text);
            string filename = "", path = "", fullpath = "";
            if(message.Length >= 255)
            {
                message = message.Substring(0,256);
            }
            if (topic.Length > 50)
            {
                topic= message.Substring(0, 51);
            }
            if (uploadedFiles.HasFile)
            {
                filename = Session["AdminId"] + DateTime.Now.ToString("ddMMyyyyhhmmss") + uploadedFiles.FileName;
                path = Server.MapPath("~/siteimages/announcements");
                fullpath = path + "//" + HttpUtility.HtmlEncode(filename);
                uploadedFiles.SaveAs(fullpath);
                //string ext = Path.GetExtension(filename);
            }
            if (btnAdd.Text == "Add Announcement")
            {
                string query = "INSERT INTO Announcements(AnnouncementTitle, AnnouncementMessage, AnnouncementTime, AnnouncementDate, AnnouncementImage, AnnouncementImportant, AnnouncementAdminId, AnnouncementApartmentId) VALUES('" + topic + "','" + message + "','" + DateTime.Now.ToString("h:mm:ss") + "','" + DateTime.Now.ToString("dd-MM-yyyy") + "','" + filename + "','" + imp + "','" + adminid + "', '" + apartmentId + "'); SELECT SCOPE_IDENTITY() AS ID";
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand(query, con);
                //con.Open();
                //cmd.ExecuteNonQuery();
                //con.Close();
                SqlDataAdapter da = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                da.Fill(dt);
                new SendNotification("AIzaSyCl2OLf_lI7lhSUZb9UI62KCRjXIwtNW7U", "423159204587")
                    .GetNotificationData(Server.HtmlDecode(topic), Server.HtmlDecode(message), int.Parse(dt.Rows[0]["ID"].ToString()), "announcement");
                Response.Redirect(Request.RawUrl);
            }

            if (btnAdd.Text == "Update")
            {
                int id = int.Parse(Request.QueryString["id"].ToString());
                SqlConnection con = new SqlConnection(strCon);
                SqlCommand cmd = new SqlCommand("UPDATE Announcements SET AnnouncementTitle='" + txtTopic.Text + "', AnnouncementMessage='" + message + "', AnnouncementImage='" + filename + "', AnnouncementImportant='" + imp + "' WHERE AnnouncementId='" + id + "'", con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
                Response.Redirect("viewAnnouncement.aspx");
            }
        }
        else
        {
            Response.Redirect("SignIn.aspx");
        }
    }

    void getEditData()
    {
        int id = int.Parse(Request.QueryString["id"].ToString());
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Announcements WHERE AnnouncementId='" + id + "' AND AnnouncementAdminId='" + Session["AdminId"] + "'", con);
        da.Fill(dt);
        txtTopic.Text = dt.Rows[0]["AnnouncementTitle"].ToString();
        txtMessage.Value = dt.Rows[0]["AnnouncementMessage"].ToString();
        if(dt.Rows[0]["AnnouncementImage"].ToString() != "" || !(dt.Rows[0]["AnnouncementImage"].ToString().Equals(string.Empty)) || !(dt.Rows[0]["AnnouncementImage"].ToString().Equals(null)))
        {
            imgPreview.Visible = true;
        }
        imgPreview.ImageUrl = "siteimages/announcements/" + dt.Rows[0]["AnnouncementImage"].ToString();

        btnAdd.Text = "Update";
        
    }

    protected void btnViewAnnouncement_Click(object sender, EventArgs e)
    {
        Response.Redirect("viewAnnouncement.aspx");
    }
}