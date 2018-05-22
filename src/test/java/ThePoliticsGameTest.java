import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import simpleproject.ASCIImageTransformer;
import simpleproject.Image2AsciiTransformer;
import simpleproject.TheASCIIImageGame;

public class ThePoliticsGameTest {

	@Test
	public void showImageTest() {
		String dir="src.main.resources.";
		File f=new File(dir);
		if(!f.exists())
			dir="";
		Image2AsciiTransformer game=new ASCIImageTransformer();
		String asciimage=game.transform("Zapatero.jpg", 10);
		assertFalse(asciimage==null);
		assertFalse(asciimage.equals(""));
		System.out.println(asciimage);
	}

}
