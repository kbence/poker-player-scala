package com.leanpoker

import akka.actor.Actor
import akka.io.Tcp
import spray.can.Http

class Controller extends Actor {
    def receive: Receive = {
        case msg: Http.Bound =>
        case _: Tcp.Unbound =>
        case some: Tcp.CommandFailed =>
            context.system.shutdown()
            sys.exit(0)
    }
}
