package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.platform.Window;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerStatsMenu;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateDamageAndReduce;
import static net.cheto97.rpgcraftmod.util.Effects.Helper.calculateValue;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToIntString;
import static net.cheto97.rpgcraftmod.util.NumberUtils.doubleToString;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerNoReqPacket;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerStatSyncPacket;
import net.cheto97.rpgcraftmod.util.MouseUtil;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        this.imageWidth = width;
        this.imageHeight = height;
    }
    private static final ResourceLocation playerStatsScreenBG = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/bc/skill_background.png");
    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        this.renderBackground(ms);
    }
    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
    }
    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        int initialX = calculatePositionX(0);
        int initialY = calculatePositionY(0);
        int endX = calculatePositionX(49);
        int endY = calculatePositionY(69);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, playerStatsScreenBG);
        blit(ms, initialX, initialY, 0, 0, endX, endY, endX, endY);
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
            MutableComponent menuString = playerClass.append(player.getName().getString() + " Stat Menu");
            GuiComponent.drawCenteredString(ms, font, menuString,
                    (calculatePositionX(30)), calculatePositionY(1), 0xFFFFFF);

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

            drawComponent(ms, "Level:", Component.literal(String.valueOf(PlayerData.getPlayerLevel())), 8, mouseX, mouseY);
            drawComponent(ms, "Reset:",Component.literal(String.valueOf(PlayerData.getPlayerReset())), 12, mouseX, mouseY);
            drawComponent(ms, "Needed Experience:", Component.literal(doubleToIntString(exp)), 16, mouseX, mouseY);
            drawComponent(ms, "Life:", Component.literal(doubleToString(PlayerData.getPlayerLifeMax())), 20, mouseX, mouseY);
            drawComponent(ms, "Life Regeneration:", componentRegLife, 24, mouseX, mouseY);
            drawComponent(ms, "Mana:", Component.literal(doubleToString(PlayerData.getPlayerManaMax())), 28, mouseX, mouseY);
            drawComponent(ms, "Mana Regeneration:", Component.literal(doubleToString(PlayerData.getPlayerManaRegeneration())), 32, mouseX, mouseY);
            drawComponent(ms, "Dexterity:", Component.literal(doubleToString(PlayerData.getPlayerDexterity())), 36, mouseX, mouseY);
            drawComponent(ms, "Intelligence:", Component.literal(doubleToString(PlayerData.getPlayerIntelligence())), 40, mouseX, mouseY);
            drawComponent(ms, "Strength:", componentStrength, 44, mouseX, mouseY);
            drawComponent(ms, "Command:", Component.literal(doubleToString(PlayerData.getPlayerCommand())), 48, mouseX, mouseY);
            drawComponent(ms, "Defense:", componentDefense, 52, mouseX, mouseY);
            drawComponent(ms, "Magic Defense:", Component.literal((doubleToString(PlayerData.getPlayerMagicDefense()))), 56, mouseX, mouseY);
            drawComponent(ms, "Luck:", componentLuck, 60, mouseX, mouseY);
            drawComponent(ms, "Agility:", Component.literal(doubleToString(PlayerData.getPlayerAgility())), 64, mouseX, mouseY);
            drawComponent(ms, "Stat Points:", statPoints, 72, mouseX, mouseY);

            setExitButton((calculatePositionX(0)),calculatePositionY(0));
            setButtons(calculatePositionX(52));
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
    private void drawComponent(PoseStack ms, String name, Component amount, int position, int mouseX, int mouseY){
        GuiComponent.drawString(ms, font, Component.literal(name), calculatePositionX(2), calculatePositionY(position), 0xFFFFFF);
        GuiComponent.drawString(ms, font, amount, widthCalculate(calculatePositionX(2)), calculatePositionY(position), 0xFFFFFF);
          //statsTooltip(ms,name,(height / 3) * position,mouseX,mouseY);
    }
    private int calculatePositionY(int position){
        return ((height / 2) - (height / 3) + ((height / 90) * (position - 8)));
    }
    private int calculatePositionX(int position){
        return ((width / 2) - (width / 3)) + ( (width / 90) * (position - 2));
    }
    private void statsTooltip(PoseStack ms, String name, int hAlt, int mouseX, int mouseY){
        List<Component> stats = getStatsTooltips(name);
        if(!stats.get(0).equals(Component.empty())){
            renderStatTooltip(ms,mouseX,mouseY,((width / 2) - (width / 3)) + (width/75),hAlt,stats, widthCalculate(calculatePositionX(1)));
        }
    }
    private int widthCalculate(int position){
        return position + calculatePositionX(9);
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
            if(windowChange() && mc.screen == this){
                height = window.getGuiScaledHeight();
                width = window.getGuiScaledWidth();
                scale = window.getGuiScale();

                previousWidth = width;
                previousHeight = height;
                previousScale = scale;

                this.imageWidth = width;
                this.imageHeight = height;

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
    private void setButtons(int x){
        int bS = (int)((calculatePositionY(0) - calculatePositionY(4)) * scale);

        life_button = new StatPlusButton(x, calculatePositionY(20), bS, bS,Component.literal(""), e -> sendData("life"));
        mana_button = new StatPlusButton(x, calculatePositionY(28), bS, bS,Component.literal(""), e -> sendData("mana"));
        dexterity_button = new StatPlusButton(x, calculatePositionY(36), bS, bS,Component.literal(""), e -> sendData("dexterity"));
        intelligence_button = new StatPlusButton(x, calculatePositionY(40), bS, bS,Component.literal(""), e -> sendData("intelligence"));
        strength_button = new StatPlusButton(x, calculatePositionY(44), bS, bS,Component.literal(""), e -> sendData("strength"));
        command_button = new StatPlusButton(x, calculatePositionY(48), bS, bS,Component.literal(""), e -> sendData("command"));
        defense_button = new StatPlusButton(x, calculatePositionY(52), bS, bS,Component.literal(""), e -> sendData("defense"));
        magicdefense_button = new StatPlusButton(x, calculatePositionY(56), bS, bS,Component.literal(""), e -> sendData("magicdefense"));
        luck_button = new StatPlusButton(x, calculatePositionY(60), bS, bS,Component.literal(""), e -> sendData("luck"));
        agility_button = new StatPlusButton(x, calculatePositionY(64), bS, bS,Component.literal(""), e -> sendData("agility"));

        ResetButton reset_button = new ResetButton(x, calculatePositionY(8), bS, bS, Component.literal(""), e -> {
            try{
                sendData("reset");
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        OffResetButton reset_button_off = new OffResetButton(x, calculatePositionY(8), bS, bS, Component.literal(""), e -> {
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
        int bS = (int)((calculatePositionY(0) - calculatePositionY(4)) * scale);
        this.addRenderableWidget(new ExitButton(x, y, bS, bS, Component.literal(""), e -> Close()));
    }
    private void sendData(String stat){
        ModMessages.sendToServer(new PlayerStatSyncPacket(stat,1));
    }
    private List<Component> getStatsTooltips(String stat) {
        MutableComponent msg;
        
        switch (stat){
            case "Life:" -> msg = statMSG("""
                Increase the maximum life stat value and the regeneration,
                 if it reach 0 you will die.
                """);

            case "Life Regeneration:" -> msg = statMSG("""
                Amount of life restoration per second.
                """);

            case "Mana:" -> msg = statMSG("""
                Increase the maximum mana stat value and the regeneration,
                 its needed for certain spells, also it will decrease fall damage!.
                """);

            case "Mana Regeneration:" -> msg = statMSG("""
                Amount of mana restoration per second.
                """);

            case "Dexterity:" -> msg = statMSG("""
                Increase range damage, also increase some life max value.
                """);

            case "Intelligence:" -> msg = statMSG("""
                Increase magical damage, also increase mana max value,
                 mana regeneration per second and magic defense.
                """);

            case "Strength:" -> msg = statMSG("""
                Increase physical damage, also increase defense,
                 and life regeneration per second.
                """);

            case "Command:" -> msg = statMSG("""
                Increase companion base stats, also it is
                 needed for tame skills.
                """);

            case "Defense:" -> msg = statMSG("""
                Reduces received physical damage.
                """);

            case "Magic Defense:" -> msg = statMSG("""
                Reduces received magic damage.
                """);

            case "Luck:" -> msg = statMSG("""
                Increase chances of better loot,
                 also increase critical chance.
                """);

            case "Agility:" -> msg = statMSG("""
                Increase player movement speed.
                """);

            default -> msg = Component.empty();
        }
        return java.util.List.of(msg);
    }
    private MutableComponent statMSG(String name){
        return Component.literal(name);
    }
    private void renderStatTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, List<Component> stats, int bWidth){
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, bWidth)) {
            renderTooltip(pPoseStack, stats,
                    Optional.empty(), x, pMouseY + (pMouseY - y));
        }
    }
    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int xMin, int yMin, int bWidth) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, xMin, yMin, xMin + bWidth, yMin);
    }
}