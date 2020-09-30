package begosrs.barbarianassault.grounditems;

import begosrs.barbarianassault.BaMinigameConfig;
import begosrs.barbarianassault.BaMinigamePlugin;
import begosrs.barbarianassault.Role;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.util.QuantityFormatter;

@Slf4j
@Singleton
public class GroundItemsOverlay extends OverlayPanel
{
	private static final int MAX_DISTANCE = 2500;
	// We must offset the text on the z-axis such that
	// it doesn't obscure the ground items below it.
	private static final int OFFSET_Z = 20;
	// The game won't send anything higher than this value to the plugin -
	// so we replace any item quantity higher with "Lots" instead.
	private static final int MAX_QUANTITY = 65535;
	// The 15 pixel gap between each drawn ground item.
	private static final int STRING_GAP = 15;

	private final StringBuilder itemStringBuilder = new StringBuilder();
	private final TextComponent textComponent = new TextComponent();
	private final Map<WorldPoint, Integer> offsetMap = new HashMap<>();

	private final Client client;

	private final BaMinigamePlugin plugin;
	private final BaMinigameConfig config;

	@Inject
	public GroundItemsOverlay(final Client client, final BaMinigamePlugin plugin,
							  final BaMinigameConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showGroundItemHighlights())
		{
			return null;
		}

		final Role role = plugin.getRole();
		if (role == null)
		{
			return null;
		}

		switch (role)
		{
			case COLLECTOR:
			{
				renderEggs(graphics);
				break;
			}
			case DEFENDER:
			{
				if (config.highlightGroundBait())
				{
					renderBait(graphics);
				}
				if (config.highlightGroundLogsHammer())
				{
					renderLogsHammer(graphics);
				}
				break;
			}
		}

		return null;
	}

	private void renderEggs(Graphics2D graphics)
	{
		final Collection<GroundItem> eggsList = plugin.getGroundEggs().values();

		final String calledEgg = plugin.getLastListen();

		final GroundEggsMode groundEggsMode = config.highlightGroundEggsMode();

		final Predicate<GroundItem> filter = item ->
			groundEggsMode == GroundEggsMode.ALL
				|| groundEggsMode == GroundEggsMode.CALLED &&
				(item.getId() == ItemID.YELLOW_EGG || calledEgg != null && calledEgg.startsWith(item.getName()));

		renderGroundItems(graphics, eggsList, filter);
	}

	private void renderBait(Graphics2D graphics)
	{
		final Collection<GroundItem> bait = plugin.getGroundBait().values();

		renderGroundItems(graphics, bait);
	}

	private void renderLogsHammer(Graphics2D graphics)
	{
		final Collection<GroundItem> logsHammer = plugin.getGroundLogsHammer().values();

		renderGroundItems(graphics, logsHammer);
	}

	private void renderGroundItems(Graphics2D graphics, Collection<GroundItem> itemsList)
	{
		renderGroundItems(graphics, itemsList, null);
	}

	private void renderGroundItems(Graphics2D graphics, Collection<GroundItem> itemsList, Predicate<GroundItem> filter)
	{
		final Player player = client.getLocalPlayer();

		if (player == null || client.getViewportWidget() == null)
		{
			return;
		}

		offsetMap.clear();
		final LocalPoint localLocation = player.getLocalLocation();

		for (GroundItem item : itemsList)
		{

			if (filter != null && !filter.test(item))
			{
				log.debug("item {} failed the filter test", item.getName());
				continue;
			}

			final LocalPoint groundPoint = LocalPoint.fromWorld(client, item.getLocation());

			if (groundPoint == null || localLocation.distanceTo(groundPoint) > MAX_DISTANCE)
			{
				continue;
			}

			itemStringBuilder.append(item.getName());

			if (item.getQuantity() > 1)
			{
				if (item.getQuantity() >= MAX_QUANTITY)
				{
					itemStringBuilder.append(" (Lots!)");
				}
				else
				{
					itemStringBuilder.append(" (")
						.append(QuantityFormatter.quantityToStackSize(item.getQuantity()))
						.append(")");
				}
			}

			final String itemString = itemStringBuilder.toString();
			itemStringBuilder.setLength(0);

			final Point textPoint = Perspective.getCanvasTextLocation(client,
				graphics,
				groundPoint,
				itemString,
				item.getHeight() + OFFSET_Z);

			if (textPoint == null)
			{
				continue;
			}

			final int offset = offsetMap.compute(item.getLocation(), (k, v) -> v != null ? v + 1 : 0);

			final int textX = textPoint.getX();
			final int textY = textPoint.getY() - (STRING_GAP * offset);

			textComponent.setText(itemString);
			textComponent.setColor(plugin.getColorForGroundItemId(item.getId()));
			textComponent.setPosition(new java.awt.Point(textX, textY));
			textComponent.render(graphics);
		}
	}

}