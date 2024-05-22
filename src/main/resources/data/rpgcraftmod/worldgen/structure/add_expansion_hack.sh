#!/bin/bash

# Buscar todos los archivos JSON en el directorio actual y subdirectorios
json_files=$(find . -name "*.json" -type f)

# Iterar sobre cada archivo JSON
for file in $json_files; do
    # Verificar si el archivo no contiene la línea "use_expansion_hack"
    if ! grep -q '"use_expansion_hack"' "$file"; then
        # Agregar la línea "use_expansion_hack": false al final del archivo
        echo '"use_expansion_hack": false' >> "$file"
        echo "Se agregó 'use_expansion_hack' a $file"
    fi
done