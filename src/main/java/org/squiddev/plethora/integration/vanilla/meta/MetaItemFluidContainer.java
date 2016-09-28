package org.squiddev.plethora.integration.vanilla.meta;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.squiddev.plethora.api.meta.BaseMetaProvider;
import org.squiddev.plethora.api.meta.IMetaProvider;
import org.squiddev.plethora.api.method.IPartialContext;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

/**
 * Displays fluids contained inside a container
 */
@IMetaProvider.Inject(value = ItemStack.class, namespace = "fluid")
public class MetaItemFluidContainer extends BaseMetaProvider<ItemStack> {
	@Nonnull
	@Override
	public Map<Object, Object> getMeta(@Nonnull IPartialContext<ItemStack> context) {
		ItemStack stack = context.getTarget();
		FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(stack);
		int capacity = 0;

		if (fluidStack == null) {
			Item item = stack.getItem();
			if (item instanceof IFluidContainerItem) {
				IFluidContainerItem container = (IFluidContainerItem) item;
				fluidStack = container.getFluid(stack);
				capacity = container.getCapacity(stack);
			}
		} else {
			capacity = FluidContainerRegistry.getContainerCapacity(fluidStack, stack);
		}

		if (fluidStack != null) {
			Map<Object, Object> data = Maps.newHashMap();
			data.put("capacity", capacity);
			data.put("fluid", context.makePartialChild(fluidStack).getMeta());
			return data;
		} else {
			return Collections.emptyMap();
		}
	}
}
