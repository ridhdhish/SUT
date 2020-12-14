<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="viewannouncement.aspx.cs" ClientIDMode="AutoID" Inherits="Announcement" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <style>
        *::selection {
            background: none;
            color: inherit;
        }

        tr td img.imageWithAnnouncement {
            width: 200px;
            margin-left: 10px;
            max-width: 230px;
            max-height: 95px;
            width: auto;
            height: auto;
        }

        tr td.title {
            overflow: hidden !important;
            max-width: 100px;
            text-overflow: ellipsis;
            font-weight: 400;
            min-width: 60px;
        }

        tr td.message {
            overflow: hidden !important;
            max-width: 650px;
            text-overflow: ellipsis;
        }

        div.js-sweetalert {
            display: inline-block;
        }

        button.btn-link {
            cursor: pointer;
            padding: 0;
            display: inline-block;
        }
        .AnnouncementImage{
            display: none;
        }
        .material-icons{
            vertical-align:middle;
        }
    </style>

    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
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
                                <h2>Announcements</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item"><a href="javascript:void(0);">Announcements</a></li>
                                    <li class="breadcrumb-item active">View Announcements</li>
                                </ul>
                            </div>
                            <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                <asp:Button CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnAddAnnouncement" OnClick="btnAddAnnouncement_Click" Text="Add Annoucement" runat="server"></asp:Button>
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
                        <h2><strong>View</strong> Announcements</h2>
                    </div>
                    <div class="body table-responsive js-sweetalert">
                        <table class="table m-b-0">
                            <thead class="thead-light">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Title</th>
                                    <th scope="col" class="message">Message</th>
                                    <th scope="col">Date</th>
                                    <%--<th scope="col">Time</th>--%>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <asp:Repeater ID="rptViewAnnouncements" OnItemCommand="rptViewAnnouncements_ItemCommand" runat="server">
                                    <ItemTemplate>
                                        <tr>
                                            <th scope="row"><%#Container.ItemIndex+1 %></th>
                                            <td class="title"><%#Eval("AnnouncementTitle") %></td>
                                            <td class="message">
                                                <%#Eval("AnnouncementImportant").ToString() == "1" ? "<i class='material-icons' title='Important'>label_important</i>" : null %>
                                                <%#String.IsNullOrEmpty(Eval("AnnouncementImage").ToString()) == true ? null :"<i class='material-icons'>attachment</i>"%>
                                                <%#Eval("AnnouncementMessage") %>
                                                <%--<img class="imageWithAnnouncement" src="siteimages/announcements/<%#Eval("AnnouncementImage")%>" style="<%#Eval("AnnouncementImage").ToString() == "" ? "display:none": ""%>" />--%>
                                            </td>
                                            <td><%#Eval("AnnouncementDate") %></td>
                                            <%--<td><%#Eval("AnnouncementTime") %></td>--%>
                                            <td>
                                                <span class="demo-google-material-icon">
                                                    <%--<asp:LinkButton Text='<i class="material-icons" title="Open">launch</i>' ID="lnkbtnOpen" CommandName="lnkbtnOpen" CommandArgument='<%#Eval("AnnouncementId") %>' runat="server" />--%>
                                                    <div class="js-sweetalert">
                                                        <button class="btn-link" data-type="announcement" data-title="<%#Eval("AnnouncementTitle") %>" data-message='<%#Eval("AnnouncementMessage") %></p><br/><img style="min-width:0; min-height:0; display:<%#String.IsNullOrEmpty(Eval("AnnouncementImage").ToString()) == false? "initial !important" : "none"%>" src="siteimages/announcements/<%#Eval("AnnouncementImage")%>">'><i class="material-icons" title="Open">launch</i></button>
                                                    </div>
                                                    <%--<button class="btn-link" onclick=""></button>--%>
                                                </span>
                                                <asp:LinkButton Text='<i class="material-icons" title="Edit">edit</i>' ID="lnkbtnEdit" CommandName="lnkbtnEdit" CommandArgument='<%#Eval("AnnouncementId") %>' runat="server" />
                                                <asp:LinkButton Text='<i class="material-icons" title="Delete">delete</i>' ID="lnkbtnDelete" CommandName="lnkbtnDelete" CommandArgument='<%#Eval("AnnouncementId") %>' runat="server" />
                                                <%--<asp:UpdatePanel ID="UpdatePanel1" runat="server">
                                                    <ContentTemplate>

                                                        <span class="checkbox">
                                                            <asp:CheckBox Text="Important" ID="chkImportant" AutoPostBack="true" OnCheckedChanged="chkImportant_CheckedChanged" AutoCheck="false" Value='<%#Eval("AnnouncementId") %>' Checked='<%#Eval("AnnouncementImportant").ToString() == "0" ? false : true %>' runat="server" />
                                                            <asp:HiddenField ID="hdn" Value='<%#Eval("AnnouncementId") %>' runat="server" />
                                                        </span>

                                                    </ContentTemplate>
                                                    <Triggers>
                                                        <asp:AsyncPostBackTrigger ControlID="chkImportant" EventName="CheckedChanged" />
                                                    </Triggers>
                                                </asp:UpdatePanel>
                                                --%></div>
                                            </td>
                                        </tr>
                                    </ItemTemplate>
                                </asp:Repeater>
                            </tbody>
                        </table>
                        <!--set value to get it from c#-->
                        <div style="display: none;">
                            <asp:Button runat="server" ID="btnReadMessage" OnClick="btnReadMessage_Click" />
                            <asp:HiddenField ID="hdMsgID" runat="server" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function readmessage() {
            document.getElementById("btnReadMessage").click();
        }
    </script>
</asp:Content>
