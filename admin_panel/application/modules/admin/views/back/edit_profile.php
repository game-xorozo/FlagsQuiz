<div>
    <ul class="breadcrumb">
        <li>
            <a href="<?php echo base_url(); ?>admin/dashboard"><?php echo lang("title_dashboard"); ?></a> <span class="divider">/</span>
        </li>
        <li>
            <?php echo lang("title_edit_profile"); ?></a>
        </li> 
    </ul>
</div>
<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-edit"></i> <?php echo lang("title_edit_profile"); ?></h2>            
        </div>
        <div class="box-content">            
            <?php echo form_open_multipart("admin/admin/profile/", array('class' => 'form-horizontal')); ?>
            <form class="form-horizontal">
                <fieldset>                    
                    <div class="control-group">
                        <label class="control-label" for="us_username"><?php echo lang("input_username"); ?></label>
                        <div class="controls">                                                        
                            <input type="text" id="us_username" name="us_username" value="<?php echo set_value("us_username", $admin_user->us_username); ?>" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("us_username"); ?></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="us_old_password"><?php echo lang("input_old_password"); ?></label>
                        <div class="controls">                                                        
                            <input type="password" id="us_old_password" name="us_old_password" value="" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("us_old_password"); ?></span>
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="us_password"><?php echo lang("input_password"); ?></label>
                        <div class="controls">                                                        
                            <input type="password" id="us_password" name="us_password" value="" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("us_password"); ?></span>
                        </div>
                    </div>     

                    <div class="control-group">
                        <label class="control-label" for="us_password_conf"><?php echo lang("input_password_conf"); ?></label>
                        <div class="controls">                                                        
                            <input type="password" id="us_password_conf" name="us_password_conf" value="" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("us_password_conf"); ?></span>
                        </div>
                    </div>     

                    <div class="control-group">
                        <label class="control-label" for="us_market_link"><?php echo lang("input_market_link"); ?></label>
                        <div class="controls">                                                        
                            <input type="text" id="us_market_link" name="us_market_link" value="<?php echo set_value("us_market_link", $admin_user->us_market_link); ?>" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("us_market_link"); ?></span>
                        </div>
                    </div>     


                    <div class="form-actions">
                        <input type="submit" name="submit" value="<?php echo lang("input_btn_save"); ?>" class="btn btn-primary" />
                        <input type="reset" name="reset" value="<?php echo lang("input_btn_reset"); ?>" class="btn reset" />
                    </div>
                </fieldset>
                <?php echo form_close(); ?>   

        </div>
    </div><!--/span-->

</div><!--/row-->
