public class Cell{
    private double util;
    private double reward;
    private Boolean wall;

    // constructor for wall
    public Cell(){
        this.wall = True;
        this.reward = 0;
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
}