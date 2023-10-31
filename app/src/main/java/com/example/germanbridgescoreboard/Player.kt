package com.example.germanbridgescoreboard

import kotlin.math.absoluteValue

class Player (var name: String){
    private var bid: ArrayList<Int> = arrayListOf()
    private var win: ArrayList<Int> = arrayListOf()
    private var score: ArrayList<Int> = arrayListOf()
    private var winStreak: Int = 0
    private var total: Int = 0

    fun getBid(round: Int): Int{
        return bid[round]
    }

    fun setBid(round: Int, bid: Int){
        this.bid[round] = bid
    }

    fun addBid(bid: Int){
        this.bid.add(bid)
    }

    fun getWin(round: Int): Int{
        return win[round]
    }

    fun setWin(round: Int, win: Int){
        this.win[round] = win
    }

    fun addWin(win: Int){
        this.win.add(win)
    }

    fun getScore(bid: Int, win: Int): Int{
        var score: Int = 0
        if(bid == win){
            score = 10 + bid*win
            return score
        }
        else{
            score = (bid-win).absoluteValue
            return -score
        }
    }

    fun setScore(round: Int, score: Int){
        this.score[round] = score
    }

    fun addScore(score: Int){
        this.score.add(score)
    }

    fun updateWinStreak(round: Int){
        if (bid[round] == win[round]) winStreak++ else winStreak = 0
    }

    fun updateTotal(){
        total = score.sum()
    }

    fun getTotal(): Int{
        return total
    }
}