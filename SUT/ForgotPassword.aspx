<%@ Page Title="" Language="C#" MasterPageFile="~/Login.master" AutoEventWireup="true" CodeFile="ForgotPassword.aspx.cs" Inherits="ForgotPassword" %>



<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">

    <div class="authentication">
        <div class="container">
            <div class="col-md-12 content-center">
                <div class="row clearfix">
                    <div class="col-lg-6 col-md-12">
                        <div class="company_detail">
                            <h4 class="logo">
                                <img src="/assets/images/logo.svg" />
                                SUT</h4>
                            <h3>Trouble Logging In?</h3>
                            <p>Don't stress out. Enter your email and we'll send you OTP. Enter sent OTP to confirm and you can reset your password.</p>
                        </div>
                    </div>
                    <div class="col-lg-5 col-md-12 offset-lg-1">
                        <div class="card-plain">
                            <div class="header">
                                <h5>Forgot Password?</h5>
                            </div>
                            <div class="input-group" id="divEmailId" runat="server">
                                <asp:TextBox CssClass="form-control" placeholder="Enter Email" ID="txtEmail" runat="server" />
                                <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                            </div>
                            <div id="divVerify" runat="server">
                                <asp:Label Text="" ID="lblEmailSent" runat="server" />
                                <div class="input-group">

                                    <asp:TextBox type="text" class="form-control" placeholder="Enter OTP" ID="txtOTP" runat="server" />
                                    <span class="input-group-addon"><i class="zmdi zmdi-key"></i></span>
                                </div>
                            </div>
                            <div id="divResetPassword" runat="server">
                                <div class="input-group">
                                    <asp:TextBox type="password" placeholder="Password" class="form-control" ID="txtPassword" runat="server" />
                                </div>
                                <div class="input-group">
                                    <asp:TextBox type="password" placeholder="Confirm Password" class="form-control" ID="txtConfirmPassword" runat="server" />
                                </div>
                            </div>
                            <asp:Label Text="" ID="lblSuccess" runat="server" />
                            <asp:Label Text="" ID="lblError" CssClass="text-danger" runat="server" />
                            <asp:LinkButton Text="Sign In" ID="lnkbtnSignIn" OnClick="lnkbtnSignIn_Click" runat="server" />
                            <br />
                            <asp:LinkButton Text="Resend" ID="lnklblResendOTP" OnClick="lnklblResendOTP_Click" runat="server" />
                            <div class="footer" id="divFooter" runat="server">
                                <asp:Button Text="Verify OTP" CssClass="btn btn-primary btn-round btn-block" ID="btnVerify" OnClick="btnVerify_Click" runat="server" />
                                <asp:Button Text="Confirm Password" CssClass="btn btn-primary btn-simple btn-round btn-block" ID="btnConfirmPassword" OnClick="btnConfirmPassword_Click" runat="server" />
                                <asp:Button Text="Send OTP" CssClass="btn btn-primary btn-simple btn-round btn-block" ID="btnSendOTP" OnClick="btnSendOTP_Click" runat="server" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="particles-js"></div>
    </div>
    <script src="assets/bundles/libscripts.bundle.js"></script>
    <script src="assets/bundles/vendorscripts.bundle.js"></script>
    <script src="assets/plugins/particles-js/particles.min.js"></script>
    <script src="assets/plugins/particles-js/particles.js"></script>
</asp:Content>
