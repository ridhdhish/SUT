<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="EditAdmin.aspx.cs" Inherits="EditAdmin" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">

    <!-- Bootstrap Tagsinput Css -->
    <link rel="stylesheet" href="assets/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css">
    <!-- Bootstrap Select Css -->
    <link rel="stylesheet" href="assets/plugins/bootstrap-select/css/bootstrap-select.css" />
    <!-- noUISlider Css -->
    <link rel="stylesheet" href="assets/plugins/nouislider/nouislider.min.css" />

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

    <section class="content profile-page">
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-12 col-md-12 col-sm-12">
                                    <h2>Profile</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.html"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Profile</a></li>
                                        <li class="breadcrumb-item active">Edit Profile</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <div class="header">
                                        <img src="assets/images/profile_av.jpg" class="user_pic rounded img-raised" alt="User">
                                        <div class="detail"                                            
                                            <div id="m_area_chart"></div>
                                        </div>
                                        <div>
                                            <label for="FirstName">First Name</label>
                                            <div class="form-group">
                                                <input type="text" id="FirstName" class="form-control">
                                            </div>
                                            <label for="LastName">Last Name</label>
                                            <div class="form-group">
                                                <input type="text" id="LastName" class="form-control">
                                            </div>
                                            <label for="email_address">Email Address*</label>
                                            <div class="demo-masked-input">
                                                <div class="row clearfix">
                                                    <div class="col-lg-12 col-md-6">
                                                        <div class="input-group">
                                                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                                                            <input type="text" class="form-control email"  placeholder="Ex: example@example.com">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <label for="PhoneNumber">Phone Number</label>
                                            <div class="demo-masked-input">
                                                <div class="row clearfix">
                                                    <div class="col-lg-12 col-md-6">
                                                        <div class="input-group">
                                                            <span class="input-group-addon"><i class="zmdi zmdi-smartphone"></i></span>
                                                            <input type="text" class="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>                                           
                                             <label for="SocietyName">Society Name</label>
                                            <div class="form-group">
                                                <input type="text" id="SocietyName" class="form-control">
                                            </div>                                            
                                             <label for="AdminSociertyPincode">Admin Socierty Pincode</label>
                                            <div class="form-group">
                                                <input type="text" id="AdminSociertyPincode" class="form-control">
                                            </div>
                                             <div class="form-group">
                                                <div class="radio inlineblock m-r-20">
                                                    <input type="radio" name="gender" id="male" class="with-gap" value="option1" checked="">
                                                    <label for="male">Male</label>
                                                </div>
                                                <div class="radio inlineblock">
                                                    <input type="radio" name="gender" id="Female" class="with-gap" value="option2">
                                                    <label for="Female">Female</label>
                                                </div>
                                            </div>
                                            
                                            <div class="col-sm-8">
                                                <button class="btn btn-primary btn-round waves-effect   ">Save Changes</button>                                                
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script src="assets/bundles/libscripts.bundle.js"></script>
    <script src="assets/bundles/vendorscripts.bundle.js"></script>
    <script src="assets/bundles/mainscripts.bundle.js"></script>
    <script src="assets/js/pages/forms/advanced-form-elements.js"></script>
</asp:Content>

