package com.oldgameshub.app.data

enum class GameAccent {
    BLUE,
    CORAL,
    NAVY,
    CREAM
}

data class RetroGame(
    val id: String,
    val title: String,
    val category: String,
    val tagline: String,
    val description: String,
    val controls: List<String>,
    val players: String,
    val difficulty: String,
    val symbol: String,
    val accent: GameAccent,
    val popularityRank: Int
)

object GameCatalog {
    val games = listOf(
        RetroGame(
            id = "snake-classic",
            title = "Snake Classic",
            category = "Arcade",
            tagline = "একটি সহজ, চিরচেনা Arcade challenge",
            description = "খাবার সংগ্রহ করে সাপকে বড় করো, কিন্তু নিজের লেজ বা দেয়ালে ধাক্কা দিও না। এই প্রজেক্টে Snake-এর একটি নতুন, offline-friendly সংস্করণ তৈরি করা হবে।",
            controls = listOf("Swipe বা Arrow key দিয়ে দিক পরিবর্তন", "Pause বোতামে খেলা থামাও"),
            players = "১ জন",
            difficulty = "সহজ থেকে কঠিন",
            symbol = "S",
            accent = GameAccent.BLUE,
            popularityRank = 1
        ),
        RetroGame(
            id = "brick-breaker",
            title = "Brick Breaker",
            category = "Arcade",
            tagline = "বল দিয়ে সব Brick ভেঙে ফেলো",
            description = "একটি Paddle চালিয়ে বলকে খেলায় রাখো এবং যত বেশি সম্ভব Brick ভাঙো। পরবর্তী playable build-এ নতুন Level ও Power-up যোগ করার পরিকল্পনা আছে।",
            controls = listOf("আঙুল দিয়ে Paddle বামে বা ডানে সরাও", "Pause বোতামে খেলা থামাও"),
            players = "১ জন",
            difficulty = "মাঝারি",
            symbol = "B",
            accent = GameAccent.CORAL,
            popularityRank = 2
        ),
        RetroGame(
            id = "space-shooter",
            title = "Space Shooter",
            category = "Action",
            tagline = "মহাকাশে শত্রুর ঢেউ সামলাও",
            description = "নিজের Spacecraft নিয়ন্ত্রণ করে সামনে আসা শত্রুদের এড়িয়ে লক্ষ্যভেদ করো। এটি Old Games Hub-এর জন্য পরিকল্পিত একটি original retro-inspired shooter।",
            controls = listOf("Drag করে Spacecraft সরাও", "Fire বোতাম দিয়ে গুলি করো"),
            players = "১ জন",
            difficulty = "মাঝারি",
            symbol = "✦",
            accent = GameAccent.NAVY,
            popularityRank = 3
        ),
        RetroGame(
            id = "pong-classic",
            title = "Pong Classic",
            category = "Sports",
            tagline = "সহজ নিয়মের দ্রুত Paddle match",
            description = "Paddle দিয়ে বল ফিরিয়ে প্রতিপক্ষের পাশ দিয়ে বল পাঠাও। ভবিষ্যতে একই ডিভাইসে দুইজন খেলার Local mode যোগ করার পরিকল্পনা আছে।",
            controls = listOf("Paddle উপরে বা নিচে সরাও", "প্রথমে নির্ধারিত Score-এ পৌঁছাও"),
            players = "১–২ জন (পরিকল্পিত)",
            difficulty = "সহজ",
            symbol = "P",
            accent = GameAccent.BLUE,
            popularityRank = 4
        ),
        RetroGame(
            id = "block-puzzle",
            title = "Block Puzzle",
            category = "Puzzle",
            tagline = "সঠিকভাবে Block সাজিয়ে জায়গা খালি করো",
            description = "বোর্ডে Block বসিয়ে সম্পূর্ণ Row বা Column তৈরি করো। জায়গা ফুরিয়ে যাওয়ার আগে যত বেশি Score সম্ভব অর্জন করো।",
            controls = listOf("Block টেনে বোর্ডে বসাও", "সম্পূর্ণ Row বা Column পরিষ্কার করো"),
            players = "১ জন",
            difficulty = "মাঝারি",
            symbol = "▦",
            accent = GameAccent.CORAL,
            popularityRank = 5
        ),
        RetroGame(
            id = "minesweeper",
            title = "Minesweeper",
            category = "Puzzle",
            tagline = "যুক্তি দিয়ে Mine খুঁজে বের করো",
            description = "সংখ্যার ইঙ্গিত দেখে Mine কোথায় আছে বুঝে নিরাপদ ঘর খুলে ফেলো। ভুল অনুমান করলে রাউন্ড শেষ হয়ে যাবে।",
            controls = listOf("Tap করে ঘর খোলো", "Long press করে সন্দেহজনক ঘরে Flag দাও"),
            players = "১ জন",
            difficulty = "কঠিন",
            symbol = "M",
            accent = GameAccent.NAVY,
            popularityRank = 6
        ),
        RetroGame(
            id = "retro-racing",
            title = "Retro Racing",
            category = "Racing",
            tagline = "Traffic এড়িয়ে Finish Line-এ পৌঁছাও",
            description = "Retro arcade style-এর ছোট Racing challenge। গাড়ি নিয়ন্ত্রণ করে অন্য যানবাহন এড়িয়ে যত দূর সম্ভব এগিয়ে যাও।",
            controls = listOf("Swipe বা Left/Right বোতামে Lane বদলাও", "Brake ও Boost পরে যোগ হবে"),
            players = "১ জন",
            difficulty = "মাঝারি",
            symbol = "R",
            accent = GameAccent.BLUE,
            popularityRank = 7
        ),
        RetroGame(
            id = "pixel-jumper",
            title = "Pixel Jumper",
            category = "Arcade",
            tagline = "Platform থেকে Platform-এ লাফ দাও",
            description = "সঠিক সময়ে Jump করে বাধা পার হও এবং যত দূর সম্ভব এগিয়ে যাও। এটি একটি original retro platform game concept।",
            controls = listOf("Tap করে Jump করো", "দীর্ঘ Tap-এ Jump height বাড়তে পারে"),
            players = "১ জন",
            difficulty = "মাঝারি",
            symbol = "J",
            accent = GameAccent.CREAM,
            popularityRank = 8
        )
    )

    val categories = listOf("All", "Arcade", "Action", "Puzzle", "Sports", "Racing")

    fun findById(id: String): RetroGame? = games.firstOrNull { it.id == id }
}
