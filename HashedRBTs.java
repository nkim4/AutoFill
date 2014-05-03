package proj3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
/**
 * This class holds the kind of hashed array "table". This class also handles all the
 * Parsing of the input txt file and adds it into the table according to the letter
 * the word starts with.
 * @author Nicholas Kim 
 * Section: 03
 * date: 4/7/14
 */
public class HashedRBTs {
	// Initializing variables...
	private ArrayList<RedBlackTree<Partial>> table;
	private int size;
	private final static int UPPERCASE_TABLE_INDEX = 65;
	private final static int LOWERCASE_TABLE_INDEX = 71;

	/**
	 * Constructor of the class HashedRBTs. This constructor holds the RBTs in
	 * its table and holds the size of the table.
	 * @param length The length of the table.
	 */
	public HashedRBTs(int length) {
		size = length;
		table = new ArrayList<RedBlackTree<Partial>>(size);
	}
	/**
	 * This method helps determine what words go where in the table. This
	 * Method shifts the ACSII values of the first letter in the word
	 * to fit the tables's A-Z a-z format.
	 * @param word: The word you want to put into the table.
	 * @return returns the index of the table that you should put the word in.
	 */
	private int charToIndex(String word) {
		int index;
		if (word.charAt(0) > 90) {
			index = word.charAt(0) - LOWERCASE_TABLE_INDEX;
			return index;
		} else {
			index = word.charAt(0) - UPPERCASE_TABLE_INDEX;
			return index;
		}
	}
	/**
	 * This method reads the file and parses the file removing all unnecessary characters
	 * from the text. This method also sorts each word of the text file into the array
	 * and adds them into the RedBlackTree.
	 * @param filename the name of the file you want to read.
	 */
	public void fileReader(String filename) {

		for (int i = 0; i < size; i++) {
			table.add(new RedBlackTree<Partial>());
		}
		try {
			Scanner in = new Scanner(new FileReader(filename));

			while (in.hasNext()) {
				String line = in.nextLine();
				if (!line.equals("Empty tree")) {
					line = line.replaceAll("word|frequency|Node", "")
							.replaceAll("[^a-zA-Z0-9|^,]", "");
					String[] data = line.split(",");
					if (data[0].length() != 0) {
						String word = data[0];
						int freq = Integer.parseInt(data[1]);
						Partial adding = new Partial(new Node(word, freq));
						Partial check = table.get(charToIndex(word))
								.retreiveIfItContains(adding);
						if (check == null) {
							table.get(charToIndex(word)).insert(adding);
						} else {
							check.insertNodeIntoHeap(new Node(word, freq));
						}

					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Prints the root of all the elements in the table if there is an element
	 * in the table.
	 */
	public void printHashCountResults() {
		for (int i = 0; i < table.size(); i++) {
			table.get(i).printRoot();
		}
	}
	/**
	 * A simple getter method that returns the RedBlackTree at index "index".
	 * @param index the index of the RedBlackTree in table that you want.
	 * @return The RedBlackTree at the index in this object's table.
	 */
	public RedBlackTree<Partial> retreiveHashedRBTat(int index) {
		return table.get(index);
	}

}
