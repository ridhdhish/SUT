<%@ Page Title="" Language="C#" MasterPageFile="~/Login.master" AutoEventWireup="true" CodeFile="VerifyRegistration.aspx.cs" Inherits="VerifyRegistration" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Body1" ContentPlaceHolderID="body1" runat="Server">
    <h3><strong>SUT-</strong> Secretary Utility Tools</h3>
    <p>SUT is a designed for the secretary of society or apartment. SUT makes secretary's life very easy by managing secretary's daily work like managing  maintanace, make any Announcement, inform about meeting, organize function etc.. </p>
</asp:Content>
<asp:Content ID="Body2" ContentPlaceHolderID="body2" runat="Server">
    <div class="col-lg-5 col-md-12 offset-lg-1">
        <div class="card-plain">
            <div class="header">
                <h5>Verify Registration</h5>
            </div>
            <div class="input-group" id="divVerify" runat="server">
                <asp:TextBox type="text" class="form-control" placeholder="Enter OTP" OnTextChanged="txtOTP_TextChanged" ID="txtOTP" runat="server" />
                <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>
            </div>
            <div id="divSend" runat="server">
                <div class="input-group" id="divEmailId" runat="server">
                    <asp:TextBox type="text" class="form-control" placeholder="Email Id" ID="txtEmail" runat="server" />
                    <span class="input-group-addon"><i class="zmdi zmdi-account-circle"></i></span>
                </div>
                <div class="input-group" id="divPassword" runat="server">
                    <asp:TextBox type="password" placeholder="Password" class="form-control" ID="txtPassword" runat="server" />
                    <span class="input-group-addon"><i class="zmdi zmdi-lock"></i></span>
                </div>
            </div>
            <div class="footer">
                <asp:Button Text="Verify OTP" CssClass="btn btn-primary btn-round btn-block" ID="btnVerify" OnClick="btnVerify_Click" runat="server" />
                <asp:Button Text="Send OTP" CssClass="btn btn-primary btn-round btn-block" ID="btnSend" OnClick="btnSend_Click" runat="server" />
            </div>
            <asp:Label Text="" runat="server" ID="lblEmailSent" /><br />
            <asp:LinkButton runat="server" ID="lnklblResendOTP" OnClick="lnklblResendOTP_Click">Resend OTP</asp:LinkButton><br />
            <a href="ForgotPassword.aspx" class="link">Forgot Password?</a>
        </div>
        <asp:HiddenField ID="hdnVerified" runat="server"/>
    </div>
    <div class="footer">
        <hr />
        <ul class="list-unstyled">
            <li><a href="SignIn.aspx" target="_self">Sign In</a></li>
        </ul>
    </div>
</asp:Content>

