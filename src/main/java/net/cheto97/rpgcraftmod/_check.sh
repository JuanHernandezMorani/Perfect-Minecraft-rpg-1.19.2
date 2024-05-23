#!/bin/bash

DIRECTORY=$(pwd)

OUTPUT_JSON="revision.json"
TEMP_FILE=$(mktemp)

rm -f "$OUTPUT_JSON"
rm -f "$TEMP_FILE"

existen=$(find "$DIRECTORY" -type f -name "*.java" | wc -l)
total_archivos=0

find "$DIRECTORY" -type f -name "*.java" | while read -r archivo; do
    if [ -f "$archivo" ]; then
        total_archivos=$((total_archivos + 1))
        proceso=$(((total_archivos*100)/existen))
        echo "Procesando: $proceso%"

        contenido=$(tr -d ' \n' < "$archivo")
        cantidad_caracteres=${#contenido}
        archivo_nombre=$(basename "$archivo")
        nombre_sin_extension="${archivo_nombre%.java}"

        echo "{\"Nombre del archivo\":\"$nombre_sin_extension\",\"Cantidad de caracteres\":$cantidad_caracteres,\"ruta\":\"$archivo\"}," >> "$TEMP_FILE"
    fi
done

detalle=$(cat "$TEMP_FILE" | sed '$ s/,$//')

echo "{" > "$OUTPUT_JSON"
echo "  \"Datos\": {" >> "$OUTPUT_JSON"
echo "    \"Cantidad de archivos\": $existen," >> "$OUTPUT_JSON"
echo "    \"Detalle\": [" >> "$OUTPUT_JSON"
echo "      $detalle" >> "$OUTPUT_JSON"
echo "    ]" >> "$OUTPUT_JSON"
echo "  }" >> "$OUTPUT_JSON"
echo "}" >> "$OUTPUT_JSON"

rm -f "$TEMP_FILE"

echo "Proceso completado. Se ha creado el archivo $OUTPUT_JSON con los detalles de los archivos Java."
