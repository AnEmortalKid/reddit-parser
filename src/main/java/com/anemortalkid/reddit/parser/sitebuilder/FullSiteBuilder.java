package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.ArrayList;
import java.util.List;

import com.anemortalkid.reddit.parser.sitebuilder.dungeons.DungeonSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.locations.LocationsSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.mysteries.MysteriesSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.npcs.NPCSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.plothooks.PlothooksSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.rooms.RoomSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.treasures.TreasureSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.villains.VillainsSiteBuilder;

/**
 * Contains a list of all the sites and lets me run them all at once instead of
 * having to run each at a time
 * 
 * @author JMonterrubio
 *
 */
public class FullSiteBuilder {

	public static void main(String[] args) {
		List<ISiteBuilder> siteBuilders = new ArrayList<ISiteBuilder>();
		siteBuilders.add(new NPCSiteBuilder());
		siteBuilders.add(new MysteriesSiteBuilder());
		siteBuilders.add(new LocationsSiteBuilder());
		siteBuilders.add(new TreasureSiteBuilder());
		siteBuilders.add(new DungeonSiteBuilder());
		siteBuilders.add(new PlothooksSiteBuilder());
		siteBuilders.add(new VillainsSiteBuilder());
		siteBuilders.add(new RoomSiteBuilder());
		siteBuilders.forEach(x -> x.buildSite());
	}
}
