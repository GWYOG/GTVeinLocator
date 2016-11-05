package pers.gwyog.gtveinlocator.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import pers.gwyog.gtveinlocator.GTVeinLocator;
import pers.gwyog.gtveinlocator.config.ModConfig;

public class ItemLocatorBase extends Item implements IElectricItem, IBoxable {
	protected final double maxCharge; 
	protected final double transferLimit; 
	protected final int tier;
	protected boolean useEnergy;
	
	public ItemLocatorBase(String name, double maxCharge, double transferLimit, int tier, boolean useEnergy) {
		this.setUnlocalizedName(GTVeinLocator.MODID + "." + name);
		this.setTextureName(GTVeinLocator.MODID + ":" + name);
		this.maxCharge = useEnergy? maxCharge: 1;
		this.transferLimit = useEnergy? transferLimit: 1;
		this.tier = useEnergy? tier: 1;
		this.useEnergy = useEnergy;
		if (useEnergy) {
			this.setMaxDamage(27);
			this.setNoRepair();
		}
		this.setMaxStackSize(1);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (useEnergy)
			return 1D - (ElectricItem.manager.getCharge(stack) / getMaxCharge(stack));
		else
			return super.getDurabilityForDisplay(stack);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return useEnergy && (ElectricItem.manager.getCharge(stack) != getMaxCharge(stack));
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.common;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		if (useEnergy) {
		    ItemStack itemStack = new ItemStack(this, 1);
		    if (getChargedItem(itemStack) == this) {
		      ItemStack charged = new ItemStack(this, 1);
		      ElectricItem.manager.charge(charged, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, false);
		      list.add(charged);
		    }
		    if (getEmptyItem(itemStack) == this) {
		      ItemStack disCharged = new ItemStack(this, 1);
		      ElectricItem.manager.charge(disCharged, 0.0D, Integer.MAX_VALUE, true, false);
		      list.add(disCharged);
		    }
		}
		else {
			ItemStack itemStack = new ItemStack(this, 1);
			list.add(itemStack);
		}
	}
	
	@Override
	public boolean canBeStoredInToolbox(ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return false;
	}

	@Override
	public Item getChargedItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public Item getEmptyItem(ItemStack itemStack) {
		return this;
	}

	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return this.maxCharge;
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return this.tier;
	}

	@Override
	public double getTransferLimit(ItemStack itemStack) {
		return this.transferLimit;
	}
	
}
