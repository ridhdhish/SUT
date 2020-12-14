using System;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;

public partial class services : System.Web.UI.Page
{
    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            getType();
            if (Request.QueryString["id"] != null)
            {
                getEditData();
            }
            else
            {
                getData();
            }
        }

    }
    public void getEditData()
    {
        int id = int.Parse(Request.QueryString["id"].ToString());
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT SPId, SPTypeId, SPName, SPContactNumber FROM ServiceProviders WHERE SPId='" + id + "'", con);
        da.Fill(dt);
        txtName.Text = dt.Rows[0]["SPName"].ToString();
        txtContactNumber.Text = dt.Rows[0]["SPContactNumber"].ToString();
        ddltype.Items.FindByValue(dt.Rows[0]["SPTypeId"].ToString()).Selected = true;
        hidden.Visible = false;
        btnAdd.Text = "Update";
    }
    void getData()
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT * FROM ServiceProviders INNER JOIN ServiceTypes ON ServiceTypes.ServiceTypeId = ServiceProviders.SPTypeId WHERE SPAdminId='" + Session["AdminId"] + "'", con);
        da.Fill(dt);
        repServices.DataSource = dt;
        repServices.DataBind();
    }
    void getType()
    {
        SqlConnection con = new SqlConnection(strCon);
        DataTable dt = new DataTable();
        SqlDataAdapter da = new SqlDataAdapter("SELECT ServiceTypeId, ServiceTypeName, ServiceTypeImage FROM ServiceTypes", con);
        da.Fill(dt);
        ddltype.DataSource = dt;
        ddltype.DataTextField = "ServiceTypeName";
        ddltype.DataValueField = "ServiceTypeId";
        ddltype.DataBind();
        for (int i = 0; i < ddltype.Items.Count; i++)
        {
            ListItem item = ddltype.Items[i];
            item.Attributes["data-content"] = "<img src='siteimages/servicetypeimage/" + dt.Rows[i]["ServiceTypeImage"].ToString() + "' width='20px' height='20px' style='margin-right: 15px;'/>" + dt.Rows[i]["ServiceTypeName"].ToString();
        }

    }
    protected void repServices_ItemCommand(object source, RepeaterCommandEventArgs e)
    {
        int id = int.Parse(e.CommandArgument.ToString());
        if (e.CommandName == "lnkbtnDelete")
        {
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("DELETE FROM ServiceProviders WHERE SPId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            getData();
        }
        if (e.CommandName == "lnkbtnEdit")
        {
            Response.Redirect("services.aspx?id=" + id);
        }
    }
    protected void btnAdd_Click(object sender, EventArgs e)
    {
        if (btnAdd.Text == "Add Service")
        {
            SqlConnection con = new SqlConnection(strCon);
            string contactNumber = txtContactNumber.Text;
            contactNumber = contactNumber.Replace(" ", string.Empty).Replace("_", string.Empty).Replace("-", string.Empty).Replace("(", string.Empty).Replace(")", string.Empty);
            SqlCommand cmd = new SqlCommand("INSERT INTO ServiceProviders(SPName, SPContactNumber, SPTypeId, SPAdminId, SPApartmentId) VALUES('" + txtName.Text + "', '" + contactNumber + "', '" + ddltype.SelectedValue.ToString() + "','" + Session["AdminId"] + "', '" + Session["ApartmentId"] + "')", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            txtContactNumber.Text = "";
            txtName.Text = "";
            ddltype.SelectedIndex = 0;
            getData();
        }
        if (btnAdd.Text == "Update")
        {
            int id = int.Parse(Request.QueryString["id"].ToString());
            SqlConnection con = new SqlConnection(strCon);
            SqlCommand cmd = new SqlCommand("UPDATE ServiceProviders SET SPName='" + txtName.Text + "', SPContactNumber='" + txtContactNumber.Text + "', SPTypeId='" + ddltype.SelectedValue.ToString() + "' WHERE SPId='" + id + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();

        }
        Response.Redirect("services.aspx");
    }
}