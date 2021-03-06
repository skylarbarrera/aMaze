package edu.wm.cs.cs301.skylarbarrera.generation;

import android.os.AsyncTask;

import java.io.File;

import edu.wm.cs.cs301.skylarbarrera.gui.GeneratingActivity;

public class StubOrder implements Order {

	private int skillLevel;
	private Builder building; 
	private boolean perfect;
	private MazeConfiguration mazeConfiger ;
	private GeneratingActivity genAct;
	private File context;
	
	public StubOrder() {
		
		// TODO Auto-generated constructor stub
	}

	public StubOrder(GeneratingActivity genActs){
		genAct = genActs;
	}

	public GeneratingActivity getAct(){
		return genAct;
	}

	public File getContext() {
		return context;
	}

	public void setContext(File con){
		context = con;
	}

	@Override
	public int getSkillLevel() {
		return skillLevel;
		// TODO Auto-generated method stub
		
	}
	public void setSkillLevel(int skill) {
		assert (0 <= skill && skill < 15) : "Skill Level must be in range 0- 15";
		skillLevel = skill;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return building;
	}
	
	public void setBuilder(Builder build) {
		building = build;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}
	
	public void setPerfect(boolean conditional) {
		perfect = conditional;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		mazeConfiger = mazeConfig;

	}
	public MazeConfiguration getMazeConfiguration() {
		return mazeConfiger;
	}

	@Override
	public void updateProgress(int percentage) {
		if (genAct != null) {
			genAct.updatedProg(percentage);
		}
	}

}
