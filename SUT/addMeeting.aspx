<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="AddMeeting.aspx.cs" Inherits="AddMeeting" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <!-- Bootstrap Material Datetime Picker Css -->
    <link href="assets/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
    <!-- Bootstrap Select Css -->
    <link href="assets/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" />
    <link rel="stylesheet" href="assets/css/color_skins.css" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
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
                                    <h2>Meetings</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Meetings</a></li>
                                        <li class="breadcrumb-item active">Add Meeting</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnViewMeetings" OnClick="btnViewMeetings_Click" Text="View Meetings" runat="server"></asp:Button>
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
                            <h2><strong>Add</strong> Meeting</h2>
                        </div>
                        <div class="body block-header">
                            <div class="row">
                                <div class="col-lg-6 col-md-8 col-sm-12">
                                    <b>Topic</b>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtTopic" CssClass="form-control" placeholder="Topic Name" runat="server" />
                                    </div>
                                    <b>Date</b>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="zmdi zmdi-calendar"></i>
                                        </span>
                                        <asp:TextBox ID="txtDate" CssClass="form-control" runat="server" placeholder="Choose a date" />
                                    </div>
                                    <b>Time</b>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="zmdi zmdi-calendar"></i>
                                        </span>
                                        <asp:TextBox ID="txtTime" CssClass="form-control timepicker" runat="server" placeholder="Choose a time" />
                                    </div>
                                    <b>Description</b>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <textarea id="txtMessage" rows="3" class="form-control no-resize" placeholder="Description" runat="server"></textarea>
                                        </div>
                                    </div>
                                    <b>Place</b>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtPlace" CssClass="form-control" placeholder="Place Name" runat="server" />
                                    </div>
                                    <div class="checkbox">
                                        <asp:CheckBox Text="Important" ID="chkImportant" value="important" Checked="false" runat="server" />
                                    </div>
                                    <div class="col-sm-8">
                                        <asp:Button Text="Add Meeting" CssClass="btn btn-primary btn-round btn-simple" ID="btnAddMeeting" OnClick="btnAddMeeting_Click" runat="server" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <script src="assets/plugins/momentjs/moment.js"></script>
    <!-- Moment Plugin Js -->
    <!-- Bootstrap Material Datetime Picker Plugin Js -->
    <script src="assets/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
    <script src="assets/plugins/bootstrap-datetimepicker/moment-with-locales.js"></script>
    <script src="assets/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>

    <script src="assets/bundles/mainscripts.bundle.js"></script>
    <!-- Custom Js -->
    <script src="assets/js/pages/forms/basic-form-elements.js"></script>

    <script type="text/javascript">
        $(function () {
            var date = new Date();
            date.setDate(date.getDate());
            $('#txtDate').bootstrapMaterialDatePicker({
                weekStart: 0,
                time: false,
                format: 'DD-MM-YYYY',
                disabledDates: ['2018-04-25', '2018-04-26']
            });
        });
    </script>
</asp:Content>

