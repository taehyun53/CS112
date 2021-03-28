/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author: Taehyun Yoon
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {
        this.collageDimension = 4;
        this.tileDimension = 100;
        this.original = new Picture(filename);
        this.collage = new Picture(this.tileDimension*this.collageDimension, this.tileDimension*this.collageDimension);
        for (int col = 0; col < this.tileDimension*this.collageDimension; col++) {
            for (int row = 0; row < this.tileDimension*this.collageDimension; row++) {
                int currentCol = (col*this.original.width()) / (this.collageDimension*this.tileDimension);
                int currentRow = (row*this.original.height()) / (this.collageDimension*this.tileDimension);
                this.collage.set(col, row, original.get(currentCol, currentRow));
            }
        }

         // WRITE YOUR CODE HERE
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {
        this.collageDimension = cd;
        this.tileDimension = td;
        this.original = new Picture(filename);
        this.collage = new Picture(this.tileDimension*this.collageDimension, this.tileDimension*this.collageDimension);
        for (int col = 0; col < this.tileDimension*this.collageDimension; col++) {
            for (int row = 0; row < this.tileDimension*this.collageDimension; row++) {
                int currentCol = (col*this.original.width()) / (this.collageDimension*this.tileDimension);
                int currentRow = (row*this.original.height()) / (this.collageDimension*this.tileDimension);
                this.collage.set(col, row, original.get(currentCol, currentRow));
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {

	    return this.collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {

	    return this.tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {

	    return this.original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {

	    return this.collage;
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {

	    this.original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {

	    this.collage.show();
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile(String filename,  int collageCol, int collageRow) {
	    Picture thisPicture = new Picture(filename);
        for (int col = collageCol*this.tileDimension; col < (collageCol+1)*this.tileDimension; col++) {
            for (int row = collageRow*this.tileDimension; row < (collageRow+1)*this.tileDimension; row++) {
                int currentCol = ((col-collageCol*this.tileDimension)*thisPicture.width()) / this.tileDimension;
                int currentRow = ((row-collageRow*this.tileDimension)*thisPicture.height()) / this.tileDimension;
                this.collage.set(col, row, thisPicture.get(currentCol, currentRow));
            }
        }
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {
    	for (int i = 0; i < this.collageDimension; i++) {
    		for (int j = 0; j < this.collageDimension; j++) {
    			for (int col = i*this.tileDimension; col < (i+1)*this.tileDimension; col++) {
    	            for (int row = j*this.tileDimension; row < (j+1)*this.tileDimension; row++) {
    	                int currentCol = ((col-i*this.tileDimension)*this.original.width()) / (this.tileDimension);
    	                int currentRow = ((row-j*this.tileDimension)*this.original.height()) / (this.tileDimension);
    	                this.collage.set(col, row, original.get(currentCol, currentRow));
    	            }
    	        }
    		}
    	}
        
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see CS111 Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        for (int col = collageCol*this.tileDimension; col < collageCol*this.tileDimension+this.tileDimension; col++) {
            for (int row = collageRow*this.tileDimension; row < collageRow*this.tileDimension+this.tileDimension; row++) {
                Color originalColor = this.collage.get(col, row);
                Color newColor;
                if(component == "red") {
                    newColor = new Color(originalColor.getRed(), 0, 0);
                } else if(component == "green") {
                    newColor = new Color(0, originalColor.getGreen(), 0);
                } else {
                    newColor = new Color(0, 0, originalColor.getBlue());
                }
                this.collage.set(col, row, newColor);
            }
        }
    }
    

    private double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void grayscaleTile (int collageCol, int collageRow) {

	    // WRITE YOUR CODE HERE
    	
        for (int col = collageCol*this.tileDimension; col < collageCol*this.tileDimension+this.tileDimension; col++) {
            for (int row = collageRow*this.tileDimension; row < collageRow*this.tileDimension+this.tileDimension; row++) {
                Color originalColor = this.collage.get(col, row);
                int y = (int) (Math.round(intensity(originalColor)));
                Color gray = new Color(y, y, y);
                this.collage.set(col, row, gray);
            }
        }
    }

    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {

    	ArtCollage art = new ArtCollage(args[0], 200, 2);
    	art.makeCollage();
        
    }
}