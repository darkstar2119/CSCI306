package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {

		ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
		Map<Character, String> rooms = new TreeMap<Character, String>();
		Map<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
		Set<BoardCell> targetSet = new HashSet<BoardCell>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		int numRows;
		int numColumns;
		int counter = 0;
		int firstSpot;
		
		public Board() {
			try {
				loadConfigFiles();
			} catch (BadConfigFormatException e) {
				System.out.println(e.getMessage());
			}
			this.calcAdjacencies();
		}
		
		public void loadConfigFiles() throws BadConfigFormatException {
			FileReader reader = null;
			try {
				reader = new FileReader("ClueBoardLegend.txt");	// use CR-ClueLegend.txt for Cyndi's jUnit tests
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			Scanner scan = new Scanner(reader);
			// populate legend map
			while(scan.hasNext()) {
				String wholeString = scan.nextLine();
				if (!wholeString.contains(", ")) {
					throw new BadConfigFormatException();
				}
				String[] stringArr = wholeString.split(", ");
				String character = stringArr[0];
				String room = stringArr[1];
				char c = character.charAt(0);
				rooms.put(c, room);
			}
			
			try {
				reader = new FileReader("ClueBoardLayout.csv");	// use CR-ClueLayout.csv for Cyndi's jUnit tests
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			scan = new Scanner(reader);
			// populate cell list
			int j = 0;
			while(scan.hasNext()) {
				String wholeString = scan.nextLine();
				if (!wholeString.contains(",")) {
					throw new BadConfigFormatException();
				}
				String[] strArr = wholeString.split(",");
				for(int i = 0; i < strArr.length; i++) {
					String str = strArr[i];
					if (str.equalsIgnoreCase("W")) {
						WalkwayCell wc = new WalkwayCell();
						wc.setCol(i);
						wc.setRow(j);
						wc.setBoardWidth(strArr.length);
						cells.add(wc);
					} else {
						RoomCell rc = new RoomCell();
						if (str.length() == 2) {
							char d = str.charAt(1);
							rc.setDoorDirection(d);
						}
						rc.setCol(i);
						rc.setRow(j);
						rc.setBoardWidth(strArr.length);
						rc.setRoom(str.charAt(0));
						cells.add(rc);
					}
				}
				numColumns = strArr.length;
				++j;
			}
			numRows = j;
		}
		
		public int calcIndex(int row, int col) {
			int index = (row*numColumns) + col;
			return index;
		}
		
		public RoomCell getRoomCellAt(int row, int col) {
			if (cells.get(this.calcIndex(row, col)).isRoom()) {
				return (RoomCell) cells.get(this.calcIndex(row, col));
			} else {
				return new RoomCell();
			}
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

		public BoardCell getCellAt(int calcIndex) {
			return cells.get(calcIndex);
		}

		public LinkedList<Integer> getAdjList(int calcIndex) {
			return map.get(calcIndex);
		}

		public void calcTargets(int calcIndex, int i) {
			Map<Integer, LinkedList<Integer>> mapc = new HashMap<Integer, LinkedList<Integer>>(map);	// create copy of map
			for (Integer target: mapc.get(calcIndex)) {
				if(!path.contains(calcIndex)) {	// add cell at calcIndex to path if not in path
					path.add(calcIndex);
				}
				System.out.println(calcIndex + "\t t " + target);
				if (path.size() >= i) {	// if number of steps (roll) has been reached, add the cell at target
					if (target != path.peekFirst() && !path.contains(target)) {
						targetSet.add(getCellAt(target));
						System.out.println("Added t " + target + "\tPeekFirst = " + path.peekFirst());
					}
				} else if (getCellAt(target).isDoorway()) {
					targetSet.add(getCellAt(target));
					System.out.println("Added t " + target + "\tPeekFirst = " + path.peekFirst());
				} else {
					if(!path.contains(target) && target != path.peekFirst()) {
						mapc.get(target).removeFirstOccurrence(calcIndex);
						calcTargets(target, i);
						mapc.get(target).add(calcIndex);
					} else {
						break;
					}
				}
				System.out.println("- " + path.peekLast());
				path.removeLast();
			}
		}

		public Set<BoardCell> getTargets() {
			Set<BoardCell> targetSet1 = new HashSet<BoardCell>(targetSet);
			targetSet = new HashSet<BoardCell>();
			counter = 0;
			calcAdjacencies();
			path.clear();
			return targetSet1;
		}
		
		public void calcAdjacencies() {
			// Map<Character, LinkedList<Integer>> roomAdj = new HashMap<Character, LinkedList<Integer>>();
			// find ROOM adjacencies
			/*
			 * If one would ever want to be able to exit a room from a different door, uncomment the code
			 * that is in this method, and comment the code with //*** around it
			 * for(int x=0; x < this.getNumColumns(); x++) {
				for(int y=0; y < this.getNumRows(); y++) {
					int index = calcIndex(y,x);
					if(this.getCellAt(index).isDoorway()) {
						int indexAdj = -1;
						if (this.getRoomCellAt(y, x).getDoorDirection() == RoomCell.DoorDirection.UP) {
							indexAdj = this.getCellAt(calcIndex(y-1,x)).getIndex();
						} else if (this.getRoomCellAt(y, x).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							indexAdj = this.getCellAt(calcIndex(y+1,x)).getIndex();
						} else if (this.getRoomCellAt(y, x).getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
							indexAdj = this.getCellAt(calcIndex(y,x+1)).getIndex();
						} else if (this.getRoomCellAt(y, x).getDoorDirection() == RoomCell.DoorDirection.LEFT) {
							indexAdj = this.getCellAt(calcIndex(y,x-1)).getIndex();
						}
						char initial = this.getRoomCellAt(y,x).getInitial();
						if(roomAdj.containsKey(initial)) {
							roomAdj.get(initial).add(indexAdj);
						} else {
							LinkedList<Integer> list = new LinkedList<Integer>();
							list.add(indexAdj);
							roomAdj.put(initial, list);
						}
					}
				}
			}
			*/
			// add adjacencies for all cells
			for(int x=0; x < this.getNumRows(); x++) {
				for(int y=0; y < this.getNumColumns(); y++) {
					int index = calcIndex(x,y);
					LinkedList<Integer> listPoint = new LinkedList<Integer>();
					if (this.getCellAt(index).isWalkway()) {	// make sure cell is a walkway
						if (x != 0 && (this.getCellAt(calcIndex(x-1, y)).isWalkway() ||
								this.getCellAt(calcIndex(x-1, y)).isDoorway())) { // add above cell
							listPoint.add(this.getCellAt(calcIndex(x-1, y)).getIndex());
						}
						if (y != 0 && (this.getCellAt(calcIndex(x, y-1)).isWalkway() ||
								this.getCellAt(calcIndex(x, y-1)).isDoorway())) { // add left cell
							listPoint.add(this.getCellAt(calcIndex(x, y-1)).getIndex());
						}
						if (x != this.getNumRows()-1 && (this.getCellAt(calcIndex(x+1, y)).isWalkway()
								|| this.getCellAt(calcIndex(x+1, y)).isDoorway())) { // add below cell
							listPoint.add(this.getCellAt(calcIndex(x+1, y)).getIndex());
						}
						if (y != this.getNumColumns()-1 && (this.getCellAt(calcIndex(x,y+1)).isWalkway()
								|| this.getCellAt(calcIndex(x,y+1)).isDoorway())) { // add right cell
							listPoint.add(this.getCellAt(calcIndex(x,y+1)).getIndex());
						}
					} else if (this.getCellAt(index).isDoorway()) {	//***
						if (this.getRoomCellAt(x, y).getDoorDirection() == RoomCell.DoorDirection.UP) {
							listPoint.add(this.getCellAt(calcIndex(x-1,y)).getIndex());
						} else if (this.getRoomCellAt(x, y).getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							listPoint.add(this.getCellAt(calcIndex(x+1,y)).getIndex());
						} else if (this.getRoomCellAt(x, y).getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
							listPoint.add(this.getCellAt(calcIndex(x,y+1)).getIndex());
						} else if (this.getRoomCellAt(x, y).getDoorDirection() == RoomCell.DoorDirection.LEFT) {
							listPoint.add(this.getCellAt(calcIndex(x, y-1)).getIndex());
						}	//***
						
						/*if (this.getCellAt(index).isRoom()) {	// check to see if room, find room adjacency list
						char initial = this.getRoomCellAt(y,x).getInitial();
						listPoint = roomAdj.get(initial);*/
					} else {	//***
						listPoint.clear();
					}	//***
					map.put(index, listPoint);	// add adjacency list for cell to map
				}
			}
			
		}
		
		/*public static void main(String [] args) {	// DEBUGGING!!!
			Board b = new Board();
			
			b.calcTargets(b.calcIndex(14,0), 6);
			/*Set<BoardCell> targets = b.getTargets();
			for (BoardCell t : targets) {
				int first = t.getIndex() / 23;
				int second = t.getIndex() % 23;
				System.out.println(first + " " + second + " <-----");
			}
			System.out.println("TargetSet Contents: ");
			Set<BoardCell> targets = b.getTargets();
			for (BoardCell t : targets) {
				System.out.println(t.getIndex());
			}
			LinkedList<Integer> ll = b.getAdjList(b.calcIndex(15, 2));
			System.out.println("BEFORE NEXT");
			for( int l : ll) {
				System.out.println(l);
			}/*
			b.calcTargets(b.calcIndex(21,7), 1);
			targets = b.getTargets();
			System.out.println("NEXT");
			for (BoardCell t : targets) {
				System.out.println(t.getIndex() + " ");
			}
		}*/
}
