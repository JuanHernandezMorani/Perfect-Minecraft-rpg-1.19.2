#!/bin/bash

# Directorio donde se encuentran los archivos .json
DIRECTORY=$(pwd)

# Valores a reemplazar
NEW_SPACING=150
NEW_SEPARATION=75

# Buscar y reemplazar los valores de spacing y separation en cada archivo .json en el directorio y subdirectorios
find "$DIRECTORY" -type f -name "*.json" | while read -r file; do
    if [ -f "$file" ]; then
        echo "Procesando archivo: $file"
        
        # Reemplazar los valores de spacing
        sed -i -E "s/\"spacing\": [0-9]+/\"spacing\": $NEW_SPACING/" "$file"
        
        # Reemplazar los valores de separation
        sed -i -E "s/\"separation\": [0-9]+/\"separation\": $NEW_SEPARATION/" "$file"
        
        echo "Archivo $file actualizado."
    fi
done

echo "Proceso completado."