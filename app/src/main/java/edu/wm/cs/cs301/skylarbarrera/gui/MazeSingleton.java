package edu.wm.cs.cs301.skylarbarrera.gui;

import edu.wm.cs.cs301.skylarbarrera.generation.MazeConfiguration;

public class MazeSingleton {
    private static MazeSingleton instance;
    private  MazeConfiguration mazeConfig;


    private MazeSingleton(){}


    public void setData(MazeConfiguration mf){
        this.mazeConfig = mf;
    }

    public MazeConfiguration getData(){
        return this.mazeConfig;
    }

    public static  synchronized MazeSingleton getInstance(){
        if (instance ==null){
            instance = new MazeSingleton();
        }
        return instance;
    }



}
