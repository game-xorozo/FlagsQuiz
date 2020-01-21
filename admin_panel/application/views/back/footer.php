<!-- content ends -->
</div><!--/#content.span10-->

</div><!--/fluid-row-->


<hr>

<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Settings</h3>
    </div>
    <div class="modal-body">
        <p>Here settings can be configured...</p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">Close</a>
        <a href="#" class="btn btn-primary">Save changes</a>
    </div>
</div>
<footer>
    <p class="pull-left">&copy; <a href="http://www.nileworx.com" target="_blank">NileWorx</a> 2014</p>
    <p class="pull-right">Powered by: <a href="http://usman.it/free-responsive-admin-template">Charisma</a></p>
</footer>

</div><!--/.fluid-container-->

<!-- external javascript
================================================== -->
<!-- Flagd at the end of the document so the pages load faster -->

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

<?php
//Google Analytics code for tracking my demo site, you can remove this.
if ($_SERVER['HTTP_HOST'] == 'usman.it') {
    ?>
    <script>
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-26532312-1']);
        _gaq.push(['_trackPageview']);
        (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
        })();
    </script>
<?php } ?>

<script type="text/javascript">
    jQuery(document).ready(function(){   
        var per_page_num = "<?php echo $per_page_num; ?>";
        jQuery("#per_page_menu option[value='" + per_page_num +"']").prop("selected", true); 
                
        jQuery("#checkall").click(function () {
            jQuery(".checkrow").prop("checked", this.checked);
        });
        
        jQuery("button.oButton").click(function () {                                        
            var len = jQuery(".checkrow:checked").length;
            if (len < 1) {
                alert("Sorry, you didn't select any row.");
                return false;
            } else {
                if (jQuery(this).hasClass("delete")) {
                    return confirm('Do you really want to delete this row(s)?');
                }
            }
        });
    });            
</script>

<script type="text/javascript">
    jQuery(document).ready(function(){
        // Specify which module is selected 
        var moduleName = "<?php echo $this->uri->segment(1); ?>" + "-" + "<?php echo $this->uri->segment(2); ?>";
        jQuery("li[id='" + moduleName +"']").addClass("current");  
                
    });            
</script>

</body>
</html>