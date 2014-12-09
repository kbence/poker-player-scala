package com.leanpoker

import akka.actor._
import akka.io.IO
import org.leanpoker.ServiceActor
import spray.can.Http

object PlayerService extends App {
    startHttp()

    def startHttp(): Unit = {
        implicit val system = ActorSystem()

        val controller: ActorRef = system.actorOf(Props[Controller], "controller")
        val service: ActorRef = system.actorOf(Props[ServiceActor], "courier")
        val port: Int = Settings.port
        val ip: String = Settings.ip

        IO(Http).tell(Http.Bind(service, interface = ip, port = port), controller)
    }
}
