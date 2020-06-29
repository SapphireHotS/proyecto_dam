<?php
include 'conexion.php';
$idmesa=$_GET['idmesa'];




$consulta= $conn->prepare("SELECT * FROM pedidos WHERE idmesa=?");
$consulta->bind_param('i',$idmesa);
$consulta->execute();
$resultado= $consulta->get_result();

while($fila=$resultado -> fetch_array()){
    $producto[] = array_map(null, $fila);
}
echo json_encode($producto, JSON_UNESCAPED_UNICODE);

$consulta->close();
$conn -> close();
?>