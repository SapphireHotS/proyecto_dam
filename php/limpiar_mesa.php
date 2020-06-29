<?php
include 'conexion.php';
$idmesa=$_POST['idmesa'];

//$idmesa=1;


$consulta= $conn->prepare("DELETE FROM pedidos WHERE idmesa=?;");
$consulta->bind_param('i',$idmesa);
$consulta->execute();
$consulta->close();

$consulta2= $conn->prepare("UPDATE mesas SET ocupada=false where nummesa=?;");
$consulta2->bind_param('s',$idmesa);
$consulta2->execute();
$consulta2->close();



$conn -> close();
?>