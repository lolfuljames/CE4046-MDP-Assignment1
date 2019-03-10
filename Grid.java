import java.util.*;
import java.util.concurrent.TimeUnit;

public class Grid{

    static final int SIZE = 6;
    static final double DISCOUNT = 0.99;
    static final double STRAIGHT_PROB = 0.8;
    static final double ERROR = 0.01;
    static boolean converge = false;
    static final Boolean DEBUG = false;
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();

        int[] position = new int[2]; //[row,col]
        int choice = 0;
        int total_count = 0;
        Type type = Type.GREEN;
        Cell cell;

        // generate map
        for(int i=0; i<SIZE; i++){
            grid.add(new ArrayList<Cell>());
            for(int j=0; j<SIZE; j++){
                grid.get(i).add(new Cell(i,j));
            }
        }

        while(true){
            print_lines();
            System.out.printf("|========================================================|\n"
                            + "|                         MENU                           |\n"
                            + "|========================================================|\n"
                            + "||     1: Preload grid                                   ||\n"
                            + "||     2: Modify grid                                   ||\n"
                            + "||     3: Print  grid                                   ||\n"
                            + "||     4: Value Iteration                               ||\n"                                
                            + "||    14: Quit                                          ||\n"
                            + "|========================================================|\n");
            System.out.printf("Enter your action: ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice == 14) break;
            switch(choice){
                case 1  :{
                    grid = new ArrayList<ArrayList<Cell>>();
                    for(int i=0; i<SIZE; i++){
                        grid.add(new ArrayList<Cell>());
                        for(int j=0; j<SIZE; j++){
                            grid.get(i).add(new Cell(i,j));
                        }
                    }
                    grid.get(0).get(0).set_type(Type.GREEN);
                    grid.get(0).get(2).set_type(Type.GREEN);
                    grid.get(0).get(5).set_type(Type.GREEN);
                    grid.get(1).get(3).set_type(Type.GREEN);
                    grid.get(2).get(4).set_type(Type.GREEN);
                    grid.get(3).get(5).set_type(Type.GREEN);

                    grid.get(0).get(1).set_type(Type.WALL);
                    grid.get(1).get(4).set_type(Type.WALL);
                    grid.get(4).get(1).set_type(Type.WALL);
                    grid.get(4).get(2).set_type(Type.WALL);
                    grid.get(4).get(3).set_type(Type.WALL);

                    grid.get(1).get(1).set_type(Type.ORANGE);
                    grid.get(1).get(5).set_type(Type.ORANGE);
                    grid.get(2).get(2).set_type(Type.ORANGE);
                    grid.get(3).get(3).set_type(Type.ORANGE);
                    grid.get(4).get(4).set_type(Type.ORANGE);
                    break;
                }
                case 2  :{
                    // while(true){
                    //     System.out.print("Enter row of the cell (0 is top) : ");
                    //     position[0] = sc.nextInt();
                    //     sc.nextLine();
                    //     if(position[0] >= 0 && position[0] < SIZE) break;
                    //     System.out.printf("Please enter a valid coordinate between 0 and %d\n", SIZE);
                    // }
                    // while(true){
                    //     System.out.print("Enter column of the cell (0 is left) : ");
                    //     position[1] = sc.nextInt();
                    //     sc.nextLine();
                    //     if(position[1] >= 0 && position[1] < SIZE) break;
                    //     System.out.printf("Please enter a valid coordinate between 0 and %d\n", SIZE);
                    // }
                    // while(true){
                    //     System.out.print("Enter type of cell (GREEN, WHITE, WALL, ORANGE): ");
                    //     try{
                    //         type = Type.valueOf(sc.nextLine().toUpperCase());
                    //         if(type == Type.GREEN || type == Type.WALL || type == Type.ORANGE || type == Type.WHITE) break;
                    //     } catch (Exception e){
                    //         System.out.println("Please enter a correct type ");
                    //     }
                    // }             
                    for(int i = 0; i<SIZE; i++){
                        for(int j = 0; j<SIZE; j++){
                            System.out.printf("Updating cell row:%d, col: %d", i , j );
                            position[0] = i;
                            position[1] = j;
                            cell = get_cell(grid,position);
                            System.out.print("Enter type of cell (GREEN, WHITE, WALL, ORANGE): ");
                            try{
                                type = Type.valueOf(sc.nextLine().toUpperCase());
                            } catch (Exception e){
                                System.out.println("Please enter a correct type ");
                                j--;
                            }
                            cell.set_type(type);
                        }
                    }


                    break;
                }
                case 3  : {
                    System.out.println("Printing grid:");
                    print_grid(grid);
                    break;
                }
                case 4  :{
                    System.out.print("Enter number of iterations:");
                    total_count = sc.nextInt();
                    sc.nextLine();
                    int count;
                    long start_time = System.currentTimeMillis();
                    converge = false;
                    for(count = 0; count<total_count; count++){
                        if(converge) break;
                        converge = true;
                        value_iteration(grid);
                        if(DEBUG){
                            System.out.printf("-----------------------ITERATION #%d --------------------------", count);
                            print_grid(grid);
                        }
                    }
                    print_grid(grid);
                    long duration = System.currentTimeMillis()-start_time;
                    System.out.printf("Value Iteration took %d iterations\nElapsed for a total of %d.%d seconds\n",count,duration/1000,duration%1000);
                    break;
                }
                default :{
                    System.out.println("Wrong selection boy");
                    break;
                }
            }
            System.out.print("Press ok to continue: ");
            sc.nextLine();
        }
    }

