<?php
include 'conexion.php';
$idmesa=$_POST['idmesa'];
$idproducto=$_POST['idproducto'];
$cantidad=$_POST['cantidad'];

//$idmesa=1;
//$idproducto=23;
//$cantidad=1;


$consulta= $conn->prepare("DELETE FROM pedidos WHERE idmesa=? AND idproducto=? AND cantidad=? LIMIT 1;");
$consulta->bind_param('iii',$idmesa,$idproducto,$cantidad);
$consulta->execute();
$consulta->close();
$conn -> close();
?>