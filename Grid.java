import java.util.*;

public class Grid{

    static final int SIZE = 5;
    static final double DISCOUNT = 0.99;
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();

        int[] position = new int[2]; //[row,col]
        int choice = 0;
        Type type;
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
                            + "||     1: Modify grid                                   ||\n"
                            + "||     2: Print  grid                                   ||\n"
                            + "||    14: Quit                                          ||\n"
                            + "|========================================================|\n");
            System.out.printf("Enter your action: ");
            choice = sc.nextInt();
            sc.nextLine();
            if(choice == 14) break;
            switch(choice){
                case 1  :{
                    while(true){
                        System.out.print("Enter row of the cell (0 is top) : ");
                        position[0] = sc.nextInt();
                        sc.nextLine();
                        if(position[0] >= 0 && position[0] < SIZE) break;
                        System.out.printf("Please enter a valid coordinate between 0 and %d\n", SIZE);
                    }
                    while(true){
                        System.out.print("Enter column of the cell (0 is left) : ");
                        position[1] = sc.nextInt();
                        sc.nextLine();
                        if(position[1] >= 0 && position[1] < SIZE) break;
                        System.out.printf("Please enter a valid coordinate between 0 and %d\n", SIZE);
                    }
                    while(true){
                        System.out.print("Enter type of cell (GREEN, WHITE, WALL, ORANGE): ");
                        try{
                            type = Type.valueOf(sc.nextLine().toUpperCase());
                            if(type == Type.GREEN || type == Type.WALL || type == Type.ORANGE || type == Type.WHITE) break;
                        } catch (Exception e){
                            System.out.println("Please enter a correct type ");
                        }
                    }             
                    cell = get_cell(grid,position);
                    cell.set_type(type);
                    break;
                }
                case 2  : {
                    System.out.println("Printing grid:");
                    System.out.println("----------------------------------------------");
                    for(int i=0; i<grid.size(); i++){
                        for(int j=0; j<grid.get(i).size(); j++){
                            System.out.print("| ");
                            System.out.print(grid.get(i).get(j));
                            System.out.print(" |");
                        }
                        System.out.println("");
                        System.out.println("----------------------------------------------");
                    }
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

    public static void value_iteration(ArrayList<ArrayList<Cell>> grid, int max){
        ArrayList<ArrayList<Integer>> grid_util = new ArrayList<ArrayList<Integer>>();
        int[] position = new int[2];
        double new_util;

        for(int i=0; i<SIZE; i++){
            grid_util.add(new ArrayList<Integer>());
            for(int j=0; j<SIZE; j++){
                grid_util.get(i).add(0);
            }
        }

        for(int k = 0; i < max; k++){
            for(ArrayList<Cell> row: grid){
                for(Cell selected: row){
                    position[0] = selected.get_row();
                    position[1] = selected.get_col();
                    new_util = get_utility(grid, position);
                    selected.set_util(new_util);
                }
            }
            update_grid(grid, grid_util);
            System.out.printf("-----------------------ITERATION #%d --------------------------", i);
            System.out.println("----------------------------------------------");
            for(int i=0; i<grid.size(); i++){
                for(int j=0; j<grid.get(i).size(); j++){
                    System.out.print("| ");
                    System.out.print(grid.get(i).get(j));
                    System.out.print(" |");
                }
                System.out.println("");
                System.out.println("----------------------------------------------");
            }
        }
    }

    public static double get_utility(ArrayList<ArrayList<Cell>> grid, int[] position){
        double util_up, util_down, util_left, util_right;

        if(position[0] == 0) util_up = get_cell(grid,position).get_util();
        else{
            position[0]--;
            util_up = get_cell(grid,position).get_util();
            position[0]++;
        }

        if(position[0] == SIZE-1) util_up = get_cell(grid,position).get_util();
        else{
            position[0]++;
            util_up = get_cell(grid,position).get_util();
            position[0]--;
        }

        if(position[1] == 0) util_up = get_cell(grid,position).get_util();
        else{
            position[1]--;
            util_up = get_cell(grid,position).get_util();
            position[1]++;
        }

        if(position[1] == SIZE-1) util_up = get_cell(grid,position).get_util();
        else{
            position[1]++;
            util_up = get_cell(grid,position).get_util();
            position[1]--;
        }

    }

    public static void update_grid(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Integer>> grid_util){
        for(int i = 0; i< SIZE; i++){
            for(int j =0; j< SIZE; j++){
                grid.get(i).get(j).set_util(grid_util.get(i).get(j));
            }
        }
    }
}