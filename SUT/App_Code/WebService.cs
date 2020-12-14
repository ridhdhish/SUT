using System;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Net;
using System.Net.Mail;
using System.Web;
using System.Web.Services;

/// <summary>
/// Summary description for WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class WebService : System.Web.Services.WebService
{

    string strCon = "Data Source=ACER;Initial Catalog=SUT;Integrated Security=True";
    SqlConnection con;
    SqlCommand cmd;
    SqlDataAdapter da;
    DataTable dt;
    public WebService()
    {
        con = new SqlConnection(strCon);
        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }
    [WebMethod]
    public string _____()
    {
        da = new SqlDataAdapter("SELECT * FROM AndroidDeviceTokens", con);
        dt = new DataTable("tt");
        da.Fill(dt);
        string str = dt.Rows[0]["DeviceToken"].ToString().Substring(0, 12);
        return str;
    }

    [WebMethod]
    public void SetTokenAndIMEI(string IMEI, string GeneratedToken)
    {
        //String TokenBefore = GeneratedToken.Substring(0, 12);
        //GeneratedToken = GeneratedToken.Substring(12);
        da = new SqlDataAdapter("SELECT DeviceIMEI FROM AndroidDeviceTokens WHERE DeviceIMEI='" + IMEI + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        con.Open();
        if (dt.Rows.Count > 0)
        {
            new SqlCommand("UPDATE AndroidDeviceTokens SET DeviceToken='" + GeneratedToken + "' WHERE DeviceIMEI='" + IMEI + "'", con)
                .ExecuteNonQuery();
        }
        else
        {
            //new SqlCommand("INSERT INTO AndroidDeviceTokens(DeviceIMEI, BeforeToken, DeviceToken) VALUES('" + IMEI + "', '"+ TokenBefore + "', '" + GeneratedToken + "')", con)
            new SqlCommand("INSERT INTO AndroidDeviceTokens(DeviceIMEI, DeviceToken, LoginStatus) VALUES('" + IMEI + "', '" + GeneratedToken + "', '0')", con)
                .ExecuteNonQuery();
        }
        con.Close();
    }
    [WebMethod]
    public void DeleteTokenAndIMEI(string IMEI)
    {
        //String TokenBefore = GeneratedToken.Substring(0, 12);
        //GeneratedToken = GeneratedToken.Substring(12);
        da = new SqlDataAdapter("SELECT DeviceIMEI FROM AndroidDeviceTokens WHERE DeviceIMEI='" + IMEI + "'", con);
        dt = new DataTable();
        da.Fill(dt);
        con.Open();
        if (dt.Rows.Count > 0)
        {
            new SqlCommand("UPDATE AndroidDeviceTokens SET LoginStatus='0' WHERE DeviceIMEI='" + IMEI + "'", con)
                .ExecuteNonQuery();
        }
        con.Close();
    }

    [WebMethod]
    public DataTable flatholder_login_select(string EmailId, string Password)
    {
        da = new SqlDataAdapter("SELECT TOP 1 UserEmailId, UserPassword, UserAdminId, UserApartmentId FROM Users WHERE UserEmailId='" + EmailId + "' and UserPassword='" + Password + "'", con);
        dt = new DataTable("Users");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_Login_Select(string emailId, string password, string IMEI)
    {
        da = new SqlDataAdapter("SELECT TOP 1 UserId, UserEmailId, UserPassword, UserAdminId, UserApartmentId FROM Users WHERE UserEmailId='" + emailId + "' and UserPassword='" + password + "'", con);
        dt = new DataTable("Users");
        da.Fill(dt);
        if (dt.Rows.Count > 0)
        {
            con.Open();
            DataTable data = new DataTable();
            new SqlDataAdapter("SELECT TokenId FROM AndroidDeviceTokens WHERE DeviceIMEI='" + IMEI + "'", con).Fill(data);
            new SqlCommand("UPDATE Users SET UserDeviceId='" + data.Rows[0]["TokenId"] + "' WHERE UserEmailId='" + emailId + "' AND UserPassword='" + password + "'", con)
                    .ExecuteNonQuery();
            new SqlCommand("UPDATE AndroidDeviceTokens SET LoginStatus='1' WHERE DeviceIMEI='" + IMEI + "'", con)
                .ExecuteNonQuery();
            con.Close();
        }
        return dt;
    }

    [WebMethod]
    public DataTable Users_ChangePassword_Select(string userId)
    {
        da = new SqlDataAdapter("SELECT UserPassword FROM Users WHERE UserId='" + userId + "'", con);
        dt = new DataTable("Users");
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public void Users_ChangePassword_Update(string userId, string password)
    {
        cmd = new SqlCommand("UPDATE Users SET UserPassword='" + password + "' WHERE UserId='" + userId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }
    [WebMethod]
    public string Users_ForgotPassword_Select(string emailId)
    {

        da = new SqlDataAdapter("SELECT UserEmailid FROM Users WHERE UserEmailid='" + emailId + "'", con);
        dt = new DataTable("Users");
        da.Fill(dt);
        try
        {
            if (dt.Rows.Count == 1 || dt.Rows.Count > 0)
            {
                return SendEmail(emailId);
            }
        }
        catch (Exception e)
        {
            Console.Write(e);
        }
        return "";
    }

    [WebMethod]
    public void Users_ForgotPassword_Update(string emailId, string password)
    {
        cmd = new SqlCommand("UPDATE Users SET UserPassword='" + password + "' WHERE UserEmailid='" + emailId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    [WebMethod]
    public DataTable Users_RoadMap_Select(string apartmentId)
    {
        dt = new DataTable("Roadmaps");
        da = new SqlDataAdapter("SELECT RoadMapImage FROM RoadMap WHERE RoadMapApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable Users_Announcement_Select(string adminId)
    {
        //da = new SqlDataAdapter("SELECT *,LEN(ISNULL(AnnouncementImage,'')) AS AnnouncementImage1 FROM Announcements WHERE AnnouncementAdminId='" + adminId + "'", con);
        da = new SqlDataAdapter("SELECT AnnouncementId, CASE WHEN ISNULL(AnnouncementTitle,'') = '' THEN ' ' ELSE AnnouncementTitle END as AnnouncementTitle, CASE WHEN ISNULL(AnnouncementMessage,'') = '' THEN ' ' ELSE AnnouncementMessage END as AnnouncementMessage, AnnouncementTime, AnnouncementDate, CASE WHEN ISNULL(AnnouncementImage,'') = '' THEN 'false' ELSE AnnouncementImage END as AnnouncementImage, AnnouncementImportant, AnnouncementAdminId, AnnouncementApartmentId FROM Announcements WHERE AnnouncementAdminId='" + adminId + "' ORDER BY CONVERT(VARCHAR(10), CONVERT(date, AnnouncementDate, 105), 23) DESC, CONVERT(time, AnnouncementTime) DESC", con);
        dt = new DataTable("Announcements");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_Announcement_Display_Image_Select(string announcementId)
    {
        da = new SqlDataAdapter("SELECT AnnouncementImage FROM Announcements WHERE AnnouncementId='" + announcementId + "'", con);
        dt = new DataTable("Announcements");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_Maintenance_Select(string apartmentId)
    {
        da = new SqlDataAdapter("SELECT CASE WHEN ISNULL(MaintenanceDueDate,'') = '' THEN ' ' ELSE MaintenanceDueDate END AS MaintenanceDueDate, CASE WHEN ISNULL(MaintenanaceMonthYear,'') = '' THEN ' ' ELSE MaintenanaceMonthYear END AS MaintenanaceMonthYear, CASE WHEN ISNULL(MaintenanceAmount,'') = '' THEN ' ' ELSE MaintenanceAmount END AS MaintenanceAmount, CASE WHEN ISNULL(MaintenancePenaltyAmount,'') = '' THEN ' ' ELSE MaintenancePenaltyAmount END MaintenancePenaltyAmount, SUBSTRING(MaintenancePostDate, 1, 10) AS MaintenancePostDate FROM Maintenances WHERE MaintenanceApartmentId='" + apartmentId + "' ORDER BY MaintenancePostDate", con);
        dt = new DataTable("Maintenances");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_Service_Select()
    {
        da = new SqlDataAdapter("SELECT * FROM ServiceProviders", con);
        dt = new DataTable("ServiceProviders");
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable Users_Meetings_Select(string apartmentId)
    {
        dt = new DataTable("Meetings");
        da = new SqlDataAdapter("SELECT CASE WHEN ISNULL(Meetingtitle,'') = '' THEN ' ' ELSE Meetingtitle END AS Meetingtitle, CASE WHEN ISNULL(MeetingMessage,'') = '' THEN ' ' ELSE MeetingMessage END AS MeetingMessage, CASE WHEN ISNULL(MeetingAddress,'') = '' THEN ' ' ELSE MeetingAddress END AS MeetingAddress, CASE WHEN ISNULL(MeetingDate,'') = '' THEN ' ' ELSE MeetingDate END AS MeetingDate, CASE WHEN ISNULL(MeetingTime,'') = '' THEN ' ' ELSE MeetingTime END AS MeetingTime, MeetingImportant FROM Meetings WHERE MeetingApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable Users_Complaints_Select(string userId)
    {
        dt = new DataTable("Complaints");
        da = new SqlDataAdapter("SELECT ComplaintId, ComplaintTitle, ComplaintMessage, ComplaintTime, ComplaintDate FROM Complaints WHERE ComplaintUserId='" + userId + "' ORDER BY ComplaintDate DESC, ComplaintTime DESC", con);
        da.Fill(dt);
        /*foreach (var DataRow in dt.Rows)
        {
            DataRow["ComplaintTitle"] = DataRow["ComplaintTitle"].ToString();
        }*/
        foreach (DataRow dr in dt.Rows)
        {
            dr["ComplaintTitle"] = HTMLDecoder(dr["ComplaintTitle"].ToString());
            dr["ComplaintMessage"] = HTMLDecoder(dr["ComplaintMessage"].ToString());
        }
        dt.AcceptChanges();
        return dt;
    }

    [WebMethod]
    public void Users_Complaints_Insert(string userId, string title, string message, string adminId, string apartmentId)
    {
        title = HTMLEncoder(title);
        message = HTMLEncoder(message);
        cmd = new SqlCommand("INSERT INTO Complaints(ComplaintTitle, ComplaintMessage, ComplaintDate, ComplaintTime, ComplaintUserId, ComplaintAdminId, ComplaintApartmentId) VALUES('" + title + "','" + message + "', '" + DateTime.Now.ToString("dd/MM/yyyy") + "', '" + DateTime.Now.ToString("HH:mm") + "','" + userId + "','" + adminId + "', '" + apartmentId + "')", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }
    [WebMethod]
    public void Users_Complaints_Update(string complaintId, string title, string message)
    {
        title = HTMLEncoder(title);
        message = HTMLEncoder(message);
        cmd = new SqlCommand("UPDATE Complaints SET ComplaintTitle='" + title + "', ComplaintMessage='" + message + "', ComplaintDate='" + DateTime.Now.ToString("dd-MM-yyyy") + "', ComplaintTime='" + DateTime.Now.ToString("HH:mm") + "' WHERE ComplaintId='" + complaintId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }
    [WebMethod]
    public void Users_Complaints_Delete(string complaintId)
    {
        cmd = new SqlCommand("DELETE FROM Complaints WHERE ComplaintId='" + complaintId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    [WebMethod]
    public DataTable Users_FlatHolders_Select(string apartmentId)
    {
        dt = new DataTable("Users");
        da = new SqlDataAdapter("SELECT UserName, UserAdminId, (SELECT TOP 1 ApartmentWingName FROM Apartments WHERE ApartmentId = UserApartmentId) + '-' + UserFlatNumber AS UserFlatNumber, CASE WHEN ISNULL(UserProfilePicture,'') = '' THEN 'defaultUser.png' ELSE UserProfilePicture END AS UserProfilePicture FROM Users WHERE UserApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable Users_ServicesType_Select()
    {
        dt = new DataTable("Services");
        da = new SqlDataAdapter("SELECT ServiceTypeId, ServiceTypeName, ServiceTypeImagePhone FROM ServiceTypes", con);
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable UsersServicesTypeSelect()
    {
        dt = new DataTable("Services");
        da = new SqlDataAdapter("SELECT ServiceTypeId, ServiceTypeName, ServiceTypeImagePhone FROM ServiceTypes", con);
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_ServicesProviders_Select(string adminId, string typeId)
    {
        dt = new DataTable("Services");
        da = new SqlDataAdapter("SELECT SPName, SPContactNumber FROM ServiceProviders WHERE SPTypeId='" + typeId + "' AND SPAdminId='" + adminId + "'", con);
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public DataTable Users_Profile_Select(string userId)
    {
        da = new SqlDataAdapter("SELECT UserName, UserEmailid, UserMobile, UserGender, UserProfilePicture,(SELECT TOP 1 ApartmentWingName FROM Apartments WHERE ApartmentId = UserApartmentId) + '-' + UserFlatNumber AS UserFlatNumber FROM Users WHERE UserId='" + userId + "'", con);
        dt = new DataTable("Users");
        da.Fill(dt);
        if (dt.Rows[0]["UserProfilePicture"].ToString().Equals(String.Empty) || dt.Rows[0]["UserProfilePicture"].ToString().Equals(null) || dt.Rows[0]["UserProfilePicture"].ToString() == null)
        {
            dt.Rows[0]["UserProfilePicture"] = "defaultUser.png";
        }
        dt.AcceptChanges();
        return dt;
    }

    [WebMethod]
    public void Users_Profile_Update(string userId, string name, string emailId, string mobile, string gender)
    {
        cmd = new SqlCommand("UPDATE Users SET UserName='" + name + "', UserEmailid='" + emailId + "', UserMobile='" + mobile + "', UserGender='" + gender + "' WHERE UserId='" + userId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }
    [WebMethod]
    public void Users_ProfilePicture_Delete(string userId)
    {
        cmd = new SqlCommand("UPDATE Users SET UserProfilePicture=null WHERE UserId='" + userId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    // Guest Users activity's Services
    [WebMethod]
    public DataTable GuestUser_Profile_Select(string guestId)
    {
        da = new SqlDataAdapter("SELECT GUName, GUEmailid, GUMobile FROM GuestUser WHERE GUId='" + guestId + "'", con);
        dt = new DataTable("GuestUsers");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public void GuestUser_Profile_Update(string guestId, string name, string emailId, string mobile)
    {
        cmd = new SqlCommand("UPDATE GuestUser SET GUName='" + name + "', GUEmailid='" + emailId + "', GUMobile='" + mobile + "' WHERE GUId='" + guestId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    [WebMethod]
    public DataTable GuestUser_FlatHolder_Select(string apartmentId)
    {
        dt = new DataTable("Users");
        //da = new SqlDataAdapter("SELECT UserName, UserProfilePicture, UserFlatNumber FROM Users WHERE UserAdminId='" + adminId + "'", con);
        da = new SqlDataAdapter("SELECT UserName, CASE WHEN ISNULL(UserProfilePicture,'') = '' THEN 'defaultUser.png' ELSE UserProfilePicture END AS UserProfilePicture, (SELECT TOP 1 ApartmentWingName FROM Apartments WHERE ApartmentId = UserApartmentId) + '-' + UserFlatNumber AS UserFlatNumber, UserApartmentId FROM Users WHERE UserApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);

        return dt;
    }

    [WebMethod]
    public DataTable GuestUser_Society_Details(string apartmentId)
    {
        dt = new DataTable("Apartments");
        da = new SqlDataAdapter("SELECT ApartmentName, ApartmentWingName, ApartmentAddress, ApartmentPincode, ApartmentCity FROM Apartments WHERE ApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable GuestUser_RoadMap_Select(string apartmentId)
    {
        dt = new DataTable("Roadmaps");
        da = new SqlDataAdapter("SELECT RoadMapImage FROM RoadMap WHERE RoadMapApartmentId='" + apartmentId + "'", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public DataTable GuestUser_AdminDetails_Select(string adminId)
    {
        dt = new DataTable("Admins");
        da = new SqlDataAdapter("SELECT AdminName, AdminEmailid, AdminMobileNumber, AdminProfilePicture, (SELECT TOP 1 ApartmentWingName FROM Apartments WHERE ApartmentId = AdminApartmentId) + '-' + AdminFlatNumber AS AdminFlatNumber FROM Admins WHERE AdminApartmentId='" + adminId + "'", con);
        da.Fill(dt);
        if (dt.Rows[0]["AdminProfilePicture"].Equals(null) || dt.Rows[0]["AdminProfilePicture"].Equals(string.Empty) || dt.Rows[0]["AdminProfilePicture"].Equals(""))
        {
            dt.Rows[0]["AdminProfilePicture"] = "defaultUser.png";
            dt.AcceptChanges();
        }
        return dt;
    }

    /*[WebMethod]
    public DataTable GuseUser_Society_Details(string adminId)
    {
        dt = new DataTable("SocietyDetails");
        da = new SqlDataAdapter("SELECT ");
        return dt;
    }*/
    [WebMethod]
    public DataTable GuestUser_Registration_Select()
    {
        dt = new DataTable("Apartments");
        da = new SqlDataAdapter("SELECT ApartmentId, ApartmentWingName + '-' + ApartmentName  + ', ' + ApartmentCity + '-' + ApartmentPincode AS ApartmentDetails, (SELECT TOP 1 AdminId FROM Admins WHERE AdminApartmentId=ApartmentId) AS AdminId FROM Apartments", con);
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public string GuestUser_Registration_Insert(string name, string emailId, string password, string mobile, string apartmentId, string adminId)
    {
        name = HTMLEncoder(name);
        emailId = HTMLEncoder(emailId);
        password = HTMLEncoder(password);
        mobile = HTMLEncoder(mobile);
        apartmentId = HTMLEncoder(apartmentId);
        cmd = new SqlCommand("INSERT INTO GuestUser(GUName, GUEmailid, GUPassword, GUApartmentId, GUMobile, GUAdminId) VALUES('" + name + "','" + emailId + "','" + password + "','" + apartmentId + "','" + mobile + "', '" + adminId + "')", con);
        con.Open();
        return cmd.ExecuteNonQuery().ToString();
    }

    [WebMethod]
    public DataTable GuestUser_ChangePassword_Select(string guId)
    {
        da = new SqlDataAdapter("SELECT GUPassword FROM GuestUser WHERE GUId='" + guId + "'", con);
        dt = new DataTable("GuestUser");
        da.Fill(dt);
        return dt;
    }

    [WebMethod]
    public void GuestUser_ChangePassword_Update(string guId, string password)
    {
        cmd = new SqlCommand("UPDATE GuestUser SET GUPassword='" + password + "' WHERE GUId='" + guId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    [WebMethod]
    public string GuestUser_ForgotPassword_Select(string emailId)
    {

        da = new SqlDataAdapter("SELECT TOP 1 GUEmailid FROM GuestUser WHERE GUEmailid='" + emailId + "'", con);
        dt = new DataTable("GuestUser");
        da.Fill(dt);
        try
        {
            //if (dt.Rows.Count == 1 || dt.Rows.Count > 0)
            {
                //  return SendEmail(emailId);
            }
        }
        catch (Exception e)
        {
            Console.Write(e);
        }
        return "";
    }

    [WebMethod]
    public void GuestUser_ForgotPassword_Update(string emailId, string password)
    {
        cmd = new SqlCommand("UPDATE GuestUser SET GUPassword='" + password + "' WHERE GUEmailid='" + emailId + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }

    [WebMethod]
    public DataTable GuestUser_Login_Select(string emailId, string password)
    {
        da = new SqlDataAdapter("SELECT GUId, GUEmailid, GUPassword, GUAdminId, GUApartmentId FROM GuestUser WHERE GUEmailid='" + emailId + "' and GUPassword='" + password + "'", con);
        dt = new DataTable("GuestUser");
        da.Fill(dt);
        return dt;
    }
    [WebMethod]
    public String SendEmail(string EmailId)
    {
        string otp = GenerateOTP();
        using (MailMessage mm = new MailMessage("sutofficialindia@gmail.com", EmailId))
        {
            string htmlFile = File.ReadAllText(HttpContext.Current.Server.MapPath("~/emailOTPTemplate.html"));
            htmlFile = htmlFile.Replace("$['txtHeader']", "Forgot Password? Use the following OTP to confirm your email address and reset your password");
            htmlFile = htmlFile.Replace("$['txtOTP']", otp);
            htmlFile = htmlFile.Replace("$['txtFooter']", "We've received your forgot password request. To reset your password use above OTP code to website. If you didn't request forgot password, ignore this email.");
            mm.Subject = "Email Varification";
            mm.Body = htmlFile;
            mm.IsBodyHtml = true;
            SmtpClient smtp = new SmtpClient();
            smtp.Host = "smtp.gmail.com";
            smtp.EnableSsl = true;
            NetworkCredential NetworkCred = new NetworkCredential("sutofficialindia@gmail.com", "sutadmin@123");
            smtp.UseDefaultCredentials = true;
            smtp.Credentials = NetworkCred;
            smtp.Port = 587;
            smtp.Send(mm);
        }
        /* dt = new DataTable("OTP");
         dt.Rows.Add(row);*/
        return otp;
    }

    private string GenerateOTP()
    {
        string alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        string numbers = "1234567890";

        string characters = numbers;

        characters += alphabets + numbers;
        string otp = string.Empty;
        for (int i = 0; i < 6; i++)
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

    protected string HTMLEncoder(string str)
    {
        return HttpUtility.HtmlEncode(str);
    }

    protected string HTMLDecoder(string str)
    {
        return HttpUtility.HtmlDecode(str);
    }
    /*[WebMethod]
    public void Profile_insert(string UserId)
    {
        SqlConnection con = new SqlConnection(strCon);
        SqlCommand cmd = new SqlCommand("insert into Users()", con);

        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
    }*/

    [WebMethod]
    public string uploadimages(string uploadImage, string userId, string isImageUpload)
    {
        String message = " ";
        if (isImageUpload == "true")
        {
            byte[] bytes = Convert.FromBase64String(uploadImage);
            string FileName = userId + DateTime.Now.ToString("ddMMyyyyhhmmss") + ".jpg";
            string filePath = Server.MapPath("~/siteimages/profilePictures/" + FileName);
            File.WriteAllBytes(filePath, bytes);
            cmd = new SqlCommand("UPDATE Users SET UserProfilePicture='" + FileName + "' WHERE UserId='" + userId + "'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            message = "Image updated";
        }
        return message;
    }

}
