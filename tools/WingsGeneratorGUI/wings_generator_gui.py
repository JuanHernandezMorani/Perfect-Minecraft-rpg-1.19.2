from __future__ import annotations

import json
import re
import shutil
import traceback
from dataclasses import dataclass
from pathlib import Path
from typing import Dict, List, Optional

import tkinter as tk
from tkinter import filedialog, messagebox, ttk

MODULE = "rpgcraftmod"
REGEX = re.compile(r"^[a-z0-9_]+$")


@dataclass
class WingEntry:
    registry_name: str
    display_name: str = ""
    type: str = "standard"
    texture_source: str = ""
    geo_source: str = ""
    animation_source: str = ""
    target_horizontal_speed: str = ""
    max_horizontal_speed: str = ""
    acceleration: str = ""
    vertical_control: str = ""
    drag: str = ""
    boost_acceleration: str = ""
    loop_ticks: str = ""
    durability_tick_interval: str = ""

    def as_dict(self) -> Dict[str, str]:
        return self.__dict__.copy()

    @staticmethod
    def from_dict(data: Dict[str, str]) -> "WingEntry":
        defaults = WingEntry(registry_name="").as_dict()
        defaults.update(data or {})
        return WingEntry(**defaults)

    def symbol(self) -> str:
        return "personalizada" if self.type == "custom" else "estándar"


class ToolTip:
    def __init__(self, widget: tk.Widget, text: str):
        self.widget = widget
        self.text = text
        self.tipwindow: Optional[tk.Toplevel] = None
        widget.bind("<Enter>", self.show)
        widget.bind("<Leave>", self.hide)

    def show(self, _event=None):
        if self.tipwindow or not self.text:
            return
        x = self.widget.winfo_rootx() + 18
        y = self.widget.winfo_rooty() + self.widget.winfo_height() + 8
        self.tipwindow = tk.Toplevel(self.widget)
        self.tipwindow.wm_overrideredirect(True)
        self.tipwindow.wm_geometry(f"+{x}+{y}")
        label = tk.Label(
            self.tipwindow,
            text=self.text,
            justify=tk.LEFT,
            background="#ffffe0",
            relief=tk.SOLID,
            borderwidth=1,
            padx=6,
            pady=4,
            font=("Segoe UI", 9),
        )
        label.pack()

    def hide(self, _event=None):
        if self.tipwindow:
            self.tipwindow.destroy()
            self.tipwindow = None