    public static void print_lines(){
        for(int i = 0; i< 10; i++){
            System.out.println("");
        }
    }

    public static Cell get_cell(ArrayList<ArrayList<Cell>> grid, int[] position){
        return grid.get(position[0]).get(position[1]);
    }

    public static void value_iteration(ArrayList<ArrayList<Cell>> grid){
        ArrayList<ArrayList<Double>> grid_util = new ArrayList<ArrayList<Double>>();
        int[] position = new int[2];
        double new_util;

        for(int i=0; i<SIZE; i++){
            grid_util.add(new ArrayList<Double>());
        }

        for(ArrayList<Cell> row: grid){
            for(Cell selected: row){
                position[0] = selected.get_row();
                position[1] = selected.get_col();
                new_util = get_utility(grid, position);
                grid_util.get(position[0]).add(new_util);
            }
        }
        update_grid(grid, grid_util);
    }

    public static double get_utility(ArrayList<ArrayList<Cell>> grid, int[] position){
        double util_up=0, util_down=0, util_left=0, util_right=0;
        double weighted_util_up=0, weighted_util_down=0, weighted_util_left=0, weighted_util_right=0;

        if(get_cell(grid, position).is_wall()) return 0;

        if(position[0] == 0) util_up = get_cell(grid,position).get_util();
        else if(grid.get(position[0]-1).get(position[1]).is_wall()) util_up = get_cell(grid,position).get_util();
        else{
            position[0]--;
            util_up = get_cell(grid,position).get_util();
            position[0]++;
        }

        if(position[0] == SIZE-1) util_down = get_cell(grid,position).get_util();
        else if(grid.get(position[0]+1).get(position[1]).is_wall()) util_down = get_cell(grid,position).get_util();
        else{
            position[0]++;
            util_down = get_cell(grid,position).get_util();
            position[0]--;
        }

        if(position[1] == 0) util_left = get_cell(grid,position).get_util();
        else if(grid.get(position[0]).get(position[1]-1).is_wall()) util_left = get_cell(grid,position).get_util();
        else{
            position[1]--;
            util_left = get_cell(grid,position).get_util();
            position[1]++;
        }

        if(position[1] == SIZE-1) util_down = get_cell(grid,position).get_util();
        else if(grid.get(position[0]).get(position[1]+1).is_wall()) util_down = get_cell(grid,position).get_util();
        else{
            position[1]++;
            util_down = get_cell(grid,position).get_util();
            position[1]--;
        }

        weighted_util_down = STRAIGHT_PROB*util_down + (1-STRAIGHT_PROB)*(util_left+util_right)/2;
        weighted_util_left = STRAIGHT_PROB*util_left + (1-STRAIGHT_PROB)*(util_up+util_down)/2;
        weighted_util_right = STRAIGHT_PROB*util_right + (1-STRAIGHT_PROB)*(util_up+util_down)/2;
        weighted_util_up = STRAIGHT_PROB*util_up + (1-STRAIGHT_PROB)*(util_left+util_right)/2;

        return Math.max(Math.max(weighted_util_down, weighted_util_left),Math.max(weighted_util_up, weighted_util_right))*DISCOUNT + get_cell(grid,position).get_reward();
    }

    public static void update_grid(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Double>> grid_util){
        for(int i = 0; i< SIZE; i++){
            for(int j =0; j< SIZE; j++){
                if(Math.abs(grid.get(i).get(j).get_util()-grid_util.get(i).get(j)) > ERROR) converge = false;
                grid.get(i).get(j).set_util(grid_util.get(i).get(j));
            }
        }
    }

    public static void print_grid(ArrayList<ArrayList<Cell>> grid){
        System.out.println("-------------------------------------------------------");
        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid.get(i).size(); j++){
                System.out.print("| ");
                System.out.print(grid.get(i).get(j));
                System.out.print(" |");
            }
            System.out.println("");
            System.out.println("-------------------------------------------------------");
        }
    }
}