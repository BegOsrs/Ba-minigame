package begosrs.barbarianassault.ui;

import begosrs.barbarianassault.WaveState;
import begosrs.barbarianassault.timer.Timer;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TimerLabel extends JLabel {

	@Nullable
	private final Timer timer;
	@Setter
	private boolean active;

	TimerLabel(Timer timer, boolean active) {
		this.timer = timer;
		this.active = active;

		String waveTime = timer == null ? "--" : timer.getWaveTimeFormatted();
		setText(waveTime);
		setToolTipText("Elapsed time");
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setForeground(active ? ColorScheme.LIGHT_GRAY_COLOR.brighter() : ColorScheme.LIGHT_GRAY_COLOR.darker());
		setBorder(new EmptyBorder(0, 0, 0, 5));
		setFont(FontManager.getRunescapeSmallFont());
		setHorizontalAlignment(JLabel.CENTER);
	}

	public void update()
	{
		if (active && timer != null) {
			setText(timer.getWaveTimeFormatted());
		}
	}
}
