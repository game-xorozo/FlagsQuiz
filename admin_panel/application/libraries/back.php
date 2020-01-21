<?php

if (!defined("BASEPATH"))
    exit("No direct script access allowed");

class back extends CI_Controller {

    public function __construct() {
        parent::__construct();

        $this->load->library("form_validation");
        $this->load->helper("form");
        $this->lang->load("back", "english");
    }

    //=====================================================================

    public function view($page_name, $data = NULL) {
        $user_data["username"] = $this->get_username();
        $this->load->view("back/header", $user_data);
        $this->load->view($page_name, $data);
        $this->load->view("back/footer");
    }

    //=====================================================================

    public function _is_logged_in() {
        if (!$this->session->userdata('logged_in')) {
            redirect('admin/login');
        }
    }

    //=====================================================================

    public function redirect_overview($noti, $page_segment = "") {
        $this->session->set_flashdata("noti_msg", $noti);
        redirect($this->uri->segment(1) . "/" . $this->uri->segment(2) . "/overview/" . $page_segment);
    }

    //=====================================================================


    public function get_username() {
        $this->db->where("us_id", $this->session->userdata["logged_in"]["id"]);
        $result = $this->db->get("users");
        return $result->row()->us_username;
    }

}