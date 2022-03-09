
public class State {

	private boolean used=false;

	private static final int RED=2;
	private static final int BLUE=1;
	private static final int EMPTY=0;
	private static final int TWO_PLAYER=0;
	private static final int AI_PLAYER=1;
	private int state=EMPTY;


	public boolean isUsed() {
		return used;
	}


	public void setState(int state) {
		this.state = state;
		if(state==BLUE || state==RED)
		{
			this.used=true;
		}
	}

	public int getState() {
		return state;
	}

	public void setRed() {
		this.used=true;
		this.state=RED;
	}

	public void setBlue() {
		this.used=true;
		this.state=BLUE;
	}



}
