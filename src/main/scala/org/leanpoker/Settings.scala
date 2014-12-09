package com.leanpoker

import scala.util.Try

object Settings {
    def ip = env("IP", "0.0.0.0")
    def port = env("PORT", 6500)

    private def env(name: String, default: Int): Int = {
        Option(System.getenv(name)) match {
            case Some(value) => toInt(value, default)
            case None => default
        }
    }

    private def env(name: String, default: String): String = {
        Option(System.getenv(name)) match {
            case Some(value) => value
            case None => default
        }
    }

    private def toInt(value: String, default: Int): Int = {
        Try(value.toInt).toOption match {
            case Some(integer) => integer
            case None => default
        }
    }
}
