<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="complaints.aspx.cs" Inherits="node_modules_complaints" %>

<asp:Content ID="ContentHead" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
    <style type="text/css">
        .container tr td, .container th td {
            white-space: normal;
        }

        tr td.tdComplaintMessage {
            overflow: hidden !important;
            max-width: 650px;
            text-overflow: ellipsis;
        }

        /*.sa-button-container {
            display: none !important;
        }

        .sweet-alert[data-has-confirm-button=false][data-has-cancel-button=false] {
            padding-bottom: 17px !important;
        }*/
    </style>

    <script>
        var hash = window.location.hash;
        window.onload = function () {
            if (hash != null || hash != "") {
                if (hash == "#unread") {

                    this.document.getElementById("btnUnread").click();
                }
                else if (hash == "#read" || hash === "#read") {
                    this.document.getElementById("btnRead").click();
                }
            }
        }
    </script>
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
</asp:Content>
<asp:Content ID="ContentBody" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <div class="row clearfix">
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Complaints</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item active">Complaints</li>
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
                        <h2><strong>Complaints</strong></h2>
                    </div>
                    <div class="body">
                        <button type="button" class="btn btn-round btn-simple btn-sm btn-default btn-filter" data-target="all">All</button>
                        <button type="button" class="btn btn-round btn-simple btn-sm btn-danger btn-filter" id="btnUnread" data-target="Unread">Unread</button>
                        <button type="button" class="btn btn-round btn-simple btn-sm btn-success btn-filter" id="btnRead" data-target="Read">Read</button>
                        <asp:UpdatePanel ID="updatepanel1" class="inlineblock" runat="server">
                            <ContentTemplate>
                        <asp:LinkButton Visible="true" class="info" id="btnRefresh" OnClick="btnRefresh_Click" runat="server"><i class='icon-refresh'> Refresh</i></asp:LinkButton>
                            </ContentTemplate>
                            <Triggers>
                                <asp:AsyncPostBackTrigger ControlID="tt" EventName="Tick" />
                            </Triggers>
                        </asp:UpdatePanel>
                        <asp:Button Text="Mark all as read" CssClass="float-md-right btn btn-round btn-simple btn-md" ID="btnReadAll" OnClick="btnReadAll_Click" runat="server" />
                    </div>
                    <div class="body js-sweetalert">
                        <div class="table-responsive">

                            <table class="table table-filter table-hover m-b-0">
                                <tbody>
                                    <asp:Timer OnTick="Timer1_Tick" ID="tt" Interval="1000" runat="server" />
                                    <asp:Repeater ID="rptComplaints" OnItemCommand="rptComplaints_ItemCommand" runat="server">
                                        <ItemTemplate>
                                            <tr data-status='<%#Eval("ComplaintRead").ToString() == "1" ? "Read" : "Unread"%>'>
                                                <td><%#Container.ItemIndex+1 %></td>
                                                <td><%#Eval("ComplaintDate") %></td>
                                                <td class="tdUserName"><%#Eval("UserName") %></td>
                                                <td class="tdComplaintTitle"><%#Eval("ComplaintTitle") %></td>
                                                <td class="tdComplaintMessage">
                                                    <asp:Label Text='<%#Eval("ComplaintMessage") %>' runat="server" ID="lblMessage" /></td>
                                                <td class="align-center">
                                                    <%--<asp:Button ID="btnOpen" Text="Open" CssClass='btn btn-primary btn-simple' runat="server"></asp:Button>--%>
                                                    <div class="js-sweetalert">
                                                        <button class="btn btn-primary btn-simple btn-round" data-type="complaint" data-value='<%#Eval("ComplaintId")%>' data-title="<%#Eval("ComplaintTitle")%>" data-html="<%#Eval("ComplaintMessage")%><br><a style='cursor: pointer; margin-bottom: -30px;' class='btn btn-primary btn-simple btn-round mt-3' onclick='readmessage()'>Okay</a>">Open</button>
                                                    </div>

                                                </td>
                                                <td>
                                                    <asp:LinkButton CommandName='lnkbtnRead' Text="Mark as read" CssClass='btn btn-simple btn-primary btn-round' CommandArgument='<%#Eval("ComplaintId") %>' Style='<%#Eval("ComplaintRead").ToString() == "1" ? "display:none": ""%>' runat="server" />
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

        <div style="display: none;">
            <asp:Button runat="server" ID="btnReadMessage" OnClick="btnReadMessage_Click" />
            <asp:HiddenField ID="hdMsgID" runat="server" />
            <asp:HiddenField ID="hdnDataCount" runat="server" />
        </div>

    </div>

    <!-- Custom Js -->
    <script>
        //$.ajaxSetup({
        //    cache: true
        //});
        //window.setInterval(function () {
        //    $("table.table").load(window.location.href); console.log("asds");
        //}, 5000);
        $(document).ready(function () {
            $('.star').on('click', function () {
                $(this).toggleClass('star-checked');
            });

            $('.ckbox label').on('click', function () {
                $(this).parents('tr').toggleClass('selected');
            });

            $('.btn-filter').on('click', function () {
                var $target = $(this).data('target');
                if ($target != 'all') {
                    $('.table tr').css('display', 'none');
                    $('.table tr[data-status="' + $target + '"]').fadeIn('slow');
                } else {
                    $('.table tr').css('display', 'none').fadeIn('slow');
                }
            });
        });

        function readmessage() {
            document.getElementById("btnReadMessage").click();
        }
    </script>
    <%-- <!-- SweetAlert Plugin Js -->
    <script src="assets/plugins/sweetalert/sweetalert.min.js"></script>
    <!-- Custom Js -->
    <script src="assets/bundles/mainscripts.bundle.js"></script>
    <script src="assets/js/pages/ui/dialogs.js"></script>--%>
</asp:Content>

