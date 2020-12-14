<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="profile.aspx.cs" ClientIDMode="Static" Inherits="profile" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <style>
        .profile-pic {
            position: relative;
            display: inline-block;
        }

            .profile-pic:hover .edit {
                display: block;
            }

        .edit {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            position: absolute;
            display: none;
            text-align: center;
            align-content: center;
            vertical-align: middle;
        }

        .divUpload {
            text-align: center;
        }

        .material-icons {
            outline: none;
            vertical-align: middle;
        }

        material-icons:active {
            outline: none;
            color: #1ab1e3;
            vertical-align: middle;
        }

        #btnEdit.btn, #btnDelete.btn {
            width: fit-content;
            padding: 0;
        }
    </style>

</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
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
                                        <li class="breadcrumb-item active">Profile</li>
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
                                    <div class="profile-pic">
                                        <asp:Image ImageUrl="siteimages/profilePictures/defaultUser.png" ID="imgProfilePictureP" Width="150px" Height="150px" CssClass="user_pic rounded img-raised" runat="server" />
                                        <div class="edit">
                                            <div class="demo-google-material-icon center" id="divEditDelete" runat="server">
                                                <button class="bg-transparent btn btn-simple btn-primary border-0" id="btnEdit"><i class="material-icons">edit</i></button>
                                                <asp:LinkButton class="bg-transparent btn btn-simple btn-primary border-0" ID="btnDelete" OnClick="btnDelete_Click" runat="server"><i class="material-icons">delete</i></asp:LinkButton>
                                            </div>
                                            <div class="demo-google-material-icon" id="divUpload" runat="server">
                                                <button class="bg-transparent btn btn-simple btn-primary border-0" id="btnUpload"><i class="material-icons">file_upload</i>Upload</button>
                                            </div>
                                        </div>
                                    </div>
                                    <asp:FileUpload ID="imgUpload" runat="server" Style="display: none" accept="image/*" onchange="imgUploadp(this);" />
                                    <div class="header">
                                        <div class="mt-3">
                                            <div class="row">
                                                <label for="txtName"><b>Name</b></label>
                                                <asp:Label Text="Name" ID="lblName" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="form-group" id="divName" runat="server">
                                                <asp:TextBox ID="txtName" placeholder="Name" CssClass="form-control" runat="server" />
                                            </div>
                                            <div class="row">
                                                <label for="txtEmailAddress"><b>Email Address</b></label>
                                                <asp:Label Text="Email Address" ID="lblEmailAddress" CssClass="ml-4" runat="server" />
                                            </div>
                                            <%--<div class="demo-masked-input" id="divEmailAddress" runat="server">
                                                <div class="row clearfix">
                                                    <div class="col-lg-12 col-md-6">
                                                        <div class="input-group">
                                                            <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                                                            <asp:TextBox ID="txtEmailAddress" placeholder="Email Address" CssClass="form-control email" runat="server" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>--%>
                                            <div class="row">
                                                <label for="txtPhoneNumber"><b>Phone Number</b></label>
                                                <asp:Label Text="Phone Number" ID="lblPhoneNumber" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="demo-masked-input" id="divPhoneNumber" runat="server">
                                                <div class="row clearfix">
                                                    <div class="col-lg-12 col-md-6">
                                                        <div class="input-group">
                                                            <span class="input-group-addon"><i class="zmdi zmdi-smartphone"></i></span>
                                                            <asp:TextBox ID="txtPhoneNumber" CssClass="form-control mobile-phone-number" placeholder="Phone Number" runat="server" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label><b>Gender</b></label>
                                                <asp:Label Text="Male" ID="lblGender" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="form-group" id="divGender" runat="server">
                                                <div class="radio inlineblock m-r-20">
                                                    <asp:RadioButton ID="rdbMale" CssClass="with-gap" Text="Male" GroupName="gender" Checked="false" runat="server" />
                                                </div>
                                                <div class="radio inlineblock">
                                                    <asp:RadioButton ID="rdbFemale" CssClass="with-gap" Text="Female" GroupName="gender" Checked="false" runat="server" />
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label><b>Society Name</b></label>
                                                <asp:Label Text="Society Name" ID="lblSocietyName" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="row">
                                                <label><b>Society Address</b></label>
                                                <asp:Label Text="Society Address" ID="lblSocietyAddress" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="row">
                                                <label><b>Society Pincode</b></label>
                                                <asp:Label Text="Society Pincode" ID="lblSocietyPincode" CssClass="ml-4" runat="server" />
                                            </div>
                                            <div class="col-sm-8">
                                                <asp:Button Text="Edit Profile" ID="btnUpdate" CssClass="btn btn-primary btn-round btn-simple" OnClick="btnUpdate_Click" runat="server" />
                                                <asp:Button Text="Save Data" ID="btnSaveData" CssClass="btn btn-primary btn-round btn-simple" OnClick="btnSaveData_Click" runat="server" />
                                                <asp:Button Text="Cancel" ID="btnCancel" CssClass="btn btn-primary btn-round btn-simple" OnClick="btnCancel_Click" runat="server" />
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
        <asp:HiddenField ID="hdnProfilePicture" runat="server" />
    </section>
    <script>
        $(document).ready(function () {
            //$("#divEditDelete").hide();
            $("#btnEdit").click(function () {
                $("#imgUpload").trigger('click');
                return false;
            });
            $("#btnUpload").click(function () {
                $("#imgUpload").trigger('click');
                return false;
            });
        });

        function imgUploadp(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#imgProfilePictureP')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(200);
                    //$("#divUpload").hide();
                    //$("#divEditDelete").show();
                };

                reader.readAsDataURL(input.files[0]);
            }
        }

    </script>
</asp:Content>
