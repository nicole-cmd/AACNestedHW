import java.util.NoSuchElementException;

import javax.speech.EngineException;

import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KVPair;
import edu.grinnell.csc207.util.KeyNotFoundException;

/**
 * Creates a set of mappings of an AAC that has two levels,
 * one for categories and then within each category, it has
 * images that have associated text to be spoken. This class
 * provides the methods for interacting with the categories
 * and updating the set of images that would be shown and handling
 * an interactions.
 * 
 * @author Catie Baker & Nicole Gorrell
 *
 */
public class AACMappings implements AACPage {
	// +--------+------------------------------------------------------
	// | Fields |
	// +--------+

	AACCategory current; // keep track of current category
	AssociativeArray<String, AACCategory> mapToCat; // map (file)names to categories 

	// gets wrapped, what will perform most method operations
	AssociativeArray item;

	// +--------------+------------------------------------------------
	// | Constructors |
	// +--------------+

	/**
	 * Creates a set of mappings for the AAC based on the provided
	 * file. The file is read in to create categories and fill each
	 * of the categories with initial items. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * @param filename the name of the file that stores the mapping information
	 */
	public AACMappings(String filename) {
		this.current = new AACCategory(filename);
		this.mapToCat = new AssociativeArray<String, AACCategory>(); // (file)names to categories
		this.item = new AssociativeArray<String, AACCategory>();
	} // AACMappings(String)
	
	// +---------+--------------------------------------------
	// | Methods |
	// +---------+

	/**
	 * Given the image location selected, it determines the action to be
	 * taken. This can be updating the information that should be displayed
	 * or returning text to be spoken. If the image provided is a category, 
	 * it updates the AAC's current category to be the category associated 
	 * with that image and returns the empty string. If the AAC is currently
	 * in a category and the image provided is in that category, it returns
	 * the text to be spoken.
	 * @param imageLoc the location where the image is stored
	 * @return if there is text to be spoken, it returns that information, otherwise
	 * it returns the empty string
	 * @throws NoSuchElementException if the image provided is not in the current 
	 * category
	 */
	public String select(String imageLoc) throws NoSuchElementException {
		try {
			if (this.mapToCat.hasKey(imageLoc)) {
				this.current.catName = imageLoc;
				return this.current.map.get(imageLoc); // uh
			} // if
		} catch (Exception KeyNotFoundException) {
			// do nothing
		} // try/catch
		
		try {
			if (this.current.hasImage(imageLoc)) {
				return this.current.select(imageLoc);
			} // if
		} catch (Exception NoSuchElementException) {
			// do nothing
		} // try/catch

		// so would this be more general, to work on both directory and category levels?
		try {
			return (String) this.item.select(imageLoc);
		} catch (Exception KeyNotFoundException) {
			// do nothing
		} // try/catch

		throw new NoSuchElementException("The provided image is not in this category.");
	} // select(String)
	
	/**
	 * Provides an array of all the images in the current category
	 * @return the array of images in the current category; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		return this.current.getImageLocs();
	} // getImageLocs()
	
	
	/**
	 * Resets the current category of the AAC back to the default
	 * category
	 */
	public void reset() {
		this.current.catName = "";
		return;
	} // reset()
	
	
	/**
	 * Writes the ACC mappings stored to a file. The file is formatted as
	 * the text location of the category followed by the text name of the
	 * category and then one line per item in the category that starts with
	 * > and then has the file name and text of that image
	 * 
	 * for instance:
	 * img/food/plate.png food
	 * >img/food/icons8-french-fries-96.png french fries
	 * >img/food/icons8-watermelon-96.png watermelon
	 * img/clothing/hanger.png clothing
	 * >img/clothing/collaredshirt.png collared shirt
	 * 
	 * represents the file with two categories, food and clothing
	 * and food has french fries and watermelon and clothing has a 
	 * collared shirt
	 * 
	 * @param filename the name of the file to write the
	 * AAC mapping to
	 */
	public void writeToFile(String filename) {
		// AACCategory look = this.mapToCat.get(filename);

		// filename += look.keyList; //what is the category location...
		// filename += " ";
		// filename += look.getCategory();
		// filename += '\n';
		// filename += "> ";

		// look.getImageLocs();
		// for (int i = 0; i < this.mapToCat.size(); i++) {
		// 	filename += this.mapToCat.val.keyList[i];
		// 	filename += " ";
		// 	filename += this.mapToCat.select(filename);
		// } // for
	} // writeToFile(String)
	
	/**
	 * Adds the mapping to the current category (or the default category if
	 * that is the current category)
	 * @param imageLoc the location of the image
	 * @param text the text associated with the image
	 */
	public void addItem(String imageLoc, String text) {
		try {
			if (this.current.catName == "") {
				this.item.set(imageLoc, text);
			} // if
		} catch (Exception KeyNotFoundException) {
			// do nothing
		} // try/catch
		
		return;
	} // addItem(String, String)


	/**
	 * Gets the name of the current category
	 * @return returns the current category or the empty string if 
	 * on the default category
	 */
	public String getCategory() {
		return this.current.catName;
	} // getCategory()


	/**
	 * Determines if the provided image is in the set of images that
	 * can be displayed and false otherwise
	 * @param imageLoc the location of the category
	 * @return true if it is in the set of images that
	 * can be displayed, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		if (this.item.hasImage(imageLoc)) {
			return true;
		} // if
		
		return false;
	} // hasImage(String)
}
