package simpleproject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.github.lalyos.jfiglet.FigletFont;
import com.google.common.collect.Lists;

@Component
public class TheASCIIImageGame implements Game {

	@Value("${itemType}")
	String itemType;
	
	@Value("${trials}")
	int trials;

	@Autowired
	Image2AsciiTransformer transformer;

	public void partida() {
		Map<String, List<String>> alternatives = generateAlternatives(itemType);
		List<String> images = Lists.newArrayList(alternatives.keySet());
		List<Integer> sizes = Lists.newArrayList(30, 20, 10, 6, 3, 1);
		Random random = new Random();
		int index=random.nextInt(images.size());
		String imageName = images.get(index);

		Scanner scanner = new Scanner(System.in);
		String name = null;
		for (int i=0;i<sizes.size() && i<trials;i++) {
			System.out.println(transformer.transform(itemType+"/"+imageName, sizes.get(i)));
			System.out.println("Please guess the name of the " + itemType + " in the image:");
			name = scanner.nextLine();
			if (alternatives.get(imageName).contains(name)) {
				System.out.println(FigletFont.convertOneLine("You Win!  :-)"));
				System.out.println(FigletFont.convertOneLine("It is  " + alternatives.get(imageName).get(0)));
				break;
			} else if (i != Math.min(trials,sizes.size())-1) {
				System.out.println(FigletFont.convertOneLine("Ouch! Try again!  :-S"));
			} else {
				System.out.println(FigletFont.convertOneLine("You loose! :'-("));
				System.out.println(FigletFont.convertOneLine("It is  " + alternatives.get(imageName).get(0)));
				break;
			}
		}

	}

	/**
	 * 
	 * 
	 * @return map between image path and item names.
	 */
	private Map<String, List<String>> generateAlternatives(String itemType) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		String dir = "src.main.resources.";			
		String resourcePath=itemType + ".properties";
		Resource r = new ClassPathResource(resourcePath);
		if(!r.exists())
			r=new ClassPathResource(dir+itemType+".properties");
		Properties p = new Properties();
		try {
			p.load(r.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> values;
		for (Entry<Object, Object> property : p.entrySet()) {
			values = Lists.newArrayList(property.getValue().toString().split(","));
			result.put(property.getKey().toString(), values);
		}

		return result;
	}

}
