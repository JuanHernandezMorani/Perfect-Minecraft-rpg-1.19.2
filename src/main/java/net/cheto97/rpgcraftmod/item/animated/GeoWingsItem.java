package net.cheto97.rpgcraftmod.item.animated;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.ModCreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import software.bernie.geckolib3.item.GeoArmorItem;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GeoWingsItem extends GeoArmorItem implements ICurioItem {
    private final String wingsName;
    public GeoWingsItem(String name){
        super(ArmorMaterials.NETHERITE, EquipmentSlot.CHEST,new Item.Properties()
                .stacksTo(1)
                .defaultDurability(1200)
                .durability(1200)
                .rarity(Rarity.RARE)
                .tab(ModCreativeModeTab.RPGCRAFT_WINGS_TAB));
        this.wingsName = name;
    }

        public ResourceLocation getModelResource() {
            return new ResourceLocation(RpgcraftMod.MOD_ID,"models/item/"+wingsName+".json");
        }

        public ResourceLocation getTextureResource() {
            return new ResourceLocation(RpgcraftMod.MOD_ID,"textures/wings/"+wingsName+".png");
        }
}
