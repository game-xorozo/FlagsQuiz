<?php

if (!defined("BASEPATH"))
    exit("No direct script access allowed");

class Admin extends back {

    public function __construct() {
        parent::__construct();
        $this->lang->load("admin", "english");
        $this->load->model("admin_model", "admin");
        $this->load->library("form_validation");

//        $this->_is_logged_in(); 
    }

    //=====================================================================

    public function index() {
        $this->_is_logged_in();
        redirect("admin/dashboard");
    }

    //=====================================================================

    public function login() {
        if ($this->session->userdata('logged_in')) {
            redirect('admin/dashboard');
        }
        $this->form_validation->set_rules('us_username', 'Username', 'trim|required');
        $this->form_validation->set_rules('us_password', 'Password', 'required');
        $this->form_validation->set_rules('us_remember_me', 'Remember Me', '');


        if ($this->form_validation->run() == FALSE) {
            $data = "";
            $submit = $this->input->post("submit");
            $username = $this->input->post("us_username");
            $password = $this->input->post("us_password");
            if (($submit && $username == "") || ($submit && $password == "")) {
                $data["error_msg"] = "<div class='alert alert-error'>Invalid username or password.</div>";
            }
            $this->load->view("back/login", $data);
        } else {
            $data = $this->admin->check_login();
            if ($data) {

                $sess_array = array(
                    'id' => $data->us_id,
                    'username' => $data->us_username,
                    'state' => 1
                );

                $this->session->set_userdata('logged_in', $sess_array);
                redirect(base_url() . "admin/dashboard");
            } else {
                $data["error_msg"] = "<div class='alert alert-error'>Invalid username or password.</div>";
                $this->load->view("back/login", $data);
            }
        }
    }

    //=====================================================================

    public function logout() {
        $this->_is_logged_in();
        $this->session->unset_userdata('logged_in');
//        session_destroy();
        redirect(base_url() . "admin/login");
    }

    //=====================================================================

    public function profile() {
        $this->_is_logged_in();
        $this->form_validation->set_rules('us_username', 'Username', 'trim|required');
        if ($this->input->post("us_password")) {
            $this->form_validation->set_rules('us_old_password', 'Old password', 'trim|callback_check_old_password');
            $this->form_validation->set_rules('us_password', 'Password', 'trim|matches[us_password_conf]');
            $this->form_validation->set_rules('us_password_conf', 'Re-type password', 'trim');
        }
        $this->form_validation->set_rules('us_market_link', 'Market link', 'trim|required');

        $data["admin_user"] = $this->admin->get_admin_user();
        if ($this->form_validation->run() == FALSE) {
            $this->view("back/edit_profile", $data);
        } else {
            $this->admin->edit_profile();
            $this->session->set_flashdata("noti_msg", "User data have been updated successfully.");
            redirect("admin/dashboard");
        }
    }

    //===================================================================== 

    public function check_old_password() {
        $this->_is_logged_in();
        if ($this->admin->get_password()) {
            return true;
        } else {
            $this->form_validation->set_message('check_old_password', 'This password is wrong.');
            return false;
        }
    }

    //=====================================================================   

    public function dashboard() {
        $this->_is_logged_in();
        $this->view("back/dashboard");
    }

}

