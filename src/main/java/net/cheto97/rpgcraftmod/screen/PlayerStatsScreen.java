package net.cheto97.rpgcraftmod.screen;

import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerStatsMenu;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateDamageAndReduce;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateValue;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerNoReqPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerStatSyncPacket;
import net.cheto97.rpgcraftmod.util.button.StatPlusButton;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class PlayerStatsScreen extends AbstractContainerScreen<PlayerStatsMenu> {
    private byte ticksSinceUpdate = 0;
    private final int alt = 2;
    private final int bH = 10;
    private final int bW = 10;
    private final int xPlus = 125; //425

    private final StatPlusButton life_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 90 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("life")));
    private final StatPlusButton mana_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 110 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("mana")));
    private final StatPlusButton dexterity_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 140 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("dexterity")));
    private final StatPlusButton intelligence_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 155 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("intelligence")));
    private final StatPlusButton strength_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 170 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("strength")));
    private final StatPlusButton command_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 185 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("command")));
    private final StatPlusButton defense_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 200 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("defense")));
    private final StatPlusButton magicdefense_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 215 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("magicdefense")));
    private final StatPlusButton luck_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 230 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("luck")));
    private final StatPlusButton agility_button = new StatPlusButton(this.leftPos + xPlus, this.topPos + 245 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("agility")));

    public PlayerStatsScreen(PlayerStatsMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        double scale = Minecraft.getInstance().getWindow().getGuiScale();
        this.imageWidth = (int) (800/scale);
        this.imageHeight = (int) (615/scale);

    }
    private static final ResourceLocation texture = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/skill_background.png");
    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        this.renderBackground(ms);
    }
    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
        double exp = PlayerData.getExpNeed()-PlayerData.getPlayerExperience();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0f, 0f, 0f, 0f);
        RenderSystem.setShaderTexture(0, texture);
        blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();

        assert Minecraft.getInstance().player != null;
        Player player = Minecraft.getInstance().player;

        ChatFormatting defColor = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? ChatFormatting.DARK_GREEN : null;
        ChatFormatting luckColor = player.hasEffect(MobEffects.LUCK) ? ChatFormatting.DARK_GREEN : null;
        ChatFormatting damColor = player.hasEffect(MobEffects.DAMAGE_BOOST) ? ChatFormatting.DARK_GREEN : null;
        ChatFormatting weakColor = player.hasEffect(MobEffects.WEAKNESS) ? ChatFormatting.DARK_RED : null;

        MobEffectInstance defEff = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? player.getEffect(MobEffects.DAMAGE_RESISTANCE) : null;
        MobEffectInstance luckEff = player.hasEffect(MobEffects.LUCK) ? player.getEffect(MobEffects.LUCK) : null;
        MobEffectInstance damEff = player.hasEffect(MobEffects.DAMAGE_BOOST) ? player.getEffect(MobEffects.DAMAGE_BOOST) : null;
        MobEffectInstance weakEff = player.hasEffect(MobEffects.WEAKNESS) ? player.getEffect(MobEffects.WEAKNESS) : null;

        double damBonus = damEff != null && weakEff != null ? calculateDamageAndReduce(damEff,weakEff,PlayerData.getPlayerStrength()) : damEff != null ? calculateValue(damEff,PlayerData.getPlayerStrength(),"add") : weakEff != null ? calculateValue(weakEff,PlayerData.getPlayerStrength(),"reduce") : 0.0;
        double defBonus = defEff != null ? calculateValue(defEff,PlayerData.getPlayerDefense(),"add") : 0.0;
        double luckBonus = luckEff != null ? calculateValue(luckEff,PlayerData.getPlayerLuck(),"add") : 0.0;

        String defenseString = doubleToString(PlayerData.getPlayerDefense() + defBonus);
        String luckString = doubleToString(PlayerData.getPlayerLuck()+luckBonus);
        String strengthString = doubleToString(PlayerData.getPlayerStrength()+damBonus);

        MutableComponent componentDefense = defColor != null ? Component.literal(defenseString).withStyle(defColor) : Component.literal(defenseString);
        ChatFormatting damWeakColor = damColor != null && weakColor != null ? Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() >= Objects.requireNonNull(player.getEffect(MobEffects.WEAKNESS)).getAmplifier() ? ChatFormatting.GREEN : ChatFormatting.RED : null;
        MutableComponent componentStrength = damWeakColor != null ? Component.literal(strengthString).withStyle(damWeakColor) : damColor != null ? Component.literal(strengthString).withStyle(damColor) : weakColor != null ? Component.literal(strengthString).withStyle(weakColor) : Component.literal(strengthString);
        MutableComponent componentLuck = luckColor != null ? Component.literal(luckString).withStyle(luckColor) : Component.literal(luckString);

        Component playerClass = switch (PlayerData.getPlayerClass()){
            case 1 -> Component.literal("Archer").withStyle(ChatFormatting.GOLD);
            case 2 -> Component.literal("Mage").withStyle(ChatFormatting.GOLD);
            case 3 -> Component.literal("Warrior").withStyle(ChatFormatting.GOLD);
            case 4 -> Component.literal("Assassin").withStyle(ChatFormatting.GOLD);
            case 6 -> Component.literal("Beast Tamer").withStyle(ChatFormatting.GOLD);
            case 7 -> Component.literal("Magic Tank").withStyle(ChatFormatting.GOLD);
            case 8 -> Component.literal("Paladin").withStyle(ChatFormatting.GOLD);
            default -> Component.literal("Balanced").withStyle(ChatFormatting.GOLD);
        };

        if(PlayerData.getExpNeed()-PlayerData.getPlayerExperience() < 0){
            exp = 0;
        }
        
        GuiComponent.drawCenteredString(ms, font, Component.literal("["+playerClass.getString()+"] "+player.getName().getString()+ " Stat Menu"), this.leftPos + 155, this.topPos + 5, -1);
        
        GuiComponent.drawString(ms, font, Component.literal("Level:"), this.leftPos + 5, this.topPos + 20, -1);
        GuiComponent.drawString(ms, font, Component.literal(String.valueOf(PlayerData.getPlayerLevel())), this.leftPos + 40, this.topPos + 20, -1);

        GuiComponent.drawString(ms,font,Component.literal("Reset:"),this.leftPos + 5, this.topPos + 30, -1);
        GuiComponent.drawString(ms,font,Component.literal(String.valueOf(PlayerData.getPlayerReset())),this.leftPos + 40, this.topPos + 30, -1);

        GuiComponent.drawString(ms, font, Component.literal("Needed Experience:"), this.leftPos + 5, this.topPos + 40, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(exp)), this.leftPos + 110, this.topPos + 40, -1);

        GuiComponent.drawString(ms, font, Component.literal("Life:"), this.leftPos + 5, this.topPos + 50, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerLifeMax())), this.leftPos + 30, this.topPos + 50, -1);

        GuiComponent.drawString(ms, font, Component.literal("Life Regeneration:"), this.leftPos + 5, this.topPos + 65, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerLifeRegeneration())), this.leftPos + 100, this.topPos + 65, -1);

        GuiComponent.drawString(ms, font, Component.literal("Mana:"), this.leftPos + 5, this.topPos + 80, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerManaMax())), this.leftPos + 35, this.topPos + 80, -1);

        GuiComponent.drawString(ms, font, Component.literal("Mana Regeneration:"), this.leftPos + 5, this.topPos + 95, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerManaRegeneration())), this.leftPos + 105, this.topPos + 95, -1);

        GuiComponent.drawString(ms, font, Component.literal("Dexterity:"), this.leftPos + 5, this.topPos + 110, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerDexterity())), this.leftPos + 55, this.topPos + 110, -1);

        GuiComponent.drawString(ms, font, Component.literal("Intelligence:"), this.leftPos + 5, this.topPos + 125, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerIntelligence())), this.leftPos + 67, this.topPos +125, -1);

        GuiComponent.drawString(ms, font, Component.literal("Strength:"), this.leftPos + 5, this.topPos + 140, -1);
        GuiComponent.drawString(ms, font, componentStrength, this.leftPos + 52, this.topPos + 140, -1);

        GuiComponent.drawString(ms, font, Component.literal("Command:"), this.leftPos + 5, this.topPos + 155, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerCommand())), this.leftPos + 51, this.topPos + 155, -1);

        GuiComponent.drawString(ms, font, Component.literal("Defense:"), this.leftPos + 5, this.topPos + 170, -1);
        GuiComponent.drawString(ms, font, componentDefense, this.leftPos + 51, this.topPos + 170, -1);

        GuiComponent.drawString(ms, font, Component.literal("Magic Defense:"), this.leftPos + 5, this.topPos + 185, -1);
        GuiComponent.drawString(ms, font, Component.literal((doubleToString(PlayerData.getPlayerMagicDefense()))), this.leftPos + 82, this.topPos + 185, -1);

        GuiComponent.drawString(ms, font, Component.literal("Luck:"), this.leftPos + 5, this.topPos + 200, -1);
        GuiComponent.drawString(ms, font, componentLuck, this.leftPos + 35, this.topPos + 200, -1);

        GuiComponent.drawString(ms, font, Component.literal("Agility:"), this.leftPos + 5, this.topPos + 215, -1);
        GuiComponent.drawString(ms, font, Component.literal(doubleToString(PlayerData.getPlayerAgility())), this.leftPos + 40, this.topPos + 215, -1);

        GuiComponent.drawString(ms, font, Component.literal("Stat Points:"), this.leftPos +5, this.topPos + 240, -1);
        GuiComponent.drawString(ms, font, Component.literal(String.valueOf(PlayerData.getPlayerStatPoints())).withStyle(ChatFormatting.LIGHT_PURPLE), this.leftPos + 70, this.topPos + 240, -1);

    }
    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == GLFW.GLFW_KEY_K) {
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
                return true;
            }
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }
    @Override
    public void containerTick() {
        this.ticksSinceUpdate++;
        if (this.ticksSinceUpdate % 5 == 0) {
            this.ticksSinceUpdate = 0;
            this.init();

        }
    }
    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {

    }
    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }
    private void TurnOn(){
        life_button.On();
        mana_button.On();
        dexterity_button.On();
        intelligence_button.On();
        strength_button.On();
        command_button.On();
        defense_button.On();
        magicdefense_button.On();
        luck_button.On();
        agility_button.On();
    }
    private void TurnOff(){
        life_button.Off();
        mana_button.Off();
        dexterity_button.Off();
        intelligence_button.Off();
        strength_button.Off();
        command_button.Off();
        defense_button.Off();
        magicdefense_button.Off();
        luck_button.Off();
        agility_button.Off();
    }
    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        if(PlayerData.getPlayerStatPoints() > 0){
            TurnOn();
        }
        else{
            TurnOff();
        }
        if(PlayerData.getPlayerLevel() > 10000){
            this.addRenderableWidget(new Button(this.leftPos +270, this.topPos + 20, 32, 12, Component.literal("Reset").withStyle(ChatFormatting.BLUE), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("reset"))));
        }
        else{
            this.addRenderableWidget(new Button(this.leftPos +270, this.topPos + 20, 32, 12, Component.literal("Reset").withStyle(ChatFormatting.DARK_GRAY), e -> ModMessages.sendToServer(new PlayerNoReqPacket("reset"))));
        }

        this.addRenderableWidget(life_button);
        this.addRenderableWidget(mana_button);
        this.addRenderableWidget(dexterity_button);
        this.addRenderableWidget(intelligence_button);
        this.addRenderableWidget(strength_button);
        this.addRenderableWidget(command_button);
        this.addRenderableWidget(defense_button);
        this.addRenderableWidget(magicdefense_button);
        this.addRenderableWidget(luck_button);
        this.addRenderableWidget(agility_button);
    }
}