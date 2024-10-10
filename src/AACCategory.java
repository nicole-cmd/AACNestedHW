import java.security.KeyPair;
import java.util.NoSuchElementException;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import edu.grinnell.csc207.util.AssociativeArray;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Nicole Gorrell
 *
 */
public class AACCategory implements AACPage {

	String catName;
	AssociativeArray<String, String> map;
	int contents;
	
	/**
	 * Creates a new empty category with the given name
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
		this.catName = name;
		this.map = null; 
		this.contents = 0;
	}
	
	/**
	 * Adds the image location, text pairing to the category
	 * @param imageLoc the location of the image
	 * @param text the text that image should speak
	 */
	public void addItem(String imageLoc, String text) {
		try {
		this.map.set(imageLoc, text);
		this.contents++;
		} catch(Exception NullKeyException) {
			new edu.grinnell.csc207.util.NullKeyException("Image location is null.");
		} // try/catch
	}

	/**
	 * Returns an array of all the images in the category
	 * @return the array of image locations; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		String[] images = {};

		for(int i = 0; i < this.contents; i++) {
			// images[i] = this.map[i];
		} // for
		return new String[] { "img/food/icons8-french-fries-96.png", "img/food/icons8-watermelon-96.png" }; // STUB
	}

	/**
	 * Returns the name of the category
	 * @return the name of the category
	 */
	public String getCategory() {
		return "food";  // STUB
	}

	/**
	 * Returns the text associated with the given image in this category
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws NoSuchElementException if the image provided is not in the current
	 * 		   category
	 */
	public String select(String imageLoc) {
		return "television";  // STUB
	}

	/**
	 * Determines if the provided images is stored in the category
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		return false;
	}
}
