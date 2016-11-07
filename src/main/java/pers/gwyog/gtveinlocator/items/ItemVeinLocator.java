package pers.gwyog.gtveinlocator.items;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import pers.gwyog.gtveinlocator.GTVeinLocator;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class ItemVeinLocator extends ItemLocatorBase {
    
    public ItemVeinLocator(String name, double maxCharge, double transferLimit, int tier, boolean useEnergy) {
        super(name, maxCharge, transferLimit, tier, useEnergy);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int searchRange = getSearchRangeFromNBT(stack);
        if (player.isSneaking()) {
            if (!world.isRemote)
                switchMode(stack, searchRange);
            else
                player.addChatMessage(new ChatComponentText(I18n.format("chat.gtveinlocator.switch_range", 4-searchRange, 4-searchRange)));
        }
        else if (!player.isSneaking()) {
            if (useEnergy)
                if (!ElectricItem.manager.use(stack, ModConfig.veinLocatorSingleUseCost*searchRange*searchRange, player))
                    return stack;
            if (world.isRemote) {
                int indexX = getClosestIndex(player.posX);
                int indexZ = getClosestIndex(player.posZ);
                player.addChatMessage(new ChatComponentText(I18n.format("chat.gtveinlocator.showing_message", searchRange*searchRange)));
                String message = "";
                for (int i=(1-searchRange)/2; i<(1+searchRange)/2; i++) {
                    for (int j=(1-searchRange)/2; j<(1+searchRange)/2; j++)
                        message = message + "(" + getCoordinateFromIndex(indexX+i) + "," + getCoordinateFromIndex(indexZ+j) + ") "; 
                    player.addChatMessage(new ChatComponentText(message));    
                    message = "";
                }
            }    
        }
        return stack;
    }
    
    protected void switchMode(ItemStack stack, int searchRange){
        if (stack.getTagCompound()==null)
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("SearchRange",4-searchRange);
        return;
    }
    
    protected int getSearchRangeFromNBT(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag!=null && tag.hasKey("SearchRange")) 
            return tag.getInteger("SearchRange");
        else
            return 3;
    }
    
    protected int getClosestIndex(double var1) {
        return (int)(var1>=8?Math.round((var1-24)/48):Math.round((var1-40)/48));
    }
    
    protected int getCoordinateFromIndex(int index) {
        return index>=0?(24+48*index):(40+48*index);
    }
}
