<?php
include 'conexion.php';
$nombre=$_GET['nombre'];




$consulta= $conn->prepare("UPDATE bloqueada SET nombre=? where idbloqueada=1;");
$consulta->bind_param('s',$nombre);
$consulta->execute();
$consulta->close();
$conn -> close();
?>