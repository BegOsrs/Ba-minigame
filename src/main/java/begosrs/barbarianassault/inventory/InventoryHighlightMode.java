package begosrs.barbarianassault.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InventoryHighlightMode
{
	OVERLAY("Overlay"),
	OUTLINE("Outline"),
	DISABLED("Disabled");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
