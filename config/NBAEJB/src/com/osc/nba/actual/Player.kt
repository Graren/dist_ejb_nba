package com.osc.nba.actual

import java.io.Serializable

/**
 * Created by oscar on 9/7/2017.
 */
class Player constructor() : Serializable{
    constructor(id: Int, game_id: Int, two_points_shots: Int, three_points_shots: Int, rebounds: Int, blocks: Int, assists: Int, fouls: Int) : this() {
        this.id = id
        this.game_id = game_id
        this.two_points_shots = two_points_shots
        this.three_points_shots = three_points_shots
        this.rebounds = rebounds
        this.blocks = blocks
        this.assists = assists
        this.fouls = fouls
    }

    var id : Int = 0
    var game_id : Int = 0
    var two_points_shots : Int = 0
    var three_points_shots : Int = 0
    var rebounds : Int = 0
    var blocks : Int = 0
    var assists : Int = 0
    var fouls : Int = 0

    fun toMap () : HashMap<String,Int>{
        val m = HashMap<String,Int>()
        m.put("id",id)
        m.put("game",game_id)
        m.put("two",two_points_shots)
        m.put("three",three_points_shots)
        m.put("reb",rebounds)
        m.put("bl",blocks)
        m.put("as",assists)
        m.put("f",fouls)
        return m
    }
}