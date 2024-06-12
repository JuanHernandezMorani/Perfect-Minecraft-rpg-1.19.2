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
import net.minecraft.client.gui.components.MultiLineEditBox;
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
    private final Window window = Minecraft.getInstance().getWindow();
    private byte ticksSinceUpdate = 0;
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
        height = window.getGuiScaledHeight();
        width = window.getGuiScaledWidth();
        previousWidth = width;
        previousHeight = height;
        scale = window.getGuiScale();
        previousScale = scale;

        this.imageWidth = width - (int)((900*scale)/width);
        this.imageHeight = height - (int)((715*scale)/height);
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
        assert Minecraft.getInstance().player != null;
        Player player = Minecraft.getInstance().player.getId() == PlayerData.getPlayerId() ? Minecraft.getInstance().player : null;
        if(player != null) {
            Component playerClass = switch (PlayerData.getPlayerClass()) {
                case 1 -> Component.literal("Archer   ").withStyle(ChatFormatting.GOLD);
                case 2 -> Component.literal("Mage   ").withStyle(ChatFormatting.GOLD);
                case 3 -> Component.literal("Warrior   ").withStyle(ChatFormatting.GOLD);
                case 4 -> Component.literal("Assassin   ").withStyle(ChatFormatting.GOLD);
                case 6 -> Component.literal("Beast Tamer   ").withStyle(ChatFormatting.GOLD);
                case 7 -> Component.literal("Priest   ").withStyle(ChatFormatting.GOLD);
                case 8 -> Component.literal("Knight   ").withStyle(ChatFormatting.GOLD);
                default -> Component.literal("Balanced   ").withStyle(ChatFormatting.GOLD);
            };
            playerClass.plainCopy().append(player.getName().getString() + " Stat Menu");
            this.addRenderableWidget(new ExitButton((width / 2) - (width / 3) + (width / 100), (height / 2) - (height / 3) + (height / 100), 12, 12, Component.literal(""), e -> Close()));
            GuiComponent.drawCenteredString(ms, font, playerClass, (width / 2), (height / 2) - (height / 3) + (height / 80), 0);
        }
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, texture);
        blit(ms, (width/2)-(width/3), (height/2)-(height/3), 0, 0, this.imageWidth, this.imageHeight,(width/2)+(width/3),(height/2)+(height/3));
        RenderSystem.disableBlend();
    }
    private void setStatsDisplay(){
        int sdW = (width / 2) - (width / 3);
        int sdH = (height / 2) - (height / 3);
        assert Minecraft.getInstance().player != null;
        Player player = Minecraft.getInstance().player.getId() == PlayerData.getPlayerId() ? Minecraft.getInstance().player : null;
        if(player != null){
            double exp = PlayerData.getExpNeed()-PlayerData.getPlayerExperience();
            Component statPoints = Component.literal(" "+PlayerData.getPlayerStatPoints()).withStyle(ChatFormatting.LIGHT_PURPLE);
            ChatFormatting defColor = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting luckColor = player.hasEffect(MobEffects.LUCK) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting damColor = player.hasEffect(MobEffects.DAMAGE_BOOST) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting weakColor = player.hasEffect(MobEffects.WEAKNESS) ? ChatFormatting.DARK_RED : null;
            ChatFormatting regLifeColor = player.hasEffect(MobEffects.REGENERATION) ? ChatFormatting.DARK_GREEN : null;
            ChatFormatting damWeakColor = damColor != null && weakColor != null ? Objects.requireNonNull(player.getEffect(MobEffects.DAMAGE_BOOST)).getAmplifier() >= Objects.requireNonNull(player.getEffect(MobEffects.WEAKNESS)).getAmplifier() ? ChatFormatting.GREEN : ChatFormatting.RED : null;


            MobEffectInstance defEff = player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ? player.getEffect(MobEffects.DAMAGE_RESISTANCE) : null;
            MobEffectInstance luckEff = player.hasEffect(MobEffects.LUCK) ? player.getEffect(MobEffects.LUCK) : null;
            MobEffectInstance damEff = player.hasEffect(MobEffects.DAMAGE_BOOST) ? player.getEffect(MobEffects.DAMAGE_BOOST) : null;
            MobEffectInstance weakEff = player.hasEffect(MobEffects.WEAKNESS) ? player.getEffect(MobEffects.WEAKNESS) : null;
            MobEffectInstance regLifeEff = player.hasEffect(MobEffects.REGENERATION) ? player.getEffect(MobEffects.REGENERATION) : null;

            double damBonus = damEff != null && weakEff != null ? calculateDamageAndReduce(damEff,weakEff,PlayerData.getPlayerStrength()) : damEff != null ? calculateValue(damEff,PlayerData.getPlayerStrength(),"add") : weakEff != null ? calculateValue(weakEff,PlayerData.getPlayerStrength(),"reduce") : 0.0;
            double defBonus = defEff != null ? calculateValue(defEff,PlayerData.getPlayerDefense(),"add") : 0.0;
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


            Component data = Component.literal(
                    "  Level:  "+ PlayerData.getPlayerLevel() + "\n"+
                            "  Resets:  "+ PlayerData.getPlayerReset() + "\n"+
                            "  Needed Experience:  "+ doubleToString(exp) + "\n"+"\n"+
                            "  Life:  "+ doubleToString(PlayerData.getPlayerLife()) + "\n"+
                            "  Life Regeneration:  "+ componentRegLife+ "\n"+"\n"+
                            "  Mana:  "+ doubleToString(PlayerData.getPlayerMana()) + "\n"+
                            "  Mana Regeneration:  "+ doubleToString(PlayerData.getPlayerManaRegeneration())+ "\n"+ "\n"+
                            "  Defense:  "+ componentDefense+ "\n"+ "\n"+
                            "  Magic defense:  "+ doubleToString(PlayerData.getPlayerMagicDefense())+ "\n"+ "\n"+
                            "  Strength:  "+ componentStrength+ "\n"+ "\n"+
                            "  Luck:  "+ componentLuck+ "\n"+ "\n"+
                            "  Intelligence:  "+ doubleToString(PlayerData.getPlayerIntelligence())+ "\n"+ "\n"+
                            "  Dexterity:  "+ doubleToString(PlayerData.getPlayerDexterity())+ "\n"+ "\n"+
                            "  Command:  "+ doubleToString(PlayerData.getPlayerCommand())+ "\n"+ "\n"+
                            "  Agility:  "+ doubleToString(PlayerData.getPlayerAgility())+"\n"+"\n"+"\n"+"\n"+
                            "     Stats Points: "+ statPoints);

            MultiLineEditBox statsDisplay = new MultiLineEditBox(Minecraft.getInstance().font, sdW, sdH, this.imageWidth, this.imageHeight, data, data);
            this.addWidget(statsDisplay);
        }
    }
    private void Close(){
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        if(this.minecraft.screen == this) {
            this.minecraft.setScreen(null);
        }else{
            this.minecraft.player.closeContainer();
        }
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
        this.ticksSinceUpdate++;
        height = window.getGuiScaledHeight();
        width = window.getGuiScaledWidth();
        scale = window.getGuiScale();
        if(windowChange()){
            previousWidth = width;
            previousHeight = height;
            previousScale = scale;
            this.imageWidth = width - (int)((900*scale)/width);
            this.imageHeight = height - (int)((715*scale)/height);
            ResetButtons();
            this.init();
        }
        if (this.ticksSinceUpdate % 5 == 0) {
            this.ticksSinceUpdate = 0;
            ResetButtons();
            this.init();
        }

    }
    private boolean windowChange(){
        return (previousScale != scale) || ((previousWidth != width) && (previousHeight != height));
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
        setStatsDisplay();

        int xPos = this.imageWidth + (width/50);
        int yPos = (height/2)-(height/3)-(height/80);
        int bW = 10;
        int bH = 10;
        int alt = 2;

        life_button = new StatPlusButton(xPos, this.topPos + 60 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("life")));
        mana_button = new StatPlusButton(xPos, this.topPos + 95 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("mana")));
        dexterity_button = new StatPlusButton(xPos, this.topPos + 130 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("dexterity")));
        intelligence_button = new StatPlusButton(xPos, this.topPos + 150 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("intelligence")));
        strength_button = new StatPlusButton(xPos, this.topPos + 170 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("strength")));
        command_button = new StatPlusButton(xPos, this.topPos + 190 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("command")));
        defense_button = new StatPlusButton(xPos, this.topPos + 210 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("defense")));
        magicdefense_button = new StatPlusButton(xPos, this.topPos + 230 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("magicdefense")));
        luck_button = new StatPlusButton(xPos, this.topPos + 250 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("luck")));
        agility_button = new StatPlusButton(xPos, this.topPos + 270 - alt, bW, bH,Component.literal(""), e -> ModMessages.sendToServer(new PlayerStatSyncPacket("agility")));

        ResetButton reset_button = new ResetButton(xPos, yPos, bW, bH, Component.literal(""), e -> {
            ModMessages.sendToServer(new PlayerStatSyncPacket("reset"));
            ResetButtons();
            init();
        });
        OffResetButton reset_button_off = new OffResetButton(xPos, yPos, bW, bH, Component.literal(""), e -> ModMessages.sendToServer(new PlayerNoReqPacket("reset")));

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
}