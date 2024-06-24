package net.cheto97.rpgcraftmod.client.curio;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class EnqueueEvent {
    public static void register(final InterModEnqueueEvent event){
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BACK.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BRACELET.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder()
                        .size(0)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("aura")
                        .size(1)
                        .icon(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/aura_slot.png"))
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("skills")
                        .size(3)
                        .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("wing")
                        .size(1)
                        .icon(new ResourceLocation(RpgcraftMod.MOD_ID,"textures/slot/wing_slot.png"))
                        .build());
    }
}
