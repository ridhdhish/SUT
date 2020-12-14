<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="viewUsers.aspx.cs" Inherits="bs4admin_light_EditUser" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <style>
        .image {
            max-width: 10pt;
            max-height: 5pt;
        }

        td::selection, img::selection {
            background: none;
        }
    </style>
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <section class="content">
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <h2>Flat-Holders</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.html"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Flat-Holders</a></li>
                                        <li class="breadcrumb-item active">View Flat-Holders</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button ID="btnAddUser" CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" Text="Add Flat-Holder" OnClick="btnAddUser_Click" runat="server" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="header">
                            <h2><strong>View</strong> Users</h2>
                        </div>
                        <div class="body">
                            <div class="table-responsive">

                                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Profile Picture</th>
                                            <th>Name</th>
                                            <th>Flat Number</th>
                                            <th>Mobile Number</th>
                                            <th>Action</th>
                                            <%--<th>Email Id</th>--%>
                                            <%--<th>Gender</th>--%>
                                        </tr>
                                    </thead>
                                    
                                    <tbody>
                                        <asp:Repeater ID="repViewUser" runat="server" OnItemCommand="rep_ItemCommand">
                                            <ItemTemplate>
                                                <tr>
                                                    <td><%#Container.ItemIndex+1 %></td>
                                                    <td><img src="siteimages/profilePictures/<%#Eval("UserProfilePicture").ToString() == "" ? "defaultUser.png" : Eval("UserProfilePicture").ToString()%>" width="35px" height="35px" class="rounded-circle" alt="<%#Eval("UserProfilePicture") %>" title="<%#Eval("UserProfilePicture") %>" /></td>
                                                    <td><%#Eval("UserName") %></td>
                                                    <td><%#Eval("UserFlatNumber") %></td>
                                                    <td><%#Eval("UserMobile") %></td>
                                                    <td>
                                                        <span class="demo-google-material-icon">
                                                            <asp:LinkButton ID="lnkbtnEdit" runat="server" CommandName="lnkbtnEdit" CommandArgument='<%#Eval("UserId") %>'><i class=" material-icons ">mode_edit</i></asp:LinkButton>
                                                            <asp:LinkButton ID="lnkbtnDelete" runat="server" CommandName="lnkbtnDelete" CommandArgument='<%#Eval("UserId") %>'><i class="material-icons">delete</i></asp:LinkButton>
                                                        </span>
                                                    </td>
                                                    <%--<td><%#Eval("UserEmailId") %></td>--%>
                                                    <%--<td style="text-transform:capitalize;"><%#Eval("UserGender") %></td>--%>
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
    </section>
</asp:Content>

