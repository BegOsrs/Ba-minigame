/*
 * Copyright (c) 2021, Begosrs <begosrunescape@gmail.com>
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
package begosrs.barbarianassault.teamhealthbar;

import begosrs.barbarianassault.BaMinigamePlugin;
import begosrs.barbarianassault.api.widgets.BaWidgetInfo;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetPositionMode;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;

@Slf4j
public class HealerTeammatesWidgetOverlay extends Overlay
{

	private static final int OFFSET = 22;

	private final BaMinigamePlugin plugin;
	protected final Client client;
	private final Rectangle parentBounds = new Rectangle();

	public HealerTeammatesWidgetOverlay(final BaMinigamePlugin plugin, final Client client)
	{
		this.plugin = plugin;
		this.client = client;
		setPriority(OverlayPriority.HIGHEST);
		setPosition(OverlayPosition.DETACHED);
		setResettable(true);
	}

	@Override
	public String getName()
	{
		return Objects.toString(BaWidgetInfo.BA_HEAL_TEAMMATES);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final Widget widget = client.getWidget(BaWidgetInfo.BA_HEAL_TEAMMATES.getGroupId(),
				  BaWidgetInfo.BA_HEAL_TEAMMATES.getChildId());
		if (widget == null)
		{
			return null;
		}

		if (!plugin.isDisplayingHealerTeammatesHealth())
		{
			widget.setHidden(true);
			return null;
		}
		else
		{
			final Rectangle bounds = getBounds();
			widget.setOriginalX(bounds.x);
			widget.setOriginalY(bounds.y - OFFSET);
			widget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
			widget.setYPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
			widget.setHidden(false);
			widget.revalidate();
			return new Dimension(widget.getWidth(), widget.getHeight());
		}
	}

	private Rectangle getParentBounds(final Widget widget)
	{
		if (widget == null || widget.isHidden())
		{
			parentBounds.setBounds(new Rectangle());
			return parentBounds;
		}

		final Widget parent = widget.getParent();
		final Rectangle bounds;

		if (parent == null)
		{
			bounds = new Rectangle(client.getRealDimensions());
		}
		else
		{
			bounds = parent.getBounds();
		}

		parentBounds.setBounds(bounds);
		return bounds;
	}

	@Override
	public Rectangle getParentBounds()
	{
		if (!client.isClientThread())
		{
			// During overlay drag this is called on the EDT, so we just
			// cache and reuse the last known parent bounds.
			return parentBounds;
		}

		final Widget widget = client.getWidget(BaWidgetInfo.BA_HEAL_TEAMMATES.getGroupId(),
				  BaWidgetInfo.BA_HEAL_TEAMMATES.getChildId());
		return getParentBounds(widget);
	}

}
