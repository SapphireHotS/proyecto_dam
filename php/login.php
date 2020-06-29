<?php
include 'conexion.php';
$correo=$_POST['correo'];
$password=$_POST['password'];


//$correo="bogdan_gabor2020@hotmail.com";
//$password="pepe";
$passwordmd=md5($password);


$consulta= $conn->prepare("SELECT nombre FROM usuarios WHERE correo=? AND password=?");
$consulta->bind_param('ss',$correo,$passwordmd);
$consulta->execute();
$resultado= $consulta->get_result();

if($fila=$resultado -> fetch_assoc()){
    echo json_encode($fila,JSON_UNESCAPED_UNICODE);
}
$consulta->close();
$conn -> close();
?>