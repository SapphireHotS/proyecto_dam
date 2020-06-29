<?php
include 'conexion.php';


$consulta= "select nombre from grupos;";
$resultado= $conn -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $mesa[] = array_map(null, $fila);
}

echo json_encode($mesa);

$resultado ->close();
$conn -> close();
?>