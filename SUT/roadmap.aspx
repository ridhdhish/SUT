<%@ Page Title="" Language="C#" MasterPageFile="~/SiteMaster.master" AutoEventWireup="true" CodeFile="roadmap.aspx.cs" Inherits="roadmap" %>

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

        /* Source: http://jsfiddle.net/6Mt3Q/ */
        .image-container.container {
            position: relative;
            width: 70%;
        }

        .image-container .after {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: none;
            color: #FFF;
        }

        .image-container:hover .after {
            display: block;
            background: rgba(0, 0, 0, .6);
        }
        .after > a, .after > a > .material-icons{
            color: #a27ce6;
        }
    </style>
</asp:Content>
<asp:Content ID="Body" ContentPlaceHolderID="body" runat="Server">
    <div class="container">
        <div class="row clearfix">
            <div class="col-lg-12">
                <div class="card">
                    <div class="body block-header">
                        <div class="row">
                            <div class="col-lg-6 col-md-8 col-sm-12">
                                <h2>Roadmap</h2>
                                <ul class="breadcrumb p-l-0 p-b-0 ">
                                    <li class="breadcrumb-item"><a href="index.aspx"><i class="icon-home"></i></a></li>
                                    <li class="breadcrumb-item active">Roadmap</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- File Upload | Drag & Drop OR With Click & Choose -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <div class="card">
                    <div class="header">
                        <h2><strong>Road</strong>map</h2>
                    </div>
                    <%--<div class="body">
                        <div class="form-group">
                            <label for="exampleFormControlFile1">Upload Roadmap file</label>
                            <input type="file" class="form-control-file" id="roadmapImage"/>
                            <asp:Button Text="Upload" CssClass="btn btn-simple" runat="server" />
                        </div>
                    </div>--%>
                    <div class="body">
                        <div class="col-sm-4" id="divUploadFile" runat="server">
                            <b>Oops! No roadmap image</b>
                            <div class="form-group">
                                <button class="btn btn-primary btn-round btn-file">
                                    Upload Roadmap Image 
                                    <asp:FileUpload ID="uploadedFile" onchange="imgUpload(this);" runat="server" />
                                </button>
                            </div>
                        </div>
                        <div id="divImageContainer" runat="server">
                            <div class="image-container container align-center">
                                <asp:Image ID="imgRoadmap" runat="server" alt="No Image Found!" />
                                <div class="after">
                                    <asp:LinkButton ID="btnEdit" runat="server"><i class="material-icons" title="Edit">edit</i></asp:LinkButton>
                                    <asp:LinkButton ID="lnkbtnDelete" runat="server" OnClick="lnkbtnDelete_Click"><i class="material-icons" title="Delete">delete</i></asp:LinkButton>
                                </div>
                            </div>
                        </div>
                        <asp:Button Text="Upload Image" ID="btnUpload" CssClass="btn btn-simple btn-round btn-default" OnClick="btnUpload_Click" runat="server" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>

        $(document).ready(function () {
            $("#btnEdit").click(function () {
                $("#uploadedFile").trigger('click');
                $("#btnUpload").show();
                return false;
            });
        });

        function imgUpload(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#imgRoadmap')
                        .attr('src', e.target.result);
                    $("#divImageContainer").show();
                    $("#divUploadFile").css("display", "none");
                    $("#btnUpload").css("display", "block");
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</asp:Content>
