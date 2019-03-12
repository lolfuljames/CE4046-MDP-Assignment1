import java.util.*;
import java.util.concurrent.TimeUnit;

public class Grid{

    static final int SIZE = 6;
    static final double DISCOUNT = 0.99;
    static final double STRAIGHT_PROB = 0.8;
    static double error = 0.00001;
    static boolean converge = false;
    static final Boolean DEBUG = false;
    static boolean value_iteration = false;
    static final int POLICY_EVALUATE_COUNT = 500;
    static int total_count = 0;
    static boolean update_policy = true;
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();

        int[] position = new int[2]; //[row,col]
        int choice = 0;
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
                            + "||     3: Value Iteration                               ||\n"   
                            + "||     4: Policy Iteration                              ||\n"  
                            + "||     5: Print grid utility                            ||\n"
                            + "||     6: Print grid utility                            ||\n"  
                            + "||     7: Change convergence threshold                  ||\n"                             
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
                case 3  :{
                    System.out.print("Enter number of iterations:");
                    total_count = sc.nextInt();
                    sc.nextLine();
                    int count;
                    long start_time = System.currentTimeMillis();
                    value_iteration = true;
                    converge = false;
                    update_policy = true;
                    for(count = 0; count<total_count; count++){
                        if(converge) break;
                        converge = true;
                        iterate_grid(grid,true);
                        if(DEBUG){
                            System.out.printf("-----------------------ITERATION #%d --------------------------", count);
                            print_util(grid);
                            print_policy(grid);
                        }
                    }
                    System.out.println("\nITERATION COMPLETE!\n");
                    print_util(grid);
                    print_policy(grid);
                    long duration = System.currentTimeMillis()-start_time;
                    System.out.printf("Value Iteration took %d iterations\nElapsed for a total of %d.%d seconds\n",count,duration/1000,duration%1000);
                    break;
                }
                case 4  :{
                    System.out.print("Enter number of iterations:");
                    total_count = sc.nextInt();
                    sc.nextLine();
                    int count, iterations = 0;
                    long start_time = System.currentTimeMillis();
                    value_iteration = false;
                    converge = false;
                    update_policy = false;
                    for(count = 0; count<total_count; count++){
                        if(converge) break;
                        for(int evaluate_count = 0; evaluate_count<POLICY_EVALUATE_COUNT-1; evaluate_count++){
                            if(value_iteration) break;
                            if(converge) {
                                iterations += evaluate_count+1;
                                break;
                            }
                            converge = true;
                            iterate_grid(grid, false);
                        }
                        converge = true;
                        iterate_grid(grid, true);
                        if(DEBUG){
                            System.out.printf("-----------------------ITERATION #%d --------------------------", iterations);
                            print_util(grid);
                            print_policy(grid);
                        }
                    }
                    print_util(grid);
                    print_policy(grid);
                    long duration = System.currentTimeMillis()-start_time;
                    System.out.printf("Policy Iteration took %d iterations\nElapsed for a total of %d.%d seconds\n",iterations,duration/1000,duration%1000);
                    break;
                }
                case 5  : {
                    System.out.println("Printing grid utility:");
                    print_util(grid);
                    break;
                }
                case 6  : {
                    System.out.println("Printing grid policy:");
                    print_policy(grid);
                    break;
                }
                case 7  : {
                    System.out.println("Please enter new threshold (Recommended 0.01 or below): ");
                    error = sc.nextDouble();
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

    /*
     * Iterates through the grid using current grid's value.
     * Grid is updated after each function call.
     * For n iterations, call it n times.
     */
    public static void iterate_grid(ArrayList<ArrayList<Cell>> grid, boolean update_policy){
        ArrayList<ArrayList<Double>> grid_util = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Choice>> grid_policy = new ArrayList<ArrayList<Choice>>();
        int[] position = new int[2];
        double new_util;

        for(int i=0; i<SIZE; i++){
            grid_util.add(new ArrayList<Double>());
            grid_policy.add(new ArrayList<Choice>());
        }

        for(ArrayList<Cell> row: grid){
            for(Cell selected: row){
                position[0] = selected.get_row();
                position[1] = selected.get_col();
                new_util = get_utility(grid, grid_policy, position);
                grid_util.get(position[0]).add(new_util);
            }
        }
        update_grid(grid, grid_util, !update_policy);
        //if policy evaluation, update policy once required amount of evaluations is hit.
        if(value_iteration == true || update_policy == true) update_policy(grid, grid_policy);
    }

    /*
     * Function to get the utility of a single cell.
     * Policy (Choice) of each state is updated in grid_policy.
     * Utility is returned.
     */
    public static double get_utility(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Choice>> grid_policy, int[] position){
        double util_up=0, util_down=0, util_left=0, util_right=0;
        double weighted_util_left=0, weighted_util_right=0, weighted_util_up=0, weighted_util_down=0, weighted_util_choice=0;

        if(get_cell(grid, position).is_wall()) {
            grid_policy.get(position[0]).add(Choice.WALL);
            return 0;
        }
        if(position[0] == 0) util_up = get_cell(grid,position).get_util();
        else if(grid.get(position[0]-1).get(position[1]).is_wall()) {
            util_up = get_cell(grid,position).get_util();
        }
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

        if(position[1] == SIZE-1) util_right = get_cell(grid,position).get_util();
        else if(grid.get(position[0]).get(position[1]+1).is_wall()) util_right = get_cell(grid,position).get_util();
        else{
            position[1]++;
            util_right = get_cell(grid,position).get_util();
            position[1]--;
        }

        weighted_util_up = STRAIGHT_PROB*util_up + (1-STRAIGHT_PROB)*(util_left+util_right)/2;
        weighted_util_down = STRAIGHT_PROB*util_down + (1-STRAIGHT_PROB)*(util_left+util_right)/2;
        weighted_util_left = STRAIGHT_PROB*util_left + (1-STRAIGHT_PROB)*(util_up+util_down)/2;
        weighted_util_right = STRAIGHT_PROB*util_right + (1-STRAIGHT_PROB)*(util_up+util_down)/2;
        weighted_util_choice = Math.max(Math.max(weighted_util_down, weighted_util_left),Math.max(weighted_util_up, weighted_util_right));
        
        if(weighted_util_choice == weighted_util_up) grid_policy.get(position[0]).add(Choice.UP);
        else if(weighted_util_choice == weighted_util_down) grid_policy.get(position[0]).add(Choice.DOWN);
        else if(weighted_util_choice == weighted_util_left) grid_policy.get(position[0]).add(Choice.LEFT);
        else grid_policy.get(position[0]).add(Choice.RIGHT);

        if(value_iteration){
            return weighted_util_choice*DISCOUNT + get_cell(grid,position).get_reward();
        }
        else{
            switch(get_cell(grid, position).get_policy()){
                case UP: return weighted_util_up*DISCOUNT + get_cell(grid,position).get_reward();
                case DOWN: return weighted_util_down*DISCOUNT + get_cell(grid,position).get_reward();
                case LEFT: return weighted_util_left*DISCOUNT + get_cell(grid,position).get_reward();
                case RIGHT: return weighted_util_right*DISCOUNT + get_cell(grid,position).get_reward(); 
                default: return weighted_util_up*DISCOUNT + get_cell(grid,position).get_reward(); 
            }
        }
    }

    /*
     * Updates the grid's utility based on current iteration's newest utility.
     * Convergence logic is also present.
     */
    public static void update_grid(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Double>> grid_util, boolean evaluate_policy){
        for(int i = 0; i< SIZE; i++){
            for(int j =0; j< SIZE; j++){
                if(Math.abs(grid.get(i).get(j).get_util()-grid_util.get(i).get(j)) > error && (value_iteration == true || evaluate_policy == true)) converge = false;
                grid.get(i).get(j).set_util(grid_util.get(i).get(j));
            }
        }
    }

    /*
     * Updates the grid's policy based on current iteration's newest policy.
     * Convergence logic is also present.
     */
    public static void update_policy(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Choice>> grid_policy){
        for(int i = 0; i< SIZE; i++){
            for(int j =0; j< SIZE; j++){
                if(grid.get(i).get(j).get_policy()!=grid_policy.get(i).get(j) && value_iteration == false) converge = false;
                grid.get(i).get(j).set_policy(grid_policy.get(i).get(j));
            }
        }
    }

    public static void print_util(ArrayList<ArrayList<Cell>> grid){
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

    public static void print_policy(ArrayList<ArrayList<Cell>> grid){
        System.out.println("-------------------------------------------------------");
        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid.get(i).size(); j++){
                System.out.print("| ");
                System.out.printf("%5s", grid.get(i).get(j).get_policy());
                System.out.print(" |");
            }
            System.out.println("");
            System.out.println("-------------------------------------------------------");
        }
    }
}