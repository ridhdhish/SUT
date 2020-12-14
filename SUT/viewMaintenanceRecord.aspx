<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="viewMaintenanceRecord.aspx.cs" Inherits="viewMaintenanceRecord" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body" Runat="Server">
    <section class="content">
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <h2>Maintenance</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.html"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="viewMaintenace.aspx">Maintenance</a></li>
                                        <li class="breadcrumb-item active">Maintenance Record</li>
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
                        <div class="header">
                            <h2><strong>Maintenance</strong> Record</h2>
                        </div>
                        <div class="body">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                    <thead>
                                        <tr>
                                            <th>Posted Date</th>
                                            <th>Amount</th>
                                            <th>Penalty Amount</th>
                                            <th>Due Date</th>
                                            <th>Year</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <asp:Repeater ID="repViewMaintenanceRecord" runat="server">
                                            <ItemTemplate>
                                                <tr>
                                                    <td><%#Eval("MaintenancePostDate") %></td>
                                                    <td><%#Eval("MaintenanceAmount") %></td>
                                                    <td><%#Eval("MaintenancePenaltyAmount") %></td>
                                                    <td><%#Eval("MaintenanceDueDate") %></td>
                                                    <td>
                                                        <!-- get only year and month here -->
                                                        <%#Eval("MaintenanceDueDate").ToString().Substring(3) %>

                                                    </td>
                                                </tr>
                                            </ItemTemplate>
                                        </asp:Repeater>
                                        </tr>
                                        
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