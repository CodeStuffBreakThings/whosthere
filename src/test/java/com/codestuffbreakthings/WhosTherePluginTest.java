package com.codestuffbreakthings;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WhosTherePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WhosTherePlugin.class);
		RuneLite.main(args);
	}
}