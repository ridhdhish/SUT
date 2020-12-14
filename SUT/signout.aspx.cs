using System;
using System.Collections.Specialized;
using System.Text;
using System.Web;

public partial class signout : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    { 
            Session.Abandon();
            Response.Redirect("signin.aspx");  
    }
}