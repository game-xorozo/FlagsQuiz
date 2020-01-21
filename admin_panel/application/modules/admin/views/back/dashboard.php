<div>
    <ul class="breadcrumb">
        <li>
            Dashboard
        </li>

    </ul>
</div>
        <div class="sortable row-fluid">   
            <?php if ($this->session->flashdata("noti_msg")) { ?>                           
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <strong><?php echo $this->session->flashdata("noti_msg"); ?></strong>
                </div><!-- notification msgsuccess -->                              
            <?php } ?>

            <a data-rel="tooltip" title="4 new pro members." class="well span3 top-block" href="<?php echo base_url(); ?>admin/profile">
                <span class="icon32 icon-color icon-user"></span>
                <div>Profile</div>

            </a>

            <a data-rel="tooltip" class="well span3 top-block" href="<?php echo base_url(); ?>flags/admin_flags">
                <span class="icon32 icon-color icon-globe"></span>
                <div>Flags</div>

            </a>              
        </div>
