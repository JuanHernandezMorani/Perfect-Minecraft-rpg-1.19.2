package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerClassSelectMenu;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerClassSelectPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class PlayerClassSelectScreen extends AbstractContainerScreen<PlayerClassSelectMenu> {
    private final Window window = Minecraft.getInstance().getWindow();
    private int width;
    private int height;
    private int previousWidth;
    private int previousHeight;
    private double scale;
    private double previousScale;
    private boolean wasClick = false;
    private String selectedClass = "Balanced";
    private String classDescription = "";
    private String classStats = "";
    private EditBox selectedClassText;
    private MultiLineEditBox classDescriptionText;
    private MultiLineEditBox initialStatsText;
    private Button warriorButton = null;
    private Button priestButton = null;
    private Button knightButton = null;
    private Button mageButton = null;
    private Button beastTameButton = null;
    private Button assassinButton = null;
    private Button archerButton = null;
    private Button balancedButton = null;
    private Button randomButton = null;
    private Button selectClassButton = null;

    private int widthModifier(String word){
        return (word.length() * 9);
    }
    public PlayerClassSelectScreen(PlayerClassSelectMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        height = window.getGuiScaledHeight();
        width = window.getGuiScaledWidth();
        previousWidth = width;
        previousHeight = height;
        scale = window.getGuiScale();
        previousScale = scale;
        this.imageWidth = width;
        this.imageHeight = height;
    }
    private static final ResourceLocation texture = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/bc/class_background.png");
   private static String setCapLetters(String name) {
        String[] words = name.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }
    private void setClassText(){
        selectedClassText = new EditBox(this.getMinecraft().font,
                (width/2)+1,
                height/30,
                80, 20,
                Component.literal(setCapLetters(this.selectedClass)));
        selectedClassText.setValue(setCapLetters(this.selectedClass));
        selectedClassText.setTextColor(16766720);
        this.addWidget(selectedClassText);
    }
    private void setDescriptionText(){
        classDescriptionText = new MultiLineEditBox(this.getMinecraft().font,
                (width/2)+((height/10)+(height/40)),
                (height/2)-(height/3),
                180, 150,
                Component.literal(this.classDescription).withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.literal(this.classDescription).withStyle(ChatFormatting.LIGHT_PURPLE));
        this.addWidget(classDescriptionText);
    }
    private void setStatsText(){
        initialStatsText = new MultiLineEditBox(this.getMinecraft().font,
                (width/2)-(width/4),
                (height/2)-(height/3),
                130, 150,
                Component.literal(this.classStats).withStyle(ChatFormatting.GREEN),
                Component.literal(this.classStats).withStyle(ChatFormatting.GREEN));
        this.addWidget(initialStatsText);

    }
    private void classCardComponent(String selectedClass) {
        switch (selectedClass) {
            case "warrior" -> {
                setClassDescription("""
                        Masters of the battlefield
                        warriors boast unparalleled resistance and vitality.
                        Their imposing presence makes them formidable opponents
                        but their dedication to physical strength means they forsake the arcane arts.""");
                setInitialStats(22,22,5,
                        5,1.2,1,
                        3,1,1,
                        1,2,1,
                        1,1);
            }
            case "priest" -> {
                setClassDescription("""
                        Priests are bastions of divine resilience
                        surpassing mages in vitality
                        while falling just shy of the sheer might of warriors and knights.
                        Their connection to higher powers
                        grants them potent healing abilities and protective spells.""");
                setInitialStats(20,20,20,
                        20,1,3,
                        1,8,2,
                        1,3,1,
                        1,1);
            }
            case "knight" -> {
                setClassDescription("""
                        Clad in impenetrable armor
                        knights stand as the paragons of endurance and fortitude.
                        While they lack spell-casting prowess
                        their unyielding defense and commanding presence
                        make them the bulwark of any group.""");
                setInitialStats(35,35,1,
                        1,3.25,0.15,
                        5,5,1,
                        1,1,1,
                        1,1);
            }
            case "mage" -> {
                setClassDescription("""
                        Possessing profound knowledge of ancient mysticism
                        mages wield incredible intelligence and harness powerful spells.
                        Their mastery of the arcane allows them to unleash devastating magical attacks
                        making them a force to be reckoned with.""");
                setInitialStats(8,8,50,
                        50,0.65,5,
                        0.5,5,0.5,
                        1,10,1,
                        1,1);
            }
            case "beast tamer" -> {
                setClassDescription("""
                        Whispers of the wild follow beast tamers wherever they roam.
                        With the ability to control and command creatures
                        they forge bonds with monsters and animals
                        fighting in tandem with their untamed allies.""");
                setInitialStats(15,15,15,
                        15,1,1,
                        1,1,1,
                        1,1,1,
                        35,1);
            }
            case "assassin" -> {
                setClassDescription("""
                        Shadows are the playground of assassins
                        nimble and lethal.
                        Sacrificing life for agility
                        assassins strike swiftly and silently
                        dealing substantial damage and evading their foes with unmatched dexterity.""");
                setInitialStats(5,5,10,
                        10,0.4,1,
                        0.2,0.2,8.3,
                        9.2,1,7.5,
                        1,8.5);
            }
            case "archer" -> {
                setClassDescription("""
                        Archers, the epitome of precision and marksmanship
                        unleash death from a distance.
                        Armed with a keen eye and deadly accuracy
                        they excel in ranged combat
                        picking off enemies from afar with their trusty bow.""");
                setInitialStats(12,12,10,
                        10,0.75,1,
                        1,1,2,
                        10,1,4,
                        1,4);
            }
            case "random" -> {
                setClassDescription("""
                        Unpredictability defines the path of the random adventurer.
                        Facing the unknown, they embrace uncertainty with a spirit of curiosity.
                        What awaits them is a mystery.""");
                setInitialStats(0,0,0,
                        0,0,0,
                        0,0,0,
                        0,0,0,
                        0,0);
            }
            default -> {
                setClassDescription("""
                        The common class embodies equilibrium
                        striking a harmonious balance between various attributes.
                        While not specializing in extremes
                        they receive initial points in all aspects
                        making them adaptable and well-rounded.""");
                setInitialStats(20,20,10,
                        10,1,1,
                        1,1,1,
                        1,1,1,
                        1,1);
            }
        }
        setClassText();
        setStatsText();
        setDescriptionText();
    }

    private void setClassDescription(String classDescription){
        this.classDescription = classDescription;
    }
    private void setSelectedClass(String newClass){
        this.selectedClass = newClass;
        classCardComponent(newClass);
    }
    private void setInitialStats(double life, double lifeMax, double mana,
                                 double manaMax, double lifeRegeneration, double manaRegeneration,
                                 double defense, double magicDefense, double strength,
                                 double luck, double intelligence, double dexterity,
                                 double command, double agility){
        if(life == 0){
            classStats = ("""
                    Life: ???
                    Max life: ???
                    Mana: ???
                    Max mana: ???
                    Life Regeneration: ???
                    Mana Regeneration: ???
                    Defense: ???
                    Magic defense: ???
                    Strength: ???
                    Luck: ???
                    Intelligence: ???
                    Dexterity: ???
                    Command: ???
                    Agility: ???""");
        }
        else{
            classStats = ("Life: "+ life + "\n"+
                    "Max life: "+ lifeMax + "\n"+
                    "Mana: "+ mana + "\n"+
                    "Max mana: "+ manaMax+ "\n"+
                    "Life Regeneration: "+ lifeRegeneration+ "\n"+
                    "Mana Regeneration: "+ manaRegeneration+ "\n"+
                    "Defense: "+ defense+ "\n"+
                    "Magic defense: "+ magicDefense+ "\n"+
                    "Strength: "+ strength+ "\n"+
                    "Luck: "+ luck+ "\n"+
                    "Intelligence: "+ intelligence+ "\n"+
                    "Dexterity: "+ dexterity+ "\n"+
                    "Command: "+ command+ "\n"+
                    "Agility: "+ agility);
        }
    }
    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        this.selectedClassText.render(ms, mouseX, mouseY, partialTicks);
        this.classDescriptionText.render(ms, mouseX, mouseY, partialTicks);
        this.initialStatsText.render(ms, mouseX, mouseY, partialTicks);
    }
    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, texture);
        blit(ms, 0, 0, 0, 0, this.imageWidth,this.imageHeight,width, height);
        RenderSystem.disableBlend();
    }
    @Override
    public boolean keyPressed(int key, int b, int c) {
        return super.keyPressed(key, b, c);
    }
    @Override
    public void containerTick() {
        height = window.getGuiScaledHeight();
        width = window.getGuiScaledWidth();
        scale = window.getGuiScale();
       if(windowChange()){
           previousWidth = width;
           previousHeight = height;
           previousScale = scale;
           this.imageWidth = width;
           this.imageHeight = height;
           SetButtons();
           classCardComponent(this.selectedClass);
       }
        super.containerTick();
    }
    private boolean windowChange(){
       return (previousScale != scale) || ((previousWidth != width) || (previousHeight != height));
    }
    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
    }
    @Override
    public void onClose() {
        super.onClose();
        if(!wasClick){
            ModMessages.sendToServer(new PlayerClassSelectPacket("random"));
            if(this.minecraft != null && this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        setSelectedClass("balanced");

        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        SetButtons();
    }
    private void ResetButtons(){
        this.renderables.clear();
        this.children().clear();
        setSelectedClass(this.selectedClass);
    }
    private void SetButtons(){
       ResetButtons();
       int buttonWidth = width/50;
       int buttonHeight = height/2;
       int h = 20;
        warriorButton = new Button(buttonWidth,  buttonHeight - (height/3), 30, h, Component.literal("Warrior").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("warrior"));
        priestButton = new Button(buttonWidth,  buttonHeight - (height/4), 30, h, Component.literal("Priest").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("priest"));
        knightButton = new Button(buttonWidth,  buttonHeight - (height/6), 30, h, Component.literal("Knight").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("knight"));
        mageButton = new Button(buttonWidth,  buttonHeight - (height/12), 30, h, Component.literal("Mage").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("mage"));
        beastTameButton = new Button(buttonWidth,  buttonHeight, 30, h, Component.literal("Beast Tamer").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("beast tamer"));
        assassinButton = new Button(buttonWidth,  buttonHeight + (height/12), 35, h, Component.literal("Assassin").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("assassin"));
        archerButton = new Button(buttonWidth,  buttonHeight + (height/6), 36, h, Component.literal("Archer").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("archer"));
        balancedButton = new Button(buttonWidth,  buttonHeight + (height/4), 37, h, Component.literal("Balanced").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("balanced"));
        randomButton = new Button(buttonWidth,  buttonHeight + (height/3), 38, h, Component.literal("Random").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("random"));
        selectClassButton = new Button((width/2)+1,  height - 30, 40, h, Component.literal("Select class").withStyle(ChatFormatting.DARK_AQUA),  e -> {
            wasClick = true;
            ModMessages.sendToServer(new PlayerClassSelectPacket(selectedClass));
            if(this.minecraft != null && this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        });

        warriorButton.setWidth(widthModifier("warrior"));
        priestButton.setWidth(widthModifier("priest"));
        knightButton.setWidth(widthModifier("knight"));
        mageButton.setWidth(widthModifier("mage"));
        beastTameButton.setWidth(widthModifier("beast tamer"));
        assassinButton.setWidth(widthModifier("assassin"));
        archerButton.setWidth(widthModifier("archer"));
        balancedButton.setWidth(widthModifier("balanced"));
        randomButton.setWidth(widthModifier("random"));
        selectClassButton.setWidth(widthModifier("select class"));

        this.addRenderableWidget(warriorButton);
        this.addRenderableWidget(priestButton);
        this.addRenderableWidget(knightButton);
        this.addRenderableWidget(mageButton);
        this.addRenderableWidget(beastTameButton);
        this.addRenderableWidget(assassinButton);
        this.addRenderableWidget(archerButton);
        this.addRenderableWidget(balancedButton);
        this.addRenderableWidget(randomButton);
        this.addRenderableWidget(selectClassButton);
    }
}