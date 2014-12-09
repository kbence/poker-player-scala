package org.leanpoker

import akka.actor.Actor
import spray.httpx.marshalling.ToResponseMarshallable
import spray.json.JsonParser
import spray.json.JsonParser.ParsingException
import spray.routing._

case class PokerAction(action: String)

class ServiceActor extends Actor with HttpService {
    def actorRefFactory = context

    override def receive: Actor.Receive = runRoute(route)

    def player = new Player()

    val route: Route =
        pathSingleSlash {
            post {
                formFields('action.as[String], 'game_state.as[Option[String]]) { (action, game_state) =>
                    complete {
                        completeRequest(action, game_state)
                    }
                }
            }
        }

    private def completeRequest(action: String, game_state: Option[String]): ToResponseMarshallable =
        action match {
            case ("bet_request" | "showdown") =>
                completeGameAction(action, game_state)
            case "version" =>
                player.version
            case _ =>
                "OK"
        }

    private def completeGameAction(action: String, game_state: Option[String]): ToResponseMarshallable =
        game_state match {
            case Some(state) =>
                try {
                    val gameState = JsonParser(state)

                    action match {
                        case "bet_request" =>
                            player.bet_request(gameState).toString
                        case "showdown" =>
                            player.showdown(gameState)
                            "OK"
                        case _ =>
                            "OK"
                    }
                } catch {
                    case e: ParsingException =>
                        "Field `game_state` must be a valid JSON!"
                }
            case None =>
                "Field `game_state` is missing!"
        }
}
