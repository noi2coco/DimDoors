/**
 * @author CreepyCre
 */
package org.dimdev.dimdoors.recipe;

import com.google.gson.Gson;
import net.minecraft.resource.JsonDataLoader;

public abstract class WorldRecipeLoader extends JsonDataLoader {
	public WorldRecipeLoader(Gson gson, String dataType) {
		super(gson, dataType);
	}
	// TODO
}
