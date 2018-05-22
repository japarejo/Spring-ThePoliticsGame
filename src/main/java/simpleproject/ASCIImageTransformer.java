package simpleproject;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import io.korhner.asciimg.image.AsciiImgCache;
import io.korhner.asciimg.image.character_fit_strategy.ColorSquareErrorFitStrategy;
import io.korhner.asciimg.image.character_fit_strategy.StructuralSimilarityFitStrategy;
import io.korhner.asciimg.image.converter.AsciiToImageConverter;
import io.korhner.asciimg.image.converter.AsciiToStringConverter;


public class ASCIImageTransformer implements Image2AsciiTransformer {

	public String transform(String file, int size) {
		BufferedImage img=loadImage(file);
		return showImage(img,size);
	}
	
	public BufferedImage loadImage(String file) { //
		String dir="src.main.resources.";
		File f=new File(dir);
		if(!f.exists())
			dir="";
		Resource r=new ClassPathResource(file);
		BufferedImage portraitImage = null;		
		try {
			//f=new File(dir+file);
			//System.out.println("Loading image  from:"+dir+file);
			portraitImage = ImageIO.read(r.getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return portraitImage;
	}

	public String showImage(BufferedImage img, int size) {

		// initialize cache
		AsciiImgCache cache = AsciiImgCache.create(new Font("Courier",
				Font.BOLD, size));

		// initialize converters
		AsciiToImageConverter imageConverter = new AsciiToImageConverter(cache,
				new ColorSquareErrorFitStrategy());
		AsciiToStringConverter stringConverter = new AsciiToStringConverter(
				cache, new StructuralSimilarityFitStrategy());

		// image output
		// ImageIO.write(imageConverter.convertImage(portraitImage), "png",
		// new File("ascii_art.png"));
		// string converter, output to console
		StringBuffer result = stringConverter.convertImage(img);		
		return result.toString();
	}


}