class WingForm(tk.Toplevel):
    def __init__(self, master: tk.Tk, project_root: Path, existing_names: List[str], initial: Optional[WingEntry] = None):
        super().__init__(master)
        self.title("Agregar/Editar ala")
        self.resizable(True, True)
        self.project_root = project_root
        self.existing_names = set(existing_names)
        self.initial = initial
        self.result: Optional[WingEntry] = None

        container = ttk.Frame(self, padding=10)
        container.grid(row=0, column=0, sticky="nsew")
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)
        container.columnconfigure(1, weight=1)

        self.registry_var = tk.StringVar(value=initial.registry_name if initial else "")
        self.display_var = tk.StringVar(value=initial.display_name if initial else "")
        self.type_var = tk.StringVar(value=initial.type if initial else "standard")
        self.texture_source_var = tk.StringVar(value=initial.texture_source if initial else "")
        self.geo_source_var = tk.StringVar(value=initial.geo_source if initial else "")
        self.animation_source_var = tk.StringVar(value=initial.animation_source if initial else "")

        self.numeric_vars = {
            "target_horizontal_speed": tk.StringVar(value=initial.target_horizontal_speed if initial else ""),
            "max_horizontal_speed": tk.StringVar(value=initial.max_horizontal_speed if initial else ""),
            "acceleration": tk.StringVar(value=initial.acceleration if initial else ""),
            "vertical_control": tk.StringVar(value=initial.vertical_control if initial else ""),
            "drag": tk.StringVar(value=initial.drag if initial else ""),
            "boost_acceleration": tk.StringVar(value=initial.boost_acceleration if initial else ""),
            "loop_ticks": tk.StringVar(value=initial.loop_ticks if initial else ""),
            "durability_tick_interval": tk.StringVar(value=initial.durability_tick_interval if initial else ""),
        }

        ttk.Label(container, text="Registry name*").grid(row=0, column=0, sticky="w", pady=4)
        registry_entry = ttk.Entry(container, textvariable=self.registry_var)
        registry_entry.grid(row=0, column=1, sticky="ew", pady=4)
        ToolTip(registry_entry, "Solo snake_case: minúsculas, números y _")

        ttk.Label(container, text="Display name").grid(row=1, column=0, sticky="w", pady=4)
        display_entry = ttk.Entry(container, textvariable=self.display_var)
        display_entry.grid(row=1, column=1, sticky="ew", pady=4)
        ToolTip(display_entry, "Opcional: se agrega en lang/en_us.json")

        type_frame = ttk.Frame(container)
        type_frame.grid(row=2, column=0, columnspan=2, sticky="w", pady=6)
        ttk.Label(type_frame, text="Tipo:").pack(side="left", padx=(0, 8))
        std = ttk.Radiobutton(type_frame, text="Estándar", value="standard", variable=self.type_var, command=self._toggle_custom)
        cst = ttk.Radiobutton(type_frame, text="Personalizada", value="custom", variable=self.type_var, command=self._toggle_custom)
        std.pack(side="left", padx=4)
        cst.pack(side="left", padx=4)
        ToolTip(cst, "Permite copiar textura/geo/animation y crear WingDefinition")

        self.custom_section = ttk.LabelFrame(container, text="Opciones de ala personalizada")
        self.custom_section.grid(row=3, column=0, columnspan=2, sticky="nsew", pady=(8, 6))
        self.custom_section.columnconfigure(1, weight=1)

        self._source_row(self.custom_section, 0, "Textura origen", self.texture_source_var, "Selecciona .png")
        self._source_row(self.custom_section, 1, "Geo origen", self.geo_source_var, "Selecciona .geo.json")
        self._source_row(self.custom_section, 2, "Animación origen", self.animation_source_var, "Selecciona .animation.json")

        numeric_labels = [
            ("target_horizontal_speed", "Velocidad horizontal objetivo"),
            ("max_horizontal_speed", "Velocidad horizontal máxima"),
            ("acceleration", "Aceleración"),
            ("vertical_control", "Control vertical"),
            ("drag", "Arrastre"),
            ("boost_acceleration", "Aceleración de impulso"),
            ("loop_ticks", "Loop ticks"),
            ("durability_tick_interval", "Durability tick interval"),
        ]
        base_row = 3
        for idx, (key, label) in enumerate(numeric_labels):
            ttk.Label(self.custom_section, text=label).grid(row=base_row + idx, column=0, sticky="w", pady=3)
            entry = ttk.Entry(self.custom_section, textvariable=self.numeric_vars[key])
            entry.grid(row=base_row + idx, column=1, sticky="ew", pady=3)
            ToolTip(entry, "Opcional. Si se deja vacío se usa valor por defecto")

        action = ttk.Frame(container)
        action.grid(row=4, column=0, columnspan=2, sticky="e", pady=(10, 0))
        ttk.Button(action, text="Cancelar", command=self.destroy).pack(side="right", padx=6)
        ttk.Button(action, text="Guardar", command=self._save).pack(side="right")

        self._toggle_custom()
        self.grab_set()
        registry_entry.focus_set()

    def _source_row(self, parent: ttk.LabelFrame, row: int, label: str, variable: tk.StringVar, tooltip: str):
        ttk.Label(parent, text=label).grid(row=row, column=0, sticky="w", pady=3)
        entry = ttk.Entry(parent, textvariable=variable)
        entry.grid(row=row, column=1, sticky="ew", pady=3)
        btn = ttk.Button(parent, text="...", width=3, command=lambda v=variable: self._pick_file(v))
        btn.grid(row=row, column=2, sticky="w", padx=(4, 0), pady=3)
        ToolTip(entry, tooltip)

    def _pick_file(self, variable: tk.StringVar):
        initial_dir = str(self.project_root)
        path = filedialog.askopenfilename(initialdir=initial_dir)
        if path:
            variable.set(path)

    def _toggle_custom(self):
        state = "normal" if self.type_var.get() == "custom" else "disabled"
        for child in self.custom_section.winfo_children():
            try:
                child.configure(state=state)
            except tk.TclError:
                pass

    def _save(self):
        name = self.registry_var.get().strip()
        if not name:
            messagebox.showerror("Validación", "registry_name es obligatorio.", parent=self)
            return
        if not REGEX.fullmatch(name):
            messagebox.showerror("Validación", "registry_name debe ser snake_case.", parent=self)
            return
        if self.initial is None and name in self.existing_names:
            messagebox.showerror("Validación", "registry_name ya existe en la lista pendiente.", parent=self)
            return
        if self.initial is not None and name != self.initial.registry_name and name in self.existing_names:
            messagebox.showerror("Validación", "registry_name ya existe en la lista pendiente.", parent=self)
            return

        item = WingEntry(
            registry_name=name,
            display_name=self.display_var.get().strip(),
            type=self.type_var.get(),
            texture_source=self.texture_source_var.get().strip(),
            geo_source=self.geo_source_var.get().strip(),
            animation_source=self.animation_source_var.get().strip(),
            target_horizontal_speed=self.numeric_vars["target_horizontal_speed"].get().strip(),
            max_horizontal_speed=self.numeric_vars["max_horizontal_speed"].get().strip(),
            acceleration=self.numeric_vars["acceleration"].get().strip(),
            vertical_control=self.numeric_vars["vertical_control"].get().strip(),
            drag=self.numeric_vars["drag"].get().strip(),
            boost_acceleration=self.numeric_vars["boost_acceleration"].get().strip(),
            loop_ticks=self.numeric_vars["loop_ticks"].get().strip(),
            durability_tick_interval=self.numeric_vars["durability_tick_interval"].get().strip(),
        )

        if item.type == "custom":
            for field_name, field_value in {
                "textura": item.texture_source,
                "geo": item.geo_source,
                "animación": item.animation_source,
            }.items():
                if not field_value:
                    messagebox.showerror("Validación", f"Debes indicar la ruta de {field_name} para alas personalizadas.", parent=self)
                    return

        for key in [
            "target_horizontal_speed",
            "max_horizontal_speed",
            "acceleration",
            "vertical_control",
            "drag",
            "boost_acceleration",
        ]:
            val = getattr(item, key)
            if val:
                try:
                    float(val)
                except ValueError:
                    messagebox.showerror("Validación", f"{key} debe ser numérico.", parent=self)
                    return

        for key in ["loop_ticks", "durability_tick_interval"]:
            val = getattr(item, key)
            if val:
                try:
                    int(val)
                except ValueError:
                    messagebox.showerror("Validación", f"{key} debe ser entero.", parent=self)
                    return

        self.result = item
        self.destroy()


