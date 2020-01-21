<?php

if (!defined("BASEPATH"))
    exit("No direct script access allowed");

class Site extends back {

    public function __construct() {
        parent::__construct();
        $this->lang->load("site", "english");        
    }

    //=====================================================================

    public function get_updates($last_flag) {        

        $this->db->select("flags.*, flags._flid");
        $this->db->from("flags");        
        $this->db->where("_flid > ", $last_flag);
        $this->db->where("fl_status", 1);
        $this->db->where("fl_image != ", "");
        $result["flags"] = $this->db->get()->result_array();

        echo json_encode($result);

    }
    
    //=====================================================================

    public function show_flag($fl_id) {        
        $this->db->where("_flid", $fl_id);               
        $data["flag"] = $this->db->get("flags")->row();
                             
        $data["market_link"] = $this->db->get("users")->row();
        $this->load->view("front/flag", $data);
    }
}

