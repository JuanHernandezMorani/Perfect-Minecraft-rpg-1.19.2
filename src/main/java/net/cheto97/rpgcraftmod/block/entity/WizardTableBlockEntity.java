package net.cheto97.rpgcraftmod.block.entity;

import net.cheto97.rpgcraftmod.fluid.ModFluids;
import net.cheto97.rpgcraftmod.item.ModItems;
import net.cheto97.rpgcraftmod.menu.GemInfusingStationMenu;
import net.cheto97.rpgcraftmod.menu.WizardTableMenu;
import net.cheto97.rpgcraftmod.networking.ModMessages;
import net.cheto97.rpgcraftmod.networking.data.PlayerData;
import net.cheto97.rpgcraftmod.networking.packet.S2C.EnergySyncPacket;
import net.cheto97.rpgcraftmod.networking.packet.S2C.FluidSyncPacket;
import net.cheto97.rpgcraftmod.networking.packet.S2C.ItemStackSyncS2CPacket;
import net.cheto97.rpgcraftmod.util.ModEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.cheto97.rpgcraftmod.util.NumberUtils.*;

public class WizardTableBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack){
            return switch (slot){
                case 0 -> stack.getItem() == ModItems.ULTIMATE_COAL.get() || stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1 -> stack.getItem() != ModItems.ULTIMATE_COAL.get() || !stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 2 -> false;
                default -> super.isItemValid(slot,stack);
            };
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(500000,2560) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncPacket(this.energy, getBlockPos()));
        }
    };

    private static final int ENERGY_REQ = 40;

    private final FluidTank FLUID_TANK = new FluidTank(600000){
        @Override
        protected void onContentsChanged() {
            setChanged();
            assert level != null;
            if(!level.isClientSide()){
                ModMessages.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.SOURCE_MANA.get();
        }
    };

    public void setFluid(FluidStack stack){
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack(){
        return this.FLUID_TANK.getFluid();
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(2).isEmpty()) {
            stack = itemHandler.getStackInSlot(2);
        } else {
            stack = itemHandler.getStackInSlot(1);
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    public WizardTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIZARD_TABLE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WizardTableBlockEntity.this.progress;
                    case 1 -> WizardTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WizardTableBlockEntity.this.progress = value;
                    case 1 -> WizardTableBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Wizard Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        ModMessages.sendToClients(new EnergySyncPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncPacket(this.getFluidStack(), worldPosition));

        return new WizardTableMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER){
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("wizard_table.progress", this.progress);
        nbt.putInt("wizard_table.energy", ENERGY_STORAGE.getEnergyStored());
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("wizard_table.progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("wizard_table.energy"));
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WizardTableBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if(hasFuel(pEntity)){
            pEntity.ENERGY_STORAGE.receiveEnergy(64*pEntity.itemHandler.getStackInSlot(0).getCount(),false);
            pEntity.itemHandler.extractItem(0,1,false);
        }

        if(hasRecipe(pEntity) && !pEntity.itemHandler.getStackInSlot(1).isEmpty() && hasEnoughEnergy(pEntity) && hasEnoughFluid(pEntity)) {
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        if(hasFluidItemInSourceSlot(pEntity)){
            transferItemFluidToFluidTank(pEntity);
        }
    }

    private static boolean hasEnoughFluid(WizardTableBlockEntity pEntity) {
        return pEntity.FLUID_TANK.getFluidAmount() >= 1500;
    }

    private static void transferItemFluidToFluidTank(WizardTableBlockEntity pEntity) {
        pEntity.itemHandler.getStackInSlot(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler ->{
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(),1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.FLUID_TANK.isFluidValid(stack)){
                stack = handler.drain(drainAmount,IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity,stack,handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(WizardTableBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0,1,false);
        pEntity.itemHandler.insertItem(0,container,false);
    }

    private static boolean hasFluidItemInSourceSlot(WizardTableBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }
    
    private static void extractEnergy(WizardTableBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(WizardTableBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private static boolean hasFuel(WizardTableBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.ULTIMATE_COAL.get();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(WizardTableBlockEntity pEntity) {

        if(hasRecipe(pEntity)) {
            pEntity.FLUID_TANK.drain(1500,IFluidHandler.FluidAction.EXECUTE);
            ItemStack item = modifyItem(pEntity.itemHandler.getStackInSlot(1));
            pEntity.itemHandler.extractItem(1, item.getCount(), false);
            pEntity.itemHandler.setStackInSlot(2, item);

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(WizardTableBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        return canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, entity.itemHandler.getStackInSlot(1));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    private static ItemStack modifyItem(ItemStack item){
        int type = 0;

        if (item.getItem() instanceof BowItem) {
            type = 1;
        }
        else if(item.getItem() instanceof CrossbowItem){
            type = 2;
        }
        else if(item.getItem() instanceof TridentItem){
            type = 3;
        }
        else if(item.getItem() instanceof PickaxeItem){
            type = 4;
        }
        else if(item.getItem() instanceof AxeItem){
            type = 5;
        }
        else if(item.getItem() instanceof SwordItem){
            type = 6;
        }
        else if(item.getItem() instanceof HoeItem){
            type = 7;
        }
        else if(item.getItem() instanceof ShieldItem){
            type = 8;
        }
        else if (item.getItem() instanceof ArmorItem armorItem) {
            switch(armorItem.getSlot()){
                case HEAD -> type = 9;
                case CHEST -> type = 10;
                case LEGS -> type = 11;
                case FEET -> type = 12;
            }
        }
        else if(item.getItem() instanceof ShovelItem){
            type = 13;
        }
        else{
            return item;
        }

        int level = PlayerData.getPlayerLevel();
        double luck = PlayerData.getPlayerLuck();
        ItemStack output = item.copy();

        if(luck > 10000){
            luck = 10000;
        }
        String name = output.getDisplayName().getString();
        int opt = selectTier(luck-1);
        switch (opt) {
            case 1 -> {
                Component iQualityComponent = Component.literal("[Crafted - Common] - {"+level+"} "+name).withStyle(ChatFormatting.GRAY);
                output.resetHoverName();
                output.setHoverName(iQualityComponent);
            }
            case 2 -> {
                Component iQualityComponent2 = Component.literal("[Crafted - Uncommon] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_GRAY);
                output.resetHoverName();
                output.setHoverName(iQualityComponent2);
            }
            case 3 -> {
                Component iQualityComponent3 = Component.literal("[Crafted - Very Uncommon] - {"+level+"} "+name).withStyle(ChatFormatting.WHITE);
                output.resetHoverName();
                output.setHoverName(iQualityComponent3);
            }
            case 4 -> {
                Component iQualityComponent4 = Component.literal("[Crafted - Rare] - {"+level+"} "+name).withStyle(ChatFormatting.AQUA);
                output.resetHoverName();
                output.setHoverName(iQualityComponent4);
            }
            case 5 -> {
                Component iQualityComponent5 = Component.literal("[Crafted - Very Rare] - {"+level+"} "+name).withStyle(ChatFormatting.GREEN);
                output.resetHoverName();
                output.setHoverName(iQualityComponent5);
            }
            case 6 -> {
                Component iQualityComponent6 = Component.literal("[Crafted - Ultra Rare] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_AQUA);
                output.resetHoverName();
                output.setHoverName(iQualityComponent6);
            }
            case 7 -> {
                Component iQualityComponent7 = Component.literal("[Crafted - Ultra Really Rare] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_GREEN);
                output.resetHoverName();
                output.setHoverName(iQualityComponent7);
            }
            case 8 -> {
                Component iQualityComponent8 = Component.literal("[Crafted - Epic] - {"+level+"} "+name).withStyle(ChatFormatting.DARK_PURPLE);
                output.resetHoverName();
                output.setHoverName(iQualityComponent8);
            }
            case 9 -> {
                Component iQualityComponent9 = Component.literal("[Crafted - Legendary] - {"+level+"} "+name).withStyle(ChatFormatting.GOLD);
                output.resetHoverName();
                output.setHoverName(iQualityComponent9);
            }
            case 10 -> {
                Component iQualityComponent10 = Component.literal("[Crafted - Mythic] - {"+level+"} "+name).withStyle(ChatFormatting.LIGHT_PURPLE);
                output.resetHoverName();
                output.setHoverName(iQualityComponent10);
            }
            case 11 -> {
                Component iQualityComponent11 = Component.literal("[Crafted - OverPowered] - {"+level+"} "+name).withStyle(ChatFormatting.RED);
                output.resetHoverName();
                output.setHoverName(iQualityComponent11);
            }
        }

        if(PlayerData.getPlayerLuck() > 10000000){
            luck = 10000000;
        }else{
            luck = PlayerData.getPlayerLuck();
        }

        int playerLevel;
        if(level >= 32767){
            playerLevel = 32767;
        }else{
            playerLevel = PlayerData.getPlayerLevel() + 1;
        }
        int levelEnchant = randomIntWithLuck(playerLevel,1,luck);

        Map<Enchantment, Integer> axeEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.SHARPNESS, levelEnchant,
                Enchantments.BLOCK_EFFICIENCY, levelEnchant,
                Enchantments.BLOCK_FORTUNE, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> pickaxeEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.BLOCK_EFFICIENCY, levelEnchant,
                Enchantments.BLOCK_FORTUNE, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> shovelEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.BLOCK_EFFICIENCY, levelEnchant,
                Enchantments.SILK_TOUCH, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> hoeEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.SILK_TOUCH, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> swordEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.SHARPNESS, levelEnchant,
                Enchantments.KNOCKBACK,levelEnchant,
                Enchantments.FIRE_ASPECT,levelEnchant,
                Enchantments.MOB_LOOTING,levelEnchant,
                Enchantments.BANE_OF_ARTHROPODS,levelEnchant,
                Enchantments.SMITE,levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> helmetEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.ALL_DAMAGE_PROTECTION,levelEnchant,
                Enchantments.FIRE_PROTECTION, levelEnchant,
                Enchantments.PROJECTILE_PROTECTION, levelEnchant,
                Enchantments.BLAST_PROTECTION, levelEnchant,
                Enchantments.AQUA_AFFINITY, levelEnchant,
                Enchantments.RESPIRATION, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> chestplateEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.ALL_DAMAGE_PROTECTION,levelEnchant,
                Enchantments.FIRE_PROTECTION, levelEnchant,
                Enchantments.PROJECTILE_PROTECTION, levelEnchant,
                Enchantments.BLAST_PROTECTION, levelEnchant,
                Enchantments.THORNS, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> leggingsEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.ALL_DAMAGE_PROTECTION,levelEnchant,
                Enchantments.FIRE_PROTECTION, levelEnchant,
                Enchantments.PROJECTILE_PROTECTION, levelEnchant,
                Enchantments.BLAST_PROTECTION, levelEnchant,
                Enchantments.THORNS, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> bootsEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.ALL_DAMAGE_PROTECTION,levelEnchant,
                Enchantments.FIRE_PROTECTION, levelEnchant,
                Enchantments.PROJECTILE_PROTECTION, levelEnchant,
                Enchantments.BLAST_PROTECTION, levelEnchant,
                Enchantments.THORNS, levelEnchant,
                Enchantments.FALL_PROTECTION, levelEnchant,
                Enchantments.DEPTH_STRIDER, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> bowEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.INFINITY_ARROWS,levelEnchant,
                Enchantments.FLAMING_ARROWS,levelEnchant,
                Enchantments.POWER_ARROWS,levelEnchant,
                Enchantments.MOB_LOOTING,levelEnchant,
                Enchantments.PUNCH_ARROWS,levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> crossbowEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.INFINITY_ARROWS, levelEnchant,
                Enchantments.FLAMING_ARROWS, levelEnchant,
                Enchantments.POWER_ARROWS, levelEnchant,
                Enchantments.PUNCH_ARROWS, levelEnchant,
                Enchantments.QUICK_CHARGE, levelEnchant,
                Enchantments.MULTISHOT, levelEnchant,
                Enchantments.PIERCING, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> tridentEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.IMPALING,levelEnchant,
                Enchantments.LOYALTY,levelEnchant,
                Enchantments.RIPTIDE,levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );

        Map<Enchantment, Integer> shieldEnchantments = Map.of(
                Enchantments.UNBREAKING, levelEnchant,
                Enchantments.ALL_DAMAGE_PROTECTION,levelEnchant,
                Enchantments.FIRE_PROTECTION, levelEnchant,
                Enchantments.PROJECTILE_PROTECTION, levelEnchant,
                Enchantments.BLAST_PROTECTION, levelEnchant,
                Enchantments.THORNS, levelEnchant,
                Enchantments.MENDING, levelEnchant,
                Enchantments.VANISHING_CURSE, levelEnchant
        );
        if(opt > 7){
            output.getOrCreateTag().putBoolean("Unbreakable", true);
        }
        if(output.isEnchanted() || !output.isEnchantable()) return output;
        switch (type){
            case 1 -> EnchantmentHelper.setEnchantments(applyEnchantments(bowEnchantments, opt), output);
            case 2 -> EnchantmentHelper.setEnchantments(applyEnchantments(crossbowEnchantments, opt), output);
            case 3 -> EnchantmentHelper.setEnchantments(applyEnchantments(tridentEnchantments, opt), output);
            case 4 -> EnchantmentHelper.setEnchantments(applyEnchantments(pickaxeEnchantments, opt), output);
            case 5 -> EnchantmentHelper.setEnchantments(applyEnchantments(axeEnchantments, opt), output);
            case 6 -> EnchantmentHelper.setEnchantments(applyEnchantments(swordEnchantments, opt), output);
            case 7 -> EnchantmentHelper.setEnchantments(applyEnchantments(hoeEnchantments, opt), output);
            case 8 -> EnchantmentHelper.setEnchantments(applyEnchantments(shieldEnchantments, opt), output);
            case 9 -> EnchantmentHelper.setEnchantments(applyEnchantments(helmetEnchantments, opt), output);
            case 10 -> EnchantmentHelper.setEnchantments(applyEnchantments(chestplateEnchantments, opt), output);
            case 11 -> EnchantmentHelper.setEnchantments(applyEnchantments(leggingsEnchantments, opt), output);
            case 12 -> EnchantmentHelper.setEnchantments(applyEnchantments(bootsEnchantments, opt), output);
            case 13 -> EnchantmentHelper.setEnchantments(applyEnchantments(shovelEnchantments, opt), output);
        }

        EquipmentSlot slot = output.getEquipmentSlot();
        double distanceToEleven = Math.abs(opt - 11);
        double chanceMultiplier = 1.0 - (distanceToEleven / 10.0);

        AttributeModifier healthModifier = new AttributeModifier("HealthModifier", randomDouble(opt *2.35,0.025), AttributeModifier.Operation.ADDITION);
        AttributeModifier movementSpeedModifier = new AttributeModifier("MovementSpeedModifier", randomDouble(opt,0.001), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackSpeedModifier = new AttributeModifier("AttackSpeedModifier", randomInt(opt,1), AttributeModifier.Operation.ADDITION);
        AttributeModifier attackDamageModifier = new AttributeModifier("AttackDamageModifier", randomDouble(opt *3.16,0.11), AttributeModifier.Operation.ADDITION);
        AttributeModifier armorToughnessModifier = new AttributeModifier("ArmorToughnessModifier", randomInt(opt *2,1), AttributeModifier.Operation.ADDITION);
        AttributeModifier armorModifier = new AttributeModifier("ArmorModifier", randomInt(opt *3,1), AttributeModifier.Operation.ADDITION);

        Random rand = new Random();

        if (rand.nextDouble() <= chanceMultiplier) {
            assert slot != null;
            output.getAttributeModifiers(slot).put(Attributes.MAX_HEALTH, healthModifier);
            output.getAttributeModifiers(slot).put(Attributes.MOVEMENT_SPEED, movementSpeedModifier);
            output.getAttributeModifiers(slot).put(Attributes.ATTACK_SPEED, attackSpeedModifier);
            output.getAttributeModifiers(slot).put(Attributes.ATTACK_DAMAGE, attackDamageModifier);
            output.getAttributeModifiers(slot).put(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier);
            output.getAttributeModifiers(slot).put(Attributes.ARMOR, armorModifier);
        }
        return output;
    }
    private static int selectTier(double inversionFactor) {
        double[] tierPercentages = {0.4, 0.25, 0.15, 0.09, 0.05, 0.03, 0.02, 0.01, 0.005, 0.0005, 0.00005};
        double totalPercentage = 0.0;
        for (double percentage : tierPercentages) {
            totalPercentage += percentage;
        }
        double maxInversionFactor = 10000.0;
        double invertedPercentage = totalPercentage * (maxInversionFactor - inversionFactor) / maxInversionFactor;
        Random random = new Random();
        double randomNumber = random.nextDouble() * (totalPercentage + invertedPercentage);
        double cumulativePercentage = 0.0;
        int selectedTier = 1;
        for (int i = 0; i < tierPercentages.length; i++) {
            cumulativePercentage += tierPercentages[i];
            if (randomNumber < cumulativePercentage) {
                selectedTier = i + 1;
                break;
            }
        }
        return selectedTier;
    }
    public static Map<Enchantment, Integer> applyEnchantments(Map<Enchantment, Integer> enchantmentMap, int number) {
        int enchantabilitySum = 0;
        for (Enchantment enchantment : enchantmentMap.keySet()) {
            enchantabilitySum += enchantment.getRarity().getWeight();
        }

        int enchantabilityDiff = Math.abs(number - 11);
        double chanceMultiplier = 1.0 - (enchantabilityDiff / (double) enchantabilitySum);
        if (chanceMultiplier < 0.1) {
            chanceMultiplier = 0.1;
        }

        Random rand = new Random();
        Map<Enchantment, Integer> appliedEnchantments = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            if (rand.nextDouble() <= chanceMultiplier) {
                appliedEnchantments.put(enchantment, level);
            }
        }

        return appliedEnchantments;
    }
}
