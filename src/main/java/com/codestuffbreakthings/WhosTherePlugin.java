package com.codestuffbreakthings;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@PluginDescriptor(
	name = "Who's There?"
)

public class WhosTherePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private WhosThereConfig config;

    @Getter
    private List<WhosTherePlayerData> playerList;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private WhosThereOverlay overlay;

    @Inject
    private ClientToolbar clientToolbar;

    private WhosTherePanel panel;
    private NavigationButton navButton;
//    @Inject
//    private WorldView worldView;

    int playerCount;

    private static final String ICON = "magnifyingglass.png";

	@Override
	protected void startUp() throws Exception
	{
        log.debug("on startUp");
        BufferedImage icon = ImageUtil.loadImageResource(getClass(), ICON);
        overlayManager.add(overlay);
        panel = new WhosTherePanel(this);
        navButton = NavigationButton.builder()
                .tooltip("Who's There?")
                .icon(icon)
                .priority(7)
                .panel(panel)
                .build();
        clientToolbar.addNavigation(navButton);
        playerList = new ArrayList<>();
        playerCount = 0;
	}

	@Override
	protected void shutDown() throws Exception
	{
        log.debug("on shutDown");
		overlayManager.remove(overlay);
        clientToolbar.removeNavigation(navButton);
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
	}


//    public void getPlayerInfo(IndexedObjectSet<? extends Player> players){
//        //IndexedObjectSet<? extends Player> players = client.getTopLevelWorldView().players();
//        if (players.stream().findAny().isPresent()) {
//            //playerCount = (int) players.stream().count() - 1;
//            log.debug("Nearby player count: {}", playerCount);
//            log.debug("Nearby player names: ");
//            for (Player p : players) {
//                if(p.getName().equalsIgnoreCase(client.getLocalPlayer().getName())){
//                    continue;
//                }
//                log.debug(p.getName());
//            }
//        }
//    }

    public void updateData(Player player, String action){
        log.debug("updateData");
        if(player.getName().equalsIgnoreCase(client.getLocalPlayer().getName())){
            log.debug("Player is self, exiting method");
            return;
        }
        log.debug("Creating WhosTherePlayerData object for {}", player.getName());
        WhosTherePlayerData data = new WhosTherePlayerData(player.getName());
        switch(action){
            case "add":
                log.debug("entering add switch case");
//                for(WhosTherePlayerData playerData : playerList){
//                    if(playerData.getUsername().equalsIgnoreCase(player.getName())){
//                        log.debug("Player {} already exists in list, no need to add", player.getName());
//                        return;
//                    }
//                }
                if(playerList.stream().noneMatch(p -> p.getUsername().equalsIgnoreCase(player.getName()))) {
                    log.debug("increasing player count and adding object to list");
                    playerCount++;
                    overlay.updateOverlayPlayerCount(playerCount);
                    playerList.add(data);
                    SwingUtilities.invokeLater(() -> panel.populate(playerList));
                }
                break;
            case "remove":
                log.debug("entering remove switch case");
                if(playerList.stream().anyMatch(p -> p.getUsername().equalsIgnoreCase(player.getName()))) {
//                for(WhosTherePlayerData playerData : playerList){
//                    if(!playerData.getUsername().equalsIgnoreCase(player.getName())){
//                        //log.debug("Player {} already does not exist in list, no need to remove", player.getName());
//                        return;
//                    }
//                }
                log.debug("decreasing player count and removing object from list");
                playerCount--;
                overlay.updateOverlayPlayerCount(playerCount);
                playerList.remove(data);
                SwingUtilities.invokeLater(() -> panel.populate(playerList));
                }
                break;
        }
    }

        public void displayData(){
            log.debug("Player count: {}", playerCount);
            log.debug(playerList.toString());
        }

    @Subscribe
    public void onPlayerSpawned(PlayerSpawned playerSpawned)
    {
//        if(!playerSpawned.getPlayer().getName().equalsIgnoreCase(client.getLocalPlayer().getName())) {
//            playerCount++;
            log.debug("onPlayerSpawned event");
            updateData(playerSpawned.getPlayer(),"add");
//            log.debug("Nearby player count is now {}", playerCount);
//            log.debug("{} appeared", playerSpawned.getPlayer().getName());
//            log.debug("World view is {}", playerSpawned.getPlayer().getWorldView().getId());
//
//        }
        displayData();
    }

    @Subscribe
    public void onPlayerDespawned(PlayerDespawned playerDespawned)
    {
//        if(!playerDespawned.getPlayer().getName().equalsIgnoreCase(client.getLocalPlayer().getName())) {
//            playerCount--;
            log.debug("onPlayerDespawned event");
//            log.debug("Nearby player count is now {}", playerCount);
//            log.debug("{} disappeared", playerDespawned.getPlayer().getName());
//        }
        updateData(playerDespawned.getPlayer(),"remove");
        displayData();
    }

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
        log.debug("onGameStateChanged to {}", gameStateChanged.getGameState());

		if (gameStateChanged.getGameState() != GameState.LOGGED_IN
        && gameStateChanged.getGameState() != GameState.LOADING)
		{
			//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
            log.debug("GameState not LOGGED_IN, clearing values");
            playerCount = 0;
            playerList.clear();
        }
	}
//
//    @Subscribe
//    public void onWorldViewLoaded(WorldViewLoaded worldViewLoaded){
//        log.debug("onWorldViewLoaded event");
//        IndexedObjectSet<? extends Player> players = worldViewLoaded.getWorldView().players();
//        log.debug(String.valueOf(players.stream().count()));
//        getPlayerInfo(worldViewLoaded.getWorldView().players());
//
////        getPlayerInfo();
//    }
//
//    @Subscribe
//    public void onWorldViewUnloaded(WorldViewUnloaded worldViewUnloaded){
//        log.debug("onWorldViewUnloaded event");
//        IndexedObjectSet<? extends Player> players = worldViewUnloaded.getWorldView().players();
//        log.debug(String.valueOf(players.stream().count()));
//        getPlayerInfo(worldViewUnloaded.getWorldView().players());
//    }

	@Provides
    WhosThereConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WhosThereConfig.class);
	}
}
