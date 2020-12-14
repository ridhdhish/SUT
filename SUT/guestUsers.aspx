<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="guestUsers.aspx.cs" Inherits="guestUsers" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <div class="row clearfix">
            <%-- Location Card --%>
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Guest Users</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item active">Guest Users</li>
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
                        <h2><strong>Guest</strong> Users</h2>
                    </div>
                    <div class="body table-responsive">
                        <table class="table m-b-0">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Email id</th>
                                    <th scope="col">Mobile Number</th>
                                    <th scope="col">Delete Access</th>
                                </tr>
                            </thead>
                            <tbody>
                                <asp:Repeater ID="rptViewGuestUsers" OnItemCommand="rptViewGuestUsers_ItemCommand" runat="server">
                                    <ItemTemplate>
                                        <tr>
                                            <th scope="row"><%#Container.ItemIndex+1 %></th>
                                            <td><%#Eval("GUName") %></td>
                                            <td><%#Eval("GUEmailid") %></td>
                                            <td><%#Eval("GUMobile") %></td>
                                            <td>
                                                <div class="demo-google-material-icon">
                                                    <asp:LinkButton runat="server" CommandArgument='<%#Eval("GUId") %>'><i class="material-icons">delete</i></asp:LinkButton>
                                                </div>
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
</asp:Content>
