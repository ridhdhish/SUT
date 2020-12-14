<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="ViewMeetings.aspx.cs" Inherits="ViewMeetings" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body" runat="Server">
    <section class="content">
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <h2>Meetings</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.html"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Meetings</a></li>
                                        <li class="breadcrumb-item active">View Meeting</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnAddMeeting" OnClick="btnAddMeeting_Click" Text="Add Meeting" runat="server"></asp:Button>
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
                            <h2><strong>View</strong> Meetings</h2>
                        </div>
                        <div class="body">
                            <div class="body table-responsive" id="hidden" runat="server">
                                <table class="table m-b-0">
                                    <thead class="thead-light">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Topic</th>
                                            <th scope="col">Date</th>
                                            <th scope="col">Time</th>
                                            <th scope="col">Message</th>
                                            <th scope="col">Place</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <asp:Repeater ID="repViewMeetings" OnItemCommand="repViewMeetings_ItemCommand" runat="server">
                                            <ItemTemplate>
                                               <tr class='<%# DateTime.ParseExact(Eval("MeetingDate").ToString() + Eval("MeetingTime").ToString(), "dd-MM-yyyyHH:mm", System.Globalization.CultureInfo.InvariantCulture).ToLocalTime() > DateTime.Now ? (Eval("MeetingImportant").ToString() == "1" ? "bg-deep-orange":"bg-green"): ""%>'>
                                                    <th scope="row"><%#Container.ItemIndex+1 %></th><!-- "bg-deep-orange" -->
                                                    <td><%#Eval("Meetingtitle") %></td>
                                                    <td><%#Eval("MeetingDate") %></td>
                                                    <td><%#Eval("MeetingTime") %></td>
                                                    <td><%#Eval("MeetingMessage") %></td>
                                                    <td><%#Eval("MeetingAddress") %></td>
                                                    <td>
                                                        <span class="demo-google-material-icon">
                                                            <asp:LinkButton ID="lnkbtnEdit" runat="server" CommandName="lnkbtnEdit" CommandArgument='<%#Eval("MeetingId") %>'><i class=" material-icons ">mode_edit</i></asp:LinkButton>
                                                            <asp:LinkButton ID="lnkbtnDelete" runat="server" CommandName="lnkbtnDelete" CommandArgument='<%#Eval("MeetingId") %>'><i class="material-icons">delete</i></asp:LinkButton>
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

