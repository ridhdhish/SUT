<%@ Page Title="" Language="C#" MasterPageFile="~/Login.master" AutoEventWireup="true" CodeFile="Registration.aspx.cs" Inherits="Registration" %>


<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <title>SUT- Secretary Utility Tools</title>
    <style>
        a.link {
            color: #007bff !important;
            text-decoration: underline !important;
        }

        a > i:hover {
            text-shadow: 0 0px 2px #1AB1E3;
        }
    </style>
    <script>
        $(document).ready(function () {
            $("#divPersonalDetails").fadeIn();
            $("#divApartmentDetails").fadeOut();
            $("#btnSignUp").fadeOut();
            $("#divAddressDetails").fadeOut();

            $("#arrow-right").click(function () {
                if ($("#txtName").val() == "" || $("#txtEmail").val() == "" || $("#txtPassword").val() == "" || $("#txtMobile").val() == "") {
                   //    alert("Fill all details to countinue");
                    $("#divPersonalDetails").fadeOut();
                    $("#divApartmentDetails").fadeIn();
                    $("#btnSignUp").fadeOut();
                    $("#divAddressDetails").fadeOut();
                    //$("#divPersonalDetails").fadeOut();
                    //$("#divApartmentDetails").fadeIn();
                
                } else {
                    $("#divPersonalDetails").fadeOut();
                    $("#divApartmentDetails").fadeIn();
                    $("#btnSignUp").fadeOut();
                    $("#divAddressDetails").fadeOut();
                }
            });

            $("#arrow-left").click(function () {
                $("#divPersonalDetails").fadeIn();
                $("#divApartmentDetails").fadeOut();
                $("#btnSignUp").fadeOut();
                $("#divAddressDetails").fadeOut();
            });

            $("#arrow-right-right").click(function () {
                //if ($("#txtFlatNumber").val() == "" || $("#txtWingName").val() == "" || $("#txtApartmentName").val() == "") {
                //    alert("Fill all details to countinue");
                //}
                //else {
                    $("#divPersonalDetails").fadeOut();
                    $("#divApartmentDetails").fadeOut();
                    $("#btnSignUp").fadeIn();
                    $("#divAddressDetails").fadeIn();
                //}
            });

            $("#arrow-left-left").click(function () {
                $("#divPersonalDetails").fadeOut();
                $("#divApartmentDetails").fadeIn();
                $("#btnSignUp").fadeOut();
                $("#divAddressDetails").fadeOut();
            });

            var $icon = $(".float-right");
            var $arrow = $('.arrow-right');

            $icon.click(function () {
                $arrow.animate([
                    { left: '0' },
                    { left: '10px' },
                    { left: '0' }
                ], {
                        duration: 700,
                        iterations: Infinity
                    });

            });
        });
    </script>

    <style>
        .icon {
            cursor: pointer;
        }

        .arrow {
            position: relative;
            display: inline-flex;
            cursor: pointer;
            vertical-align: middle;
            animation-timing-function: linear;
        }
    </style>
</asp:Content>
<asp:Content ID="Body1" ContentPlaceHolderID="body1" runat="Server">
    <h3><strong>SUT-</strong> Secretary Utility Tools</h3>
    <p>SUT is a designed for the secretary of society or apartment. SUT makes secretary's life very easy by managing secretary's daily work like managing  maintanace, make any Announcement, inform about meeting, organize function etc.. </p>
