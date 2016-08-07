package com.anemortalkid.reddit.parser.sitebuilder;

import java.util.List;
import java.util.function.Function;

import com.anemortalkid.reddit.parser.sitebuilder.dungeons.DungeonSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.locations.LocationsSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.mysteries.MysteriesSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.npcs.NPCSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.plothooks.PlothooksSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.rooms.RoomSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.treasures.TreasureSiteBuilder;
import com.anemortalkid.reddit.parser.sitebuilder.villains.VillainsSiteBuilder;

public enum DataTypes {

	PLOT_HOOKS(PlothooksSiteBuilder::new, "Plot Hooks", "Plothook", "Plot", "plot hooks"), //
	MYSTERIES(MysteriesSiteBuilder::new, "Mysteries"), //
	VILLAINS(VillainsSiteBuilder::new, "Villains"), //
	DUNGEONS(DungeonSiteBuilder::new, "Dungeons"), //
	TREASURES(TreasureSiteBuilder::new, "Treasure"), //
	LOCATIONS(LocationsSiteBuilder::new, "Locations"), //
	NPCS(NPCSiteBuilder::new, "NPCs"), //
	ROOMS(RoomSiteBuilder::new, "Rooms");

	private String[] keyWords;
	private Function<List<String>, ISiteBuilder<?>> factory;

	private DataTypes(Function<List<String>, ISiteBuilder<?>> siteBuilderFactory, String... keywords) {
		this.factory = siteBuilderFactory;
		this.keyWords = keywords;
	}

	public static DataTypes getFromTitle(String title) {
		for (DataTypes dataType : DataTypes.values()) {
			for (String keyWord : dataType.keyWords) {
				if (title.contains(keyWord))
					return dataType;
			}
		}

		throw new UnsupportedOperationException("Title: " + title + " did not match any data type");
	}

	public Function<List<String>, ISiteBuilder<?>> getSiteFactory() {
		return factory;
	}

}
