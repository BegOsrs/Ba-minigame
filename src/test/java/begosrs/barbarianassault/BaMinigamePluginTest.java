package begosrs.barbarianassault;

import begosrs.barbarianassault.timer.Timer;
import java.util.regex.Pattern;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BaMinigamePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BaMinigamePlugin.class);
		RuneLite.main(args);
	}

	@Test
	public void testRegex()
	{
		String text = "(50) Defender";
		text = text.replaceAll("\\(.*\\) ", "");
		assertEquals("Defender", text);

		text = "(5) 00:00";
		text = text.replaceAll("\\(.*\\) ", "");
		assertEquals("00:00", text);

		text = " , Red egg  ,     Green egg  ,  Blue egg  ,Yellow egg,";
		text = text.replaceAll("\\s*,*\\s*(?i)" + Pattern.quote("red egg") + "\\s*,*\\s*", ",");
		text = trimComma(text);
		assertEquals("Green egg  ,  Blue egg  ,Yellow egg", text);

		text = text.replaceAll("\\s*,*\\s*(?i)" + Pattern.quote("blue egg") + "\\s*,*\\s*", ",");
		text = trimComma(text);
		assertEquals("Green egg,Yellow egg", text);

		text = text.replaceAll("\\s*,*\\s*(?i)" + Pattern.quote("yellow egg") + "\\s*,*\\s*", ",");
		text = trimComma(text);
		assertEquals("Green egg", text);
	}

	private String trimComma(String text)
	{
		if (text.startsWith(","))
		{
			text = text.substring(1);
		}
		if (text.endsWith(","))
		{
			text = text.substring(0, text.length() - 1);
		}

		return text;
	}

	@Test
	public void testTimer()
	{
		final Timer timer = new Timer();

		final Round round = new Round(1, timer);
		assertTrue(round.getTimer().getRoundTime(false).toMillis() <= 1000);

		// 1st wave starts
		Wave wave = new Wave(null, 1, null, timer);
		assertTrue(wave.getTimeUntilCallChange() >= 29);
		assertTrue(wave.getTimer().getWaveTime().toMillis() <= 1000);

		sleep(5000);

		assertTrue(wave.getTimeUntilCallChange() <= 25);
		assertTrue(wave.getTimer().getWaveTime().toMillis() >= 5000);

		// 2nd wave starts
		timer.setWaveStartTime();
		wave = new Wave(null, 2, null, timer);
		assertTrue(wave.getTimeUntilCallChange() >= 29);
		assertTrue(wave.getTimer().getWaveTime().toMillis() <= 1000);
	}

	private void sleep(long ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException ignored)
		{
		}
	}

}
