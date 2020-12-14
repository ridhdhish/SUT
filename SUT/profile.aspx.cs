using System;
using System.Data;
using System.Data.SqlClient;
using System.IO;

public partial class profile : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    string profilePicturePath = "~/siteimages/profilePictures/";

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            labelVisibility(true);
            controlsVisibility(false);
            divUpload.Visible = false;
            divEditDelete.Visible = false;
            getData();
        }
    }

    void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter daAdminDetails = new SqlDataAdapter("SELECT AdminName, AdminEmailid, AdminMobileNumber, AdminGender, AdminProfilePicture, AdminSocietyName, AdminSocietyAddress, AdminSocietyPincode FROM Admins WHERE AdminId='" + Session["AdminId"] + "'", strCon);
        SqlDataAdapter daApartmentDetails = new SqlDataAdapter("SELECT ApartmentName, ApartmentAddress, ApartmentWingName, ApartmentPincode, ApartmentCity FROM Apartments WHERE ApartmentId='" + Session["ApartmentId"] + "'", strCon);
        DataTable dtAdminDetails = new DataTable();
        DataTable dtApartmentDetails = new DataTable();
        daAdminDetails.Fill(dtAdminDetails);
        daApartmentDetails.Fill(dtApartmentDetails);
        if (dtAdminDetails != null && dtApartmentDetails != null)
        {
            if (dtAdminDetails.Rows.Count > 0 && dtApartmentDetails.Rows.Count > 0)
            {
                lblName.Text = txtName.Text = dtAdminDetails.Rows[0]["AdminName"].ToString();
                // lblEmailAddress.Text = txtEmailAddress.Text = dtAdminDetails.Rows[0]["AdminEmailid"].ToString();
                lblEmailAddress.Text = dtAdminDetails.Rows[0]["AdminEmailid"].ToString();
                lblPhoneNumber.Text = txtPhoneNumber.Text = dtAdminDetails.Rows[0]["AdminMobileNumber"].ToString();

                lblSocietyName.Text = dtApartmentDetails.Rows[0]["ApartmentName"].ToString();
                lblSocietyAddress.Text = dtApartmentDetails.Rows[0]["ApartmentWingName"].ToString() + ", " + dtApartmentDetails.Rows[0]["ApartmentAddress"].ToString() + ", " + dtApartmentDetails.Rows[0]["ApartmentCity"].ToString();
                lblSocietyPincode.Text = dtApartmentDetails.Rows[0]["ApartmentPincode"].ToString();


                string gen = dtAdminDetails.Rows[0]["AdminGender"].ToString();
                if (gen == "male")
                {
                    rdbMale.Checked = true;
                    lblGender.Text = "Male";
                }
                else if (gen == "female")
                {
                    rdbFemale.Checked = true;
                    lblGender.Text = "Female";
                }
                string profilePictureImage = dtAdminDetails.Rows[0]["AdminProfilePicture"].ToString();
                if (profilePictureImage == "")
                {
                    profilePicturePath += "defaultUser.png";
                }
                else
                {
                    profilePicturePath += profilePictureImage.ToString();
                }
                imgProfilePictureP.ImageUrl = profilePicturePath;
                hdnProfilePicture.Value = dtAdminDetails.Rows[0]["AdminProfilePicture"].ToString();
            }
        }
    }

    void controlsVisibility(Boolean visibility)
    {
        txtName.Visible = visibility;
        divName.Visible = visibility;
        // txtEmailAddress.Visible = visibility;
        // divEmailAddress.Visible = visibility;
        txtPhoneNumber.Visible = visibility;
        divPhoneNumber.Visible = visibility;
        divGender.Visible = visibility;
        btnSaveData.Visible = visibility;
        btnCancel.Visible = visibility;
    }

    void labelVisibility(Boolean visibility)
    {
        //lblEmailAddress.Visible = visibility;
        lblGender.Visible = visibility;
        lblName.Visible = visibility;
        lblPhoneNumber.Visible = visibility;
        btnUpdate.Visible = visibility;
    }

    protected void btnUpdate_Click(object sender, EventArgs e)
    {
        labelVisibility(false);
        controlsVisibility(true);
        string profilePictureImage = imgProfilePictureP.ImageUrl.ToString();
        profilePictureImage = profilePictureImage.Replace("~/siteimages/profilePictures/", string.Empty);
        if (profilePictureImage == "defaultUser.png")
        {
            divEditDelete.Visible = false;
            divUpload.Visible = true;
        }
        else
        {
            divEditDelete.Visible = true;
            divUpload.Visible = false;
        }
    }

    protected void btnSaveData_Click(object sender, EventArgs e)
    {
        string gender = "";
        if (rdbFemale.Checked == true)
        {
            gender = "female";
        }
        else if (rdbMale.Checked == true)
        {
            gender = "male";
        }
        string filename = "", path = "", fullpath = "";
        if (imgUpload.HasFile)
        {
            string ext = Path.GetExtension(imgUpload.FileName.ToString());
            filename = Session["AdminId"].ToString() + DateTime.Now.ToString("ddMMyyyyhhmmss");
            filename += ext;
            path = Server.MapPath("~/siteimages/profilePictures");
            fullpath = path + "//" + filename;
            imgUpload.SaveAs(fullpath);
        }
        else
        {
            filename = hdnProfilePicture.Value;
        }
        string contactNumber = txtPhoneNumber.Text;
        contactNumber = contactNumber.Replace(" ", string.Empty).Replace("_", string.Empty).Replace("-", string.Empty).Replace("(", string.Empty).Replace(")", string.Empty);
        //string query = "UPDATE Admins SET AdminName='" + txtName.Text + "', AdminGender='" + gender + "', AdminEmailid='" + txtEmailAddress.Text + "', AdminMobileNumber='" + contactNumber + "', AdminProfilePicture='" + filename + "' WHERE AdminId='" + Session["AdminId"] + "'";
        string query = "UPDATE Admins SET AdminName='" + txtName.Text + "', AdminGender='" + gender + "', AdminMobileNumber='" + contactNumber + "', AdminProfilePicture='" + filename + "' WHERE AdminId='" + Session["AdminId"] + "'";
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand(query, con);
        con.Open();
        cmd.ExecuteNonQuery();
        new SqlCommand("UPDATE Users UserName='" + txtName.Text + "', UserGender='" + gender + "', UserMobile='" + contactNumber + "',UserProfilePicture='" + filename + "' WHERE UserEmailid='" + lblEmailAddress.Text + "'", con)
            .ExecuteNonQuery();
        con.Close();
        Response.Redirect(Request.RawUrl);
    }

    protected void btnDelete_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("UPDATE Admins SET  AdminProfilePicture=null WHERE AdminId='" + Session["AdminId"] + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        Response.Redirect(Request.RawUrl);
    }

    protected void btnCancel_Click(object sender, EventArgs e)
    {
        Response.Redirect(Request.RawUrl);
    }
}