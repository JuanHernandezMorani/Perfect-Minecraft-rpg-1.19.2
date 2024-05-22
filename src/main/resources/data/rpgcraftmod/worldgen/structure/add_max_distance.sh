#!/bin/bash

# Buscar todos los archivos JSON en el directorio actual y subdirectorios
json_files=$(find . -name "*.json" -type f)

# Iterar sobre cada archivo JSON
for file in $json_files; do
    # Verificar si el archivo no contiene la línea "max_distance_from_center"
    if ! grep -q '"max_distance_from_center"' "$file"; then
        # Agregar la línea "max_distance_from_center": 80 al final del archivo
        echo '"max_distance_from_center": 80' >> "$file"
        echo "Se agregó 'max_distance_from_center' a $file"
    fi
done