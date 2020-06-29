<?php
include 'conexion.php';


$consulta= "select nummesa from mesas;";
$resultado= $conn -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $mesa[] = array_map('utf8_encode', $fila);
}

echo json_encode($mesa);

$resultado ->close();
$conn -> close();
?>