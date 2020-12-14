<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="services.aspx.cs" Inherits="services" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" href="assets/plugins/bootstrap-select/css/bootstrap-select.css" />
    <style>
        .bootstrap-select .dropdown-toggle:focus {
            outline: none !important;
        }

        td[content="Milkman"]::before {
            background-image: url('https://png.icons8.com/ios/20/000000/milk-bottle-filled.png');
            width: 20px;
            height: 20px;
        }

        .bootstrap-select .btn.btn-round.btn-simple .filter-option, .bootstrap-select.btn-group .dropdown-menu li a {
            text-transform: capitalize;
        }
    </style>
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <div class="row clearfix">
            <%-- Location Card --%>
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12">
                                <h2>Services</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item active">Services</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="card">
                    <div class="header">
                        <h2><strong>Services</strong></h2>
                    </div>
                    <div class="body block-header">
                        <div class="demo-masked-input">
                            <div class="row clearfix">
                                <div class="col-md-3">
                                    <b>Name</b>
                                    <div class="form-group">
                                        <asp:TextBox CssClass="form-control" placehodler="Name" ID="txtName" runat="server" />
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-6">
                                    <b>Phone Number</b>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="zmdi zmdi-phone"></i></span>
                                        <asp:TextBox CssClass="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00" ID="txtContactNumber" runat="server" />
                                   </div>
                                </div>
                                <div class="col-md-4">
                                    <b>Service</b>
                                    <asp:DropDownList CssClass="form-control" ID="ddltype" runat="server">
                                    </asp:DropDownList>
                                </div>
                                <div class="col-md-2">
                                    <asp:Button Text="Add Service" CssClass="btn btn-primary btn-round btn-simple" ID="btnAdd" OnClick="btnAdd_Click" runat="server" />
                                </div>
                            </div>
                        </div>
                        <div class="body table-responsive" id="hidden" runat="server">
                            <table class="table m-b-0">
                                <thead class="thead-light">
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Mobile Number</th>
                                        <th scope="col">Service</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <asp:Repeater ID="repServices" OnItemCommand="repServices_ItemCommand" runat="server">
                                        <ItemTemplate>
                                            <tr>
                                                <th scope="row"><%#Container.ItemIndex+1 %></th>
                                                <td><%#Eval("SPName") %></td>
                                                <td><%#Eval("SPContactNumber") %></td>
                                                <td>
                                                    <img src='siteimages/servicetypeimage/<%#Eval("ServiceTypeImage") %>' width='20px' height='20px' style='margin-right: 15px;' /><%#Eval("ServiceTypeName") %></td>
                                                <td>
                                                    <span class="demo-google-material-icon">
                                                        <asp:LinkButton ID="lnkbtnEdit" runat="server" CommandName="lnkbtnEdit" CommandArgument='<%#Eval("SPId") %>'><i class=" material-icons ">mode_edit</i></asp:LinkButton>
                                                        <asp:LinkButton ID="lnkbtnDelete" runat="server" CommandName="lnkbtnDelete" CommandArgument='<%#Eval("SPId") %>'><i class="material-icons">delete</i></asp:LinkButton>
                                                    </span>
                                                </td>
                                            </tr>
                                        </ItemTemplate>
                                    </asp:Repeater>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
    <script src="assets/plugins/jquery-inputmask/jquery.inputmask.bundle.js"></script>
    <script src="assets/js/pages/forms/advanced-form-elements.js"></script>
</asp:Content>
