<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="index.aspx.cs" Inherits="index" %>

<asp:Content ID="ContentHead" ContentPlaceHolderID="head" runat="Server">
    <style>
        .img-VAlign-unset {
            vertical-align: unset;
        }

        .body > .icon > i.icon-calendar,
        .body > .icon > .icon-user,
        .body > .icon > .icon-users,
        .body > .icon > .icon-note {
            color: #ffffff !important;
        }
    </style>
</asp:Content>
<asp:Content ID="ContentBody" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <div class="row clearfix">
            <%-- Location Card --%>
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Home</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item active"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-4">
                <div class="card info-box-2 bg-green">
                    <a href="ViewMeetings.aspx">
                        <div class="body">
                            <div class="icon align-center w-25">
                                <i class="icon-calendar"></i>
                            </div>
                            <div class="content">
                                <div class="text">Upcoming Meetings</div>
                                <div class="number">
                                    <div class="number">
                                        <span class="number count-to" data-from="0" data-to="0" data-speed="1750" data-fresh-interval="700" id="spanUpcominMeetingsCount" runat="server">
                                            <asp:Label Text="0" ID="lblUpcominMeetingsCount" runat="server" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-4">
                <div class="card info-box-2 bg-deep-orange">
                    <a href="complaints.aspx#unread">
                        <div class="body">
                            <div class="icon align-center w-25">
                                <img class="img-VAlign-unset" src="siteimages/complaints.png" width="30" height="30" />
                            </div>
                            <div class="content">
                                <div class="text">Unread Complaints</div>
                                <div class="number">
                                    <div class="number">
                                        <span class="number count-to" data-from="0" data-to="5" data-speed="1750" data-fresh-interval="700" id="spanUnreadComplaintsCount" runat="server">
                                            <asp:Label Text="0" ID="lblUnreadComplaintsCount" runat="server" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-4">
                <div class="card info-box-2 bg-blue">
                    <a href="guestUsers.aspx">
                        <div class="body">
                            <div class="icon align-center w-25">
                                <i class="icon-user"></i>
                            </div>
                            <%--<div class="icon align-center w-25">
                                <img class="img-VAlign-unset" src="siteimages/complaints.png" width="30" height="30" />
                            </div>--%>
                            <div class="content">
                                <div class="text">Guest Users</div>
                                <div class="number">
                                    <div class="number">
                                        <span class="number count-to" data-from="0" data-to="0" data-speed="1750" data-fresh-interval="700" id="spanGuestUsersCount" runat="server">
                                            <asp:Label Text="0" ID="lblGuestUsersCount" runat="server" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="row clearfix" style="margin: 0 16%">
            <div class="col-6">
                <div class="card info-box-2 bg-amber">
                    <a href="viewAnnouncement.aspx">
                        <div class="body">
                            <div class="icon align-center w-25">
                                <i class="icon-note"></i>
                            </div>
                            <%--<div class="icon align-center w-25">
                                <img class="img-VAlign-unset" src="siteimages/complaints.png" width="30" height="30" />
                            </div>--%>
                            <div class="content">
                                <div class="text">Announcements</div>
                                <div class="number">
                                    <div class="number">
                                        <span class="number count-to" data-from="0" data-to="0" data-speed="1750" data-fresh-interval="700" id="spanAnnouncementsCount" runat="server">
                                            <asp:Label Text="0" ID="lblAnnouncementsCount" runat="server" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-6">
                <div class="card info-box-2 bg-purple">
                    <a href="ViewUsers.aspx">
                        <div class="body">
                            <div class="icon align-center w-25">
                                <i class="icon-users"></i>
                            </div>
                            <%--<div class="icon align-center w-25">
                                <img class="img-VAlign-unset" src="siteimages/complaints.png" width="30" height="30" />
                            </div>--%>
                            <div class="content">
                                <div class="text">Flat-Holders</div>
                                <div class="number">
                                    <div class="number">
                                        <span class="number count-to" data-from="0" data-to="0" data-speed="1750" data-fresh-interval="700" id="spanFlatHoldersCount" runat="server">
                                            <asp:Label Text="0" ID="lblFlatHoldersCount" runat="server" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
