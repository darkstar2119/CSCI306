package clueGame;

import java.util.*;

public class Board {

		ArrayList<BoardCell> cells;
		Map<Character, String> rooms;
		int numRows;
		int numColumns;
		
		public void loadConfigFiles() {
			
		}
		
		public int calcIndex() {
			int test = 0;
			return test;
		}
		
		public RoomCell GetRoomCellAt(int row, int col) {
			RoomCell test = new RoomCell();
			return test;
		}

		public ArrayList<BoardCell> getCells() {
			return cells;
		}

		public Map<Character, String> getRooms() {
			return rooms;
		}

		public int getNumRows() {
			return numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}
		
		
}
