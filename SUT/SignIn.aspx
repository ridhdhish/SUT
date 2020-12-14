<%@ Page Title="" Language="C#" MasterPageFile="~/Login.master" AutoEventWireup="true" CodeFile="SignIn.aspx.cs" Inherits="SignIn" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <title>Sign In</title>
</asp:Content>
<asp:Content ID="Body1" ContentPlaceHolderID="body1" runat="Server">
    <h3><strong>SUT-</strong> Secretary Utility Tools</h3>
    <p>SUT is a designed for the secretary of society or apartment. SUT makes secretary's life very easy by managing secretary's daily work like managing  maintanace, make any Announcement, inform about meeting, organize function etc.. </p>
</asp:Content>
<asp:Content ID="Body2" ContentPlaceHolderID="body2" runat="Server">
    <div class="col-lg-5 col-md-12 offset-lg-1">
        <div class="card-plain">
            <div class="header">
                <h5>Sign In</h5>
            </div>
            <div class="demo-masked-input">
                    <div class="row clearfix">
                        <div class="col-lg-12 col-md-6">
                            <div class="input-group">
                                <asp:TextBox type="text" class="form-control email" placeholder="Email Id" ID="txtEmail" runat="server" />
                                <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            </div>
                        </div>
                    </div>
                </div>
            <%--<div class="input-group">
                <asp:TextBox type="text" class="form-control" placeholder="Email Id" ID="txtEmail" runat="server" />
                <span class="input-group-addon"><i class="zmdi zmdi-account-circle"></i></span>
            </div>--%>
            <div class="input-group">
                <asp:TextBox type="password" placeholder="Password" class="form-control" ID="txtPassword" MaxLength="32" runat="server" />
                <span class="input-group-addon"><i class="zmdi zmdi-lock"></i></span>
            </div>
            <div class="footer">
                <asp:Button Text="Sign In" CssClass="btn btn-primary btn-round btn-block" ID="btnSignIn" runat="server" OnClick="BtnSignIn_Click" />
            </div>
            <asp:Label Text="" runat="server" ID="lblInvalid" /><br />
            <a href="forgot-password.html" class="link">Forgot Password?</a>
        </div>
    </div>
    <div class="footer">
        <hr />
        <ul class="list-unstyled">
            <li><a href="VerifyRegistration.aspx" target="_self">Verify Registation</a></li>  
            <li><a href="Registration.aspx" target="_self">Registration</a></li>
        </ul>
    </div>
    <asp:HiddenField ID="hdpage" Value='' runat="server" />
</asp:Content>
