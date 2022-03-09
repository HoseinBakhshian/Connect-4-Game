
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Run extends JPanel implements ActionListener{


	private static final int RED=2;
	private static final int BLUE=1;
	private static final int EMPTY=0;
	private static final int TWO_PLAYER=0;
	private static final int AI_PLAYER=1;
	private static final int rows=8;
	private static final int columns=8;
	private static final int  x=700;
	private static final int y=1000;
	private static final String LOSE="YOU LOSE!";
	private static final String BLUE_WON="BLUE WON!";
	private static final String RED_WON="RED WON!";

	private  int mode=AI_PLAYER;
	private  State [][] st;
	private  int [] block;
	private  boolean GameOver=false;

	private  int InLine=4;
	private  int turn=BLUE;
	private  int e_count=0;
	private  int w_count=0;
	private  int n_count=0;
	private  int s_count=0;
	private  int ne_count=0;
	private  int sw_count=0;
	private  int nw_count=0;
	private  int se_count=0;
	private  int player1_last_row;
	private  int player1_last_column;
	private  int player2_last_row;
	private  int player2_last_column;
	private  int last_row;
	private  int last_column;
	private  int best_select;

	private  JFrame frame;
	private  JButton [][] btn;
	private  ImageIcon blue_icon; 
	private  ImageIcon red_icon;
	private  ImageIcon white_icon;
	private  ImageIcon ground;
	private  ImageIcon alert_ground;
	private  JPanel panel;
	private  JLabel turn_label;
	int move=0;
	

	
	public static void main(String[] args) {
		
		Run start = new Run();

	}

	
	public Run() {
		
		Random r = new Random();
		turn=r.nextInt(2)+1;
		//turn=BLUE;
		game_page();

		st= new State[rows][columns];
		for(int i=0 ; i< rows ;i++)
		{
			for(int j=0 ; j< columns ;j++)
			{
				st[i][j]= new State();
			}
		}

		block  = new int [InLine];
		if(turn==RED)
		{
			Check_AI();
		}
	}


	private void game_page() {

		frame = new JFrame("Connect 4");
	
		frame.setResizable(false);
		frame.setBounds(500,20, x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		blue_icon = new ImageIcon(this.getClass().getResource("blue.png"));
		red_icon = new ImageIcon(this.getClass().getResource("red.png"));
		white_icon= new ImageIcon(this.getClass().getResource("white.png"));
		ground= new ImageIcon(this.getClass().getResource("ground.png"));

		
		panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 5, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -5, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -5, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		JPanel sub_panel = new JPanel();
		sub_panel.setBackground(Color.BLACK);
		sl_panel.putConstraint(SpringLayout.NORTH, sub_panel, 300, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, sub_panel, 50, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, sub_panel, -100, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, sub_panel, -50, SpringLayout.EAST, panel);
		panel.add(sub_panel);
		sub_panel.setLayout(new GridLayout(0, 8, 0, 0));


		turn_label = new JLabel("TURN :");
		turn_label.setHorizontalTextPosition(SwingConstants.LEFT);		
		turn_label.setFont(new Font("Arial Black", Font.BOLD, 25));
		turn_label.setForeground(Color.WHITE);
		turn_label.setBackground(Color.RED);
		sl_panel.putConstraint(SpringLayout.NORTH, turn_label, 30, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, turn_label, -200, SpringLayout.NORTH, sub_panel);	
		sl_panel.putConstraint(SpringLayout.WEST, turn_label, 250, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, turn_label, -250, SpringLayout.EAST, panel);
		panel.add(turn_label);

		if(turn== BLUE)
			turn_label.setIcon(blue_icon);
		else
			turn_label.setIcon(red_icon);


		btn = new JButton[rows][columns];
		panel.add(new JLabel(ground));

		for(int i=0 ; i< rows ;i++)
		{
			for(int j=0 ; j< columns ;j++)
			{
				btn[i][j]= new JButton();
				sub_panel.add(btn[i][j]);
				btn[i][j].setBackground(Color.black );
				btn[i][j].setIcon(white_icon);
				btn[i][j].addActionListener(this);
				btn[i][j].setFocusPainted(false);              
				btn[i][j].setBorderPainted(false);

			}
		}
		frame.setVisible(true);
	}


	public void set_blue(int row, int column)
	{
		outer : for(int i=0 ; i< rows ;i++)
		{
			if( st[0][column].isUsed()==true ) 
			{		
				turn=BLUE;
				break outer;
			}

			else if(st[i][column].isUsed()==true )
			{
				btn[i-1][column].setIcon(blue_icon);
				st[i-1][column].setBlue();
				turn=RED;
				turn_label.setIcon(red_icon);
				player1_last_row=i-1;
				player1_last_column=column;

				if(win_condition(st, player1_last_row, player1_last_column,BLUE)==true)
				{
					GameOver=true;
					frame.dispose();
					new Win_Alert().alert(BLUE_WON);		
				}

				break outer;
			}

			else if(st[i][column].isUsed()==false & i==7)
			{
				btn[i][column].setIcon(blue_icon);
				st[i][column].setBlue();
				turn=RED;
				turn_label.setIcon(red_icon);
				player1_last_row=i;
				player1_last_column=column;

				if(win_condition(st, player1_last_row, player1_last_column,BLUE)==true)
				{
					GameOver=true;
					frame.dispose();

					new Win_Alert().alert(BLUE_WON);
				}
				break outer;
			}

		}
	Check_AI();
	}


	public void set_red(int row, int column)
	{
		outer : for(int i=0 ; i< rows ;i++)
		{
			if( st[0][column].isUsed()==true ) 
			{
				turn=RED;
				break outer;
			}

			else if(st[i][column].isUsed()==true )
			{
				btn[i-1][column].setIcon(red_icon);
				st[i-1][column].setRed();
				turn=BLUE;
				player2_last_row=i-1;
				player2_last_column=column;
				turn_label.setIcon(blue_icon);

				if(win_condition(st, player2_last_row, player2_last_column,RED)==true)
				{
					new Win_Alert().alert(RED_WON);
				}

				break outer;
			}

			else if(st[i][column].isUsed()==false & i==7)
			{
				btn[i][column].setIcon(red_icon);
				st[i][column].setRed();
				turn=BLUE;
				player2_last_row=i;
				player2_last_column=column;
				turn_label.setIcon(blue_icon);

				if(win_condition(st, player2_last_row, player2_last_column,RED)==true)
				{
					new Win_Alert().alert(RED_WON);
				}

				break outer;
			}

		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		JButton select = (JButton) e.getSource();

		for(int i=0 ; i< rows ;i++)
		{
			for(int j=0 ; j< columns ;j++)
			{
				if(btn[i][j]==select)
				{
					if(mode==TWO_PLAYER)
					{
						if(turn==BLUE)
						{	
							set_blue(i,j);
						}
						else 
						{	
							set_red(i, j);							
						}
					}

					else
					{
						if(turn==BLUE)					
						{								
							set_blue(i,j);
						}
					}
				}
			}
		}
	}


	public  boolean win_condition(State[][] st, int lastr,int lastc , int player)
	{
		e_count=0;
		w_count=0;
		n_count=0;
		s_count=0;
		ne_count=0;
		sw_count=0;
		nw_count=0;
		se_count=0;

		//check east side
		for(int j=lastc ; j< columns ;j++)
		{
			if(st[lastr][j].getState()==player)
			{
				e_count++;
			}
			else
			{			
				break;
			}
		}

		//check west side
		for(int j=lastc ; j >= 0 ;j--)
		{
			if(st[lastr][j].getState()==player)
			{
				w_count++;
			}
			else
			{
				break;
			}
		}

		//check north side
		for(int j=lastr ; j >= 0 ;j--)
		{
			if(st[j][lastc].getState()==player)
			{
				n_count++;
			}
			else
			{
				break;
			}
		}

		//check south side
		for(int j=lastr ; j<rows ;j++)
		{
			if(st[j][lastc].getState()==player)
			{
				s_count++;
			}
			else
			{
				break;
			}
		}

		//check north east side		
		for(int i=lastr , j=lastc ; i>=0 & j<columns ; i-- , j++)
		{

			if(st[i][j].getState()==player)
			{
				ne_count++;
			}
			else
			{
				break;
			}
		}


		//check south west side		
		for(int i=lastr , j=lastc ; i<rows & j>= 0 ; i++ , j--)
		{

			if(st[i][j].getState()==player)
			{
				sw_count++;
			}
			else
			{
				break;
			}
		}

		//check north west side		
		for(int i=lastr , j=lastc ; i>=0 & j>= 0 ; i-- , j--)
		{

			if(st[i][j].getState()==player)
			{
				nw_count++;
			}
			else
			{
				break;
			}
		}

		//check south east side		
		for(int i=lastr , j=lastc ; i<rows & j<columns ; i++ , j++)
		{

			if(st[i][j].getState()==player)
			{
				se_count++;
			}
			else
			{
				break;
			}
		}

		//check conditions
		if((e_count + w_count)-1 >= InLine)
		{
			return true;
		}

		else if((s_count + n_count)-1 >= InLine)
		{
			return true;
		}

		else if((ne_count + sw_count)-1 >= InLine)
		{
			return true;
		}

		else if((nw_count + se_count)-1 >= InLine)
		{
			return true;
		}
		else
		{
			return false;
		}

	}


	public  int setScore(State [][]copy_state,int  player)
	{
		int score =0;

		int [] center_array=new int[InLine];
		center_array=getColumnBlock(copy_state, 4, 0);
		int center =Find_sequence(center_array, AI_PLAYER);
		score += center*3;


		// horizontal score
		for(int r=0 ; r<rows ; r++)
		{
			for(int c=0 ; c<columns-3 ; c++)
			{
				block=getRowBlock(copy_state,r, c);
				score+=Score_calculate(block, player);
			}
		}

		// vertical score
		for(int c=0 ; c<columns ; c++)
		{
			for(int r=0 ; r<rows-3 ; r++)
			{
				block=getColumnBlock(copy_state, c, r);
				score+= Score_calculate(block, player);
			}
		}

		//  diagonal score

		for(int r=0 ; r<rows-3 ; r++)
		{
			for(int c=0 ; c<columns-3 ; c++)
			{

				block=getDiagonalBlock1(copy_state, r, c);
				score+= Score_calculate(block, player);
			}		
		}


		for(int r=0 ; r<rows-3 ; r++)
		{
			for(int c=3 ; c<columns ; c++)
			{
				block=getDiagonalBlock2(copy_state, r, c);
				score+= Score_calculate(block, player);
			}		
		}

		return score;
	}


	public int NegaMax(State [][] states, int depth, int alpha, int beta, int actor)
	{
		ArrayList<Integer> valid_locations=get_empty_columns(states);

		if(depth==0 )
			return setScore(states, RED);

		else if (win_condition(states, last_row, last_column, RED) || win_condition(states, last_row, last_column, BLUE) || get_empty_columns(states).size()==0)
		{
			if (win_condition(states, last_row, last_column, RED))
			{
				return (int) Double.POSITIVE_INFINITY;
			}
			else if (win_condition(states, last_row, last_column, BLUE))
			{
				return (int) -Double.POSITIVE_INFINITY;
			}
			else
				return 0;
		}		

		if (actor == BLUE)
		{	
			int  value = (int) -Double.POSITIVE_INFINITY;
			int column = new Random().nextInt(8);

			for(int i=0 ; i< valid_locations.size(); i++)
			{
				int row = get_row_for_columns(states, valid_locations.get(i));
				State [][] copy=GeneratArray();
				Copy_array(copy, states);
				copy[row][valid_locations.get(i)].setState(RED);		
				last_row=row;
				last_column=valid_locations.get(i);
				int new_score= NegaMax(copy, depth-1, alpha, beta, RED);
				if (new_score > value)
				{
					value = new_score;
					column = valid_locations.get(i);
				}
				//						else if(new_score == value)
				//						{
				//							int x= Math.abs(valid_locations.get(i)-player1_last_column);
				//							int y = Math.abs(column-player1_last_column); 
				//							if(x < y)
				//							{
				//								column = valid_locations.get(i);
				//							}
				//						}
				alpha = Math.max(alpha, value);

				if (alpha > beta)
				{
					break;
				}
			}
			best_select = column;
			return value;
		}

		else 
		{
			int  value = (int) Double.POSITIVE_INFINITY;
			int column = new Random().nextInt(8);

			for(int i=0 ; i< valid_locations.size(); i++)
			{
				int row = get_row_for_columns(states, valid_locations.get(i));
				State [][] copy=GeneratArray();
				Copy_array(copy, states);
				copy[row][valid_locations.get(i)].setState(BLUE);
				last_row=row;
				last_column=valid_locations.get(i);
				int new_score= NegaMax(copy, depth-1, alpha, beta, BLUE);
				if (new_score < value)
				{
					value = new_score;
					column = valid_locations.get(i);
				}
				//						else if(new_score == value)
				//						{
				//							int x= Math.abs(valid_locations.get(i)-player1_last_column);
				//							int y = Math.abs(column-player1_last_column); 
				//							if(x < y)
				//							{
				//								column = valid_locations.get(i);
				//							}
				//						}
				beta = Math.min(beta, value);

				if (alpha > beta)
				{
					break;
				}
			}
			best_select = column;
			return value;
		}

	}	


	public  int Score_calculate(int[] block ,int player)
	{
		int score=0;
		int opponent;
		if(player==BLUE)
			opponent=RED;
		else
			opponent=BLUE;

		int player_count=Find_sequence(block, player);
		int empty_count=Find_sequence(block, EMPTY );
		int opponent_count=Find_sequence(block, opponent );

		if (player_count==4)
			score = 1000;
		else if (opponent_count==3 & empty_count==1)
			score = -1000;
		else if (player_count==3 & empty_count==1)
			score = 10;

		else if(player_count==2 & empty_count==2)
			score = 5;
		else if (opponent_count==2 & empty_count==2)
		{
			score = -10;
		}
		return score;
	}


	public  int[] getRowBlock(State [][] copy_state, int row, int start)
	{
		int [] row_array=new int[InLine];

		for(int i=start ; i<InLine+start ; i++)
		{
			row_array[i-start]= copy_state[row][i].getState();
		}

		return row_array;
	}


	public  int[] getColumnBlock(State [][] copy_state, int column, int start)
	{
		int [] column_array=new int[InLine];

		for(int i=start ; i<InLine+start ; i++)
		{
			column_array[i-start]= copy_state[i][column].getState();
		}

		return column_array;
	}


	public  int[] getDiagonalBlock1(State [][] copy_state, int row, int column)
	{
		int [] diagonal_array=new int[InLine];

		for(int i=row, j=column ; i<InLine+row & j<InLine+column ; i++,j++)
		{
			diagonal_array[i-row]= copy_state[i][j].getState();
		}

		return diagonal_array;
	}


	public  int[] getDiagonalBlock2(State [][] copy_state, int row, int column)
	{
		int [] diagonal_array2=new int[InLine];

		for(int i=row, j=column ; i<InLine+row & j>=0 ; i++, j--)
		{
			diagonal_array2[i-row]= copy_state[i][j].getState();
		}

		return diagonal_array2;
	}


	public  int Find_sequence(int[] block, int player)
	{
		int count=0;

		for(int i=0; i< block.length; i++)
		{
			if(block[i]==player)
			{
				count++;
			}
		}

		return count;
	}


	public ArrayList<Integer> get_empty_columns(State [][] states)
	{
		ArrayList<Integer> valid_columns= new ArrayList<Integer>();
		valid_columns.clear();
		for(int i=0; i < columns; i++)
		{
			if(st[0][i].isUsed()==false)
			{
				valid_columns.add(i);
			}
		}
		return valid_columns;
	}


	public  int get_row_for_columns(State [][] st, int col)
	{
		int row=10;
		for(int i=0 ; i< rows ; i++)
		{
			if(st[0][col].isUsed()==true)
			{
				break;
			}
			else if(st[i][col].isUsed()==true )
			{
				row=i-1;
				break;
			}

			else if(st[i][col].isUsed()==false & i==7)
			{
				row=7;
				break;
			}
		}
		return row;
	}


	public void Copy_array(State [][] copy, State [][] st)
	{

		for(int i=0 ; i< rows ;i++)
		{
			for(int j=0 ; j< columns ;j++)
			{			
				copy[i][j].setState(st[i][j].getState());				
			}
		}

	}


	public State[][] GeneratArray()

	{
		State [][] copy = new State [rows][columns];

		for(int i=0 ; i< rows ;i++)
		{
			for(int j=0 ; j< columns ;j++)
			{
				copy[i][j]= new State();
			}
		}

		return copy;
	}


	public void Check_AI()
	{
		if(mode==AI_PLAYER & turn==RED)
		{                                  
			NegaMax(st, 1, (int) -Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY, BLUE);
			int col=best_select;
			int r = get_row_for_columns(st, col);        
			btn[r][col].setIcon(red_icon);         
			st[r][col].setRed();                   

			turn_label.setIcon(blue_icon);           

			if(win_condition(st, r, col,RED)==true)    
			{                                        
				GameOver=true;                       
				frame.dispose();                     
				new Win_Alert().alert(LOSE);         
			} 
			turn=BLUE;
		}

	}



	
}
