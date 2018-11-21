# En la primera carpeta, Reinas, se encuentra el contenido de la primera aplicación.
# Al ejecutarse, se debe incluir el tipo de figura que queremos en cada posicion:
$java Aplicacion1 "RRRRRRRR" , $java Aplicacion1 "TRRRRRRR" , etc.....
# como hay varias soluciones, lo mejor es redirigir stdOut a un fichero de texto.
$java Aplicacion1 "RRRRRRTT" > resultados.txt  
# En la segunda carpeta, Arbol, se encuentra el contenido de la segunda aplicación. Se ejecuta mediante
$java Aplicacion2 "1+log(2*3)" , $java Aplicacion2 "1+3*(11+4)"
