<!DOCTYPE html>
<html lang="en">
    <head>
        <!--
                Charisma v1.0.0

                Copyright 2012 Muhammad Usman
                Licensed under the Apache License v2.0
                http://www.apache.org/licenses/LICENSE-2.0

                http://usman.it
                http://twitter.com/halalit_usman
        -->
        <meta charset="utf-8">
        <title>Flags Quiz - Admin Panel</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
        <meta name="author" content="Muhammad Usman">

        <!-- The styles -->
        <link id="bs-css" href="<?php echo base_url(); ?>global/views/back/css/foot-classic.css" rel="stylesheet">
        <style type="text/css">
            body {
                padding-bottom: 40px;
            }
            .sidebar-nav {
                padding: 9px 0;
            }
        </style>
        <link href="<?php echo base_url(); ?>global/views/back/css/foot-responsive.css" rel="stylesheet">
        <link href="<?php echo base_url(); ?>global/views/back/css/charisma-app.css" rel="stylesheet">
        <link href="<?php echo base_url(); ?>global/views/back/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
        <link href='<?php echo base_url(); ?>global/views/back/css/fullcalendar.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
        <link href='<?php echo base_url(); ?>global/views/back/css/chosen.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/uniform.default.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/colorbox.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/jquery.cleditor.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/jquery.noty.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/noty_theme_default.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/elfinder.min.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/elfinder.theme.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/jquery.iphone.toggle.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/opa-icons.css' rel='stylesheet'>
        <link href='<?php echo base_url(); ?>global/views/back/css/uploadify.css' rel='stylesheet'>

        <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- The fav icon -->
        <link rel="shortcut icon" href="<?php echo base_url(); ?>global/views/back/img/favicon.ico">

    </head>

    <body>
        <div class="container-fluid">
            <div class="row-fluid">

                <div class="row-fluid">
                    <div class="span12 center login-header">
                        <h2>Welcome to Flags Quiz Admin Panel</h2>
                    </div><!--/span-->
                </div><!--/row-->

                <div class="row-fluid">
                    <div class="well span5 center login-box">                        
                        <div class="alert alert-info">
                            Please login with your Username and Password.
                        </div>
                        <?php echo (isset($error_msg) && $error_msg != "") ? $error_msg  : ""; ?>

                        <?php echo form_open("admin/login", array('class' => 'form-horizontal')); ?>
                        <fieldset>
                            <div class="input-prepend" title="Username" data-rel="tooltip">
                                <span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="us_username" id="username" type="text" />
                            </div>
                            <div class="clearfix"></div>

                            <div class="input-prepend" title="Password" data-rel="tooltip">
                                <span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="us_password" id="password" type="password" />
                            </div>
                            <div class="clearfix"></div>                           

                            <p class="center span5">                                
                                <input type="submit" name="submit" value="Login" class="btn btn-primary" />
                            </p>
                        </fieldset>
                        <?php echo form_close(); ?>
                    </div><!--/span-->
                </div><!--/row-->
            </div><!--/fluid-row-->

        </div><!--/.fluid-container-->

        <!-- external javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->

        <!-- jQuery -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery-1.7.2.min.js"></script>
        <!-- jQuery UI -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery-ui-1.8.21.custom.min.js"></script>
        <!-- transition / effect library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-transition.js"></script>
        <!-- alert enhancer library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-alert.js"></script>
        <!-- modal / dialog library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-modal.js"></script>
        <!-- custom dropdown library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-dropdown.js"></script>
        <!-- scrolspy library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-scrollspy.js"></script>
        <!-- library for creating tabs -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-tab.js"></script>
        <!-- library for advanced tooltip -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-tooltip.js"></script>
        <!-- popover effect library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-popover.js"></script>
        <!-- button enhancer library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-button.js"></script>
        <!-- accordion library (optional, not used in demo) -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-collapse.js"></script>
        <!-- carousel slideshow library (optional, not used in demo) -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-carousel.js"></script>
        <!-- autocomplete library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-typeahead.js"></script>
        <!-- tour library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/foot-tour.js"></script>
        <!-- library for cookie management -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.cookie.js"></script>
        <!-- calander plugin -->
        <script src='<?php echo base_url(); ?>global/views/back/js/fullcalendar.min.js'></script>
        <!-- data table plugin -->
        <script src='<?php echo base_url(); ?>global/views/back/js/jquery.dataTables.min.js'></script>

        <!-- chart libraries start -->
        <script src="<?php echo base_url(); ?>global/views/back/js/excanvas.js"></script>
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.flot.min.js"></script>
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.flot.pie.min.js"></script>
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.flot.stack.js"></script>
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.flot.resize.min.js"></script>
        <!-- chart libraries end -->

        <!-- select or dropdown enhancer -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.chosen.min.js"></script>
        <!-- checkbox, radio, and file input styler -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.uniform.min.js"></script>
        <!-- plugin for gallery image view -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.colorbox.min.js"></script>
        <!-- rich text editor library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.cleditor.min.js"></script>
        <!-- notification plugin -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.noty.js"></script>
        <!-- file manager library -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.elfinder.min.js"></script>
        <!-- star rating plugin -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.raty.min.js"></script>
        <!-- for iOS style toggle switch -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.iphone.toggle.js"></script>
        <!-- autogrowing textarea plugin -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.autogrow-textarea.js"></script>
        <!-- multiple file upload plugin -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.uploadify-3.1.min.js"></script>
        <!-- history.js for cross-browser state change on ajax -->
        <script src="<?php echo base_url(); ?>global/views/back/js/jquery.history.js"></script>
        <!-- application script for Charisma demo -->
        <!--<script src="<?php echo base_url(); ?>global/views/back/js/charisma.js"></script>-->
    </body>
</html>

