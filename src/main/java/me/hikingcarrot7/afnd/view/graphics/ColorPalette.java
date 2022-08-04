package me.hikingcarrot7.afnd.view.graphics;

import me.hikingcarrot7.afnd.core.utils.Pair;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ColorPalette {
  private final Map<ColorKey, Color> colorCatalog;

  public enum ColorKey {
    FILL_COLOR_KEY,
    STROKE_COLOR_KEY,
    TEXT_COLOR_KEY
  }

  public static class ColorPaletteBuilder {
    List<Pair<ColorKey, Color>> palette;

    public ColorPaletteBuilder() {
      palette = new ArrayList<>();
    }

    public ColorPaletteBuilder addColor(ColorKey key, Color color) {
      palette.add(new Pair<>(key, color));
      return this;
    }

    public ColorPalette build() {
      return new ColorPalette(palette.stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight)));
    }
  }

  public ColorPalette(Map<ColorKey, Color> colors) {
    this.colorCatalog = colors;
  }

  public Color getColor(ColorKey key) {
    return colorCatalog.get(key);
  }

}
