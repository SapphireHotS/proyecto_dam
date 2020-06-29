<?php
include 'conexion.php';
$nombre=$_GET['nombre'];




$consulta= $conn->prepare("SELECT nombrecorto, fotoprod FROM productos p JOIN grupos g on p.id_grupo=g.id WHERE g.nombre=?");
$consulta->bind_param('s',$nombre);
$consulta->execute();
$resultado= $consulta->get_result();

while($fila=$resultado -> fetch_array()){
    $prod[] = array_map('base64_encode', $fila);
}
echo json_encode($prod, JSON_UNESCAPED_UNICODE);

$consulta->close();
$conn -> close();
?>