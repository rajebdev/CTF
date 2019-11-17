<?php 

include 'flag.php';
class KKSI2019{
	public $status   = "";
	public $function = "";
	public $argument = "";
	public $the_len  = "";
	public $hidden   = "";


}
if(isset($_GET['ctf'])){

	function xor_master($string, $key){
		$ret = "";
		for($i=0; $i<strlen($string); $i++){
			$ret .= chr( (ord($string[$i]) ^ $key) );
		}
		return $ret;
	}


	$anti_shell = ['shell_exec', 'exec', 'system', 'popen', 'passthru'];
	
	$data = unserialize($_GET['ctf']);
	
	if($data->the_len != strlen(key)){
		die('YO!');
	}

	echo key;
	
	if(xor_master(key, $data->hidden) != "FaccgMfooidaifg"){
		die('Close');
	}

	if($data->status === 'KKSI2019'){
		$func = $data->function;
		if(in_array(strtolower($func), $anti_shell)){ die('Nonono'); }
		$arg = $data->argument;
		if(strlen($arg) > 2 or strpos($arg, "*") !== False) { die('Anti cheating!'); }
		var_dump($func($arg));
	}

}else{
	// show_source(__FILE__);
	echo 'KKSI2019{Kalau_Kamu_Submit_ini_Kamu_Pepega_Sekali}';
}