package begosrs.barbarianassault.ui;

import begosrs.barbarianassault.Role;
import begosrs.barbarianassault.Wave;
import begosrs.barbarianassault.timer.Timer;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BaMinigameWavesPanel extends JPanel {

	private static final String ERROR_PANEL = "ERROR_PANEL";
	private static final String WAVES_PANEL = "WAVES_PANEL";

	private final GridBagConstraints constraints = new GridBagConstraints();
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel wavesPanel = new JPanel();
	private final JPanel container = new JPanel(cardLayout);

	private final List<BaMinigameWavePanel> wavePanels = new ArrayList<>();

	public BaMinigameWavesPanel() {
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 5, 0);

		JPanel wavesWrapper = new JPanel(new BorderLayout());
		wavesWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
		wavesWrapper.add(wavesPanel, BorderLayout.NORTH);

		wavesPanel.setLayout(new GridBagLayout());
		wavesPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		wavesPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel errorWrapper = new JPanel(new BorderLayout());
		errorWrapper.setBackground(ColorScheme.DARK_GRAY_COLOR);
		PluginErrorPanel errorPanel = new PluginErrorPanel();
		errorWrapper.add(errorPanel, BorderLayout.NORTH);

		errorPanel.setBorder(new EmptyBorder(50, 20, 20, 20));
		errorPanel.setContent("No waves recorded", "No waves were found on this account.");

		container.add(wavesWrapper, WAVES_PANEL);
		container.add(errorWrapper, ERROR_PANEL);
		add(container, BorderLayout.CENTER);

		addWaves();
	}

	void addWaves() {
		Wave wave4 = new Wave(null, 4, Role.HEALER, new Timer());
		BaMinigameWavePanel wave4Panel = new BaMinigameWavePanel(wave4);
		wavePanels.add(wave4Panel);
		wavesPanel.add(wave4Panel, constraints);
		constraints.gridy++;

		Wave wave3 = new Wave(null, 3, Role.DEFENDER, new Timer());
		wave3.setWaveFinished();
		BaMinigameWavePanel wave3Panel = new BaMinigameWavePanel(wave3);
		wavePanels.add(wave3Panel);
		wavesPanel.add(wave3Panel, constraints);
		constraints.gridy++;

		Wave wave2 = new Wave(null, 2, Role.COLLECTOR, new Timer());
		wave2.setWaveFinished();
		BaMinigameWavePanel wave2Panel = new BaMinigameWavePanel(wave2);
		wavePanels.add(wave2Panel);
		wavesPanel.add(wave2Panel, constraints);
		constraints.gridy++;

		Wave wave1 = new Wave(null, 1, Role.ATTACKER, new Timer());
		wave1.setWaveFinished();
		BaMinigameWavePanel wave1Panel = new BaMinigameWavePanel(wave1);
		wavePanels.add(wave1Panel);
		wavesPanel.add(wave1Panel, constraints);
		constraints.gridy++;

		if (wavePanels.size() > 0) {
			cardLayout.show(container, WAVES_PANEL);
		}
		else {
			cardLayout.show(container, ERROR_PANEL);
		}
	}

	public void updatePanel() {
		wavePanels.forEach(BaMinigameWavePanel::updatePanel);
	}
}
