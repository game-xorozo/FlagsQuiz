<!DOCTYPE html>
<html lang="en">
    <head>       
        <meta charset="utf-8">
        <title><?php echo lang("page_title"); ?></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Who knows this flag?">       

        <meta property="og:image" itemprop="image primaryImageOfPage" content="<?php echo base_url(); ?>global/views/front/img/app_icon_lrg.png" />
        <meta property="og:image:type" content="image/png" />
        <meta property="og:image:width" content="200" />
        <meta property="og:image:height" content="200" />
        
        <!-- The styles -->
        <link id="bs-css" href="<?php echo base_url(); ?>global/views/back/css/foot-classic.css" rel="stylesheet">
        <link href="<?php echo base_url(); ?>global/views/back/css/foot-responsive.css" rel="stylesheet">
        <link href="<?php echo base_url(); ?>global/views/back/css/charisma-app.css" rel="stylesheet">


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
                    <div class="span12 center">
                        <h2 style="margin: 15px"><?php echo lang("content_title"); ?></h2>
                    </div><!--/span-->
                </div><!--/row-->

                <div class="row-fluid">
                    <div class="well span5 center login-box">                        

                        <?php if ($flag && $flag->fl_image && file_exists("./global/uploads/flags/" . $flag->fl_image)) { ?>
                            <img src="<?php echo base_url(); ?>global/uploads/flags/<?php echo $flag->fl_image; ?>" />
                        <?php } else { ?>
                            <img src="<?php echo base_url(); ?>global/views/back/img/no_image.gif" title="<?php echo lang("img_no_image"); ?>" alt="<?php echo lang("img_no_image"); ?>" width="128px" height="128px" />
                        <?php } ?>
                        
                        <br />
                        <br />
                        <br />                       
                        <p><b><?php echo lang("enjoy_game"); ?></b></p>
                        <img src="<?php echo base_url(); ?>global/views/front/img/app_icon.png" width="48px" height="48px" />
                        <p><img src="<?php echo base_url(); ?>global/views/front/img/googleplay.png" width="32px" height="32px" /><a href="<?php echo $market_link->us_market_link; ?>" onclick="wndow.open(this.href); return false;"><?php echo lang("install_from_google_play"); ?></a></p>
                    </div><!--/span-->
                </div><!--/row-->
            </div><!--/fluid-row-->

        </div><!--/.fluid-container-->     
    </body>
</html>

