package Classes;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class DebugHandler 
{
	private static BufferedImage view;
	private static int[] pixels;

	
	public DebugHandler() 
	{
		view = new BufferedImage(100, 800, BufferedImage.TYPE_INT_RGB);

		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}
	
	public static void render(Graphics graphics)
	{
		//Draw background
		int colour = 0x303080;
		for (int index = 0; index < pixels.length; index++) {
			pixels[index] = colour;
			}
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
		
		}
		
	
	}