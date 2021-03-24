package begosrs.barbarianassault.ui;

import begosrs.barbarianassault.Wave;
import begosrs.barbarianassault.WaveState;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class BaMinigameWavePanel extends JPanel
{

	private static final String FACE_CARD = "FACE_CARD";
	private static final String DETAILS_CARD = "DETAILS_CARD";

	private static final ImageIcon RIGHT_ARROW_ICON;
	private static final ImageIcon LEFT_ARROW_ICON;

	private static final Dimension ROLE_ICON_SIZE = new Dimension(38, 38);
	private static final Dimension SWITCH_SIZE = new Dimension(12, 12);
	private static final Dimension PENANCE_ICON_SIZE = new Dimension(14, 20);

	private static final Border ORANGE_BORDER =
		BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_INPROGRESS_COLOR).darker());

	private static final Border ORANGE_PADDED_BORDER =
		new CompoundBorder(ORANGE_BORDER, BorderFactory.createEmptyBorder(1, 0, 1, 12));

	private static final Border GREEN_BORDER =
		BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_COMPLETE_COLOR).darker());

	private static final Border GREEN_PADDED_BORDER =
		new CompoundBorder(GREEN_BORDER, BorderFactory.createEmptyBorder(1, 0, 1, 12));

	private static final Border RED_BORDER =
		BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_ERROR_COLOR).darker());

	private static final Border RED_PADDED_BORDER =
		new CompoundBorder(RED_BORDER, BorderFactory.createEmptyBorder(1, 0, 1, 12));

	private final Wave wave;
	private boolean showingFace;

	private final JPanel container;
	private final CardLayout cardLayout;
	private final TimerLabel timerLabel;

	static
	{
		final BufferedImage rightArrow = ImageUtil.alphaOffset(ImageUtil.getResourceStreamFromClass(BaMinigameWavePanel.class, "/arrow_right.png"), 0.25f);
		RIGHT_ARROW_ICON = new ImageIcon(rightArrow);
		LEFT_ARROW_ICON	= new ImageIcon(ImageUtil.flipImage(rightArrow, true, false));
	}

	BaMinigameWavePanel(Wave wave)
	{
		this.wave = wave;
		this.showingFace = true;

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARKER_GRAY_COLOR);

		cardLayout = new CardLayout();

		container = new JPanel();
		container.setLayout(cardLayout);
		container.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		timerLabel = new TimerLabel(wave.getTimer(), wave.getState() == WaveState.IN_PROGRESS);

		JPanel faceCard = new JPanel();
		faceCard.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		faceCard.setLayout(new BorderLayout());
		JLabel faceCardIcon = makeRoleIcon();
		faceCard.add(faceCardIcon, BorderLayout.LINE_START);
		faceCard.add(makeInfo(), BorderLayout.CENTER);
		if (wave.getState() == WaveState.IN_PROGRESS) {
			faceCard.add(makeTimerSwitch(), BorderLayout.LINE_END);
		}
		else {
			faceCard.add(timerLabel, BorderLayout.LINE_END);
		}

		MouseListener ml;
		switch (wave.getState()) {
			case IN_PROGRESS:
				faceCard.setBorder(ORANGE_BORDER);
				ml = new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent mouseEvent)
					{
						if (SwingUtilities.isLeftMouseButton(mouseEvent))
						{
							switchPanel();
						}
					}
				};
				faceCardIcon.addMouseListener(ml);
				timerLabel.addMouseListener(ml);
				faceCard.addMouseListener(ml);

				JPanel detailsCard = new JPanel();
				detailsCard.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				detailsCard.setLayout(new BorderLayout());
				detailsCard.setBorder(ORANGE_BORDER);

				JPanel waveDetails = makeDetails();
				waveDetails.addMouseListener(ml);
				detailsCard.add(waveDetails, BorderLayout.CENTER);
				detailsCard.add(makeSwitchIcon(LEFT_ARROW_ICON), BorderLayout.EAST);
				detailsCard.addMouseListener(ml);
				container.add(detailsCard, DETAILS_CARD);
				break;
			case FINISHED:
				faceCard.setBorder(GREEN_PADDED_BORDER);
				break;
			case CANCELLED:
				faceCard.setBorder(RED_PADDED_BORDER);
				break;
		}

		container.add(faceCard, FACE_CARD);

		cardLayout.show(container, FACE_CARD);

		add(container, BorderLayout.CENTER);
	}

	private JPanel makeTimerSwitch() {
		JPanel timerSwitch = new JPanel();
		timerSwitch.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		timerSwitch.setLayout(new BorderLayout());
		timerSwitch.add(timerLabel, BorderLayout.LINE_START);
		JLabel switchIcon = makeSwitchIcon(RIGHT_ARROW_ICON);
		timerSwitch.add(switchIcon, BorderLayout.LINE_END);
		return timerSwitch;
	}

	private JLabel makeSwitchIcon(ImageIcon icon) {
		JLabel switchFaceViewIcon = new JLabel();
		switchFaceViewIcon.setIcon(icon);
		switchFaceViewIcon.setVerticalAlignment(JLabel.CENTER);
		switchFaceViewIcon.setHorizontalAlignment(JLabel.CENTER);
		switchFaceViewIcon.setPreferredSize(SWITCH_SIZE);
		return switchFaceViewIcon;
	}

	private JPanel makeInfo() {
		JPanel waveInfo = new JPanel(new GridLayout(wave.getState() == WaveState.IN_PROGRESS ? 1 : 2, 1));
		waveInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		JShadowedLabel uiWaveTitle = new JShadowedLabel("Wave " + wave.getNumber());
		uiWaveTitle.setForeground(Color.WHITE);
		waveInfo.add(uiWaveTitle);
		if (wave.getState() == WaveState.FINISHED) {
			JShadowedLabel uiPoints = new JShadowedLabel( wave.getRolesPoints()[wave.getRole().ordinal()] + " points");
			uiPoints.setFont(FontManager.getRunescapeSmallFont());
			uiPoints.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
			waveInfo.add(uiPoints);
		}
		return waveInfo;
	}

	// amount of npcs on this wave
	private JPanel makeDetails() {
		JPanel waveDetails = new JPanel(new GridLayout(1, 4));
		waveDetails.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		waveDetails.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 0));

		JLabel penanceRangerLabel = makePenanceCount(1, 5);
		penanceRangerLabel.setIcon(makePenanceIcon("penance_ranger"));
		waveDetails.add(penanceRangerLabel);

		JLabel penanceFighterLabel = makePenanceCount(1, 5);
		penanceFighterLabel.setIcon(makePenanceIcon("penance_fighter"));
		waveDetails.add(penanceFighterLabel);

		JLabel penanceRunnerLabel = makePenanceCount(1, 5);
		penanceRunnerLabel.setIcon(makePenanceIcon("penance_runner"));
		waveDetails.add(penanceRunnerLabel);

		JLabel penanceHealerLabel = makePenanceCount(1, 5);
		penanceHealerLabel.setIcon(makePenanceIcon("penance_healer"));
		waveDetails.add(penanceHealerLabel);

		return waveDetails;
	}

	private JShadowedLabel makePenanceCount(int currentCount, int totalCount) {
		JShadowedLabel count = new JShadowedLabel( currentCount + "/" + totalCount);
		count.setFont(FontManager.getRunescapeSmallFont());
		count.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		count.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		return count;
	}

	private ImageIcon makePenanceIcon(String name) {
		BufferedImage image = ImageUtil.getResourceStreamFromClass(getClass(), "/" + name + ".png");
		BufferedImage resizedImage = ImageUtil.resizeImage(image, (int) (image.getWidth() * 0.04), (int) (image.getHeight() * 0.04));
		return new ImageIcon(resizedImage);
	}

	private JLabel makeRoleIcon() {
		JLabel uiIcon = new JLabel();
		uiIcon.setBorder(new EmptyBorder(0, 0, 0, 5));
		String roleIcon = "/" + wave.getRole().getName().toLowerCase() + "_xl.png";
		uiIcon.setIcon(new ImageIcon(ImageUtil.getResourceStreamFromClass(getClass(), roleIcon)));
		uiIcon.setMinimumSize(ROLE_ICON_SIZE);
		uiIcon.setMaximumSize(ROLE_ICON_SIZE);
		uiIcon.setPreferredSize(ROLE_ICON_SIZE);
		uiIcon.setHorizontalAlignment(JLabel.CENTER);
		uiIcon.setToolTipText(wave.getRole().getName());
		return uiIcon;
	}

	private void switchPanel()
	{
		this.showingFace = !this.showingFace;
		cardLayout.show(container, showingFace ? FACE_CARD : DETAILS_CARD);
	}

	void setState(WaveState state) {
		/*switch (state) {
			case IN_PROGRESS:
				setBorder(ORANGE_BORDER);
				break;
			case FINISHED:
				setBorder(GREEN_PADDED_BORDER);
				JShadowedLabel uiPoints = new JShadowedLabel( wave.getRolesPoints()[wave.getRole().ordinal()] + " points");
				uiPoints.setFont(FontManager.getRunescapeSmallFont());
				uiPoints.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
				waveInfo.add(uiPoints);
				repaint();
				break;
			case CANCELLED:
				setBorder(RED_PADDED_BORDER);
				break;
		}*/
	}

	public void updatePanel() {
		timerLabel.update();
	}
}
