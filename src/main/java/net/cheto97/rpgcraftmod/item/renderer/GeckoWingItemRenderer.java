package net.cheto97.rpgcraftmod.item.renderer;

import net.cheto97.rpgcraftmod.item.model.GeckoWingModel;
import net.cheto97.rpgcraftmod.item.prefabs.AbstractWingCurioItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GeckoWingItemRenderer extends GeoItemRenderer<AbstractWingCurioItem> {
    public GeckoWingItemRenderer() {
        super(new GeckoWingModel());
    }
}
