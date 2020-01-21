<div>
    <ul class="breadcrumb">
        <li>
            <a href="<?php echo base_url(); ?>admin/dashboard"><?php echo lang("title_dashboard"); ?></a> <span class="divider">/</span>
        </li>
        <li>
            <?php echo lang("title_flags"); ?>
        </li>
    </ul>
</div>

<a class="btn btn-info action-deactivate" href="<?php echo base_url(); ?>flags/admin_flags/create">
    <i class="icon-plus icon-white"></i>
    <?php echo lang("button_new_flag"); ?>                                            
</a>
<div class="row-fluid sortable">
    <?php if ($flags) { ?>
        <div class="box span12">

            <div class="box-header well" data-original-title>
                <h2><i class="icon-flag"></i><?php echo lang("title_flags"); ?></h2>            
            </div>
            <div class="box-content">
                <?php echo form_open("flags/admin_flags/operation/", array("id" => "form", "name" => "myform")); ?>
                <div id="per_page_menu">
                    <select name="per_page" onchange="this.form.submit();" class="styledselect_pages" width="50px">
                        <option value="">Number of rows</option>
                        <option value="10">10</option>
                        <option value="25">25</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </div>
                <?php if ($this->session->flashdata("noti_msg")) { ?>                           
                    <div class="alert alert-success">
                        <button type="button" class="close" data-dismiss="alert">×</button>
                        <strong><?php echo $this->session->flashdata("noti_msg"); ?></strong>
                    </div><!-- notification msgsuccess -->                              
                <?php } ?>                
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                    <thead>
                        <tr>                                               
                            <th><input type="checkbox" id="checkall" /></th>
                            <th><a href="">#</a></th>
                            <th class="leftContent"><?php echo lang("tbl_title_country"); ?></th>
                            <th><?php echo lang("tbl_title_image"); ?></th>                          
                            <th><?php echo lang("tbl_title_action"); ?></th>                                                                          
                        </tr>
                    </thead>   
                    <tbody>

                        <?php foreach ($flags as $pl) { ?> 
                            <tr class="<?php
                    if (($pl["fl_order"] % 2) == 0) {
                        echo 'alternate-row';
                    }
                            ?>">
                                <td><input type="checkbox" name="rows[]" value="<?php echo $pl["_flid"]; ?>" class="checkrow" /></td>
                                <td><?php echo $start_number; ?></td>
                                <td class="leftContent"><?php echo ucwords($pl["fl_country"]); ?></td>
                                <td>
                                    <?php if ($pl["fl_image"] && file_exists("./global/uploads/flags/" . $pl["fl_image"])) { ?>
                                        <img src="<?php echo base_url(); ?>global/uploads/flags/<?php echo $pl["fl_image"]; ?>?<?php echo time(); ?>" title="<?php echo $pl["fl_country"]; ?>" alt="<?php echo $pl["fl_country"]; ?>" width="70" height="50" />
                                    <?php } else { ?>
                                        <img src="<?php echo base_url(); ?>global/views/back/img/no_image.gif" title="<?php echo lang("img_no_image"); ?>" alt="<?php echo lang("img_no_image"); ?>" width="70" height="50" />
                                    <?php } ?>
                                </td>
                                <td>                                     
                                    <a href="<?php echo base_url(); ?>flags/admin_flags/edit/<?php echo $pl["_flid"]; ?>" title="<?php echo lang("btn_edit"); ?>"><img src="<?php echo base_url(); ?>global/views/back/img/icons/edit-icon.png" alt="<?php echo lang("btn_edit"); ?>" /></a> &nbsp;&nbsp;|&nbsp;&nbsp;                
                                    <?php if ($pl["fl_status"] == 0) { ?>
                                        <a href="<?php echo base_url(); ?>flags/admin_flags/activate/<?php echo $pl["_flid"]; ?>" title="<?php echo lang("btn_activate"); ?>"><img src="<?php echo base_url(); ?>global/views/back/img/icons/activate-icon.png" alt="<?php echo lang("btn_activate"); ?>" /></a> &nbsp;&nbsp;|&nbsp;&nbsp;
                                    <?php } else { ?>
                                        <a href="<?php echo base_url(); ?>flags/admin_flags/deactivate/<?php echo $pl["_flid"]; ?>" title="<?php echo lang("btn_deactivate"); ?>"><img src="<?php echo base_url(); ?>global/views/back/img/icons/deactivate-icon.png" alt="<?php echo lang("btn_deactivate"); ?>" /></a> &nbsp;&nbsp;|&nbsp;&nbsp;
                                    <?php } ?>
                                    <a href="<?php echo base_url(); ?>flags/admin_flags/delete/<?php echo $pl["_flid"]; ?>/<?php echo $pl["fl_order"]; ?>" title="<?php echo lang("btn_delete"); ?>" onclick="return confirm('Do you really want to delete this row(s)?');"><img src="<?php echo base_url(); ?>global/views/back/img/icons/delete-icon.png" alt="<?php echo lang("btn_delete"); ?>" /></a>                                
                                </td>
                            </tr>                                        
                        <?php $start_number++; } ?>                    
                    </tbody>
                </table>                        

                <div class="pagination pagination-centered"><ul><?php echo $pagination; ?></ul></div>

                <!--  start actions-box ............................................... -->
                <div>                                              
                    <button name="activate" class="btn btn-success action-activate activate oButton">
                        <i class="icon-ok icon-white"></i>  
                        <?php echo lang("btn_activate"); ?>                                            
                    </button>
                    <button name="deactivate" class="btn btn-info action-deactivate deactivate oButton">
                        <i class="icon-minus icon-white"></i>
                        <?php echo lang("btn_deactivate"); ?>                                            
                    </button>
                    <button name="delete" class="btn btn-danger action-delete delete oButton">
                        <i class="icon-trash icon-white"></i> 
                        <?php echo lang("btn_delete"); ?>
                    </button>
                </div>
                <?php echo form_close(); ?>
                <!-- end actions-box........... -->
            </div>
        </div><!--/span-->

    <?php } else { ?>
        <br clear="all" />            
        <div class="alert alert-info">                    
            <strong><?php echo lang("noti_info_nodata"); ?></strong>
        </div><!-- notification msginfo -->      
    <?php } ?>
</div><!--/row-->