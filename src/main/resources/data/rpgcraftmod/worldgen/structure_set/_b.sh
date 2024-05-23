#!/bin/bash

# Directorio donde se encuentran los archivos .json
DIRECTORY=$(pwd)

# Rango para el valor de salt
MIN_SALT=1209130
MAX_SALT=22090409

# Array para mantener un registro de los valores de salt usados
declare -A used_salts

# Función para generar un valor de salt único
generate_unique_salt() {
    local salt
    while true; do
        salt=$((RANDOM % (MAX_SALT - MIN_SALT + 1) + MIN_SALT))
        if [ -z "${used_salts[$salt]}" ]; then
            used_salts[$salt]=1
            echo $salt
            return
        fi
    done
}

# Buscar y reemplazar los valores de salt en cada archivo .json en el directorio y subdirectorios
find "$DIRECTORY" -type f -name "*.json" | while read -r file; do
    if [ -f "$file" ]; then
        echo "Procesando archivo: $file"
        
        # Generar un valor de salt único
        NEW_SALT=$(generate_unique_salt)
        
        # Reemplazar los valores de salt
        sed -i -E "s/\"salt\": [0-9]+/\"salt\": $NEW_SALT/" "$file"
        
        echo "Archivo $file actualizado con salt: $NEW_SALT"
    fi
done

echo "Proceso completado."