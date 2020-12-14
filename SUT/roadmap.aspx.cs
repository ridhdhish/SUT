using System;
using System.Data;
using System.Data.SqlClient;

public partial class roadmap : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["AdminId"] != null)
        {
            if (!IsPostBack)
            {
                getRoadMapImage();
            }
        }
        else
        {
            Response.Redirect("signin.aspx");
        }
    }

    void getRoadMapImage()
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT RoadMapImage FROM RoadMap WHERE RoadMapAdminId='" + Session["AdminId"].ToString() + "'", con);
        con.Open();
        da.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            if (dt.Rows[0]["RoadMapImage"] == null || dt.Rows[0]["RoadMapImage"].ToString() == string.Empty)
            {
                divUploadFile.Visible = true;
                divImageContainer.Visible = false;
            }
            else
            {
                divUploadFile.Style.Add("display", "none");
                divImageContainer.Visible = true;
                btnUpload.Style.Add("display", "none");
            }
            imgRoadmap.ImageUrl = "~/siteimages/roadmaps/" + dt.Rows[0]["RoadMapImage"];
            imgRoadmap.Visible = true;
        }
        else
        {
            divUploadFile.Visible = true;
            divImageContainer.Style.Add("display", "none");
            btnUpload.Style.Add("display", "none");
        }
        con.Close();
    }

    protected void btnUpload_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT RoadMapImage FROM RoadMap WHERE RoadMapAdminId='" + Session["AdminId"].ToString() + "'", con);
        con.Open();
        da.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            string filename = "", path = "", fullpath = "";
            if (uploadedFile.HasFile)
            {
                filename = uploadedFile.FileName;
                path = Server.MapPath("~/siteimages/roadmaps");
                fullpath = path + "//" + filename;
                uploadedFile.SaveAs(fullpath);
                //string ext = Path.GetExtension(filename);
            }
            SqlCommand cmd = new SqlCommand("UPDATE RoadMap SET RoadMapImage='" + filename + "' WHERE RoadMapAdminId='" + Session["AdminId"].ToString() + "'", con);
            cmd.ExecuteNonQuery();
        }
        else
        {
            string filename = "", path = "", fullpath = "";
            if (uploadedFile.HasFile)
            {
                filename = uploadedFile.FileName;
                path = Server.MapPath("~/siteimages/roadmaps");
                fullpath = path + "//" + filename;
                uploadedFile.SaveAs(fullpath);
                //string ext = Path.GetExtension(filename);
            }
            SqlCommand cmd = new SqlCommand("INSERT INTO RoadMap(RoadMapImage,RoadMapAdminId, RoadMapApartmentId) VALUES('" + filename + "', '" + Session["AdminId"].ToString() + "', '" + Session["ApartmentId"] + "')", con);
            cmd.ExecuteNonQuery();
        }
        con.Close();
        Response.Redirect("roadmap.aspx");
    }

    protected void lnkbtnDelete_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("DELETE FROM RoadMap WHERE RoadMapApartmentId='"+Session["ApartmentId"] +"'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        Response.Redirect("roadmap.aspx");    
    }
}