class WingsGeneratorApp:
    def __init__(self, root: tk.Tk):
        self.root = root
        self.root.title("RPGcraftmod Wings Generator GUI")
        self.root.geometry("1200x700")
        self.project_root = self.detect_project_root()
        self.pending_path = Path(__file__).resolve().parent / "pending_wings.json"
        self.pending: List[WingEntry] = []

        self._build_ui()
        self.load_pending()
        self.refresh_table()

    def detect_project_root(self) -> Path:
        here = Path(__file__).resolve().parent
        candidates = [Path.cwd(), here, here.parent, here.parent.parent, here.parent.parent.parent]
        for p in candidates:
            if (p / "src" / "main" / "java").exists() and (p / "src" / "main" / "resources").exists():
                return p.resolve()
        return Path.cwd().resolve()

    def _build_ui(self):
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(2, weight=1)
        self.root.rowconfigure(3, weight=1)

        top = ttk.Frame(self.root, padding=10)
        top.grid(row=0, column=0, sticky="ew")
        top.columnconfigure(1, weight=1)

        ttk.Label(top, text="Ruta raíz del mod").grid(row=0, column=0, sticky="w", padx=(0, 6))
        self.root_var = tk.StringVar(value=str(self.project_root))
        root_entry = ttk.Entry(top, textvariable=self.root_var)
        root_entry.grid(row=0, column=1, sticky="ew")
        ToolTip(root_entry, "Debe contener src/main/java y src/main/resources")
        ttk.Button(top, text="Examinar", command=self.pick_root).grid(row=0, column=2, padx=6)

        buttons = ttk.Frame(self.root, padding=(10, 0, 10, 8))
        buttons.grid(row=1, column=0, sticky="ew")
        for i in range(5):
            buttons.columnconfigure(i, weight=1)

        add_btn = ttk.Button(buttons, text="Agregar", command=self.add_wing)
        edit_btn = ttk.Button(buttons, text="Editar", command=self.edit_wing)
        del_btn = ttk.Button(buttons, text="Eliminar", command=self.remove_wing)
        gen_btn = ttk.Button(buttons, text="Crear alas agregadas", command=self.generate)
        save_btn = ttk.Button(buttons, text="Guardar pendientes", command=self.save_pending)

        add_btn.grid(row=0, column=0, sticky="ew", padx=4)
        edit_btn.grid(row=0, column=1, sticky="ew", padx=4)
        del_btn.grid(row=0, column=2, sticky="ew", padx=4)
        gen_btn.grid(row=0, column=3, sticky="ew", padx=4)
        save_btn.grid(row=0, column=4, sticky="ew", padx=4)

        ToolTip(gen_btn, "Procesa todos los pendientes: ModItems, Curios, models y assets")

        table_frame = ttk.LabelFrame(self.root, text="Alas pendientes", padding=8)
        table_frame.grid(row=2, column=0, sticky="nsew", padx=10, pady=(0, 8))
        table_frame.rowconfigure(0, weight=1)
        table_frame.columnconfigure(0, weight=1)

        self.table = ttk.Treeview(table_frame, columns=("registry_name", "type", "display_name"), show="headings", height=14)
        self.table.heading("registry_name", text="registry_name")
        self.table.heading("type", text="tipo")
        self.table.heading("display_name", text="display_name")
        self.table.column("registry_name", width=260)
        self.table.column("type", width=120)
        self.table.column("display_name", width=260)
        self.table.grid(row=0, column=0, sticky="nsew")

        sb = ttk.Scrollbar(table_frame, orient="vertical", command=self.table.yview)
        sb.grid(row=0, column=1, sticky="ns")
        self.table.configure(yscrollcommand=sb.set)

        log_frame = ttk.LabelFrame(self.root, text="Logs", padding=8)
        log_frame.grid(row=3, column=0, sticky="nsew", padx=10, pady=(0, 10))
        log_frame.rowconfigure(0, weight=1)
        log_frame.columnconfigure(0, weight=1)

        self.log_text = tk.Text(log_frame, wrap="word", height=12)
        self.log_text.grid(row=0, column=0, sticky="nsew")
        log_sb = ttk.Scrollbar(log_frame, orient="vertical", command=self.log_text.yview)
        log_sb.grid(row=0, column=1, sticky="ns")
        self.log_text.configure(yscrollcommand=log_sb.set)

    def log(self, msg: str):
        self.log_text.insert("end", msg + "\n")
        self.log_text.see("end")
        self.root.update_idletasks()

    def pick_root(self):
        path = filedialog.askdirectory(initialdir=str(self.project_root))
        if path:
            self.root_var.set(path)
            self.project_root = Path(path).resolve()
            self.log(f"Ruta raíz configurada: {self.project_root}")

    def load_pending(self):
        if not self.pending_path.exists():
            self.pending = []
            return
        try:
            payload = json.loads(self.pending_path.read_text(encoding="utf-8"))
            self.pending = [WingEntry.from_dict(x) for x in payload]
            self.log(f"Pendientes cargados: {len(self.pending)}")
        except Exception as exc:
            self.pending = []
            self.log(f"Error cargando pendientes: {exc}")

    def save_pending(self):
        try:
            data = [x.as_dict() for x in self.pending]
            self.pending_path.parent.mkdir(parents=True, exist_ok=True)
            self.pending_path.write_text(json.dumps(data, indent=2, ensure_ascii=False), encoding="utf-8")
            self.log(f"Pendientes guardados: {self.pending_path}")
        except Exception as exc:
            self.log(f"No se pudo guardar pending_wings.json: {exc}")

    def refresh_table(self):
        for row in self.table.get_children():
            self.table.delete(row)
        for idx, wing in enumerate(self.pending):
            self.table.insert("", "end", iid=str(idx), values=(wing.registry_name, wing.symbol(), wing.display_name))

    def _selected_index(self) -> Optional[int]:
        sel = self.table.selection()
        if not sel:
            return None
        return int(sel[0])

    def add_wing(self):
        form = WingForm(self.root, self.project_root, [x.registry_name for x in self.pending])
        self.root.wait_window(form)
        if form.result:
            self.pending.append(form.result)
            self.refresh_table()
            self.save_pending()
            self.log(f"Ala agregada: {form.result.registry_name}")

    def edit_wing(self):
        idx = self._selected_index()
        if idx is None:
            messagebox.showinfo("Editar", "Selecciona una ala para editar.")
            return
        target = self.pending[idx]
        existing = [x.registry_name for i, x in enumerate(self.pending) if i != idx]
        form = WingForm(self.root, self.project_root, existing_names=existing, initial=target)
        self.root.wait_window(form)
        if form.result:
            self.pending[idx] = form.result
            self.refresh_table()
            self.save_pending()
            self.log(f"Ala editada: {form.result.registry_name}")

    def remove_wing(self):
        idx = self._selected_index()
        if idx is None:
            messagebox.showinfo("Eliminar", "Selecciona una ala para eliminar.")
            return
        wing = self.pending[idx]
        if messagebox.askyesno("Confirmar", f"¿Eliminar {wing.registry_name} de pendientes?"):
            self.pending.pop(idx)
            self.refresh_table()
            self.save_pending()
            self.log(f"Ala eliminada: {wing.registry_name}")

    def resolve_source_path(self, user_value: str, expected_base: Path) -> Path:
        if not user_value:
            return expected_base
        raw = Path(user_value)
        if raw.is_absolute():
            return raw
        path1 = (self.project_root / raw).resolve()
        if path1.exists():
            return path1
        path2 = (expected_base / raw).resolve()
        if path2.exists():
            return path2
        return path1

    def to_constant_name(self, registry_name: str) -> str:
        return registry_name.upper()

    def to_class_name(self, registry_name: str) -> str:
        return "".join(x.capitalize() for x in registry_name.split("_")) + "WingDefinition"

    def validate_project_root(self) -> bool:
        self.project_root = Path(self.root_var.get()).resolve()
        if not (self.project_root / "src" / "main" / "java").exists():
            messagebox.showerror("Ruta inválida", "No se encontró src/main/java en la ruta raíz.")
            return False
        if not (self.project_root / "src" / "main" / "resources").exists():
            messagebox.showerror("Ruta inválida", "No se encontró src/main/resources en la ruta raíz.")
            return False
        return True

    def default_custom_paths(self, registry_name: str) -> Dict[str, Path]:
        base = self.project_root / "src" / "main" / "resources" / "assets" / MODULE
        return {
            "texture": base / "textures" / "wings" / f"{registry_name}.png",
            "geo": base / "geo" / "item" / f"{registry_name}.geo.json",
            "animation": base / "animations" / "item" / f"{registry_name}.animation.json",
        }

    def validate_wing_for_generation(self, wing: WingEntry, duplicated_registry: bool) -> List[str]:
        errors: List[str] = []
        if not wing.registry_name or not REGEX.fullmatch(wing.registry_name):
            errors.append("registry_name inválido (usa snake_case: minúsculas, números y _).")
        if duplicated_registry:
            errors.append("registry_name duplicado dentro de la lista pendiente.")
        if wing.type not in {"standard", "custom"}:
            errors.append("tipo inválido (debe ser standard o custom).")
        defaults = self.default_custom_paths(wing.registry_name)
        if wing.type == "custom":
            if wing.texture_source and not wing.texture_source.lower().endswith(".png"):
                errors.append("texture_source debe terminar en .png.")
            if wing.geo_source and not wing.geo_source.lower().endswith(".geo.json"):
                errors.append("geo_source debe terminar en .geo.json.")
            if wing.animation_source and not wing.animation_source.lower().endswith(".animation.json"):
                errors.append("animation_source debe terminar en .animation.json.")
            if not wing.texture_source and not defaults["texture"].exists():
                errors.append("faltan textura origen y textura por defecto en assets.")
            if not wing.geo_source and not defaults["geo"].exists():
                errors.append("faltan geo origen y geo por defecto en assets.")
            if not wing.animation_source and not defaults["animation"].exists():
                errors.append("faltan animación origen y animación por defecto en assets.")

        for key in [
            "target_horizontal_speed",
            "max_horizontal_speed",
            "acceleration",
            "vertical_control",
            "drag",
            "boost_acceleration",
        ]:
            value = getattr(wing, key).strip()
            if value:
                try:
                    parsed = float(value)
                    if key == "drag" and (parsed <= 0 or parsed > 1.5):
                        errors.append("drag debe ser > 0 y <= 1.5.")
                    if key in {"target_horizontal_speed", "max_horizontal_speed", "acceleration", "vertical_control", "boost_acceleration"} and parsed < 0:
                        errors.append(f"{key} no puede ser negativo.")
                except ValueError:
                    errors.append(f"{key} debe ser numérico.")

        for key in ["loop_ticks", "durability_tick_interval"]:
            value = getattr(wing, key).strip()
            if value:
                try:
                    parsed = int(value)
                    if parsed <= 0:
                        errors.append(f"{key} debe ser > 0.")
                except ValueError:
                    errors.append(f"{key} debe ser entero.")
        return errors

    def generate(self):
        if not self.pending:
            messagebox.showinfo("Sin pendientes", "No hay alas pendientes para generar.")
            return
        if not self.validate_project_root():
            return

        kept: List[WingEntry] = []
        generated_names: List[str] = []
        missing_textures: List[str] = []

        try:
            moditems = self.project_root / "src" / "main" / "java" / "net" / "cheto97" / "rpgcraftmod" / "item" / "ModItems.java"
            curios_tag = self.project_root / "src" / "main" / "resources" / "data" / "curios" / "tags" / "items" / "wing.json"
            model_dir = self.project_root / "src" / "main" / "resources" / "assets" / MODULE / "models" / "item"
            texture_dir = self.project_root / "src" / "main" / "resources" / "assets" / MODULE / "textures" / "wings"
            geo_dir = self.project_root / "src" / "main" / "resources" / "assets" / MODULE / "geo" / "item"
            anim_dir = self.project_root / "src" / "main" / "resources" / "assets" / MODULE / "animations" / "item"
            generated_java_dir = self.project_root / "src" / "main" / "java" / "net" / "cheto97" / "rpgcraftmod" / "item" / "generated"
            lang_file = self.project_root / "src" / "main" / "resources" / "assets" / MODULE / "lang" / "en_us.json"

            moditems_content = moditems.read_text(encoding="utf-8") if moditems.exists() else ""
            registry_counts: Dict[str, int] = {}
            for w in self.pending:
                registry_counts[w.registry_name] = registry_counts.get(w.registry_name, 0) + 1

            for wing in self.pending:
                try:
                    errors = self.validate_wing_for_generation(wing, registry_counts.get(wing.registry_name, 0) > 1)
                    if errors:
                        for err in errors:
                            self.log(f"[ERROR] {wing.registry_name or '(sin nombre)'}: {err}")
                        kept.append(wing)
                        continue

                    if re.search(rf'\"{re.escape(wing.registry_name)}\"', moditems_content):
                        self.log(f"[ERROR] {wing.registry_name}: ya está registrado en ModItems.java.")
                        kept.append(wing)
                        continue

                    if wing.type == "custom":
                        defaults = self.default_custom_paths(wing.registry_name)
                        texture_src = self.resolve_source_path(wing.texture_source, texture_dir)
                        geo_src = self.resolve_source_path(wing.geo_source, geo_dir)
                        anim_src = self.resolve_source_path(wing.animation_source, anim_dir)
                        if not wing.texture_source:
                            texture_src = defaults["texture"]
                        if not wing.geo_source:
                            geo_src = defaults["geo"]
                        if not wing.animation_source:
                            anim_src = defaults["animation"]

                        for label, path in [("textura", texture_src), ("geo", geo_src), ("animación", anim_src)]:
                            if not path.exists() or not path.is_file():
                                self.log(f"[ERROR] {wing.registry_name}: no existe archivo de {label}: {path}")
                                kept.append(wing)
                                break
                        else:
                            texture_dir.mkdir(parents=True, exist_ok=True)
                            geo_dir.mkdir(parents=True, exist_ok=True)
                            anim_dir.mkdir(parents=True, exist_ok=True)
                            tex_dst = texture_dir / f"{wing.registry_name}.png"
                            geo_dst = geo_dir / f"{wing.registry_name}.geo.json"
                            anim_dst = anim_dir / f"{wing.registry_name}.animation.json"
                            if texture_src.resolve() != tex_dst.resolve():
                                shutil.copy2(texture_src, tex_dst)
                            if geo_src.resolve() != geo_dst.resolve():
                                shutil.copy2(geo_src, geo_dst)
                            if anim_src.resolve() != anim_dst.resolve():
                                shutil.copy2(anim_src, anim_dst)
                            self.log(f"[OK] Copiados assets custom para {wing.registry_name}")

                            class_name = self.to_class_name(wing.registry_name)
                            generated_java_dir.mkdir(parents=True, exist_ok=True)
                            java_file = generated_java_dir / f"{class_name}.java"
                            java_content = self.build_wing_definition_java(class_name, wing)
                            java_file.write_text(java_content, encoding="utf-8")
                            self.log(f"[OK] Generado {java_file.relative_to(self.project_root)}")

                            moditems_content = self.add_moditems_registration(moditems_content, wing, class_name)
                            generated_names.append(wing.registry_name)
                            self.ensure_item_model(model_dir, wing.registry_name)
                            self.append_curios_tag(curios_tag, wing.registry_name)
                            if wing.display_name:
                                self.update_lang(lang_file, wing.registry_name, wing.display_name)
                    else:
                        moditems_content = self.add_moditems_registration(moditems_content, wing, None)
                        generated_names.append(wing.registry_name)
                        self.ensure_item_model(model_dir, wing.registry_name)
                        self.append_curios_tag(curios_tag, wing.registry_name)
                        if wing.display_name:
                            self.update_lang(lang_file, wing.registry_name, wing.display_name)

                except Exception as wing_exc:
                    kept.append(wing)
                    self.log(f"[ERROR] {wing.registry_name}: {wing_exc}")
                    self.log(traceback.format_exc())

            if moditems_content:
                moditems.write_text(moditems_content, encoding="utf-8")

            for name in generated_names:
                t = texture_dir / f"{name}.png"
                if not t.exists():
                    missing_textures.append(name)

            self.pending = kept
            self.refresh_table()
            self.save_pending()

            if missing_textures:
                self.log("[WARN] Faltan texturas para: " + ", ".join(missing_textures))
                messagebox.showwarning("Texturas faltantes", "Faltan texturas para: " + ", ".join(missing_textures))

            self.log(f"Proceso finalizado. Generadas: {len(generated_names)}. Pendientes conservadas: {len(self.pending)}")
            if generated_names:
                messagebox.showinfo("Completado", f"Alas generadas: {', '.join(generated_names)}")

        except Exception as exc:
            self.log(f"[ERROR] Proceso interrumpido: {exc}")
            self.log(traceback.format_exc())
            messagebox.showerror("Error", str(exc))

    def add_moditems_registration(self, content: str, wing: WingEntry, class_name: Optional[str]) -> str:
        constant = self.to_constant_name(wing.registry_name)
        if re.search(rf"\b{re.escape(constant)}\b", content):
            return content

        lines = content.splitlines()
        last_wings_idx = None
        for i, line in enumerate(lines):
            if "createWings(" in line and "RegistryObject<Item>" in line:
                last_wings_idx = i

        if class_name:
            import_line = f"import net.cheto97.rpgcraftmod.item.generated.{class_name};"
            if import_line not in content:
                last_import = max(i for i, l in enumerate(lines) if l.startswith("import "))
                lines.insert(last_import + 1, import_line)
            register_line = f"    public static final RegistryObject<Item> {constant} = registerItems(\"{wing.registry_name}\", () -> new CustomWingsItem({class_name}.DEFINITION));"
        else:
            register_line = f"    public static final RegistryObject<Item> {constant} = createWings(\"{wing.registry_name}\");"

        insert_at = last_wings_idx + 1 if last_wings_idx is not None else 0
        lines.insert(insert_at, register_line)
        self.log(f"[OK] Registro agregado en ModItems: {wing.registry_name}")
        return "\n".join(lines) + "\n"

    def append_curios_tag(self, wing_json: Path, registry_name: str):
        wing_json.parent.mkdir(parents=True, exist_ok=True)
        if wing_json.exists():
            data = json.loads(wing_json.read_text(encoding="utf-8"))
        else:
            data = {"replace": False, "values": ["minecraft:elytra"]}
        values = data.setdefault("values", [])
        entry = f"{MODULE}:{registry_name}"
        if entry not in values:
            values.append(entry)
            wing_json.write_text(json.dumps(data, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
            self.log(f"[OK] Curios tag actualizado: {entry}")
        else:
            self.log(f"[SKIP] Curios tag ya contenía: {entry}")

    def ensure_item_model(self, model_dir: Path, registry_name: str):
        model_dir.mkdir(parents=True, exist_ok=True)
        model_path = model_dir / f"{registry_name}.json"
        if not model_path.exists():
            model_path.write_text(json.dumps({"parent": "builtin/entity"}, indent=2) + "\n", encoding="utf-8")
            self.log(f"[OK] Model item creado: {model_path.relative_to(self.project_root)}")
        else:
            self.log(f"[SKIP] Model item ya existe: {model_path.relative_to(self.project_root)}")

    def update_lang(self, lang_file: Path, registry_name: str, display_name: str):
        lang_file.parent.mkdir(parents=True, exist_ok=True)
        if lang_file.exists():
            data = json.loads(lang_file.read_text(encoding="utf-8"))
        else:
            data = {}
        key = f"item.{MODULE}.{registry_name}"
        if key not in data:
            data[key] = display_name
            lang_file.write_text(json.dumps(data, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
            self.log(f"[OK] Lang actualizado: {key}")

    def build_wing_definition_java(self, class_name: str, wing: WingEntry) -> str:
        values = {
            "target_horizontal_speed": "0.26D",
            "max_horizontal_speed": "0.42D",
            "acceleration": "0.085D",
            "vertical_control": "0.22D",
            "drag": "0.985D",
            "boost_acceleration": "0.02D",
            "loop_ticks": "8",
            "durability_tick_interval": "40",
        }
        mapping = {
            "target_horizontal_speed": wing.target_horizontal_speed,
            "max_horizontal_speed": wing.max_horizontal_speed,
            "acceleration": wing.acceleration,
            "vertical_control": wing.vertical_control,
            "drag": wing.drag,
            "boost_acceleration": wing.boost_acceleration,
            "loop_ticks": wing.loop_ticks,
            "durability_tick_interval": wing.durability_tick_interval,
        }
        for k, v in mapping.items():
            if v:
                if k in ["loop_ticks", "durability_tick_interval"]:
                    values[k] = str(int(v))
                else:
                    values[k] = f"{float(v)}D"

        registry = wing.registry_name
        return f'''package net.cheto97.rpgcraftmod.item.generated;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.prefabs.WingDefinition;
import net.minecraft.resources.ResourceLocation;

public final class {class_name} {{
    public static final WingDefinition DEFINITION = new WingDefinition(
            "{registry}",
            new ResourceLocation(RpgcraftMod.MOD_ID, "geo/item/{registry}.geo.json"),
            new ResourceLocation(RpgcraftMod.MOD_ID, "animations/item/{registry}.animation.json"),
            new ResourceLocation(RpgcraftMod.MOD_ID, "textures/wings/{registry}.png"),
            new WingDefinition.FlightTuning(
                    {values["target_horizontal_speed"]},
                    {values["max_horizontal_speed"]},
                    {values["acceleration"]},
                    {values["vertical_control"]},
                    {values["drag"]},
                    {values["boost_acceleration"]},
                    {values["loop_ticks"]},
                    {values["durability_tick_interval"]}
            )
    );

    private {class_name}() {{
    }}
}}
'''


def main():
    root = tk.Tk()
    app = WingsGeneratorApp(root)
    root.protocol("WM_DELETE_WINDOW", lambda: (app.save_pending(), root.destroy()))
    root.mainloop()


if __name__ == "__main__":
    main()
