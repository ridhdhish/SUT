<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SendSMS.aspx.cs" Inherits="_SendSMS" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <asp:TextBox id="txtPhoneNumber" placeholder="Receiver Phone Number" runat="server" />
            <asp:TextBox id="txtMessage" placeholder="Message" Rows="5" runat="server" />
            <asp:Button Text="Send" ID="btnSend" OnClick="btnSend_Click" runat="server" />
        </div>
    </form>
</body>
</html>
