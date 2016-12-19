package org.squiddev.plethora.integration.vanilla.method;

import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.squiddev.plethora.EquipmentInvWrapper;
import org.squiddev.plethora.api.method.IContext;
import org.squiddev.plethora.api.method.IMethod;
import org.squiddev.plethora.api.method.IUnbakedContext;
import org.squiddev.plethora.api.module.IModuleContainer;
import org.squiddev.plethora.api.module.ModuleObjectMethod;
import org.squiddev.plethora.gameplay.modules.PlethoraModules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.squiddev.plethora.api.reference.Reference.id;

/**
 * Various introspection modules which rely on Vanilla classes.
 */
public class ModuleIntrospectionEntity {
	@IMethod.Inject(IModuleContainer.class)
	public static final class MethodEntityPlayerInventory extends ModuleObjectMethod<IModuleContainer> {
		public MethodEntityPlayerInventory() {
			super("getInventory", PlethoraModules.INTROSPECTION, true, "function():table -- Get this player's inventory");
		}

		@Nullable
		@Override
		public Object[] apply(@Nonnull IContext<IModuleContainer> context, @Nonnull Object[] args) throws LuaException {
			EntityPlayer player = context.getContext(EntityPlayer.class);
			if (player == null) throw new LuaException("Player not found");

			IItemHandler inventory = new PlayerMainInvWrapper(player.inventory);
			IUnbakedContext<IItemHandler> newContext = context.makeChild(id(inventory));
			return new Object[]{newContext.getObject()};
		}
	}

	@IMethod.Inject(IModuleContainer.class)
	public static final class MethodEntityEquipment extends ModuleObjectMethod<IModuleContainer> {
		public MethodEntityEquipment() {
			super("getEquipment", PlethoraModules.INTROSPECTION, true, "function():table -- Get this entity's held item and armor");
		}

		@Nullable
		@Override
		public Object[] apply(@Nonnull IContext<IModuleContainer> context, @Nonnull Object[] args) throws LuaException {
			EntityLivingBase entity = context.getContext(EntityLivingBase.class);
			if (entity == null) throw new LuaException("Entity not found");

			IItemHandler inventory = new EquipmentInvWrapper(entity);
			IUnbakedContext<IItemHandler> newContext = context.makeChild(id(inventory));
			return new Object[]{newContext.getObject()};
		}
	}

	@IMethod.Inject(IModuleContainer.class)
	public static final class MethodEntityPlayerGetEnder extends ModuleObjectMethod<IModuleContainer> {
		public MethodEntityPlayerGetEnder() {
			super("getEnder", PlethoraModules.INTROSPECTION, true, "function():table -- Get this player's ender chest");
		}

		@Nullable
		@Override
		public Object[] apply(@Nonnull IContext<IModuleContainer> context, @Nonnull Object[] args) throws LuaException {
			EntityPlayer player = context.getContext(EntityPlayer.class);
			if (player == null) throw new LuaException("Player not found");

			IInventory inventory = player.getInventoryEnderChest();
			IUnbakedContext<IInventory> newContext = context.makeChild(id(inventory));
			return new Object[]{newContext.getObject()};
		}
	}
}