<div>
    <ul class="breadcrumb">
        <li>
            <a href="<?php echo base_url(); ?>admin/dashboard"><?php echo lang("title_dashboard"); ?></a> <span class="divider">/</span>
        </li>
        <li>
            <a href="<?php echo base_url(); ?>flags/admin_flags"><?php echo lang("title_flags"); ?></a> <span class="divider">/</span>
        </li>
        <li>
            <?php echo lang("title_new_flag"); ?>
        </li>
    </ul>
</div>

<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-header well" data-original-title>
            <h2><i class="icon-plus"></i> <?php echo lang("title_new_flag"); ?></h2>            
        </div>
        <div class="box-content">
            <?php echo form_open_multipart("flags/admin_flags/create", array('class' => 'form-horizontal')); ?>
            <form class="form-horizontal">
                <fieldset>
                    <legend>Add New Flag</legend>
                    <div class="control-group">
                        <label class="control-label" for="fl_country"><?php echo lang("input_country"); ?></label>
                        <div class="controls">                                                        
                            <input type="text" id="fl_country" name="fl_country" value="<?php echo set_value("fl_country"); ?>" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("fl_country"); ?></span>                                                        
                        </div>
                    </div>                    

                    <div class="control-group">
                        <label class="control-label" for="fl_image"><?php echo lang("input_image"); ?></label>
                        <div class="controls">                            
                            <input type="file" id="fl_image" name="fl_image" value="" class="input-file uniform_on" />
                            <span class="help-inline error"><?php echo form_error("fl_image"); ?></span>
                        </div>
                    </div>                                              

                    <div class="control-group">
                        <label class="control-label" for="fl_wikipedia"><?php echo lang("input_wikipedia"); ?></label>
                        <div class="controls">                                                        
                            <input type="text" id="fl_wikipedia" name="fl_wikipedia" value="<?php echo set_value("fl_wikipedia"); ?>" class="input-xlarge focused" />
                            <span class="help-inline error"><?php echo form_error("fl_wikipedia"); ?></span>                                                        
                        </div>
                    </div>                     

                    <div class="control-group">
                        <label class="control-label" for="fl_status"><?php echo lang("input_status"); ?></label>
                        <div class="controls">
                            <label class="checkbox">
                                <input type="checkbox" id="fl_status" value="1" name="fl_status" <?php echo set_checkbox("fl_status", "1", TRUE); ?> />
                                <?php echo lang("input_active"); ?>
                                <span class="help-inline error"><?php echo form_error("fl_status"); ?></span>
                            </label>
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
