package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Context;
import android.util.Log;

import java.io.File;

import edu.wm.cs.cs301.skylarbarrera.generation.MazeFactory;
import edu.wm.cs.cs301.skylarbarrera.generation.Order;
import edu.wm.cs.cs301.skylarbarrera.generation.StubOrder;

public class MazeStore {

    private File context;

    MazeStore(File cont){
        context = cont;

        Order order0 = new StubOrder();
        Order order1 = new StubOrder();
        Order order2 = new StubOrder();
        Order order3 = new StubOrder();

        ((StubOrder) order0).setContext(context);
        ((StubOrder) order1).setContext(context);
        ((StubOrder) order2).setContext(context);
        ((StubOrder) order3).setContext(context);

        ((StubOrder) order0).setBuilder(StubOrder.Builder.Prim);
        ((StubOrder) order1).setBuilder(StubOrder.Builder.Prim);
        ((StubOrder) order2).setBuilder(StubOrder.Builder.Prim);
        ((StubOrder) order3).setBuilder(StubOrder.Builder.Prim);

        ((StubOrder) order0).setSkillLevel(0);
        ((StubOrder) order1).setSkillLevel(1);
        ((StubOrder) order2).setSkillLevel(2);
        ((StubOrder) order3).setSkillLevel(3);

        ((StubOrder) order0).setPerfect(true);
        ((StubOrder) order1).setPerfect(true);
        ((StubOrder) order2).setPerfect(true);
        ((StubOrder) order3).setPerfect(true);

        MazeFactory mazeFact = new MazeFactory();
        mazeFact.order(order0);
        mazeFact.waitTillDelivered();
        mazeFact.order(order1);
        mazeFact.waitTillDelivered();
        mazeFact.order(order2);
        mazeFact.waitTillDelivered();
        mazeFact.order(order3);
        mazeFact.waitTillDelivered();


        //MazeFileWriter mazeFileWriter = new MazeFileWriter();


    }
}
