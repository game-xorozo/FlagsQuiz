<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Admin_model extends CI_Model {

    public function __construct() {
        parent::__construct();
    }
    
    //=====================================================================
    
    public function check_login() {

        $this->db->where("us_username", $this->input->post("us_username"));
        $this->db->where("us_password", md5($this->input->post("us_password")));
        $result = $this->db->get("users");
        return $result->row();
    }
    
    //=====================================================================
    
    public function get_admin_user() {        
        $this->db->where("us_id",$this->session->userdata["logged_in"]["id"]);
        $result = $this->db->get("users");
        return $result->row();
    }
    
    //=====================================================================
    
    public function edit_profile() {

        $this->db->set("us_username", $this->input->post("us_username"));
        if ($this->input->post("us_password")) {
           $this->db->set("us_password", md5($this->input->post("us_password")));
        }
        
        $this->db->set("us_market_link", $this->input->post("us_market_link"));
        $this->db->update("users");        
    }
    
    //=====================================================================   
    
    public function get_password() {

        $this->db->where("us_id",1);
        $result = $this->db->get("users")->row();
         
        if ($result->us_password == md5($this->input->post("us_old_password"))) {            
            return true;
        } else {
            return false;
        }        
    }

}