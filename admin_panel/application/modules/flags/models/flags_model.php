<?php

if (!defined("BASEPATH"))
    exit("No direct script access allowed");

class Flags_model extends CI_Model {

    private $table = "flags";

    public function __construct() {
        parent::__construct();
    }

    //=====================================================================

    public function overview($limit, $start) {

        $start = ($start != 0) ? (($limit * $start) - $limit) : 0;

        $this->db->limit($limit, $start);
        $this->db->order_by("fl_order", "asc");
        $this->db->select("flags.*");
        $this->db->from("flags");        


        return $this->db->get()->result_array();
    }

    //=====================================================================       

    public function get_flags_count() {

        return $this->db->count_all($this->table);
    }

    //=====================================================================

    public function create($image) {

        $this->db->set("fl_country", ucwords($this->input->post("fl_country")));
        $this->db->set("fl_image", $image);       
        $this->db->set("fl_wikipedia", $this->input->post("fl_wikipedia"));        
        $this->db->set("fl_status", $this->input->post("fl_status"));
        $this->db->set("fl_image_sdcard", 1);
        $this->db->insert($this->table);

        $inserted_id = mysql_insert_id();
        $this->update_inserted_row_order($inserted_id);
        $this->update_image_name($inserted_id);

        return TRUE;
    }

    //=====================================================================

    public function update_inserted_row_order($inserted_id) {
        $this->db->select_max("fl_order");
        $max_order = $this->db->get($this->table)->row();

        $this->db->where("_flid", $inserted_id);
        $this->db->set("fl_order", $max_order->fl_order + 1);
        $this->db->update($this->table);
    }

    //=====================================================================

    public function update_image_name($fl_id) {
        $this->db->where("_flid", $fl_id);
        $this->db->select("fl_image");
        $image = $this->db->get($this->table)->row();

        $this->db->where("_flid", $fl_id);
        $this->db->set("fl_image", $fl_id . ".png");
        $this->db->update($this->table);

        rename("./global/uploads/flags/" . $image->fl_image, "./global/uploads/flags/" . $fl_id . ".png");
    }

    //=====================================================================

    public function edit($_flid, $image) {

        $this->db->where("_flid", $_flid);
        $this->db->set("fl_country", ucwords($this->input->post("fl_country")));
        if ($image) {
            $this->db->set("fl_image", $image);
        }        
        $this->db->set("fl_wikipedia", $this->input->post("fl_wikipedia"));        
        $this->db->set("fl_status", $this->input->post("fl_status"));

        $this->db->update($this->table);

        if ($image) {
            $this->update_image_name($_flid);
        }


        return TRUE;
    }

    //=====================================================================

    public function get_this_flag($_flid) {

        $this->db->where("_flid", $_flid);
        return $this->db->get($this->table)->row();
    }

    //=====================================================================

    public function order_up($_flid) {

        $this->db->where("_flid", $_flid);
        $this->db->select("fl_order");
        $order = $this->db->get($this->table)->row();

        $this->db->select_max("fl_order");
        $this->db->select("_flid");
        $this->db->where("fl_order < ", $order->fl_order);
        $max = $this->db->get($this->table)->row();
        if ($max->fl_order != 0) {
            $this->db->where("fl_order", $max->fl_order);
            $this->db->set("fl_order", $order->fl_order);
            $this->db->update($this->table);

            $this->db->where("_flid", $_flid);
            $this->db->set("fl_order", $max->fl_order);
            $this->db->update($this->table);
        }

        return TRUE;
    }

    //=====================================================================

    public function order_down($_flid) {

        $this->db->where("_flid", $_flid);
        $this->db->select("fl_order");
        $order = $this->db->get($this->table)->row();

        $this->db->select_min("fl_order");
        $this->db->select("_flid");
        $this->db->where("fl_order > ", $order->fl_order);
        $max = $this->db->get($this->table)->row();
        if ($max->fl_order != 0) {
            $this->db->where("fl_order", $max->fl_order);
            $this->db->set("fl_order", $order->fl_order);
            $this->db->update($this->table);

            $this->db->where("_flid", $_flid);
            $this->db->set("fl_order", $max->fl_order);
            $this->db->update($this->table);
        }

        return TRUE;
    }

    //=====================================================================

    public function operation() {
        if (isset($_POST["rows"])) {
            foreach ($_POST["rows"] as $_flid) {
                if (isset($_POST["activate"])) {
                    $this->activate($_flid);
                } elseif (isset($_POST["deactivate"])) {
                    $this->deactivate($_flid);
                } elseif (isset($_POST["delete"])) {
                    $this->db->where("_flid", $_flid);
                    $this->db->select("fl_image, fl_order");
                    $result = $this->db->get($this->table)->row();
                    
                    if ($result->fl_image && file_exists("./global/uploads/flags/" . $result->fl_image)) {
                        unlink("./global/uploads/flags/" . $result->fl_image);
                    }
                    $this->delete($_flid, $result->fl_order);
                }
            }
        }
    }

    //=====================================================================

    public function activate($_flid) {

        $this->db->where("_flid", $_flid);
        $this->db->set("fl_status", "1");
        $this->db->update($this->table);

        return TRUE;
    }

    //=====================================================================

    public function deactivate($_flid) {

        $this->db->where("_flid", $_flid);
        $this->db->set("fl_status", "0");
        $this->db->update($this->table);

        return TRUE;
    }

    //=====================================================================

    public function delete($_flid, $fl_order) {

        $this->db->where("_flid", $_flid);
        if ($this->db->delete($this->table)) {
            $query = "UPDATE {$this->table} ";
            $query.= "SET fl_order = fl_order - 1 ";
            $query.= "WHERE fl_order > '{$fl_order}'";
            $this->db->query($query);
        }

        return TRUE;
    }

    //=====================================================================

    public function get_flags() {

        $this->db->order_by("fl_order", "asc");
        return $this->db->get("flags")->result_array();
    }

}