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
        return Double.toString(this.reward);
    }
}