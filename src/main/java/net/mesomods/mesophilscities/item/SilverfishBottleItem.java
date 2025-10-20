
package net.mesomods.mesophilscities.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;

import net.mesomods.mesophilscities.procedures.SilverfishBottleRightclickedOnBlockProcedure;
import net.mesomods.mesophilscities.procedures.SilverfishBottlePlayerFinishesUsingItemProcedure;

public class SilverfishBottleItem extends Item {
	public SilverfishBottleItem() {
		super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0f).alwaysEat().build()));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(Items.GLASS_BOTTLE);
		if (entity instanceof Player player) {
			world.playSound(player, player, SoundEvents.SILVERFISH_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
		}
		super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		SilverfishBottlePlayerFinishesUsingItemProcedure.execute(entity);
		if (itemstack.isEmpty()) {
			return retval;
		} else {
			if (entity instanceof Player player && !player.getAbilities().instabuild) {
				if (!player.getInventory().add(retval))
					player.drop(retval, false);
			}
			return itemstack;
		}
	}

	public SoundEvent getDrinkingSound() {
		return SoundEvents.SILVERFISH_HURT;
	}

	public SoundEvent getEatingSound() {
		return SoundEvents.SILVERFISH_HURT;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		return SilverfishBottleRightclickedOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
				context.getClickedFace(), context.getPlayer(), context.getItemInHand());
	}
}
