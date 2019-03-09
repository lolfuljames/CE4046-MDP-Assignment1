
enum Type{
    GREEN, WALL, ORANGE, WHITE;
}

public class Cell{
    private double util;
    private double reward;
    private Boolean wall;

    //default constructor for cells (white space)
    public Cell(){
        this.wall = false;
        this.reward = -0.04;
        this.util = 0;
    }

    public Cell(double util, double reward, Boolean wall){
        this.util = util;
        this.reward = reward;
        this.wall = wall;
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

    public String toString(){
        return String.format("%.2f",this.reward);
    }

    public void set_type(Type type){
        switch(type){
            case WALL:{
                this.reward = 0;
                this.wall = true;
                this.util = 0;
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
        return new Cell(this.util, this.reward, this.wall);
    }
}