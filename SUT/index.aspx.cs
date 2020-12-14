using System;
using System.Data;
using System.Data.SqlClient;

public partial class index : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    SqlConnection con;
    SqlDataAdapter da;
    DataTable dt;

    protected void Page_Load(object sender, EventArgs e)
    {
        con = new SqlConnection(strCon);
        getUpcomingMeetingsCount();
        getAnnouncementsCount();
        getFlatHoldersCount();
        getGuestUsersCount();
        getUnreadComplaintsCount();

    }
    public void getUpcomingMeetingsCount()
    {
        da = new SqlDataAdapter("SELECT COUNT(MeetingId) as TotalUpcomingMeetings FROM Meetings WHERE MeetingApartmentId='" + Session["ApartmentId"] + "' and MeetingDate >= Convert(varchar,GETDATE(),105)", con);
        dt = new DataTable();
        da.Fill(dt);
        spanUpcominMeetingsCount.Attributes.Add("data-to", dt.Rows[0]["TotalUpcomingMeetings"].ToString());
    }

    public void getAnnouncementsCount()
    {
        da = new SqlDataAdapter("SELECT COUNT(AnnouncementID) AS TotalAnnouncements FROM Announcements WHERE AnnouncementApartmentId='" + Session["ApartmentId"] + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        spanAnnouncementsCount.Attributes.Add("data-to", dt.Rows[0]["TotalAnnouncements"].ToString());
    }
    public void getFlatHoldersCount()
    {
        da = new SqlDataAdapter("SELECT COUNT(UserId) AS TotalFlatHolders FROM Users WHERE UserApartmentId='" + Session["ApartmentId"] + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        spanFlatHoldersCount.Attributes.Add("data-to", dt.Rows[0]["TotalFlatHolders"].ToString());
    }

    public void getGuestUsersCount()
    {
        da = new SqlDataAdapter("SELECT COUNT(GUId) AS TotalGuestUsers FROM GuestUser WHERE GUApartmentId='" + Session["ApartmentId"] + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        spanGuestUsersCount.Attributes.Add("data-to", dt.Rows[0]["TotalGuestUsers"].ToString());
    }
    public void getUnreadComplaintsCount()
    {
        da = new SqlDataAdapter("SELECT COUNT(ComplaintId) AS TotalUnreadComplaints FROM Complaints WHERE  ComplaintRead='0' AND ComplaintApartmentId='" + Session["ApartmentId"] + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        spanUnreadComplaintsCount.Attributes.Add("data-to", dt.Rows[0]["TotalUnreadComplaints"].ToString());
    }
}