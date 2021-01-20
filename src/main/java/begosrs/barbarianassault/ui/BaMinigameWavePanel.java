package begosrs.barbarianassault.ui;

import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BaMinigameSlotPanel extends JPanel
{
	private static final Dimension ICON_SIZE = new Dimension(32, 32);
	private final JShadowedLabel uiLabelActions;
	private final JShadowedLabel uiLabelTitle;

	BaMinigameSlotPanel()
	{
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

		JLabel uiIcon = new JLabel();
		uiIcon.setBorder(new EmptyBorder(0, 0, 0, 5));
		//spriteManager.addSpriteTo(uiIcon, 582, 0);

		uiIcon.setMinimumSize(ICON_SIZE);
		uiIcon.setMaximumSize(ICON_SIZE);
		uiIcon.setPreferredSize(ICON_SIZE);
		uiIcon.setHorizontalAlignment(JLabel.CENTER);
		add(uiIcon, BorderLayout.LINE_START);

		JPanel uiInfo = new JPanel(new GridLayout(2, 1));
		uiInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		uiLabelTitle = new JShadowedLabel("No Action Selected");
		uiLabelTitle.setForeground(Color.WHITE);

		uiLabelActions = new JShadowedLabel("Shift-click to select multiple");
		uiLabelActions.setFont(FontManager.getRunescapeSmallFont());
		uiLabelActions.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

		uiInfo.add(uiLabelTitle);
		uiInfo.add(uiLabelActions);

		add(uiInfo, BorderLayout.CENTER);
	}

	void setText(String text)
	{
		uiLabelActions.setText(text);
	}

	void setTitle(String text)
	{
		uiLabelTitle.setText(text);
	}
}
