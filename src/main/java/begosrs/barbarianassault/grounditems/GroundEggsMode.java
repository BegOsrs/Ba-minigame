package begosrs.barbarianassault.grounditems;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroundEggsMode
{
	CALLED("Called"),
	ALL("All"),
	DISABLED("Disabled");

	private final String name;

	@Override
	public String toString()
	{
		return name;
	}
}
