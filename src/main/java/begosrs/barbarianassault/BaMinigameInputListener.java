package begosrs.barbarianassault;

import net.runelite.client.input.KeyListener;

import javax.inject.Inject;
import java.awt.event.KeyEvent;

public class BaMinigameInputListener implements KeyListener
{
	private static final int HOTKEY = KeyEvent.VK_CONTROL;

	@Inject
	private BaMinigamePlugin plugin;

	@Inject
	private BaMinigameConfig config;

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == HOTKEY)
		{
			plugin.onTeammatesHealthHotkeyChanged(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == HOTKEY)
		{
			plugin.onTeammatesHealthHotkeyChanged(false);
		}
	}
}
