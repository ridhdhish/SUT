<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="addAnnouncement.aspx.cs" Inherits="addannouncement" %>

<asp:Content ID="Head" ContentPlaceHolderID="head" runat="Server">
    <style>
        .btn-file {
            position: relative;
            overflow: hidden;
        }

            .btn-file input[type=file] {
                position: absolute;
                top: 0;
                right: 0;
                min-width: 100%;
                min-height: 100%;
                font-size: 100px;
                text-align: right;
                filter: alpha(opacity=0);
                opacity: 0;
                outline: none;
                cursor: inherit;
                display: block;
            }

        img#imgPreview {
            vertical-align: top;
            width: auto;
            max-height: 450px;
        }
        div.sweet-alert.showSweetAlert.visible{
            padding-bottom: 17px !important;
        }
    </style>
    <script>
        var script = document.createElement('script');
        ///script.src = 'http://code.jquery.com/jquery-1.11.0.min.js';
        script.type = 'text/javascript';
        document.getElementsByTagName('head')[0].appendChild(script);
        function imagePreview(input) {
            $("#imgPreview").show();
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                $(reader).on("load", function (e) {
                    $('#imgPreview').attr('src', e.target.result);
                });
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
    
    <link rel="stylesheet" href="assets/plugins/sweetalert/sweetalert.css" />
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <%-- Location Card --%>
        <div class="row clearfix">
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Announcements</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item"><a href="javascript:void(0);">Announcements</a></li>
                                    <li class="breadcrumb-item active">Add Announcement</li>
                                </ul>
                            </div>
                            <div class="col-lg-6 col-md-4 col-sm-12 text-right">
                                <asp:Button CssClass="btn btn-primary btn-round btn-simple float-right hidden-xs m-l-10" ID="btnViewAnnouncement" OnClick="btnViewAnnouncement_Click" Text="View Annoucements" runat="server"></asp:Button>
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
                        <h2><strong>Add</strong> Announcements</h2>
                    </div>
                    <div class="body">
                        <b>Topic</b>
                        <div class="col-sm-5">
                            <div class="form-group">
                                <%--<input type="text" class="form-control" placeholder="Topic" />--%>
                                <asp:TextBox CssClass="form-control" ID="txtTopic" MaxLength="50" placeholder="Topic" runat="server" />
                            </div>
                        </div>
                        <b>Message<b class="text-danger">*</b></b>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <div class="form-line">
                                    <textarea id="txtMessage" rows="3" class="form-control no-resize" maxlength="255" placeholder="Message" runat="server"></textarea>
                                </div>
                            </div>
                        </div>
                        <b>Attach Files</b>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <button class="btn btn-primary btn-round btn-simple btn-file inlineblock" type="submit">
                                    Choose File
                                    <asp:FileUpload ID="uploadedFiles" onchange="imagePreview(this);" accept="image/*" runat="server" />
                                </button>
                                <br />
                                <asp:Image ID="imgPreview" width="100" Height="100" CssClass="img-thumbnail inlineblock" runat="server" />
                            </div>
                        </div>
                        <div class="checkbox">
                            <asp:CheckBox Text="Important" ID="chkImportant" value="important" Checked="false" runat="server" />
                        </div>
                        <asp:Button class="btn btn-primary btn-round btn-simple" type="submit" ID="btnAdd" Text="Add Announcement" runat="server" OnClick="btnAdd_Click"></asp:Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
