<?php

if (!defined("BASEPATH"))
    exit("No direct script access allowed");

class Admin_flags extends back {

    private $c_name = "admin_flags_";

    public function __construct() {
        parent::__construct();
        $this->lang->load("flags", "english");
        $this->load->model("flags_model", "flags");
        $this->load->library("form_validation");
        $this->form_validation->set_error_delimiters("<label class='error'>", "</label>");

        $this->_is_logged_in();
    }

    //=====================================================================

    public function index() {
        $this->overview();
    }

    //=====================================================================

    public function overview() {

        $this->session->set_userdata("page_number", $this->uri->segment(4));
        $per_page_sess = $this->session->userdata($this->c_name . "per_page_sess");
        $this->load->library("pagination");


        $config["base_url"] = base_url() . "flags/admin_flags/overview/";
        $config["total_rows"] = $this->flags->get_flags_count();
        $config["per_page"] = ($per_page_sess) ? $per_page_sess : 10;
        $config["uri_segment"] = 4;
        $config["use_page_numbers"] = TRUE;
        $config['num_links'] = 3;

        $config['num_tag_open'] = '<li>';
        $config['num_tag_close'] = '</li>';

        $config['cur_tag_open'] = '<li class="active"><a href="#">';
        $config['cur_tag_close'] = '</a></li>';

        $config['first_link'] = 'First';
        $config['first_tag_open'] = '<li>';
        $config['first_tag_close'] = '</li>';

        $config['last_link'] = 'Last';
        $config['last_tag_open'] = '<li>';
        $config['last_tag_close'] = '</li>';

        $config['prev_link'] = 'Prev';
        $config['prev_tag_open'] = '<li>';
        $config['prev_tag_close'] = '</li>';

        $config['next_link'] = 'Next';
        $config['next_tag_open'] = '<li>';
        $config['next_tag_close'] = '</li>';

        $this->pagination->initialize($config);

        $current_page = ($this->uri->segment(4)) ? $this->uri->segment(4) : 1;
        $data["start_number"] = ($config["per_page"] * ($current_page - 1)) + 1;

        $data["pagination"] = $this->pagination->create_links();

        $data["flags"] = $this->flags->overview($config["per_page"], $this->uri->segment(4));
        if (!$data["flags"] && $this->uri->segment(4) >= 2) {
            $data["flags"] = $this->flags->overview($config["per_page"], $this->uri->segment(4) - 1);
        }
        $data["per_page_num"] = $per_page_sess;
        $this->view("back/overview", $data);
    }

    //=====================================================================

    private function set_form_validation_rules() {
        $this->form_validation->set_rules("fl_country", lang("input_country"), "required|trim|xss_clean|htmlspecialchars");
        $this->form_validation->set_rules("fl_wikipedia", lang("input_wikipedia"), "trim|xss_clean|htmlspecialchars");
        $this->form_validation->set_rules("fl_status", lang("input_status"), "");
    }

    //=====================================================================

    public function create() {

        $this->set_form_validation_rules();
        $data["flags"] = $this->flags->get_flags();
        if ($this->form_validation->run() == FALSE) {
            $this->view("back/create", $data);
        } else {
            $image = $this->uploadImage("fl_image");
            $this->flags->create($image);
            $this->redirect_overview(lang("noti_success_added"));
        }
    }

    //=====================================================================

    function uploadImage($image) {

        $config['upload_path'] = PUBPATH . "global/uploads/flags/";

        $config['allowed_types'] = "gif|jpg|jpeg|png";        

        $this->load->library('upload', $config);

        if (!$this->upload->do_upload($image)) {

            echo $this->upload->display_errors();
        } else {

            $img_info = $this->upload->data();
            return $img_info['file_name'];
        }
    }

    //=====================================================================        

    public function edit($_flid) {
        $data["flag"] = $this->flags->get_this_flag($_flid);
        $data["flags"] = $this->flags->get_flags();
        $this->set_form_validation_rules();

        if ($this->form_validation->run() == FALSE) {
            $this->view("back/edit", $data);
        } else {
            $image = $this->uploadImage("fl_image");
            if ($image) {
                if ($data["flag"]->fl_image && file_exists("./global/uploads/flags/" . $data["flag"]->fl_image)) {
                    unlink("./global/uploads/flags/" . $data["flag"]->fl_image);
                }
            }

            $this->flags->edit($_flid, $image);
            $this->redirect_overview(lang("noti_success_updated"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================       

    public function order_up($_flid) {

        if ($this->flags->order_up($_flid)) {
            $this->redirect_overview(lang("noti_success_reordered"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================

    public function order_down($_flid) {

        if ($this->flags->order_down($_flid)) {
            $this->redirect_overview(lang("noti_success_reordered"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================        

    public function operation() {
        if ($_POST["per_page"] != $this->session->userdata($this->c_name . "per_page_sess")) {
            $noti = "";
            $this->session->set_userdata($this->c_name . "per_page_sess", $_POST["per_page"]);
            $this->session->unset_userdata("page_number");
        } else {
            if (isset($_POST["activate"])) {
                $noti = lang("noti_success_activated");
            } elseif (isset($_POST["deactivate"])) {
                $noti = lang("noti_success_deactivated");
            } elseif (isset($_POST["delete"])) {
                $noti = lang("noti_success_deleted");
            }
            $this->flags->operation();
        }
        $this->redirect_overview($noti, $this->session->userdata("page_number"));
    }

    //=====================================================================

    public function activate($_flid) {
        if ($this->flags->activate($_flid)) {
            $this->redirect_overview(lang("noti_success_activated"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================

    public function deactivate($_flid) {

        if ($this->flags->deactivate($_flid)) {
            $this->redirect_overview(lang("noti_success_deactivated"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================

    public function delete($_flid, $fl_order) {
        $flag = $this->flags->get_this_flag($_flid);

        if ($this->flags->delete($_flid, $fl_order)) {
            if ($flag->fl_image && file_exists("./global/uploads/flags/" . $flag->fl_image)) {
                unlink("./global/uploads/flags/" . $flag->fl_image);
            }
            $this->redirect_overview(lang("noti_success_deleted"), $this->session->userdata("page_number"));
        }
    }

    //=====================================================================
//    public function insert_data() {
//        $i = 1;
//        while ($i <= 100) {
//            $this->db->set("fl_title", "a - " . $i);
//            $this->db->set("fl_details", "wohdho");
//            $this->db->set("fl_picture", "image - " . $i . ".jpg");
//            $this->db->set("fl_order", $i);
//            $this->db->set("fl_created", time());
//            $this->db->set("fl_status", "1");
//            $this->db->insert("flags");
//            $i++;
//        }
//    }
//    public function rename_flags() {
//        $data = $this->db->get("flags")->result_array();
//        
//        foreach ($data as $d) {
//            rename("./global/uploads/flags/" . $d["fl_image"], "./global/uploads/flags/" . $d["_flid"] . ".png");
//        }
//    }
}

