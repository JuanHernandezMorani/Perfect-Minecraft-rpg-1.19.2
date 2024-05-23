#!/bin/bash

# Ejecutar el script _a.sh
./_a.sh

# Esperar a que el script _a.sh termine
wait

# Ejecutar el script _b.sh solo después de que _a.sh termine
./_b.sh