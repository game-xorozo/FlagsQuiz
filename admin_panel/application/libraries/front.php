<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class front extends CI_Controller {
        /**
         *
         * @param type $page_name
         * @param   $data 
         */
	public function view($page_name,$data = NULL)
	{
		$this->load->view('front/header');
		$this->load->view($page_name,$data);
		$this->load->view('front/footer');
	}
        //==========================================================================================
}

