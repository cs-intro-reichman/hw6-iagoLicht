import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {

		//// Hide / change / add to the testing code below, as needed.

		// Tests the reading and printing of an image:
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);

		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/**
	 * Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file.
	 */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];

		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < numCols; c++) {
				int R = in.readInt();
				int G = in.readInt();
				int B = in.readInt();
				image[r][c] = new Color(R, G, B);
			}

		}
		return image;

	}

	// Prints the RGB values of a given color.
	private static void print(Color c) {
		System.out.print("(");
		System.out.printf("%3s,", c.getRed()); // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
		System.out.printf("%3s", c.getBlue()); // Prints the blue component
		System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting
	// image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}

	}

	/**
	 * Returns an image which is the horizontally flipped version of the given
	 * image.
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;

		Color[][] flippedHorizon = new Color[numRows][numCols];
		for (int r = 0; r < image.length; r++) {
			for (int c = 0; c < image[0].length; c++) {
				flippedHorizon[r][numCols - 1 - c] = image[r][c];
			}
		}
		return flippedHorizon;
	}

	/**
	 * Returns an image which is the vertically flipped version of the given image.
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;

		Color[][] flippedVertical = new Color[numRows][numCols];
		for (int c = 0; c < numCols; c++) {
			for (int r = 0; r < numRows; r++) {
				flippedVertical[r][c] = image[numRows - r - 1][c];
			}
		}
		return flippedVertical;
	}

	// Computes the luminance of the RGB values of the given pixel, using the
	// formula
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object
	// consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		int grey = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		Color greyPixel = new Color(grey, grey, grey);
		return greyPixel;
	}

	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int row = image.length;
		int cols = image[0].length;
		Color[][] greyArray = new Color[row][cols];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < cols; j++) {
				greyArray[i][j] = luminance(image[i][j]);

			}

		}
		return greyArray;
	}

	/**
	 * Returns an image which is the scaled version of the given image.
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int h0 = image.length;
		int w0 = image[0].length;
		int h = width;
		int w = height;
		double scaleW = w0 / w;
		double scaleH = h0 / h;
		Color[][] scaledImage = new Color[w][h];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				double ii = i * scaleH;
				double jj = j * scaleW;
				scaledImage[i][j] = image[(int) jj][(int) ii];
			}
		}

		return scaledImage;
	}

	/**
	 * Computes and returns a blended color which is a linear combination of the two
	 * given
	 * colors. Each r, g, b, value v in the returned color is calculated using the
	 * formula
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r,
	 * g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int R1 = c1.getRed();
		int G1 = c1.getGreen();
		int B1 = c1.getBlue();
		int R2 = c2.getRed();
		int G2 = c2.getGreen();
		int B2 = c2.getBlue();
		int newR = (int) (alpha * R1 + (1 - alpha) * R2);
		int newG = (int) (alpha * G1 + (1 - alpha) * G2);
		int newB = (int) (alpha * B1 + (1 - alpha) * B2);
		Color c3 = new Color(newR, newG, newB);

		return c3;
	}

	/**
	 * Cosntructs and returns an image which is the blending of the two given
	 * images.
	 * The blended image is the linear combination of (alpha) part of the first
	 * image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int numCols = image1[0].length;
		int numRows = image1.length;
		// Creates the flipped image array
		Color[][] newImage = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				newImage[i][j] = blend(image1[i][j], image2[i][j], alpha);

			}
		}
		return newImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int cols = source[0].length;
		int rows = source.length;
		double alpha = 0;
		Color[][] newImage = new Color[rows][cols];
		Color[][] image2 = new Color[rows][cols];
		image2 = scaled(target, cols, rows);
		for (int i = 0; i < n; i++) {
			alpha = (double) (n - i) / n;
			newImage = blend(source, image2, alpha);

			Runigram.display(newImage);
			StdDraw.pause(100);
		}
	}

	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		// Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor(image[i][j].getRed(),
						image[i][j].getGreen(),
						image[i][j].getBlue());
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}
