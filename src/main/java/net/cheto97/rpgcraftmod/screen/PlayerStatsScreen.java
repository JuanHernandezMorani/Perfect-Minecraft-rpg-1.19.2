package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.platform.Window;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerStatsMenu;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateDamageAndReduce;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateValue;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerNoReqPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerStatSyncPacket;
import net.cheto97.rpgcraftmod.util.button.ExitButton;
import net.cheto97.rpgcraftmod.util.button.OffResetButton;
import net.cheto97.rpgcraftmod.util.button.ResetButton;
import net.cheto97.rpgcraftmod.util.button.StatPlusButton;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
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
    private final Minecraft mc = Minecraft.getInstance();
    private final Window window = mc.getWindow();
    private int ticks = 0;
    private final Player player;
    private StatPlusButton life_button;
    private StatPlusButton mana_button;
    private StatPlusButton dexterity_button;
    private StatPlusButton intelligence_button;
    private StatPlusButton strength_button;
    private StatPlusButton command_button;
    private StatPlusButton defense_button;
    private StatPlusButton magicdefense_button;
    private StatPlusButton luck_button;
    private StatPlusButton agility_button;
    private int width;
    private int height;
    private int previousWidth;
    private int previousHeight;
    private double scale;
    private double previousScale;

    public PlayerStatsScreen(PlayerStatsMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        player = inventory.player;
        height = window.getGuiScaledHeight();
        width = window.getGuiScaledWidth();
        previousWidth = width;
        previousHeight = height;
        scale = window.getGuiScale();
        previousScale = scale;

        this.imageWidth = width - (int)((215*scale)/width);
        this.imageHeight = height - (int)((200*scale)/height);
    }
    private static final ResourceLocation playerStatsScreenBG = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/bc/skill_background.png");
    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        this.renderBackground(ms);
    }
    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, playerStatsScreenBG);
        blit(ms, (width/2)-(width/3), (height/2)-(height/3), 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();

        if(player != null && player.getId() == PlayerData.getPlayerId()){
            MutableComponent playerClass = switch (PlayerData.getPlayerClass()) {
                case 1 -> Component.literal("Archer ").withStyle(ChatFormatting.GOLD);
                case 2 -> Component.literal("Mage ").withStyle(ChatFormatting.GOLD);
                case 3 -> Component.literal("Warrior ").withStyle(ChatFormatting.GOLD);
                case 4 -> Component.literal("Assassin ").withStyle(ChatFormatting.GOLD);
                case 6 -> Component.literal("Beast Tamer ").withStyle(ChatFormatting.GOLD);
                case 7 -> Component.literal("Priest ").withStyle(ChatFormatting.GOLD);
                case 8 -> Component.literal("Knight ").withStyle(ChatFormatting.GOLD);
                default -> Component.literal("Balanced ").withStyle(ChatFormatting.GOLD);
            };
            GuiComponent.drawCenteredString(ms, font, playerClass.append(player.getName().getString() + " Stat Menu"),
                    (width / 2) - (width / 20), ((height / 2) - (height / 3)) + (height / 80), 0xFFFFFF);

            double exp = PlayerData.getExpNeed()-PlayerData.getPlayerExperience();
            Component statPoints = Component.literal(" "+PlayerData.getPlayerStatPoints()).withStyle(ChatFormatting.LIGHT_PURPLE);
            ChatFormatting defColor = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting luckColor = player.hasEffect(MobEffects.LUCK) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting damColor = player.hasEffect(MobEffects.DAMAGE_BOOST) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting weakColor = player.hasEffect(MobEffects.WEAKNESS) ? ChatFormatting.DARK_RED : null;
            ChatFormatting regLifeColor = player.hasEffect(MobEffects.REGENERATION) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting damWeakColor = damColor != null && weakColor != null ? Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() >= Objects.requireNonNull(player.getEffect(MobEffects.WEAKNESS)).getAmplifier() ? ChatFormatting.GREEN : ChatFormatting.RED : null;


            MobEffectInstance defsEff = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? player.getEffect(MobEffects.DAMAGE_RESISTANCE) : null;
            MobEffectInstance luckEff = player.hasEffect(MobEffects.LUCK) ? player.getEffect(MobEffects.LUCK) : null;
            MobEffectInstance damEff = player.hasEffect(MobEffects.DAMAGE_BOOST) ? player.getEffect(MobEffects.DAMAGE_BOOST) : null;
            MobEffectInstance weakEff = player.hasEffect(MobEffects.WEAKNESS) ? player.getEffect(MobEffects.WEAKNESS) : null;
            MobEffectInstance regLifeEff = player.hasEffect(MobEffects.REGENERATION) ? player.getEffect(MobEffects.REGENERATION) : null;

            double damBonus = damEff != null && weakEff != null ? calculateDamageAndReduce(damEff,weakEff,PlayerData.getPlayerStrength()) : damEff != null ? calculateValue(damEff,PlayerData.getPlayerStrength(),"add") : weakEff != null ? calculateValue(weakEff,PlayerData.getPlayerStrength(),"reduce") : 0.0;
            double defBonus = defsEff != null ? calculateValue(defsEff,PlayerData.getPlayerDefense(),"add") : 0.0;
            double luckBonus = luckEff != null ? calculateValue(luckEff,PlayerData.getPlayerLuck(),"add") : 0.0;
            double regLifeBonus = regLifeEff != null ? calculateValue(regLifeEff, PlayerData.getPlayerLifeRegeneration(),"add") : 0.0;

            String defenseString = doubleToString(PlayerData.getPlayerDefense() + defBonus);
            String luckString = doubleToString(PlayerData.getPlayerLuck()+luckBonus);
            String strengthString = doubleToString(PlayerData.getPlayerStrength()+damBonus);
            String regLifeString = doubleToString(PlayerData.getPlayerLifeRegeneration() + regLifeBonus);

            MutableComponent componentDefense = defColor != null ? Component.literal(defenseString).withStyle(defColor) : Component.literal(defenseString);
            MutableComponent componentStrength = damWeakColor != null ? Component.literal(strengthString).withStyle(damWeakColor) : damColor != null ? Component.literal(strengthString).withStyle(damColor) : weakColor != null ? Component.literal(strengthString).withStyle(weakColor) : Component.literal(strengthString);
            MutableComponent componentLuck = luckColor != null ? Component.literal(luckString).withStyle(luckColor) : Component.literal(luckString);
            MutableComponent componentRegLife = regLifeColor != null ? Component.literal(regLifeString).withStyle(regLifeColor) : Component.literal(regLifeString);


            drawComponent(ms, Component.literal("Level:"), Component.literal(String.valueOf(PlayerData.getPlayerLevel())), (height / 10));

            drawComponent(ms, Component.literal("Reset:"),Component.literal(String.valueOf(PlayerData.getPlayerReset())), (height / 7));

            drawComponent(ms, Component.literal("Needed Experience:"), Component.literal(doubleToString(exp)), (height / 6));

            drawComponent(ms, Component.literal("Life:"), Component.literal(doubleToString(PlayerData.getPlayerLifeMax())), (height / 4));

            drawComponent(ms, Component.literal("Life Regeneration:"), componentRegLife, (height / 3));

            drawComponent(ms, Component.literal("Mana:"), Component.literal(doubleToString(PlayerData.getPlayerManaMax())), (height / 2) - (height / 30));

            drawComponent(ms, Component.literal("Mana Regeneration:"), Component.literal(doubleToString(PlayerData.getPlayerManaRegeneration())), (height / 2) - (height / 20));

            drawComponent(ms, Component.literal("Dexterity:"), Component.literal(doubleToString(PlayerData.getPlayerDexterity())), (height / 2));

            drawComponent(ms, Component.literal("Intelligence:"), Component.literal(doubleToString(PlayerData.getPlayerIntelligence())), -(height / 2));

            drawComponent(ms, Component.literal("Strength:"), componentStrength, -(height / 2) + (height / 20));

            drawComponent(ms, Component.literal("Command:"), Component.literal(doubleToString(PlayerData.getPlayerCommand())), -(height / 2) + (height / 30));

            drawComponent(ms, Component.literal("Defense:"), componentDefense, -(height / 3));

            drawComponent(ms, Component.literal("Magic Defense:"), Component.literal((doubleToString(PlayerData.getPlayerMagicDefense()))), -(height / 4));

            drawComponent(ms, Component.literal("Luck:"), componentLuck, -(height / 5));

            drawComponent(ms, Component.literal("Agility:"), Component.literal(doubleToString(PlayerData.getPlayerAgility())), -(height / 6));

            drawComponent(ms, Component.literal("Stat Points:"), statPoints, -(height / 10));

            setExitButton(((width / 2) - (width / 3)) + (width / 90),((height / 2) - (height / 3)) + (height / 90));
            setButtons(((width / 2) + (width / 3)) - (width/75),((height / 2) - (height / 3)));
        }
    }
    private void Close(){
        if(player != null){
            if(mc.screen == this) {
                mc.setScreen(null);
            }else{
                player.closeContainer();
            }
        }
    }
    private void drawComponent(PoseStack ms, Component name, Component amount, int hAlt){
        // hAlt > 0 -> derecha | hAlt < 0 -> izquierda
        GuiComponent.drawString(ms, font, name, ((width / 2) - (width / 3)) + (width/75), ((height / 2) - (height / 3)) + hAlt, 0xFFFFFF);
        GuiComponent.drawString(ms, font, amount, ((width / 2) - (width / 3)) + (width/4), ((height / 2) - (height / 3)) + hAlt, 0xFFFFFF);

    }
    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == GLFW.GLFW_KEY_K) {
            Close();
            return true;
        }
        return super.keyPressed(key, b, c);
    }
    @Override
    public void containerTick() {
        ticks++;
        if(ticks % 5 == 0){
            if(windowChange()){
                height = window.getGuiScaledHeight();
                width = window.getGuiScaledWidth();
                scale = window.getGuiScale();
                previousWidth = width;
                previousHeight = height;
                previousScale = scale;
                this.imageWidth = width - (int)((215*scale)/width);
                this.imageHeight = height - (int)((200*scale)/height);
                ResetButtons();
            }
            ticks = 0;
        }

    }
    private boolean windowChange(){
        return (previousScale != window.getGuiScale()) || ((previousWidth != window.getGuiScaledWidth()) && (previousHeight != window.getGuiScaledHeight()));
    }
    private void ResetButtons(){
        this.renderables.clear();
        this.children().clear();
    }
    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {

    }
    @Override
    public void onClose() {
        super.onClose();
        mc.keyboardHandler.setSendRepeatsToGui(false);
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
        mc.keyboardHandler.setSendRepeatsToGui(true);
    }
    private void setButtons(int x, int y){
        int bS = (int)Math.floor(((double)(x + y) / 80));

        life_button = new StatPlusButton(x, y + bS + 1, bS, bS,Component.literal(""), e -> sendData("life"));
        mana_button = new StatPlusButton(x, y + bS + 3, bS, bS,Component.literal(""), e -> sendData("mana"));
        dexterity_button = new StatPlusButton(x, y + bS + 5, bS, bS,Component.literal(""), e -> sendData("dexterity"));
        intelligence_button = new StatPlusButton(x, y + bS + 7, bS, bS,Component.literal(""), e -> sendData("intelligence"));
        strength_button = new StatPlusButton(x, y + bS + 9, bS, bS,Component.literal(""), e -> sendData("strength"));
        command_button = new StatPlusButton(x, y + bS + 11, bS, bS,Component.literal(""), e -> sendData("command"));
        defense_button = new StatPlusButton(x, y + bS + 13, bS, bS,Component.literal(""), e -> sendData("defense"));
        magicdefense_button = new StatPlusButton(x, y + bS + 15, bS, bS,Component.literal(""), e -> sendData("magicdefense"));
        luck_button = new StatPlusButton(x, y + bS + 17, bS, bS,Component.literal(""), e -> sendData("luck"));
        agility_button = new StatPlusButton(x, y + bS + 19, bS, bS,Component.literal(""), e -> sendData("agility"));

        ResetButton reset_button = new ResetButton(x, y, bS, bS, Component.literal(""), e -> {
            try{
                sendData("reset");
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        OffResetButton reset_button_off = new OffResetButton(x, y, bS, bS, Component.literal(""), e -> {
            try{
                ModMessages.sendToServer(new PlayerNoReqPacket("reset"));
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });

        if(PlayerData.getPlayerStatPoints() > 0){
            TurnOn();
        }
        else{
            TurnOff();
        }
        if(PlayerData.getPlayerLevel() > 9999){
            this.addRenderableWidget(reset_button);
        }
        else{
            this.addRenderableWidget(reset_button_off);
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
    private void setExitButton(int x, int y){
        this.addRenderableWidget(new ExitButton(x, y, 12, 12, Component.literal(""), e -> Close()));
    }
    private void sendData(String stat){
        ModMessages.sendToServer(new PlayerStatSyncPacket(stat));
    }
}