</asp:Content>
<asp:Content ID="Body2" ContentPlaceHolderID="body2" runat="Server">
    <div class="col-lg-5 col-md-12 offset-lg-1">
        <div class="card-plain">
            <div class="header">
                <h5>Registration</h5>
            </div>

            <div id="divPersonalDetails">
                <h6>Personal Details</h6>
                <div class="input-group">
                    <span class="input-group-addon"><i class="zmdi zmdi-account-circle"></i></span>
                    <asp:TextBox type="text" class="form-control" placeholder="Name" ID="txtName" runat="server" required="required" />
                </div>
                <div class="demo-masked-input">
                    <div class="row clearfix">
                        <div class="col-lg-12 col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="zmdi zmdi-email"></i></span>
                                <asp:TextBox type="text" class="form-control email" placeholder="Email Id" ID="txtEmail" runat="server" required="required" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="input-group">
                    <span class="input-group-addon"><i class="zmdi zmdi-lock"></i></span>
                    <asp:TextBox type="password" placeholder="Password" class="form-control" MaxLength="32" ID="txtPassword" runat="server" required="required" />
                </div>

                <div class="demo-masked-input">
                    <div class="row clearfix">
                        <div class="col-lg-12 col-md-6">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="zmdi zmdi-smartphone"></i></span>
                                <asp:TextBox ID="txtMobileNumber" CssClass="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00" runat="server" required="required" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="input-group">
                    <div class="radio inlineblock m-r-20">
                        <asp:RadioButton ID="rdbMale" CssClass="with-gap" Text="Male" GroupName="gender" Checked="false" runat="server" required="required" />
                    </div>
                    <div class="radio inlineblock">
                        <asp:RadioButton ID="rdbFemale" CssClass="with-gap" Text="Female" GroupName="gender" Checked="false" runat="server" required="required" />
                    </div>
                </div>
                
                <span class="float-right icon">
                    <a href="#" id="arrow-right" style="display: inline-flex; vertical-align: middle;">Next&nbsp;<i class="material-icons">arrow_forward</i>
                    </a>
                </span>
                <%--<div class="float-right icon">
                    <div id="arrow-right" class="arrow">
                        Next&nbsp;<i class="material-icons">arrow_forward</i>
                    </div>
                </div>--%>
            </div>

            <div id="divApartmentDetails" class="slide-now">
                <h6>Apartment Details</h6>
                <div class="input-group">
                    <asp:TextBox type="text" class="form-control" placeholder="Flat Number" ID="txtFlatNumber" runat="server" required="required" />
                </div>

                <div class="input-group">
                    <asp:TextBox type="text" class="form-control" placeholder="Wing Name" ID="txtWingName" runat="server" required="required" />
                </div>

                <div class="input-group">
                    <asp:TextBox type="text" class="form-control" placeholder="Apartment Name" ID="txtApartmentName" runat="server" required="required" />
                </div>

                <span class="float-left icon">
                    <a href="#" id="arrow-left" style="display: inline-flex; vertical-align: middle;"><i class="material-icons" style="width: 30px;">arrow_backward</i>Back
                    </a>
                </span>
                <span class="float-right icon">
                    <a href="#" id="arrow-right-right" style="display: inline-flex; vertical-align: middle;">Next&nbsp;<i class="material-icons">arrow_forward</i>
                    </a>
                </span>
            </div>
            <div id="divAddressDetails">
                <h6>Apartment Address Details</h6>
                <div class="input-group">
                    <asp:TextBox type="text" class="form-control" placeholder="Pincode" MaxLength="6" ID="txtPincode" runat="server" />
                </div>

                <div class="input-group">
                    <asp:TextBox type="text" class="form-control" placeholder="City" ID="txtCity" runat="server" required="required" />
                </div>

                <div class="input-group">
                    <textarea id="txtAddress" rows="3" class="form-control no-resize" placeholder="Address" runat="server" required="required"></textarea>
                </div>

                <span class="float-left icon">
                    <a href="#" id="arrow-left-left" style="display: inline-flex; vertical-align: middle;"><i class="material-icons" style="width: 30px;">arrow_backward</i>Back
                    </a>
                </span>
            </div>
            <div class="footer m-4">
                <asp:Button Text="Sign Up" CssClass="btn btn-primary btn-round btn-block" ID="btnSignUp" OnClick="btnSignUp_Click" runat="server" />
            </div>
            <asp:Label Text="" runat="server" ID="lblInvalid" /><br />
            <a href="forgot-password.html" class="link">Forgot Password?</a><br />
            Already have an account? <a href="SignIn.aspx" target="_self">Sign In</a>
        </div>
    </div>

    <script>
        const $icon = document.querySelector('.icon');
        const $arrow = document.querySelector('.arrow');
        $icon.addEventListener('mouseover', function () {
            $(".arrow").css("position", "relative");
            $arrow.animate([
                { left: '0' },
                { left: '10px' },
                { left: '0' }],
                {
                    duration: 700,
                    iterations: Infinity
                });
        });
        $icon.addEventListener('mouseleave', function () {
            setTimeout(function () {
                $(".arrow").css("position", "static");
            }, 3000);
        });

    </script>
    <%--<style class="cp-pen-styles">
        body {
            background-color: #00b894;
            margin: 0;
            padding: 0;
        }

        .icon {
            position: absolute;x
            top: 50%;
            left: 50%;
            transform: translate(-50%,-50%);
            width: 80px;
            height: 60px;
            cursor: pointer;
        }

        .arrow {
            position: absolute;
            top: 25px;
            width: 90%;
            height: 10px;
            background-color: #fff;
            box-shadow: 0 3px 5px rgba(0, 0, 0, .2);
            animation: arrow 700ms linear infinite;
        }

            .arrow::after {
                content: '';
                position: absolute;
                width: 60%;
                height: 10px;
                top: -12px;
                right: -8px;
                background-color: #fff;
                transform: rotate(45deg);
            }

            .arrow::before {
                content: '';
                position: absolute;
                width: 60%;
                height: 10px;
                top: 12px;
                right: -8px;
                background-color: #fff;
                box-shadow: 0 3px 5px rgba(0, 0, 0, .2);
                transform: rotate(-45deg);
            }
    </style>--%>
    <%--<div class="icon">
        <div class="arrow"></div>
    </div>--%>
</asp:Content>

