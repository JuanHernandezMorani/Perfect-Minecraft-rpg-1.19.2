package net.cheto97.rpgcraftmod.util;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.item.wings.WingItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.util.Mth;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

public class WingHelper {
    public static float wingSpeed = 0.0124F;
    public static float modifier = 2F;

    public static void applySpeed(Player player) {
        WingsValues values = RpgcraftMod.WINGS.apply(player);

        if(player instanceof SlowFallEntity){
            ((SlowFallEntity) player).setSlowFalling(false);
        }
        Vec3 rotation = player.getForward();
        Vec3 velocity = player.getDeltaMovement();

        float speed = (values.getSpeed() * (player.getVisualRotationYInDegrees() < -75 && player.getVisualRotationYInDegrees() > -105 ? (float)(Math.max(0.2, Math.abs(rotation.y))) : 1F)) + 0.4F / modifier;

        player.setDeltaMovement(velocity.add(rotation.x * speed + (rotation.x * 1.5D - velocity.x) * speed,
                rotation.y * speed + (rotation.y * 1.5D - velocity.y) * speed,
                rotation.z * speed + (rotation.z * 1.5D - velocity.z) * speed));
    }

    public static void applySpeed(Player player, ItemStack item) {

        if(player instanceof SlowFallEntity){
            ((SlowFallEntity) player).setSlowFalling(false);
        }
        Vec3 rotation = player.getForward();
        Vec3 velocity = player.getDeltaMovement();

        float speed = (wingSpeed * (player.getVisualRotationYInDegrees() < -75 && player.getVisualRotationYInDegrees() > -105 ? (float)(Math.max(0.2, Math.abs(rotation.y))) : 1F)) + 0.4F / modifier;

        player.setDeltaMovement(velocity.add(rotation.x * speed + (rotation.x * 1.5D - velocity.x) * speed,
                rotation.y * speed + (rotation.y * 1.5D - velocity.y) * speed,
                rotation.z * speed + (rotation.z * 1.5D - velocity.z) * speed));
    }

    public static void stopFlying(Player player) {
        if(player instanceof SlowFallEntity){
            ((SlowFallEntity) player).setSlowFalling(true);
        }

        player.stopFallFlying();
    }
}
