import java.security.KeyPair;
import java.util.NoSuchElementException;

import javax.speech.EngineException;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KVPair;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Nicole Gorrell
 *
 */
public class AACCategory extends KVPair<String, String> implements AACPage {
	// +--------+------------------------------------------------------
	// | Fields |
	// +--------+

	String catName; // category name
	AssociativeArray<String, String> map; // overall list mapping added images
	KVPair<String, String>[] list; // list of images within a category (each of which contain location and words)

	// +--------------+------------------------------------------------
	// | Constructors |
	// +--------------+

	/**
	 * Creates a new empty category with the given name
	 * 
	 * @param name the name of the category
	 */
	public AACCategory(String name, String key, String val) {
		super(key, val);
		this.catName = name;
		this.map = new AssociativeArray<String, String>(); // uninitialized
		this.list = null;
	} // AACCategory(String)

	// +---------+--------------------------------------------
	// | Methods |
	// +---------+

	/**
	 * Adds the image location, text pairing to the category
	 * 
	 * @param imageLoc the location of the image
	 * @param text     the text that image should speak
	 */
	public void addItem(String imageLoc, String text) {
		try {
			this.map.set(imageLoc, text);
		} catch (Exception NullKeyException) {
			new edu.grinnell.csc207.util.NullKeyException("Image location is null.");
		} // try/catch

		// add key/val pair to list to keep track of how many image/text maps we add
		this.list[this.list.length - 1].key = imageLoc;
		this.list[this.list.length - 1].val = text;

	} // addItem(String, Str ing)

	/**
	 * Returns an array of all the images in the category
	 * 
	 * @return the array of image locations; if there are no images,
	 *         it should return an empty array
	 */
	public String[] getImageLocs() {
		String[] images = {};

		for (int i = 0; i < this.map.size(); i++) {
			images[i] = this.list[i].key;
		} // for
		return images;
	} // getImageLocs()

	/**
	 * Returns the name of the category
	 * 
	 * @return the name of the category
	 */
	public String getCategory() {
		return this.catName;
	} // getCategory()

	/**
	 * Returns the text associated with the given image in this category
	 * 
	 * @param imageLoc the location of the image
	 * @return the text associated with the image
	 * @throws NoSuchElementException if the image provided is not in the current
	 *                                category
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		if (this.hasImage(imageLoc)) {
			try {
				return this.map.get(imageLoc);
			} catch (Exception KeyNotFoundException) {
				// Do nothing
			} // try/catch
		} // if
		throw new NoSuchElementException("This image is not in this category.");
	} // select(imageLoc)

	/**
	 * Determines if the provided images is stored in the category
	 * 
	 * @param imageLoc the location of the category
	 * @return true if it is in the category, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		for (int i = 0; i < this.map.size(); i++) { // cycles through array to see if there is a matching image location
			if (this.list[i].key == imageLoc) {
				return true;
			} // if
		} // for
		return false; // if key is not there/key is null
	} // hasImage(String)
} // class AACCategory
