package game.ui.window.menus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.ui.window.BlankPanel;
import game.ui.window.GameScreen;
import game.ui.window.GameWindow;
import game.ui.window.GraphicsPane;

public class InventoryMenu implements GraphicsPane {
	private GameScreen game;
	private BlankPanel panel;

	//the draw able space for the inventory menu
	private Rectangle frame;
	private int width = 500;
	private int startX;
	private int startY = GameWindow.FRAME_HEIGHT/9;

	//inventory grid
	private int gridX;
	private int gridY;
	private int gridSize;
	private int gap = 20;
	private int numbCol = 5;
	private int numbRow = 3;

	private Color backColor;
	private BufferedImage characterImage;
	private Rectangle characterFrame;
	int selectedGrid = -1;

	/**
	 *The constructor for the InventoryMenu
	 * */
	public InventoryMenu(BlankPanel panel, GameScreen game){
		this.panel = panel;
		this.game = game;
		this.backColor = new Color(0f,0f,0f,0.5f);

		this.startX = (GameWindow.FRAME_WIDTH/2) - (width/2);//places the frame in the middle of the screen
		this.frame = new Rectangle(startX, startY, width, GameWindow.FRAME_HEIGHT - (startY*2));//creates the frame as a rectangle

		this.gridX = startX + 20;//set the start of the grid
		this.gridY = (int) (startY + frame.height/2);//start the grid half way down the frame
		this.gridSize = (int) ((frame.getWidth() - (gap*2))/numbCol);//creates a grid that fits within the frame

		setCharacterInventory();
		loadImages();
	}


	/**
	 * draws the grid on the screen representing the inventory
	 * changes the grid color if one of the squares is selected
	 * */
	public void drawInventoryGrid(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		//modify the stroke size to 4
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));

		int x = gridX;
		int y = gridY;

		int curGrid = 0;//the number value of the grid square that is currently being drawn

		for(int i = 0; i < numbRow; i++ ){
			for(int j = 0; j < numbCol; j++){

				//if the current grid square is selected change the color
				if(curGrid == selectedGrid){
					g.setColor(new Color(0f,0f,0f,0.5f));
				}
				else{
					g.setColor(new Color(1f,1f,1f,0.5f));
				}
				//draw the current grid square
				g.fillRect(x, y, gridSize, gridSize);
				g.setColor(Color.black);
				g.drawRect(x, y, gridSize, gridSize);
				x +=gridSize;
				curGrid++;
			}
			x = gridX;//reset x
			y += gridSize;
		}
		g2d.setStroke(oldStroke);
	}


	/**
	 * Draws the character information part of the inventory screen
	 * on top of the game screen
	 * */
	private void drawCharactrInventory(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		//set the border size to 4
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(4));

		g2d.draw(characterFrame);
		g.setColor(new Color(1f,1f,1f,0.5f));
		g2d.fill(characterFrame);


		//reset the border size
		g2d.setStroke(oldStroke);
	}


	/**
	 *Draws the outside of the inventory menu on the game screen
	 * */
	public void drawFrame(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		//increase the border size to 4
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(4));

		//draw the inside of the frame
		g2d.setColor(backColor);
		g2d.fill(frame);

		//draw the border of the frame
		g2d.setColor(Color.black);
		g2d.draw(frame);

		//reset the border size
		g2d.setStroke(oldStroke);
	}

	public void setCharacterInventory(){
		int width = 200;
		int x = (GameWindow.FRAME_WIDTH/2) - (width/2);
		int y = startY+20;
		int height = gridY - 20 - y;

		characterFrame = new Rectangle(x, y, width, height);
	}


	/**
	 * Returns the number of the square in the
	 * grid of the inventory screen returns -1 if not on the grid
	 * */
	public int getGridClicked(int x, int y){
		int xStart = gridX;
		int yStart = gridY;

		int numbGrid = 0;//the number of the grid across then down

		for(int i = 0; i < numbRow; i++ ){
			for(int j = 0; j < numbCol; j++){
				Rectangle gridRec = new Rectangle(xStart,yStart,gridSize,gridSize);
				if(gridRec.contains(x,y)){//checks if the mouse press is inside the current grid square
					return numbGrid;
				}
				xStart += gridSize;
				numbGrid++;
			}
			xStart = gridX;
			yStart+=gridSize;
		}
		return -1;//no grid square selected
	}


	@Override
	public void render(Graphics g) {
		drawFrame(g);//draws the outside of the inventory
		drawCharactrInventory(g);//draws the character part of the inventory
		g.drawImage(characterImage, (int)characterFrame.getX(),(int)characterFrame.getY(),panel);

		g.setColor(Color.black);
		drawInventoryGrid(g);//draws the grid on the screen
	}


	@Override
	public void handleMouseMoved(MouseEvent e) {

		//sets the selected grid square -1 if none selected
		selectedGrid = getGridClicked(e.getX(), e.getY());

		//TODO check the rest of the menu
	}


	@Override
	public void handleMouseReleased(MouseEvent e) {
		// TODO send some info to the game
	}


	@Override
	public void keyPressed(String keyEvent) {
		if(keyEvent.equals("escape") || keyEvent.equals("inventory")){
			game.setMenu(null);//inventory menu no longer to be required to render so set to null in the gameScreen
		}
	}

	public void loadImages(){
		java.net.URL imagefile = InventoryMenu.class.getResource("resources/characterAlph.jpg");


		//load background image
		try {
			this.characterImage = ImageIO.read(imagefile);
			Image tempImage =  characterImage.getScaledInstance((int)characterFrame.getWidth(),(int)characterFrame.getHeight(), BufferedImage.SCALE_DEFAULT);//TODO change to the selected image by the user
			characterImage = new BufferedImage(tempImage.getWidth(null), tempImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

			//writes the temp image onto the bufferedimage
		    Graphics2D bGr = characterImage.createGraphics();
		    bGr.drawImage(tempImage, 0, 0, null);
		    bGr.dispose();

		} catch (IOException e) {
			System.out.println("failed reading image");
			e.printStackTrace();
		}
	}
}
