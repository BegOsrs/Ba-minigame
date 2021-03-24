package begosrs.barbarianassault.ui;

import begosrs.barbarianassault.BaMinigamePlugin;
import begosrs.barbarianassault.Role;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.timetracking.TabContentPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.ComboBoxListRenderer;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public class BaMinigamePanel extends PluginPanel
{
	private JComboBox<String> accountSelector;
	private final JPanel display = new JPanel();
	private final MaterialTabGroup tabGroup = new MaterialTabGroup(display);

	@Inject
	private SpriteManager spriteManager;

	@Getter
	private BaMinigamePointsPanel pointsPanel;
	@Getter
	private BaMinigameWavesPanel wavesPanel;
	@Getter
	private BaMinigameRoundsPanel roundsPanel;

	@Inject
	public BaMinigamePanel(BaMinigamePlugin plugin)
	{
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 10, 0);

		add(accountSelection(), c);
		c.gridy++;

		pointsPanel = new BaMinigamePointsPanel();
		pointsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		add(pointsPanel, c);
		c.gridy++;

		wavesPanel = new BaMinigameWavesPanel();
		roundsPanel = new BaMinigameRoundsPanel();
		MaterialTab wavesTab = new MaterialTab("Waves", tabGroup, wavesPanel);
		MaterialTab roundsTab = new MaterialTab("Rounds", tabGroup, roundsPanel);

		tabGroup.setBorder(new EmptyBorder(5, 0, 0, 0));
		tabGroup.addTab(wavesTab);
		tabGroup.addTab(roundsTab);
		tabGroup.select(wavesTab);
		add(tabGroup, c);
		c.gridy++;

		add(display, c);
		c.gridy++;
	}

	private JComboBox accountSelection()
	{
		String[] items = {"Account#1", "Account#2"};
		accountSelector = new JComboBox<>(items);
		accountSelector.setRenderer(new ComboBoxListRenderer());
		accountSelector.setPreferredSize(new Dimension(accountSelector.getPreferredSize().width, 25));
		accountSelector.setForeground(Color.WHITE);
		accountSelector.setFocusable(false);

		/*Enum<?> selectedItem = Enum.valueOf(type, configManager.getConfiguration(cd.getGroup().value(), cid.getItem().keyName()));
		accountSelector.setSelectedItem(selectedItem);
		accountSelector.setToolTipText(Text.titleCase(selectedItem));*/

		accountSelector.addItemListener(e ->
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				/*changeConfiguration(accountSelector, cd, cid);
				accountSelector.setToolTipText(Text.titleCase((Enum<?>) box.getSelectedItem()));*/
			}
		});

		return accountSelector;
	}

	public void update()
	{
		// TODO update only active panel
		SwingUtilities.invokeLater(wavesPanel::updatePanel);
		SwingUtilities.invokeLater(roundsPanel::updatePanel);
	}

}
