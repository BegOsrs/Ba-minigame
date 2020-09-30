/*
 * Copyright (c) 2018, Cameron <https://github.com/noremac201>
 * Copyright (c) 2018, Jacob M <https://github.com/jacoblairm>
 * Copyright (c) 2020, BegOsrs <https://github.com/begosrs>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package begosrs.barbarianassault;

import begosrs.barbarianassault.deathtimes.DeathTimesMode;
import begosrs.barbarianassault.grounditems.GroundEggsMode;
import begosrs.barbarianassault.grounditems.MenuHighlightMode;
import begosrs.barbarianassault.inventory.InventoryHighlightMode;
import begosrs.barbarianassault.points.PointsMode;
import begosrs.barbarianassault.points.RewardsBreakdownMode;
import begosrs.barbarianassault.timer.DurationMode;
import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("baMinigame")
public interface BaMinigameConfig extends Config
{
	@ConfigItem(
		keyName = "enableGameChatColors",
		name = "Chat colors",
		description = "Enable game chat colors on messages announced by this plugin",
		position = 1
	)
	default boolean enableGameChatColors()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapQuickStart",
		name = "Quick-start swap",
		description = "Swap Climb-down with Quick-start on lobby ladders",
		position = 2
	)
	default boolean swapQuickStart()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapGetRewards",
		name = "Get-rewards swap",
		description = "Swap Talk-to with Get-rewards for the Commander Connad",
		position = 3
	)
	default boolean swapGetRewards()
	{
		return true;
	}

	@ConfigSection(
		name = "In-game",
		description = "",
		position = 4,
		closedByDefault = true
	)
	String inGameSection = "inGameSection";

	@ConfigItem(
		keyName = "showTimer",
		name = "Call change timer",
		description = "Shows time to next call change",
		section = inGameSection,
		position = 0
	)
	default boolean showTimer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "deathTimesMode",
		name = "Death times",
		description = "Shows the time all penance monsters of a certain type are killed in an info box, the chat, or both",
		section = inGameSection,
		position = 1
	)
	default DeathTimesMode deathTimesMode()
	{
		return DeathTimesMode.INFOBOX_CHAT;
	}

	@ConfigItem(
		keyName = "showEggsOnHopper",
		name = "Eggs loaded on hoppers",
		description = "Displays the amount of loaded eggs on cannon hoppers",
		section = inGameSection,
		position = 2
	)
	default boolean showEggsOnHopper()
	{
		return true;
	}

	@ConfigItem(
		keyName = "inventoryHighlightMode",
		name = "Inventory highlight",
		description = "Define the mode of all inventory highlights",
		section = inGameSection,
		position = 3
	)
	default InventoryHighlightMode inventoryHighlightMode()
	{
		return InventoryHighlightMode.OVERLAY;
	}

	@ConfigItem(
		keyName = "showGroundItemHighlights",
		name = "Ground item highlights",
		description = "Show ground item highlights",
		section = inGameSection,
		position = 4
	)
	default boolean showGroundItemHighlights()
	{
		return true;
	}

	@ConfigSection(
		name = "Attacker",
		description = "Options associated to the Attacker role",
		position = 5,
		closedByDefault = true
	)
	String attackerSection = "attacker";

	@ConfigItem(
		keyName = "highlightArrows",
		name = "Highlight arrows",
		description = "Highlights arrows called by your teammate",
		position = 0,
		section = attackerSection
	)
	default boolean highlightArrows()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "highlightArrowColor",
		name = "Highlight arrow color",
		description = "Configures the color to highlight the called arrows",
		position = 1,
		section = attackerSection
	)
	default Color highlightArrowColor()
	{
		return new Color(0, 255, 0, 100);
	}

	@ConfigItem(
		keyName = "highlightAttackStyle",
		name = "Highlight attack style",
		description = "Highlights the attack style called by your teammate",
		position = 2,
		section = attackerSection
	)
	default boolean highlightAttackStyle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightAttackStyleColor",
		name = "Highlight attack style color",
		description = "Configures the color to highlight the attack style",
		position = 3,
		section = attackerSection
	)
	default Color highlightAttackStyleColor()
	{
		return Color.GREEN;
	}

	@ConfigSection(
		name = "Defender",
		description = "Options associated to the Defender role",
		position = 6,
		closedByDefault = true
	)
	String defenderSection = "defender";

	@ConfigItem(
		keyName = "highlightBait",
		name = "Highlight called bait",
		description = "Highlights bait called by your teammate",
		position = 0,
		section = defenderSection
	)
	default boolean highlightBait()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "highlightBaitColor",
		name = "Called bait color",
		description = "Color to highlight the bait called by your teammate",
		position = 1,
		section = defenderSection
	)
	default Color highlightBaitColor()
	{
		return new Color(0, 255, 0, 100);
	}

	@ConfigItem(
		keyName = "highlightGroundBait",
		name = "Highlight ground bait",
		description = "Highlight bait dropped on the ground",
		position = 2,
		section = defenderSection
	)
	default boolean highlightGroundBait()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightGroundBaitColor",
		name = "Ground bait color",
		description = "Color to highlight the bait dropped on the ground",
		position = 3,
		section = defenderSection
	)
	default Color highlightGroundBaitColor()
	{
		return Color.WHITE;
	}

	@ConfigItem(
		keyName = "highlightLogsHammer",
		name = "Highlight ground logs/hammer",
		description = "Highlight logs and hammer on the ground",
		position = 4,
		section = defenderSection
	)
	default boolean highlightGroundLogsHammer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightLogsHammerColor",
		name = "Ground logs/hammer color",
		description = "Color to highlight the bait dropped on the ground",
		position = 5,
		section = defenderSection
	)
	default Color highlightGroundLogsHammerColor()
	{
		return Color.WHITE;
	}

	@ConfigItem(
		keyName = "showRunnerTickTimer",
		name = "Show runner tick timer",
		description = "Shows the current cycle tick of runners",
		position = 6,
		section = defenderSection
	)
	default boolean showRunnerTickTimer()
	{
		return true;
	}

	@ConfigSection(
		name = "Collector",
		description = "Options associated to the Collector role",
		position = 7,
		closedByDefault = true
	)
	String collectorSection = "collector";

	@ConfigItem(
		keyName = "showEggCountOverlay",
		name = "Show number of eggs collected",
		description = "Displays current number of eggs collected",
		position = 0,
		section = collectorSection
	)
	default boolean showEggCountOverlay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightGroundEggsMode",
		name = "Highlight eggs",
		description = "Highlight egg colors on the ground when performing the Collector role",
		position = 1,
		section = collectorSection
	)
	default GroundEggsMode highlightGroundEggsMode()
	{
		return GroundEggsMode.CALLED;
	}

	@ConfigItem(
		keyName = "menuHighlightMode",
		name = "Menu highlight mode",
		description = "Configures what to highlight in right-click menu",
		section = collectorSection,
		position = 2
	)
	default MenuHighlightMode menuHighlightMode()
	{
		return MenuHighlightMode.NAME;
	}

	@ConfigItem(
		keyName = "swapCollectionBag",
		name = "Collection bag swap",
		description = "Swap Look-in with Empty on the collection bag",
		position = 3,
		section = collectorSection
	)
	default boolean swapCollectionBag()
	{
		return true;
	}

	@ConfigItem(
		keyName = "swapCollectorHorn",
		name = "Collector horn swap",
		description = "Swap Use with Tell-defensive on the collector horn",
		position = 4,
		section = collectorSection
	)
	default boolean swapCollectorHorn()
	{
		return false;
	}

	@ConfigItem(
		keyName = "swapDestroyEggs",
		name = "Collector eggs swap",
		description = "Swap Use with Destroy on red/green/blue eggs",
		position = 5,
		section = collectorSection
	)
	default boolean swapDestroyEggs()
	{
		return false;
	}

	@ConfigSection(
		name = "Healer",
		description = "Options associated to the Healer role",
		position = 8,
		closedByDefault = true
	)
	String healerSection = "healer";

	@ConfigItem(
		keyName = "highlightPoison",
		name = "Highlight called poison",
		description = "Highlights poison food called by your teammate",
		position = 0,
		section = healerSection
	)
	default boolean highlightPoison()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "highlightPoisonColor",
		name = "Called poison color",
		description = "Configures the color to highlight the correct poison food",
		position = 1,
		section = healerSection
	)
	default Color highlightPoisonColor()
	{
		return new Color(0, 255, 0, 100);
	}

	@ConfigItem(
		keyName = "highlightNotification",
		name = "Highlight incorrect notification",
		description = "Highlights incorrect poison chat notification",
		position = 2,
		section = healerSection
	)
	default boolean highlightNotification()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightNotificationColor",
		name = "Notification color",
		description = "Configures the color to highlight the notification text",
		position = 3,
		section = healerSection
	)
	default Color highlightNotificationColor()
	{
		return new Color(228, 18, 31);
	}

	@ConfigItem(
		keyName = "showHpCountOverlay",
		name = "Show number of hitpoints healed",
		description = "Displays current number of hitpoints healed",
		position = 4,
		section = healerSection
	)
	default boolean showHpCountOverlay()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showTeammateHealthBars",
		name = "Show teammate health bars",
		description = "Displays a health bar where a teammate's remaining health is located",
		position = 5,
		section = healerSection
	)
	default boolean showTeammateHealthBars()
	{
		return true;
	}

	@ConfigSection(
		name = "Post-game",
		description = "",
		position = 9,
		closedByDefault = true
	)
	String postGameSection = "postGameSection";

	@ConfigItem(
		keyName = "showDurationMode",
		name = "Duration",
		description = "Displays duration after each wave and/or round",
		section = postGameSection,
		position = 0
	)
	default DurationMode showDurationMode()
	{
		return DurationMode.WAVE_ROUND;
	}

	@ConfigItem(
		keyName = "showRewardPointsMode",
		name = "Reward points",
		description = "Gives summary of reward points in the chat after each wave and/or round",
		section = postGameSection,
		position = 1
	)
	default PointsMode showRewardPointsMode()
	{
		return PointsMode.WAVE_ROUND;
	}

	@ConfigItem(
		keyName = "showRewardsBreakdownMode",
		name = "Rewards breakdown",
		description = "Gives summary of advanced points breakdown in the chat after each wave and/or round",
		section = postGameSection,
		position = 2
	)
	default RewardsBreakdownMode showRewardsBreakdownMode()
	{
		return RewardsBreakdownMode.ROUND;
	}

	@ConfigItem(
		keyName = "groundItemsPluginHiddenList",
		name = "Ground Items Hidden List",
		description = "Stores all the items automatically added to the ground items plugin hidden list",
		hidden = true
	)
	default String getGroundItemsPluginHiddenList()
	{
		return "";
	}

	@ConfigItem(
		keyName = "groundItemsPluginHiddenList",
		name = "",
		description = "",
		hidden = true
	)
	void setGroundItemsPluginHiddenList(String list);

	@ConfigItem(
		keyName = "barbarianAssaultConfigs",
		name = "Barbarian Assault Configs",
		description = "Stores all the configs previously set on the barbarian assault plugin",
		hidden = true
	)
	default String getBarbarianAssaultConfigs()
	{
		return "";
	}

	@ConfigItem(
		keyName = "barbarianAssaultConfigs",
		name = "",
		description = "",
		hidden = true
	)
	void setBarbarianAssaultConfigs(String configs);
}