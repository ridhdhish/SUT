using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;

public partial class node_modules_complaints : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            btnRefresh.Visible = false;
            getComplaints();
        }
    }

    void getComplaints()
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Complaints INNER JOIN Users ON Complaints.ComplaintUserId = Users.UserId WHERE ComplaintAdminId='" + Session["AdminId"] + "' ORDER BY CONVERT(VARCHAR(10), CONVERT(date, ComplaintDate, 105), 23) DESC,ComplaintTime DESC", con);
        //SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Complaints INNER JOIN Users ON Complaints.ComplaintUserId = Users.UserId", con);
        da.Fill(dt);
        rptComplaints.DataSource = dt;
        rptComplaints.DataBind();
        hdnDataCount.Value = dt.Rows.Count.ToString();
    }

    protected void rptComplaints_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        int id = int.Parse(e.CommandArgument.ToString());
        SqlConnection con = new SqlConnection(strCon);
        if (e.CommandName == "lnkbtnRead")
        {
            SqlCommand cmd = new SqlCommand("UPDATE Complaints SET ComplaintRead='1' WHERE ComplaintId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
        }/*
        if (e.CommandName == "lnkbtnUnread")
        {
            SqlCommand cmd = new SqlCommand("UPDATE Complaints SET ComplaintRead='0' WHERE ComplaintId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
        }*/
        getComplaints();
    }
    protected void btnReadAll_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("UPDATE Complaints SET ComplaintRead='1' WHERE ComplaintAdminId='" + Session["AdminId"] + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        Response.Redirect("complaints.aspx");
    }

    protected void btnReadMessage_Click(object sender, EventArgs e)
    {
        int id = int.Parse(hdMsgID.Value.ToString());
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("UPDATE Complaints SET ComplaintRead='1' WHERE ComplaintId='" + id + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        getComplaints();
    }
    protected void Timer1_Tick(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Complaints INNER JOIN Users ON Complaints.ComplaintUserId = Users.UserId WHERE ComplaintAdminId='" + Session["AdminId"] + "' ORDER BY CONVERT(VARCHAR(10), CONVERT(date, ComplaintDate, 105), 23) DESC,ComplaintTime DESC", con);
        //SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM Complaints INNER JOIN Users ON Complaints.ComplaintUserId = Users.UserId", con);
        da.Fill(dt);

        int newDataCount = dt.Rows.Count;
        if(int.Parse(hdnDataCount.Value) != newDataCount)
        {
            btnRefresh.Visible = true;
        }
    }

    protected void btnRefresh_Click(object sender, EventArgs e)
    {
        btnRefresh.Visible = false;
        Response.Redirect(Request.RawUrl);
    }
}