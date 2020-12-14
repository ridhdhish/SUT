<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="viewMaintenance.aspx.cs" Inherits="Maintanance" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
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
                                    <h2>Maintenace</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.html"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="viewMaintenance.aspx">Maintenace</a></li>
                                        <li class="breadcrumb-item active">View Maintenace</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button Text="Add Maintenance" CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnAddMaintenance" OnClick="btnAddMaintenance_Click" runat="server" />
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
                            <h2><strong>View</strong> Maintenace</h2>
                        </div>
                        <div class="body">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                    <thead>
                                        <tr>
                                            <th>Number</th>                                    
                                            <th>Posted date</th>
                                            <th>Amount</th>
                                            <th>Penalty Amount</th>
                                            <th>Due Date</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <%--<tfoot>
                                        <tr>
                                            <th>Amount</th>
                                            <th>Due Amount</th>
                                            <th>Month</th>
                                            <th>Action</th>
                                        </tr>
                                    </tfoot>--%>
                                    <tbody>
                                        <asp:Repeater ID="repViewMaintenance" runat="server" OnItemCommand="rep_ItemCommand">
                                            <ItemTemplate>
                                                <tr>
                                                    <td><%#Container.ItemIndex+1 %></td>
                                                    <td><%#Eval("MaintenancePostDate").ToString().Substring(0,10) %></td>
                                                    <td><%#Eval("MaintenanceAmount") %></td>
                                                    <td><%#Eval("MaintenancePenaltyAmount") %></td>
                                                    <td><%#Eval("MaintenanceDueDate") %></td>
                                                    <td>
                                                        <span class="demo-google-material-icon">
                                                            <asp:LinkButton ID="lnkbtnEdit" runat="server" CommandName="lnkbtnEdit" CommandArgument='<%#Eval("MaintenanceId") %>'><i class=" material-icons ">mode_edit</i></asp:LinkButton>
                                                            <asp:LinkButton ID="lnkbtnDelete" runat="server" CommandName="lnkbtnDelete" CommandArgument='<%#Eval("MaintenanceId") %>'><i class="material-icons">delete</i></asp:LinkButton>
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
    </section>
</asp:Content>
