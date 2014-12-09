package org.leanpoker

import spray.json.JsValue

class Player {
    def version = "0.0.1"

    def bet_request(game_state: JsValue): Int = {
        0
    }

    def showdown(gameState: JsValue): Unit = {
    }
}
