package begosrs.barbarianassault.attackstyle;

import begosrs.barbarianassault.api.widgets.BaWidgetInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttackStyleWidget
{
	ONE(BaWidgetInfo.COMBAT_STYLE_ONE, BaWidgetInfo.COMBAT_STYLE_ONE_ICON, BaWidgetInfo.COMBAT_STYLE_ONE_TEXT),
	TWO(BaWidgetInfo.COMBAT_STYLE_TWO, BaWidgetInfo.COMBAT_STYLE_TWO_ICON, BaWidgetInfo.COMBAT_STYLE_TWO_TEXT),
	THREE(BaWidgetInfo.COMBAT_STYLE_THREE, BaWidgetInfo.COMBAT_STYLE_THREE_ICON, BaWidgetInfo.COMBAT_STYLE_THREE_TEXT),
	FOUR(BaWidgetInfo.COMBAT_STYLE_FOUR, BaWidgetInfo.COMBAT_STYLE_FOUR_ICON, BaWidgetInfo.COMBAT_STYLE_FOUR_TEXT);

	private final BaWidgetInfo containerWidget;
	private final BaWidgetInfo iconWidget;
	private final BaWidgetInfo textWidget;

	@Getter
	private static final AttackStyleWidget[] attackStyles;

	static
	{
		attackStyles = new AttackStyleWidget[values().length];
		int i = 0;
		for (AttackStyleWidget attackStyleWidget : values())
		{
			attackStyles[i] = attackStyleWidget;
			i++;
		}
	}
}
