<?php
$dbname='sgh_bd';
$user='root';
$pass='';
$host='127.0.0.1:3306';
$conn = mysqli_connect($host,$user,$pass);
$db = mysqli_select_db($conn,$dbname);
mysqli_set_charset($conn,"utf8");

#if($conn->connect_errno){
#    echo "Hay problemas con la conexión";
#}else{
#    echo "Conexión realizada con éxito.";
#}


?>