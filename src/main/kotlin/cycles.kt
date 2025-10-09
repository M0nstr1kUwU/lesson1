import kotlin.random.Random

fun main(){
    println("===! БОЙ ЗА БРЕЙНРОТ ДО ПОБЕДНОГО !===")
    val playerName = "Guild"
    var playerHealth = 100
    val playerAttakck = 15

    val monsterName = "Тун-Тун-Тун-Сахур"
    var monsterHealth = 60
    val monsterAttack = 12


    var round = 1

    while (playerHealth > 0 && monsterHealth > 0){
        println("--- Раунд: $round ---")
        println("Ход игрока: $playerName")
        val playerDamageVariation = Random.nextInt(80, 121)
        val playerActualDamage = (playerAttakck * playerDamageVariation) / 100
        monsterHealth -= playerActualDamage
        println("$playerName наносит $playerActualDamage урона! \n У $monsterName осталось $monsterHealth")

        if (monsterHealth <= 0){
            break
        }

        println("Ход монстра: $monsterName")
        val monsterDamageVariation = Random.nextInt(80, 121)
        val monsterActualDamage = (monsterAttack * monsterDamageVariation) / 100
        playerHealth -= monsterActualDamage
        println("$monsterName наносит $monsterActualDamage урона! \n У $playerName осталось $playerHealth")
    }

    println("=== БОЙ ОКОНЧЕН ===")
    if (playerHealth > 0){
        println("Победа! $playerName стал правителем брейнротопии")
    }else{
        println("Победа! $monsterName стал правителем брейнротопии")
    }
}