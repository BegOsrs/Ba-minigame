package begosrs.barbarianassault.ui;

import begosrs.barbarianassault.Role;
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class BaMinigamePointsPanel extends JPanel {

	private final Map<Role, JLabel> pointLabels;

	BaMinigamePointsPanel()
	{
		setLayout(new BorderLayout());
		pointLabels = new EnumMap<>(Role.class);

		add(makeLabel("Points"), BorderLayout.NORTH);
		add(makeRolesPanel(), BorderLayout.CENTER);
	}

	private JLabel makeLabel(String label)
	{
		final JLabel uiLabel = new JLabel(label);
		uiLabel.setFont(FontManager.getRunescapeSmallFont());
		uiLabel.setBorder(new EmptyBorder(0, 0, 4, 0));
		uiLabel.setForeground(Color.WHITE);
		return uiLabel;
	}

	private JPanel makeRolesPanel()
	{
		JPanel pointsPanel = new JPanel();
		pointsPanel.setLayout(new GridLayout(1, 4));
		pointsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		for (Role role : Role.values()) {
			pointsPanel.add(makeRolePanel(role));
		}
		return pointsPanel;
	}

	private JPanel makeRolePanel(Role role)
	{
		JLabel label = new JLabel();
		String roleName = role.getName();
		label.setToolTipText(role.getName() + " points");
		label.setFont(FontManager.getRunescapeSmallFont());
		label.setText(StringUtils.leftPad("--", 2));
		String roleIcon = "/" + roleName.toLowerCase() + ".png";
		label.setIcon(new ImageIcon(ImageUtil.getResourceStreamFromClass(getClass(), roleIcon)));
		label.setIconTextGap(4);

		JPanel points = new JPanel();
		points.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		points.setBorder(new EmptyBorder(2, 0, 2, 0));
		pointLabels.put(role, label);
		points.add(label);

		return points;
	}

}
