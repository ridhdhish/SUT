<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="AddUser.aspx.cs" Inherits="AddUser" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <style>
        label{
            font-weight: 400;
        }
    </style>
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <!-- Main Content -->
    <section class="content">
        <div class="container">
            <!-- Location Card -->
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <h2>Flat-Holders</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Flat-Holders</a></li>
                                        <li class="breadcrumb-item active" id="lblAddFH">Add Flat-Holder</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button class="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnViewUser" Text="View All Flat-Holders" OnClick="btnViewUser_Click" runat="server"></asp:Button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <div class="header">
                                    <h2><strong id="lblHeadingAddFH" runat="server">Add</strong> Flat-Holder</h2>
                                </div>
                                <div class="body block-header">
                                    <b>Name<b class="text-danger">*</b></b>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtName" CssClass="form-control" placeholder="Name" runat="server" />
                                        <%--<input type="text" id="FirstName" class="form-control" placeholder="Enter User's First Name">--%>
                                    </div>
                                    <b>Email Address<b class="text-danger">*</b></label>
                                    <div class="demo-masked-input">
                                        <div class="row clearfix">
                                            <div class="col-lg-12 col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                                                    <asp:TextBox ID="txtEmail" CssClass="form-control email" placeholder="Ex: example@example.com" runat="server" />
                                                    <%--<input type="text" class="form-control email" placeholder="Ex: example@example.com" >--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <b>Password<b class="text-danger">*</b></label>
                                    <div class="form-group form-float">
                                        <asp:TextBox ID="txtPassword" TextMode="Password" CssClass="form-control" name="minmaxlength" MaxLength="32" minlength="6" runat="server" />
                                        <%--<input type="text" class="form-control" name="minmaxlength" maxlength="32" minlength="6" required>--%>
                                        <div class="help-info">Min. 6, Max. 32 characters</div>
                                    </div>
                                    <b>Phone Number<b class="text-danger">*</b></label>
                                    <div class="demo-masked-input">
                                        <div class="row clearfix">
                                            <div class="col-lg-12 col-md-6">
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="zmdi zmdi-smartphone"></i></span>
                                                    <asp:TextBox ID="txtMobileNumber" CssClass="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00" runat="server" />
                                                    <%--<input type="text" class="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00">--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <b>Flat Number<b class="text-danger">*</b></label>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtFlatNumber" CssClass="form-control" placeholder="Flat Number" runat="server" />
                                        <%--<input type="text" id="FlatNumber" class="form-control" placeholder="Enter User's Flat Number" />--%>
                                    </div>
                                    <div class="form-group">
                                        <div class="radio inlineblock m-r-20">
                                            <asp:RadioButton ID="rdbMale" CssClass="with-gap" Text="Male" GroupName="gender" Checked="false" runat="server" />
                                            <%--<input type="radio" name="gender" id="male" class="with-gap" value="option1" checked="" />--%>
                                            <%--<label for="male">Male</label>--%>
                                        </div>
                                        <div class="radio inlineblock">
                                            <asp:RadioButton ID="rdbFemale" CssClass="with-gap" Text="Female" GroupName="gender" Checked="false" runat="server" />
                                            <%--<input type="radio" name="gender" id="Female" class="with-gap" value="option2" />--%>
                                            <%--<label for="Female">Female</label>--%>
                                        </div>
                                    </div>
                                    <div class="col-sm-8">
                                        <asp:Button Text="Add User" ID="btnAddUser" CssClass="btn btn-primary btn-round btn-simple" runat="server" OnClick="btnSubmit_Click" />
                                        <%--<asp:Button Text="Add" CssClass="btn btn-success btn-round waves-effect btn-lg" runat="server" />--%>
                                    </div>
                                    <asp:Label Text="" ID="lblMessege" runat="server" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script>
        $(document).ready(function () {
            $(".sa-confirm-button-container .confirm").click(function () {
                window.location = "AddUser.aspx";
            });
        });
    </script>
</asp:Content>

