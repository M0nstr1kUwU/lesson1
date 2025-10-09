fun main(){
      println("Привет, с возвращением в котлин!")

      var playerName: String = "Aleg"
      val maxHealth: Int = 100
      var currentHealth: Int = 100
      var isAlive: Boolean = true

      var gold: Int = 50

      println("Игрок: $playerName входит в игру с двух ног!")
      println("HP: $currentHealth / $maxHealth")
      println("В кошельке: $gold деняк")
      println("Он жив? Состояние: $isAlive")

      val trapDamage: Int = 35

      println("Игрок $playerName попадает в ловушку джоуШкера")

      currentHealth -= trapDamage

      if (currentHealth <= 0){
            isAlive = false
            println("$playerName погиб в битве за брейнрот!")
      }else{
            println("$playerName выжил! осталось HP: $currentHealth")
      }
}