<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="AddMaintenance.aspx.cs" Inherits="AddMaintenance" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">

    <link href="assets/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
    <!-- Bootstrap Select Css -->
    <link href="assets/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" />
    <link rel="stylesheet" href="/assets/css/color_skins.css" />
    <link rel="stylesheet" href="/assets/css/main.css" />
    <style>
        .sut-info {
            margin-top: 15px;
            margin-bottom: 0px;
            padding: 4px 12px;
            background-color: #e7dcfc;
            border-left: 6px solid #a27ce6;
        }

            .sut-info > p {
                margin: 0px;
                padding: 0px;
            }

        ::selection {
            background: inherit;
            color: inherit;
        }
    </style>
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
                                    <h2>Maintenance</h2>
                                    <ul class="breadcrumb p-l-0 p-b-0 ">
                                        <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                        <li class="breadcrumb-item"><a href="javascript:void(0);">Maintenance</a></li>
                                        <li class="breadcrumb-item active">Add Maintenance</li>
                                    </ul>
                                </div>
                                <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                    <asp:Button Text="View Maintenance" CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnViewMaintenance" OnClick="btnViewMaintenance_Click" runat="server" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row clearfix">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <div class="header">
                                    <h2><strong>Add Maintenance</strong></h2>
                                </div>
                                <div class="body block-header">
                                    <b for="Amount">Amount<b class="text-danger">*</b></b>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtAmount" type="number" CssClass="form-control" placeholder="Amount" runat="server" />
                                    </div>
                                    <b for="Amount">Penalty Amount</b>
                                    <div class="form-group">
                                        <asp:TextBox ID="txtAmountPenalty" type="number" min="0" CssClass="form-control" placeholder="Amount" runat="server" />
                                    </div>
                                    <b>Maintenance of Month/Year<b class="text-danger">*</b></b>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="zmdi zmdi-calendar"></i>
                                        </span>

                                        <asp:TextBox ID="txtMonthYear" CssClass="form-control" runat="server" placeholder="Choose a date" />
                                    </div>

                                    <b>Due Date<b class="text-danger">*</b></b>
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="zmdi zmdi-calendar"></i>
                                        </span>
                                        <asp:TextBox ID="txtDueDate" CssClass="form-control" runat="server" placeholder="Choose a date" />
                                    </div>
                                    <%--                                    <label>Description</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <textarea id="txtDescription" rows="3" class="form-control no-resize" placeholder="Description" runat="server"></textarea>
                                        </div>
                                    </div>--%>
                                    <div class="col-sm-8">
                                        <asp:Button ID="btnAddMaintenance" Text="Add Maintenance" CssClass="btn btn-primary btn-round btn-simple" OnClick="btnAddMaintenance_Click" runat="server" />
                                    </div>
                                    <div class="sut-info" id="divNote" runat="server">
                                        <p><strong>Note:</strong> This maintenance will added as comman maintenance for the flat-holders.</p>
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
            $('#txtDueDate').bootstrapMaterialDatePicker({
                time: false,
                format: 'DD-MM-YYYY',
            });
        });
        $(function () {
            var date = new Date();
            date.setDate(date.getDate());
            $('#txtMonthYear').bootstrapMaterialDatePicker({
                time: false,
                autoclose: true,
                minViewMode: 1,
                format: 'MMMM, YYYY',
            });
        });
        var dataMonthYear;
        $(function () {
            dataMonthYear = $("#txtMonthYear").attr("data-dtp");
        });
        $(document).ready(function () {
            $("#txtMonthYear").focus(function () {
                $("#" + dataMonthYear + " .table.dtp-picker-days").addClass("hidden");
                $("#" + dataMonthYear + " .dtp-actual-day").addClass("hidden");
                $("#" + dataMonthYear + " .dtp-actual-num").addClass("hidden");
            });
            $(".dtp-select-month-before, .dtp-select-month-after, .dtp-select-year-before, .dtp-select-year-after").click(function () {
                $("#" + dataMonthYear + " .table.dtp-picker-days").addClass("hidden");
                $("#" + dataMonthYear + " .dtp-actual-day").addClass("hidden");
                $("#" + dataMonthYear + " .dtp-actual-num").addClass("hidden");
            });
        });
    </script>
</asp:Content>

