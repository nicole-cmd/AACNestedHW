import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.*;

import javax.speech.EngineException;

import edu.grinnell.csc207.util.*;

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

	String file; // store the filename when constructing a new AACMappings
	AACCategory topDirectory; // the home directory
	AACCategory current; // keep track of current category
	AssociativeArray<String, AACCategory> mapToCat; // map (file)names to categories 

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
	 * @param name the name of the category
	 */
	public AACMappings(String filename) {
		this.file = filename; // initializes to home directory
		this.mapToCat = new AssociativeArray<String, AACCategory>(); // (file)names to categories
		this.topDirectory = new AACCategory("Home Directory");
		this.current = new AACCategory("Current Category");

		try {
			this.mapToCat.set(this.topDirectory.catName, this.topDirectory);
			this.mapToCat.set(this.current.catName, this.current);
		} catch (Exception NullKeyException) {
			new NullKeyException("Cannot set pairs in the map associative array with null keys.");
		} // try/catch		
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
		String text = "";

		try {
			// if imageLoc is in the current category
			if (this.mapToCat.get(this.current.catName).hasImage(imageLoc)) {
				text = this.mapToCat.get(this.current.catName).select(imageLoc);
			} // if

			// else - the image could be a category
			String[] list = this.mapToCat.get(this.topDirectory.catName).getImageLocs(); // create a list of all image locations in this top directory

			for (String s : list) {
				if (imageLoc.equals(s)) {
					// update the current category to be that image's category
					this.mapToCat.set(this.current.catName, this.mapToCat.get(imageLoc));
				} // if
			} // for
		} catch (Exception NoSuchElementException) {
			new NoSuchElementException("The image location cannot be sourced.");
		} // try/catch
		
		return text;
	} // select(String)
	
	/**
	 * Provides an array of all the images in the current category
	 * @return the array of images in the current category; if there are no images,
	 * it should return an empty array
	 */
	public String[] getImageLocs() {
		String[] imageList = {};

		try {
			imageList = this.mapToCat.get(this.current.catName).getImageLocs();
		} catch (Exception KeyNotFoundException) {
			new KeyNotFoundException("Could not find the current category.");
		} // try/catch

		return imageList;
	} // getImageLocs()
	
	/**
	 * Resets the current category of the AAC back to the default
	 * category
	 */
	public void reset() {
		try {
			this.mapToCat.set("", this.current);
		} catch (Exception NullKeyException) {
			new NullKeyException("Could not reset the current category.");
		} // try/catch 

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
		File outFile = new File(filename);
		if (outFile.exists()) {
			/* do something */
		} else {
			/* continue */
		}
	} // writeToFile(String)
	
	/**
	 * Adds the mapping to the current category (or the default category if
	 * that is the current category)
	 * @param imageLoc the location of the image
	 * @param text the text associated with the image
	 */
	public void addItem(String imageLoc, String text) {
		try {
			if (this.mapToCat.get(this.current.catName).getCategory() == "") { 
				this.mapToCat.get(this.topDirectory.catName).addItem(imageLoc, text);
			} // if

			// else - the current category isn't the home directory
			this.mapToCat.get(this.current.catName).addItem(imageLoc, text);
		} catch (Exception KeyNotFoundException) {
			new KeyNotFoundException("Could not check category levels.");
		} // try/catch
		
		return;
	} // addItem(String, String)


	/**
	 * Gets the name of the current category
	 * @return returns the current category or the empty string if 
	 * on the default category
	 */
	public String getCategory() {
		String currentName = "";
		try {
			return this.mapToCat.get(this.current.catName).getCategory();
		} catch (Exception KeyNotFoundException) {
			new KeyNotFoundException("Failed to find current category name.");
		} // try/catch

		return currentName;
	} // getCategory()


	/**
	 * Determines if the provided image is in the set of images that
	 * can be displayed and false otherwise
	 * @param imageLoc the location of the category
	 * @return true if it is in the set of images that
	 * can be displayed, false otherwise
	 */
	public boolean hasImage(String imageLoc) {
		try {
			if (this.mapToCat.get(this.current.catName).hasImage(imageLoc) || 
				this.mapToCat.get(this.topDirectory.catName).hasImage(imageLoc)) {
				return true;
			} // if
		} catch (Exception KeyNotFoundException) {
			new KeyNotFoundException("Could not check category levels.");
		} // try/catch

		return false;
	} // hasImage(String)
} // class AACMappings
