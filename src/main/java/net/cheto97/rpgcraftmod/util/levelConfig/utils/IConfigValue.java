package net.cheto97.rpgcraftmod.util.levelConfig.utils;

import com.google.gson.JsonObject;

public interface IConfigValue<T> {

	String getIdentifier();

	void setToDefault();

	T getValue();
	
	void serialize(JsonObject jsonObject);

	void deserialize(JsonObject jsonObject);

}
