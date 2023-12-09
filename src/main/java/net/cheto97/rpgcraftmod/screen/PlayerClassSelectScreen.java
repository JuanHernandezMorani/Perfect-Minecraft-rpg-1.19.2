package net.cheto97.rpgcraftmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cheto97.rpgcraftmod.RpgcraftMod;
import net.cheto97.rpgcraftmod.menu.PlayerClassSelectMenu;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.packet.C2S.PlayerClassSelectPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class PlayerClassSelectScreen extends AbstractContainerScreen<PlayerClassSelectMenu> {
    private final double scale = Minecraft.getInstance().getWindow().getGuiScale();
    private final int width = (int) (650 / scale);
    private final int height = (int) (650 / scale);
    private static final String newline = System.getProperty("line.separator");
    private String selectedClass = "balanced";
    private String classDescription = "";
    private String classStats = "";
    private MultiLineEditBox selectedClassText;
    private MultiLineEditBox classDescriptionText;
    private MultiLineEditBox initialStatsText;

    public PlayerClassSelectScreen(PlayerClassSelectMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        
        this.imageWidth = Minecraft.getInstance().getWindow().getScreenWidth();
        this.imageHeight = Minecraft.getInstance().getWindow().getScreenHeight();
    }
    private static final ResourceLocation texture = new ResourceLocation(RpgcraftMod.MOD_ID,"textures/gui/class_background.png");
    private void setClassText(){
        if(selectedClassText == null){
            selectedClassText = new MultiLineEditBox(this.getMinecraft().font, width - (this.leftPos  + 450),  height - (this.topPos + 480), 40, 20, Component.empty().withStyle(ChatFormatting.GOLD), Component.empty().withStyle(ChatFormatting.GOLD));
            selectedClassText.setCharacterLimit(50);
            selectedClassText.setValue(this.selectedClass);
            this.addWidget(selectedClassText);
        }
        else{
            selectedClassText.setValue(this.selectedClass);
        }
    }
    private void setDescriptionText(){
        if(classDescriptionText == null){
            classDescriptionText = new MultiLineEditBox(this.getMinecraft().font,
                    width - (this.leftPos  + 450),
                    height - (this.topPos + 300),
                    200, 150, 
                    Component.empty().withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.empty().withStyle(ChatFormatting.LIGHT_PURPLE));
            classDescriptionText.setCharacterLimit(44);
            classDescriptionText.setValue(this.classDescription);
            this.addWidget(classDescriptionText);
        }
        else{
            classDescriptionText.setValue(this.classDescription);
        }
    }
    private void setStatsText(){
        if(initialStatsText == null){
            initialStatsText = new MultiLineEditBox(this.getMinecraft().font,
                    width - (this.leftPos  + 450),
                    height - (this.topPos + 470),
                    200, 150,
                    Component.empty().withStyle(ChatFormatting.GREEN),
                    Component.empty().withStyle(ChatFormatting.GREEN));
            initialStatsText.setCharacterLimit(44);
            initialStatsText.setValue(this.classStats);
            this.addWidget(initialStatsText);
        }
        else{
            initialStatsText.setValue(this.classStats);
        }
    }
    private void classCardComponent(String selectedClass) {
        switch (selectedClass) {
            case "warrior" -> {
                setClassDescription("Masters of the battlefield," +
                                "warriors boast unparalleled resistance and vitality."+
                         "Their imposing presence makes them formidable opponents,"+
                         "but their dedication to physical strength means they forsake the arcane arts.");
                setInitialStats(22,22,5,
                        5,1.2,1,
                        3,1,1,
                        1,2,1,
                        1,1);
            }
            case "priest" -> {
                setClassDescription("Priests are bastions of divine resilience, surpassing mages in vitality while falling just shy of the sheer might of warriors and knights. Their connection to higher powers grants them potent healing abilities and protective spells.");
                setInitialStats(20,20,20,
                        20,1,3,
                        1,8,2,
                        1,3,1,
                        1,1);
            }
            case "knight" -> {
                setClassDescription("Clad in impenetrable armor, knights stand as the paragons of endurance and fortitude. While they lack spell-casting prowess, their unyielding defense and commanding presence make them the bulwark of any group.");
                setInitialStats(35,35,1,
                        1,3.25,0.15,
                        5,5,1,
                        1,1,1,
                        1,1);
            }
            case "mage" -> {
                setClassDescription("Possessing profound knowledge of ancient mysticism, mages wield incredible intelligence and harness powerful spells. Their mastery of the arcane allows them to unleash devastating magical attacks, making them a force to be reckoned with.");
                setInitialStats(8,8,50,
                        50,0.65,5,
                        0.5,5,0.5,
                        1,10,1,
                        1,1);
            }
            case "beast tamer" -> {
                setClassDescription("Whispers of the wild follow beast tamers wherever they roam. With the ability to control and command creatures, they forge bonds with monsters and animals, fighting in tandem with their untamed allies.");
                setInitialStats(15,15,15,
                        15,1,1,
                        1,1,1,
                        1,1,1,
                        35,1);
            }
            case "assassin" -> {
                setClassDescription("Shadows are the playground of assassins, nimble and lethal. Sacrificing life for agility, assassins strike swiftly and silently, dealing substantial damage and evading their foes with unmatched dexterity.");
                setInitialStats(5,5,10,
                        10,0.4,1,
                        0.2,0.2,8.3,
                        9.2,1,7.5,
                        1,8.5);
            }
            case "archer" -> {
                setClassDescription("Archers, the epitome of precision and marksmanship, unleash death from a distance. Armed with a keen eye and deadly accuracy, they excel in ranged combat, picking off enemies from afar with their trusty bow.");
                setInitialStats(12,12,10,
                        10,0.75,1,
                        1,1,2,
                        10,1,4,
                        1,4);
            }
            case "random" -> {
                setClassDescription("Unpredictability defines the path of the random adventurer. Facing the unknown, they embrace uncertainty with a spirit of curiosity.What awaits them is a mystery.");
                setInitialStats(0,0,0,
                        0,0,0,
                        0,0,0,
                        0,0,0,
                        0,0);
            }
            default -> {
                setClassDescription("The common class embodies equilibrium,striking a harmonious balance between various attributes."+
                         "While not specializing in extremes,they receive initial points in all aspects,making them adaptable and well-rounded.");
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
            classStats = ("Life: ??,Max life: ??,Mana: ??,Max mana: ??,Life Regeneration: ??,Mana Regeneration: ??,Defense: ??,Magic defense: ??,Strength: ??,Luck: ??,Intelligence: ??,Dexterity: ??,Command: ??,Agility: ??");
        }
        else{
            classStats = ("Life: " + life + ","+newline +
                    "Max life: " + lifeMax + ","+newline +
                    "Mana: " + mana + ","+newline +
                    "Max mana: " + manaMax+ ","+newline +
                    "Life Regeneration: " + lifeRegeneration+ ","+newline +
                    "Mana Regeneration: " + manaRegeneration+ ","+newline +
                    "Defense: " + defense+ ","+newline +
                    "Magic defense: " + magicDefense+ ","+newline +
                    "Strength: " + strength+ ","+newline +
                    "Luck: " + luck+ ","+newline +
                    "Intelligence: " + intelligence+ ","+newline +
                    "Dexterity: " + dexterity+ ","+newline +
                    "Command: " + command+ ","+newline +
                    "Agility: " + agility);

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
        blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        setSelectedClass("balanced");

        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 250), 32, 20, Component.literal("Warrior").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("warrior")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 275), 32, 20, Component.literal("Priest").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("priest")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 300), 32, 20, Component.literal("Knight").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("knight")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 325), 32, 20, Component.literal("Mage").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("mage")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 350), 32, 20, Component.literal("Tamer").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("beast tamer")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 375), 32, 20, Component.literal("Killer").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("assassin")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 400), 32, 20, Component.literal("Archer").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("archer")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 425), 32, 20, Component.literal("Balanced").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("balanced")));
       this.addRenderableWidget(new Button(width - (this.leftPos + 580),  height - (this.topPos + 450), 32, 20, Component.literal("Random").withStyle(ChatFormatting.DARK_AQUA), e -> setSelectedClass("random")));

        this.addRenderableWidget(new Button(width - (this.leftPos+ 360),  height - (this.topPos + 267), 32, 20, Component.literal("Select class").withStyle(ChatFormatting.DARK_AQUA),  e -> {
            ModMessages.sendToServer(new PlayerClassSelectPacket(selectedClass));
            if(this.minecraft.screen == this) {
                this.minecraft.setScreen(null);
            }
        }));
    }
}