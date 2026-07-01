package com.example.myandroidapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Game Variables (The Data Model)
    private var day = 1
    private var health = 100
    private var credits = 50
    private var food = 5
    private var water = 5
    private var scrapMetal = 0

    // UI Elements
    private lateinit var statusTextView: TextView
    private lateinit var logTextView: TextView
    private lateinit var btnScavenge: Button
    private lateinit var btnRest: Button
    private lateinit var btnMarket: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI (We will match these IDs in activity_main.xml next)
        statusTextView = findViewById(R.id.statusTextView)
        logTextView = findViewById(R.id.logTextView)
        btnScavenge = findViewById(R.id.btnScavenge)
        btnRest = findViewById(R.id.btnRest)
        btnMarket = findViewById(R.id.btnMarket)

        // Set up Button Click Listeners
        btnScavenge.setOnClickListener { handleScavenge() }
        btnRest.setOnClickListener { handleRest() }
        btnMarket.setOnClickListener { handleMarket() }

        updateUI()
    }

    // Action 1: Scavenge for supplies
    private var marketDay = false

private fun handleScavenge() {
        if (health <= 10) {
            Toast.makeText(this, "Too weak to scavenge! Rest first.", Toast.LENGTH_SHORT).show()
            return
        }
        
        val energyCost = Random.nextInt(5, 15)
        health = (health - energyCost).coerceAtLeast(0)
        
        val lootRoll = Random.nextInt(1, 4)
        var logMessage = ""
        
        when (lootRoll) {
            1 -> {
                val foundFood = Random.nextInt(1, 3)
                food += foundFood
                logMessage = "Scavenged an abandoned pantry. Found +$foundFood Food!"
            }
            2 -> {
                val foundWater = Random.nextInt(1, 3)
                water += foundWater
                logMessage = "Found a clean water collector. Found +$foundWater Water!"
            }
            3 -> {
                val foundScrap = Random.nextInt(2, 5)
                scrapMetal += foundScrap
                logMessage = "Disassembled old machinery. Hauled +$foundScrap Scrap Metal!"
            }
        }
        
        logTextView.text = "Action: $logMessage (Lost $energyCost HP)"
        nextTurn()
    }

    // Action 2: Rest to heal up
    private fun handleRest() {
        val healAmount = Random.nextInt(15, 25)
        health = (health + healAmount).coerceAtMost(100)
        logTextView.text = "Action: You spent the day resting securely. Healed +$healAmount HP."
        nextTurn()
    }

    // Action 3: Trade Scrap for Credits at the Market
    private fun handleMarket() {
        if (scrapMetal > 0) {
            val totalEarnings = scrapMetal * 12
            credits += totalEarnings
            logTextView.text = "Action: Visited traveling merchant. Sold $scrapMetal Scrap for $totalEarnings Credits!"
            scrapMetal = 0
        } else {
            if (credits >= 15) {
                credits -= 15
                food += 1
                water += 1
                logTextView.text = "Action: Bought 1 Food and 1 Water rations for 15 Credits."
            } else {
                logTextView.text = "Action: The market is active, but you have no scrap to sell or credits to buy rations!"
            }
        }
        nextTurn()
    }

    // Turn Control & Survival Check
    private fun nextTurn() {
        day++
        
        // Consume Rations
        if (food > 0) food-- else health -= 15
        if (water > 0) water-- else health -= 20
        
        // Check Defeat or Victory Conditions
        if (health <= 0) {
            health = 0
            Toast.makeText(this, "GAME OVER: You perished on Day $day", Toast.LENGTH_LONG).show()
            resetGame()
        } else if (day >= 30) {
            Toast.makeText(this, "VICTORY! You survived to Day 30 with $credits Credits!", Toast.LENGTH_LONG).show()
            resetGame()
        }
        
        updateUI()
    }

    private fun updateUI() {
        statusTextView.text = """
            DAY: $day / 30
            ❤️ Health: $health%
            💰 Credits: $credits
            🍖 Food: $food Rations
            💧 Water: $water Rations
            ⚙️ Scrap: $scrapMetal units
        """.trimIndent()
    }

    private fun resetGame() {
        day = 1
        health = 100
        credits = 50
        food = 5
        water = 5
        scrapMetal = 0
        logTextView.text = "A new survival journey begins."
    }
}
