<?php
include 'conexion.php';
$nombre=$_POST['nombre'];
$cantidad=$_POST['cantidad'];
$idmesa=$_POST['idmesa'];
//$nombrecorto="Pepsi";
//$cantidad="2";
//$mesa="2";



$consulta= $conn->prepare("SELECT id_prod, nombreprod, precio FROM productos WHERE nombrecorto=?");
$consulta->bind_param('s',$nombre);
$consulta->execute();
$resultado= $consulta->get_result();

$fila=$resultado -> fetch_array();
//echo $fila[0];//idprod
//echo $fila[1];//nombreprod
//echo $fila[2];//precio
$preciot= $fila[2]*$cantidad;
$sql="INSERT into pedidos VALUES (null,$idmesa,$fila[0],'$fila[1]',$cantidad,$fila[2],$preciot)";


if ($conn->query($sql) === TRUE) {
    //echo "New record created successfully";
} else {
    //echo "Error: " . $sql . "<br>" . $conn->error;
}


$consulta2= $conn->prepare("UPDATE mesas SET ocupada=true where nummesa=?;");
$consulta2->bind_param('s',$idmesa);
$consulta2->execute();
$consulta2->close();


$consulta->close();
$conn -> close();
?>