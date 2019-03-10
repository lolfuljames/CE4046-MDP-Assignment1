
enum Type{
    GREEN, WALL, ORANGE, WHITE;
}

enum Choice{
    UP, DOWN, LEFT, RIGHT, WALL;
}
public class Cell{
    private double util;
    private double reward;
    private Boolean wall;
    private int row;
    private int col;
    private Choice policy;

    //default constructor for cells (white space)
    public Cell(){
        this.wall = false;
        this.reward = -0.04;
        this.util = 0;
        this.row = 0;
        this.col = 0;
        this.policy = Choice.WALL;
    }

    public Cell(int row, int col){
        this.wall = false;
        this.reward = -0.04;
        this.util = 0;
        this.row = row;
        this.col = col;
        this.policy = Choice.WALL;
    }

    public Cell(double util, double reward, Boolean wall, int row, int col, Choice policy){
        this.util = util;
        this.reward = reward;
        this.wall = wall;
        this.row = row;
        this.col = col;
        this.policy = policy;
    }

    public double get_util(){
        return this.util;
    }

    public void set_util(double util){
        this.util = util;
    }

    public double get_reward(){
        return this.reward;
    }

    public void set_reward(double reward){
        this.reward = reward;
    }

    public Boolean is_wall(){
        return this.wall;
    }

    public void set_wall(Boolean wall){
        this.wall = wall;
    }

    public int get_row(){
        return this.row;
    }

    public void set_row(int row){
        this.row = row;
    }
    
    public int get_col(){
        return this.col;
    }
    
    public void set_col(int col){
        this.col = col;
    }

    public Choice get_policy(){
        return this.policy;
    }

    public void set_policy(Choice policy){
        this.policy = policy;
    }

    public String toString(){
        return String.format("%5.2f",this.util);
    }

    public void set_type(Type type){
        switch(type){
            case WALL:{
                this.reward = 0;
                this.wall = true;
                this.util = 0;
                this.policy = Choice.WALL;
                break;
            }
            case ORANGE:{
                this.reward = -1.00;
                this.wall = false;
                this.util = 0;
                break;
            }
            case GREEN:{
                this.reward = 1.00;
                this.wall = false;
                this.util = 0;
                break;
            }
            case WHITE:{
                this.reward = -0.04;
                this.wall = false;
                this.util = 0;
                break;
            }
            default:{
                this.reward = -0.04;
                this.wall = false;
                this.util = 0;
                break;
            }
        }
    }

    public Cell copy(){
        return new Cell(this.util, this.reward, this.wall, this.row, this.col, this.policy);
    }
}