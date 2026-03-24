# WingsGeneratorGUI

Aplicación en Python + Tkinter para gestionar y generar alas nuevas del mod `rpgcraftmod`.

## Requisitos

- Python 3.9+
- Ejecutar desde la raíz del proyecto del mod o configurar la ruta raíz dentro de la app.

## Ejecutar

```bash
python3 tools/WingsGeneratorGUI/wings_generator_gui.py
```

## Uso

1. Configura la ruta raíz del proyecto si no se detecta automáticamente.
2. Usa **Agregar** para crear alas pendientes (estándar o personalizada).
3. Para alas personalizadas, puedes seleccionar archivos origen de textura (`.png`), geo (`.geo.json`) y animación (`.animation.json`) o dejar campos vacíos para usar las rutas por defecto según `registry_name` si esos archivos ya existen en assets.
4. Opcionalmente define `display_name` y parámetros de `FlightTuning`.
5. Usa **Crear alas agregadas** para generar:
   - Registro en `ModItems.java`.
   - Entrada en `data/curios/tags/items/wing.json`.
   - Modelo item `assets/rpgcraftmod/models/item/<registry_name>.json` con `builtin/entity`.
   - En custom: copia de textura/geo/anim y clase Java `WingDefinition` en `item/generated`.
   - En `display_name`: actualización de `assets/rpgcraftmod/lang/en_us.json`.
6. Revisa el panel de logs para errores, advertencias y archivos creados.

## Validaciones clave

- `registry_name` en snake_case y sin duplicados dentro de la cola pendiente.
- Bloqueo si `registry_name` ya existe en `ModItems.java`.
- Validación de extensiones de archivos custom (`.png`, `.geo.json`, `.animation.json`).
- Si una ruta custom se deja vacía, la herramienta exige que exista el archivo por defecto en assets.
- Validación numérica de `FlightTuning`, incluyendo rangos para evitar valores inválidos (por ejemplo `drag`).

## ¿Qué es literalmente `FlightTuning` y para qué sirve?

`FlightTuning` es el bloque de parámetros numéricos que usa el mod para definir cómo se comporta el vuelo de un ala en juego.

Modificar `FlightTuning` sirve para ajustar la “sensación” y el rendimiento del ala, por ejemplo:

- velocidad horizontal objetivo y máxima,
- aceleración al volar,
- control vertical (subida/bajada),
- arrastre (`drag`),
- impulso extra (`boost_acceleration`),
- frecuencia de bucle (`loop_ticks`),
- y desgaste por uso (`durability_tick_interval`).

En resumen: cambiar `FlightTuning` cambia directamente el balance y estilo de vuelo de cada ala (más rápida, más estable, más ágil, más costosa en durabilidad, etc.).

## Persistencia

La lista de pendientes se guarda en:

- `tools/WingsGeneratorGUI/pending_wings.json`

Se actualiza al agregar/editar/eliminar, al guardar manualmente y al cerrar la ventana.

## Comportamiento de limpieza

- Las alas procesadas correctamente se eliminan de pendientes.
- Las alas con conflicto de nombre en `ModItems.java` se conservan para que puedas editar el `registry_name` y reintentar.
- Si ocurre otro error durante el procesamiento de una ala, también se conserva en pendientes para corrección.
