# 🍀 Map Tile Exporter 📝:

![java_map_tile_exporter](https://github.com/user-attachments/assets/33472eb3-e637-4649-a0e1-daabe165c974)

## A basic tile exporter for developing maps for 2D games.

**Information:**<br>

It exports to a .zip file, which contains the following files:
- A list of images used when creating the map/level.
- **[map name]$.txt**
- This stores the indices of each tile used. Most implementations utilize this file when loading tiles of their maps.
- **tile_data.txt**
- Columns represent index, solid, and animated, respectively, with 1 being true and 0 as false for solid and animated values.

**Repo Link:**<br>
Made by yours truly
https://github.com/DymNomZ/java-map-tile-exporter/

🕹**Controls:**

Left-click = place/remove tile | Click a tile to select/deselect it, deselecting will replace your cursor with a void tile, allowing you to remove tiles.<br>
Right-click = drag grid<br>
Middle-click = toggle paint mode hotkey<br>
Mouse-wheel = zoom<br>
Ctrl + Z = Undo<br>
Ctrl + X = Redo<br>
