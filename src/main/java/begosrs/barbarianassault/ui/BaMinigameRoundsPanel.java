package begosrs.barbarianassault.ui;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BaMinigameRoundsPanel extends JPanel {

	private static final String ERROR_PANEL = "ERROR_PANEL";
	private static final String ROUNDS_PANEL = "ROUNDS_PANEL";

	private final GridBagConstraints constraints = new GridBagConstraints();
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel roundsPanel = new JPanel();
	private final JPanel container = new JPanel(cardLayout);

	private final List<BaMinigameRoundPanel> rounds = new ArrayList<>();

	public BaMinigameRoundsPanel() {
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;

		JPanel roundsWrapper = new JPanel(new BorderLayout());
		roundsWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
		roundsWrapper.add(roundsPanel, BorderLayout.NORTH);

		roundsPanel.setLayout(new GridBagLayout());
		roundsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		roundsPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel errorWrapper = new JPanel(new BorderLayout());
		errorWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
		PluginErrorPanel errorPanel = new PluginErrorPanel();
		errorWrapper.add(errorPanel, BorderLayout.NORTH);

		errorPanel.setBorder(new EmptyBorder(50, 20, 20, 20));
		errorPanel.setContent("No rounds recorded", "No rounds were found on this account.");

		container.add(roundsWrapper, ROUNDS_PANEL);
		container.add(errorWrapper, ERROR_PANEL);
		add(container, BorderLayout.CENTER);

		addRounds();
	}

	void addRounds() {
		rounds.add(new BaMinigameRoundPanel());
		roundsPanel.add(new BaMinigameRoundPanel(), constraints);
		constraints.gridy++;
		if (rounds.size() > 0) {
			cardLayout.show(container, ROUNDS_PANEL);
		}
		else {
			cardLayout.show(container, ERROR_PANEL);
		}
	}

	public void updatePanel() {

	}
}

