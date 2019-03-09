import java.util.*;

public class Grid{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<Integer>> grid_util = new ArrayList<ArrayList<Integer>>();
        int[] position = new int[2];
        int choice = 0;
        final int SIZE = 5;
        final double DISCOUNT = 0.99;
        Type type;
        Cell cell;

        double util_up, util_down, util_left, util_right;

        // generate map
        for(int i=0; i<SIZE; i++){
            grid.add(new ArrayList<Cell>());
            for(int j=0; j<SIZE; j++){
                grid.get(i).add(new Cell());
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
                        System.out.print("Enter X coordinate of the cell: ");
                        position[0] = sc.nextInt();
                        sc.nextLine();
                        if(position[0] >= 0 && position[0] < SIZE) break;
                        System.out.printf("Please enter a valid coordinate between 0 and %d\n", SIZE);
                    }
                    while(true){
                        System.out.print("Enter Y coordinate of the cell: ");
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
        for(int i = 0; i < max; i++){
        }
    }

    public static double get_utility(ArrayList<ArrayList<Cell>> grid, int[] position){
        return 0.2;
    }

    public static void update_utility(ArrayList<ArrayList<Cell>> grid, ArrayList<ArrayList<Integer>> grid_util){
        
    }

}