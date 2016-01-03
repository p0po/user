package net.yongpo.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by p0po on 15-4-1.
 */
public class MyActorC  {
    public static void main(String[] args) {
        ActorSystem rootSystem = ActorSystem.create("p0po");
        ActorRef actorRef = rootSystem.actorOf(Props.create(MyUntypedActor.class),"actor");

        actorRef.tell(12312434,null);
        for(int i=0;i<10;i++)
        actorRef.tell("dddd-"+i,null);
        actorRef.tell(1122,null);
    }
}
