<!DOCTYPE html>
<html>
<head>
    <title>${titulo}</title>
    <link rel="stylesheet" href="/publico/estilo.css">
</head>
<body>

<h1 id="logih1">Ejemplo de formulario</h1>


<form action="/procesarFormulario" method="post"  enctype="application/x-www-form-urlencoded">
    <h3>Usuario: <input name="usuario" type="text"/><br/></h3>
    <h3>Clave: <input name="contrasena" type="password"/><br/></h3>
    <button name="Enviar" type="submit"><h4>Enviar</h4></button>
    <!-- Es un comentario en HTML -->
    <#--        Es lo mismo que arriba.-->
    <#--        <input type="submit" value="Enviar"/>-->
</form>
</body>
</html>