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
        <?php if (!isset($no_visible_elements) || !$no_visible_elements) { ?>
            <!-- topbar starts -->
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="container-fluid">
                        <a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </a>
                        <a class="brand" href="<?php echo base_url(); ?>admin"> <img alt="Logo" src="<?php echo base_url(); ?>global/views/back/img/header_icon.png" /> <span>Admin Panel</span></a>
                    
                        <!-- user dropdown starts -->
                        <div class="btn-group pull-right" >
                            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">                                
                                <i class="icon-user"></i><span class="hidden-phone"> <?php echo $username; ?></span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<?php echo base_url(); ?>admin/profile">Profile</a></li>
                                <li class="divider"></li>
                                <li><a href="<?php echo base_url(); ?>admin/logout">Logout</a></li>
                            </ul>
                        </div>
                        <!-- user dropdown ends -->
                    </div>
                </div>
            </div>
        <?php } ?>
        <!-- topbar ends -->
        <div class="container-fluid">
            <div class="row-fluid">
                <?php if (!isset($no_visible_elements) || !$no_visible_elements) { ?>
                    <!-- left menu starts -->
                    <div class="span2 main-menu-span">
                        <div class="well nav-collapse sidebar-nav">
                            <ul class="nav nav-tabs nav-stacked main-menu">
                                <li class="nav-header hidden-tablet">Main Menu</li>
                                <li id="admin-dashboard"><a  href="<?php echo base_url(); ?>admin/dashboard"><i class="icon-home"></i><span class="hidden-tablet"> Dashboard</span></a></li>
                                <li id="admin-profile"><a  href="<?php echo base_url(); ?>admin/profile"><i class="icon-user"></i><span class="hidden-tablet"> Profile</span></a></li>                                
                                <li id="flags-admin_levels"><a class="ajax-link" href="<?php echo base_url(); ?>flags/admin_flags"><i class="icon-globe"></i><span class="hidden-tablet"> Flags</span></a></li>
                                
                            </ul>                            
                        </div><!--/.well -->
                    </div><!--/span-->
                    <!-- left menu ends -->

                    <noscript>
                    <div class="alert alert-block span10">
                        <h4 class="alert-heading">Warning!</h4>
                        <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
                    </div>
                    </noscript>
                    <div id="content" class="span10">
                        <!-- content starts -->
                    <?php } ?>