#!/bin/bash

DIRECTORY=$(pwd)

OUTPUT_JSON="C2.json"

rm -f "$OUTPUT_JSON"

declare -A complexity_grades

total_archivos=$(find "$DIRECTORY" -type f -name "*.java" | wc -l)

calcular_complejidad() {
    local archivo="$1"
    local complejidad=0

    local contenido=$(< "$archivo")

    [[ "$contenido" =~ ArrayList ]] && complejidad=$((complejidad + 3))
    [[ "$contenido" =~ LinkedList ]] && complejidad=$((complejidad + 4))
    [[ "$contenido" =~ HashMap ]] && complejidad=$((complejidad + 5))
    [[ "$contenido" =~ TreeMap ]] && complejidad=$((complejidad + 6))
    [[ "$contenido" =~ HashSet ]] && complejidad=$((complejidad + 3))
    [[ "$contenido" =~ TreeSet ]] && complejidad=$((complejidad + 4))
    [[ "$contenido" =~ sort\( ]] && complejidad=$((complejidad + 10))
    [[ "$contenido" =~ for\ \( ]] && ! [[ "$contenido" =~ for\ \(.*for\ \( ]] && complejidad=$((complejidad + 8))
    [[ "$contenido" =~ for\ \(.*for\ \( ]] && complejidad=$((complejidad + 16))
    [[ "$contenido" =~ if.*\{.*if ]] && complejidad=$((complejidad + 5))
    [[ "$contenido" =~ switch ]] && complejidad=$((complejidad + 0))

    echo "$complejidad"
}

archivos_procesados=0
temp_file=$(mktemp)

find "$DIRECTORY" -type f -name "*.java" | while read -r archivo; do
    if [ -f "$archivo" ]; then
        archivos_procesados=$((archivos_procesados + 1))

        if (( archivos_procesados % 10 == 0 )) || (( archivos_procesados == total_archivos )); then
            porcentaje_completado=$(($archivos_procesados * 100 / $total_archivos))
            printf "Completado: %.0f%%\n" "$porcentaje_completado"
        fi

        nivel_complejidad=$(calcular_complejidad "$archivo")
        archivo_nombre=$(basename "$archivo")

        echo "$nivel_complejidad,$archivo_nombre" >> "$temp_file"
    fi
done

while IFS=, read -r nivel_complejidad archivo; do
    if [ -z "${complexity_grades["$nivel_complejidad"]}" ]; then
        complexity_grades["$nivel_complejidad"]=$archivo
    else
        complexity_grades["$nivel_complejidad"]+=",$archivo"
    fi
done < "$temp_file"

rm -f "$temp_file"

echo "{" >> "$OUTPUT_JSON"
for nivel_complejidad in "${!complexity_grades[@]}"; do
    echo "\"Puntos de complejidad $nivel_complejidad\": {" >> "$OUTPUT_JSON"
    archivos=$(echo "${complexity_grades["$nivel_complejidad"]}" | tr "," "\n")
    cantidad=$(echo "$archivos" | wc -l)
    echo "  \"cantidad\": $cantidad," >> "$OUTPUT_JSON"
    echo "  \"archivos\": [" >> "$OUTPUT_JSON"
    while read -r archivo; do
        echo "    \"$archivo\"," >> "$OUTPUT_JSON"
    done <<< "$archivos"
    echo "  ]" >> "$OUTPUT_JSON"
    echo "}," >> "$OUTPUT_JSON"
done
echo "}" >> "$OUTPUT_JSON"

sed -i '$ s/},/}/' "$OUTPUT_JSON"

echo "Proceso completado. Se ha creado el archivo $OUTPUT_JSON con los niveles de complejidad de los archivos Java."