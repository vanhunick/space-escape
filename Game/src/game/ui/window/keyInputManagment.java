package game.ui.window;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class keyInputManagment  implements KeyEventDispatcher{
	private BlankPanel panel;
	private static HashMap<String , Integer> keyMap;


	private static KeyEvent lastKeyEvent;

	private int escapeKey;

	public keyInputManagment(BlankPanel panel){
		this.panel = panel;

		//assign keys to the map
		keyMap = setUpkeys();
		this.escapeKey = KeyEvent.VK_ESCAPE;//set the escape key here so the user cannot change it
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {

		//set the last key event used by rebinding menu
		lastKeyEvent = e;

		//if a key is pressed
		if(e.getID() == KeyEvent.KEY_PRESSED){
			if(e.getKeyCode() == escapeKey)panel.keyPressed("escape");

			for(String key : keyMap.keySet() ){
				if(keyMap.get(key) == e.getKeyCode()){
					System.out.println(key);
					panel.keyPressed(key);
					return false;
				}
			}
			panel.keyPressed("unbound key");
		}
		return false;

	}

	/**
	 * Sets up the key values corrosponding to the actions
	 * */
	private HashMap<String,Integer> setUpkeys(){
		HashMap<String, Integer> tempKeyMap = new HashMap<String, Integer>();


		//place the keys into the map
		tempKeyMap.put("move up", KeyEvent.VK_W);//w
		tempKeyMap.put("move down", KeyEvent.VK_S);//s
		tempKeyMap.put("move right", KeyEvent.VK_D);//d
		tempKeyMap.put("move left", KeyEvent.VK_A);//a
		tempKeyMap.put("inventory", KeyEvent.VK_I);//i
		tempKeyMap.put("interact", KeyEvent.VK_F);//f

		return tempKeyMap;
	}


	/**
	 * Returns the keyMap
	 * */
	public static HashMap<String,Integer> getKeyMap(){
		return keyMap;
	}


	/**
	 * Returns the last key press from the user
	 * */
	public static KeyEvent getLastKeyEvent(){
		return lastKeyEvent;
	}


}
