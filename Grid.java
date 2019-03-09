import java.util.*;

public class Grid{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
        int[] position = new int[2];
        int choice = 0;
        int SIZE = 5;

        while(true){
            System.out.printf("|========================================================|\n"
                            + "|                         MENU                           |\n"
                            + "|========================================================|\n"
                            + "||     1: Generate grid                                 ||\n"
                            + "||     2: Modify grid                                   ||\n"
                            + "||     3: Print  grid                                   ||\n"
                            + "||    14: Quit                                          ||\n"
                            + "|========================================================|\n");
            System.out.printf("Enter your action: ");
            choice = sc.nextInt();
            if(choice == 14) break;
            switch(choice){
                case 1  :{
                    for(int i=0; i<SIZE; i++){
                        grid.add(new ArrayList<Cell>());
                        for(int j=0; j<SIZE; j++){
                            Cell cell = new Cell();
                            grid.get(i).add(cell);
                        }
                    }
                    break;
                }
                case 3  : {
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
        }
    }
}