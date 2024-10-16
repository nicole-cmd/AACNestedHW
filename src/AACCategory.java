import java.security.KeyPair;
import java.util.NoSuchElementException;

import javax.speech.EngineException;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import edu.grinnell.csc207.util.AssociativeArray;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Nicole Gorrell
 *
 */
public class AACCategory extends AssociativeArray<String, String> implements AACPage {
	// +--------+------------------------------------------------------
	// | Fields |
	// +--------+

	String catName; // category name
	AssociativeArray<String, String> map; // overall list mapping added images
	String[] keyList; // list of image locations within a category

	// +--------------+------------------------------------------------
	// | Constructors |
	// +--------------+

	/**
	 * Creates a new empty category with the given name
	 * 
	 * @param name the name of the category
	 */
	public AACCategory(String name) {
		this.catName = name;
		this.map = new AssociativeArray<String, String>(); // uninitialized
		this.keyList = null;
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

		// add key to keyList to keep track of how many image locations we add
		this.keyList[this.keyList.length - 1] = imageLoc;

	} // addItem(String, String)

	/**
	 * Returns an array of all the images in the category
	 * 
	 * @return the array of image locations; if there are no images,
	 *         it should return an empty array
	 */
	public String[] getImageLocs() {
		return this.keyList;
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
		// cycle through String array to see if there is a matching image location
		for (int i = 0; i < this.map.size(); i++) { 
			if (this.keyList[i] == imageLoc) {
				return true;
			} // if
		} // for
		return false; // if key is not there/key is null
	} // hasImage(String)
} // class AACCategory
