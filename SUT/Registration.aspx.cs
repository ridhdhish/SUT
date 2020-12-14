using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI;

public partial class Registration : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    private bool checkAlreadyRegistered()
    {
        DataTable dt = new DataTable();
        new SqlDataAdapter("SELECT * FROM Admins WHERE AdminEmailid='" + txtEmail.Text.ToLower() + "'", new SqlConnection(strCon)).Fill(dt);
        if (dt.Rows.Count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    void register()
    {
        string name = txtName.Text.ToLower();
        string email = txtEmail.Text.ToLower();
        string password = txtPassword.Text.ToLower();
        string mobile = txtMobileNumber.Text.ToLower();
        string gender = "";
        mobile = mobile.Replace(" ", string.Empty).Replace("_", string.Empty).Replace("-", string.Empty).Replace("(", string.Empty).Replace(")", string.Empty);
        string flatNumber = txtFlatNumber.Text.ToLower();
        string wingName = txtWingName.Text.ToLower();
        string apartmentName = txtApartmentName.Text.ToLower();

        string pincode = txtPincode.Text.ToLower();
        string address = txtAddress.Value.ToLower();
        string city = txtCity.Text.ToLower();

        if (rdbMale.Checked == true)
        {
            gender = "male";
        }
        else if (rdbFemale.Checked == true)
        {
            gender = "female";
        }
        string uniqueId = GenerateOTP();
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd;
        SqlDataAdapter da = new SqlDataAdapter("SELECT ApartmentId FROM Apartments WHERE ApartmentUniqueId='" + uniqueId + "'", con);
        DataTable dt = new DataTable();
        string apartmentId = "";
        string adminId = "";

        con.Open();
        cmd = new SqlCommand("INSERT INTO Apartments(ApartmentName, ApartmentAddress, ApartmentWingName, ApartmentPincode, ApartmentCity, ApartmentUniqueId) VALUES('" + apartmentName + "', '" + address + "', '" + wingName + "', '" + pincode + "', '" + city + "', '" + uniqueId + "')", con);
        cmd.ExecuteNonQuery();
        da.Fill(dt);
        apartmentId = dt.Rows[0]["ApartmentId"].ToString();
        cmd = new SqlCommand("INSERT INTO Admins(AdminName, AdminEmailid, AdminPassword, AdminMobileNumber, AdminGender, AdminFlatNumber, AdminApartmentId) VALUES('" + name + "', '" + email + "', '" + password + "', '" + mobile + "', '" + gender + "', '" + flatNumber + "', '" + apartmentId + "')", con);
        cmd.ExecuteNonQuery();

        SqlDataAdapter adminAD = new SqlDataAdapter("SELECT AdminId FROM Admins WHERE AdminApartmentId='" + apartmentId + "'", con);
        adminAD.Fill(dt);
        adminId = dt.Rows[0]["AdminId"].ToString();
        cmd = new SqlCommand("INSERT INTO Users(UserName, UserEmailid, UserPassword, UserMobile, UserGender, UserFlatNumber, UserAdminId, UserApartmentId) VALUES('" + name + "', '" + email + "', '" + password + "', '" + mobile + "', '" + gender + "', '" + flatNumber + "', '" + 10 + "', '" + apartmentId + "')", con);
        cmd.ExecuteNonQuery();
    }

    private string GenerateOTP()
    {
        string alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string numbers = "1234567890";

        string characters = numbers;

        characters += alphabets + numbers;
        string otp = string.Empty;
        for (int i = 0; i < 12; i++)
        {
            string character = string.Empty;
            do
            {
                int index = new Random().Next(0, characters.Length);
                character = characters.ToCharArray()[index].ToString();
            } while (otp.IndexOf(character) != -1);
            otp += character;
        }
        return otp;
    }

    protected void btnSignUp_Click(object sender, EventArgs e)
    {
        if (!checkAlreadyRegistered())
        {
            register();
            Response.Redirect("SignIn.aspx");
        }
        else
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "Error", "alert('Already registred')", true);
        }
    }
}