using System;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web.UI;

public partial class AddUser : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    String gender = "";
    string contactNumber = "";

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (Request.QueryString["id"] != null)
            {
                txtPassword.Enabled = false;
                txtFlatNumber.Enabled = false;
                btnAddUser.Text = "Update";
                getDataUser(Request.QueryString["id"]);
            }
        }
    }
    void getFieldData()
    {
        if (rdbMale.Checked == true)
        {
            gender = rdbMale.Text.ToLower();
        }
        else if (rdbFemale.Checked == true)
        {
            gender = rdbFemale.Text.ToLower();
        }

        /*
          char[] trimable = { '+', ' ', '-', '(', ')' };
        txtMobileNumber.Text.Trim(trimable);
        */

        contactNumber = txtMobileNumber.Text.Replace(" ", string.Empty).Replace("_", string.Empty).Replace("-", string.Empty).Replace("(", string.Empty).Replace(")", string.Empty);
    }
    protected void btnSubmit_Click(object sender, EventArgs e)
    {
        string[] flatNumbers = getFlatNumbers(Session["ApartmentId"].ToString()).AsEnumerable().Select(x => x.Field<string>("UserFlatNumber")).ToArray();
        flatNumbers = flatNumbers.Distinct().ToArray();

        if (txtName.Text == "" || txtEmail.Text == "" || txtPassword.Text == "" || txtMobileNumber.Text == "" || txtFlatNumber.Text == "")
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "errorField", "showError(\"Fields can't be empty\")", true);
        }
        else
        {
            if (btnAddUser.Text == "Add User")
            {
                if (flatNumbers.Contains(txtFlatNumber.Text))
                {
                    Page.ClientScript.RegisterStartupScript(this.GetType(), "errorFlatNumberAssigned", "showError('FlatNumber is already assigned')", true);
                }
                else
                {
                    getDataUser_AddUser();
                }
            }
            else if (btnAddUser.Text == "Update")
            {
                updateUser(Request.QueryString["id"]);
            }
        }
    }

    private void updateUser(string id)
    {
        getFieldData();
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("UPDATE Users SET UserName='" + txtName.Text + "', UserEmailid='" + txtEmail.Text + "', UserMobile='" + contactNumber + "', UserGender='" + gender + "' WHERE UserId='" + id + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        Response.Redirect("viewUsers.aspx");
    }

    private void getDataUser(string id)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT TOP 1 UserName, UserEmailid, UserMobile, UserGender, UserPassword, UserFlatNumber FROM Users WHERE UserId='" + id + "'", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            txtName.Text = dt.Rows[0]["UserName"].ToString();
            txtEmail.Text = dt.Rows[0]["UserEmailid"].ToString();
            txtMobileNumber.Text = dt.Rows[0]["UserMobile"].ToString();
            txtFlatNumber.Text = dt.Rows[0]["UserFlatNumber"].ToString();
            txtPassword.Text = dt.Rows[0]["UserPassword"].ToString();
            if (dt.Rows[0]["UserGender"].ToString().Equals("male"))
            {
                rdbMale.Checked = true;
            }
            if (dt.Rows[0]["UserGender"].ToString().Equals("female"))
            {
                rdbFemale.Checked = true;
            }
        }
    }

    void getDataUser_AddUser()
    {
        getFieldData();
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("INSERT INTO Users(UserName, UserEmailid, UserPassword, UserMobile, UserGender, UserFlatNumber, UserAdminId, UserApartmentId) VALUES('" + txtName.Text + "', '" + txtEmail.Text + "', '" + txtPassword.Text + "', '" + contactNumber + "', '" + gender + "', '" + txtFlatNumber.Text.ToString() + "', '" + Session["AdminId"] + "', '" + Session["ApartmentId"] + "')", con);
        con.Open();
        try
        {
            if (cmd.ExecuteNonQuery() > 0)
            {
                DataTable dt = new DataTable();
                new SqlDataAdapter("SELECT AdminName FROM Admins WHERE AdminId='" + Session["AdminId"] + "'", con).Fill(dt);
                string AdminName = dt.Rows[0]["AdminName"].ToString();
                //new SendSMS().SendOTP("SUTINF", String.Format("Welcome, {0} on SUT. Use {1} and {2} in android application to login. Your account is created by {3}.", txtName.Text, txtEmail.Text, txtPassword.Text, AdminName), contactNumber);
                //Page.ClientScript.RegisterStartupScript(this.GetType(), "errorFlatNumberAssigned", "showError('Flat-Holder Added')", true);
                Response.Redirect(Request.RawUrl);
            }
            else
            {
                lblMessege.CssClass = "text-danger";
                lblMessege.Text = "Something went wrong";
            }
        }
        catch (Exception e)
        {
            lblMessege.CssClass = "text-danger";
            lblMessege.Text = e.ToString();
        }
        con.Close();
    }
    public DataTable getFlatNumbers(string apartmentId)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlDataAdapter da = new SqlDataAdapter("SELECT UserFlatNumber FROM Users WHERE UserApartmentId='" + apartmentId + "'", con);
        DataTable dt = new DataTable();
        da.Fill(dt);
        return dt;
    }
    protected void btnViewUser_Click(object sender, EventArgs e)
    {
        Response.Redirect("ViewUsers.aspx");
    }
}