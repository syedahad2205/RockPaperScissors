import kotlin.random.Random

enum class Choice {
    ROCK,
    PAPER,
    SCISSORS
}

fun main() {
    println("Welcome to Rock, Paper, Scissors Game!")

    println("Best of (1, 3, 5, 7, etc.):")
    val totalRounds = readLine()?.toIntOrNull()

    if (totalRounds == null || totalRounds % 2 == 0 || totalRounds < 1) {
        println("Please enter odd number")
        return
    }

    val (userScore, computerScore) = (1..totalRounds).map { round ->
        println("\nRound $round:")
        val userChoice = getUserChoice()
        val computerChoice = generateComputerChoice()
        println("Computer chose: $computerChoice")

        val roundResult = determineRoundWinner(userChoice, computerChoice)
        println("Result: $roundResult")

        val roundScores = when (roundResult) {
            "You win!" -> Pair(1, 0)
            "Computer wins!" -> Pair(0, 1)
            else -> Pair(0, 0)
        }

        println("Current Score - You: ${roundScores.first}, Computer: ${roundScores.second}")
        roundScores
    }.unzip()

    // Determine the overall winner
    val userScoreSum = userScore.sum()
    val computerScoreSum = computerScore.sum()
    val overallResult = determineOverallWinner(userScoreSum, computerScoreSum)
    println("\nOverall Result: $overallResult")
}

fun getUserChoice(): Choice {
    println("Enter your choice: (ROCK, PAPER, SCISSORS)")
    val userInput = readLine()?.uppercase()
    return parseChoice(userInput) ?: run {
        println("Invalid choice")
        getUserChoice()
    }
}

fun parseChoice(input: String?): Choice? {
    return when (input) {
        "ROCK" -> Choice.ROCK
        "PAPER" -> Choice.PAPER
        "SCISSORS" -> Choice.SCISSORS
        else -> null
    }
}

fun generateComputerChoice(): Choice {
    val randomIndex = Random.nextInt(Choice.values().size)
    return Choice.values()[randomIndex]
}

fun determineRoundWinner(userChoice: Choice, computerChoice: Choice): String {
    return when {
        userChoice == computerChoice -> "It's a tie!"
        userChoice == Choice.ROCK && computerChoice == Choice.SCISSORS ||
                userChoice == Choice.PAPER && computerChoice == Choice.ROCK ||
                userChoice == Choice.SCISSORS && computerChoice == Choice.PAPER -> "You win!"

        else -> "Computer wins!"
    }
}

fun determineOverallWinner(userScore: Int, computerScore: Int): String {
    return when {
        userScore > computerScore -> "Congratulations! You are the overall winner!"
        userScore < computerScore -> "Computer is the overall winner. Better luck next time!"
        else -> "It's a tie! The overall result is a draw."
    }
}
