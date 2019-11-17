<?php ${"GLOBALS"}["temp"] = "_POST";
${"GLOBALS"}["gak_boleh"] = "wh";
${"GLOBALS"}["coba"] = "nep";
${"GLOBALS"}["cst"] = "ska";
highlight_file(__FILE__);
extract($_POST);

if (isset($_POST['nep'])) {
    $wh = ['system', 'shell_exec', 'passthru', 'exec', 'file_get_contents', 'readdir', 'glob', 'assert'];
    if (in_array(strtolower($nep), $wh)) {
        die('NonoNoNO');
    }
    echo json_encode(array_map($nep, [$ska]));
    die();
}
