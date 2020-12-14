<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="ChangePassword.aspx.cs" Inherits="ChangePassword" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
    <style>
        .sweet-alert > h2{
            font-weight: 400;
        }
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <%-- Location Card --%>
        <div class="row clearfix">
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Change Password</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item active">Change Password</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <div class="card">
                    <div class="header">
                        <h2><strong>Change</strong> Password</h2>
                    </div>
                    <div class="body">
                        <div class="col-sm-5">
                            <div class="form-group">
                                <asp:TextBox CssClass="form-control" TextMode="Password" MaxLength="32" placeholder="Current password" ID="txtCurrentPassword" runat="server" />
                            </div> 
                        </div>
                        <div class="col-sm-5">
                            <div class="form-group">
                                <asp:TextBox CssClass="form-control" ID="txtNewPassword" TextMode="Password" MaxLength="32" placeholder="New password"  runat="server" />
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="form-group">
                                <asp:TextBox CssClass="form-control" ID="txtConfirmNewPassword" TextMode="Password" MaxLength="32" placeholder="Confirm new password"  runat="server" />
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <asp:Button class="btn btn-primary btn-round btn-simple" type="submit" ID="btnChangePassword" Text="Change Password" OnClick="btnChangePassword_Click" runat="server" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
