package org.squiddev.plethora.integration.vanilla.converter;

import net.minecraft.tileentity.TileEntity;
import org.squiddev.plethora.api.Constants;
import org.squiddev.plethora.api.converter.IConverter;
import org.squiddev.plethora.api.module.IModuleContainer;
import org.squiddev.plethora.api.module.IModuleHandler;
import org.squiddev.plethora.api.module.SingletonModuleContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@IConverter.Inject(TileEntity.class)
public class ConverterTileModule implements IConverter<TileEntity, IModuleContainer> {
	@Nullable
	@Override
	public IModuleContainer convert(@Nonnull TileEntity from) {
		IModuleHandler handler = from.getCapability(Constants.MODULE_HANDLER_CAPABILITY, null);
		if (handler == null) return null;
		return new SingletonModuleContainer(handler.getModule());
	}
}
