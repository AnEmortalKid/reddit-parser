package com.anemortalkid.reddit.parser.sitebuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private static String INFO_TEMPLATE = "<p><b>{0}</b> <a href=\"./{1}/\">{2}</a></p>";
	
	public static void main(String[] args) {
		List<SiteBuilder<?>> siteBuilders = new ArrayList<SiteBuilder<?>>();
		siteBuilders.add(new NPCSiteBuilder());
		siteBuilders.add(new MysteriesSiteBuilder());
		siteBuilders.add(new LocationsSiteBuilder());
		siteBuilders.add(new TreasureSiteBuilder());
		siteBuilders.add(new DungeonSiteBuilder());
		siteBuilders.add(new PlothooksSiteBuilder());
		siteBuilders.add(new VillainsSiteBuilder());
		siteBuilders.add(new RoomSiteBuilder());
		siteBuilders.forEach(x -> x.buildSite());
		
		System.out.println("<p>Last updated: " + new Date() + "</p>\n");
		siteBuilders.forEach(sb -> {
			String titleReplaced = sb.getTitle().replace(" ", "").toLowerCase();
			String line = MessageFormat.format(INFO_TEMPLATE, sb.scrubbedCount(), titleReplaced, sb.getTitle());
			System.out.println(line);
		});
	}
}
