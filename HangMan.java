import java.util.*;

public class HangMan {

    //This collection of arrays stores the words the user could guess from when playing the game.
    static String[] dinosaurs = { "tyrannosaurus", "spinosaurus", "triceratops", "velociraptor", "stegosaurus",
            "brachiosaurus", "allosaurus", "giganotosaurus", "pterodactyl", "archaeopteryx", "ankylosaurus",
            "diplodocus", "brontosaurus", "carnotaurus", "Therizinosaurus", "baryonyx", "iguanadon",
            "troodon", "megalosaurus", "suchomimus",};
    static String[] chemicalElements = { "roentgenium", "rhenium", "neptunium", "yttrium", "vanadium",
            "fluorine", "astatine", "calcium", "chlorine", "technetium", "titanium", "gold", "silver",
            "oxygen", "carbon", "nitrogen", "hydrogen", "tungsten", "plutonium", "uranium"};
    static String[] carBrands = {"ford", "bmw", "audi", "cadillac", "honda", "buick", "chevrolet", "toyota",
            "subaru", "ferrari", "lamborghini", "bentley", "chrysler", "hyundai", "mazda", "tesla",
            "dodge", "porsche", "bugatti", "volkswagen"};
    //____________________________________________________________________________________________________________

    //The method convertIntoLists() converts/transfers the data inside an array into an ArrayList.
    //The ArrayList will then be stored as a value inside a Hashmap.
    public static void convertIntoLists(String[] topic, ArrayList<String> list){
        for(int i = 0; i < topic.length; i++){
            list.add(topic[i]);
        }
    }
    //______________________________________________________________________________

    //The method addToHashMap() facilitates the process of adding keys and values into a Hashmap.
    public static void addToHashMap(HashMap<Character, ArrayList<String>> map, char key, ArrayList<String> value){
        map.put(key, value);
    }
    //________________________________________________________________________________________________________________

    //The method clearScreen() repetitively prints new lines to emulate the terminal being cleared.
    //This visually helps prevent and remove clutter from the terminal.
    static void clearScreen() {
        for (int i = 0; i < 45; i++){
            System.out.println();
        }
    }
    //_____________________________

    //The method singlePlayer() is the method that executes by default when the user runs the program.
    public static void singlePlayer(){
        clearScreen();

        //The boolean retry is a switch determined by the player. If the player wishes to play immediately after
        //the game ends, they type in 1, or else, if they wish to stop playing, they type in 0.
        boolean retry = true;

        //The while-loop continues as long as the player continues to play.
        while (retry) {

            //These are the ArrayLists from which data will be transferred to from the arrays with data relating to the
            //words of each topic/genre.
            ArrayList<String> dinosaursList = new ArrayList<String>();
            ArrayList<String> chemElementsList = new ArrayList<String>();
            ArrayList<String> carBrandsList = new ArrayList<String>();
            convertIntoLists(dinosaurs, dinosaursList);
            convertIntoLists(chemicalElements, chemElementsList);
            convertIntoLists(carBrands, carBrandsList);

            //This is the Hashmap that contains each ArrayList as a value and certain Characters as keys.
            HashMap<Character, ArrayList<String>> mapOfTopics = new HashMap<Character, ArrayList<String>>();
            addToHashMap(mapOfTopics, 'd', dinosaursList);
            addToHashMap(mapOfTopics, 'e', chemElementsList);
            addToHashMap(mapOfTopics, 'c', carBrandsList);
            ArrayList<String> tempList = new ArrayList<String>();

            //This portion of print statements inform the user of the options/genres available to play.
            System.out.println();
            System.out.println("Genres: Dinosaurs, Chemical Elements, Car Brands.");
            System.out.println("d = Dinosaurs       e = Chemical Elements       c = Car Brands");
            System.out.println();
            //System.out.print("Enter the character correlated to the topic you want to play: ");
            int livesRemaining = 6; //The amount of guesses the user may get incorrectly before the game ends.
            boolean catchError = true;
            boolean lifeLost; //Remains true if the user guesses a letter incorrectly.
            boolean terminate = false; //Will activate if the number of lives the user has is zero, and will end the game.
            boolean uncoverLetter = false; //A switch that determines if the user gets a power-up which uncovers a letter.
            boolean plusOneLife = false; //A switch that determines if the user gets a power-up that provides an additional life.
            Random random = new Random(); //To generate random numbers.
            int randomizer = random.nextInt(20); //The bound refers to the amount of words available in each topic.
            int matchRandom1; //Random number to determine if the user gets a power-up.
            int matchRandom2; //Random number to determine if the user gets a power-up.
            int randomLetterFromWord; //Used to determine the index of the letter that will be uncovered with power-up.
            char randomLetter; //Will hold the Character to be uncovered with power-up
            LinkedList<Character> letterList = new LinkedList<>(); //Shows the letters the user has guessed.
            Scanner scanner = new Scanner(System.in); //Allow the user to enter data and interact with game.
            String userPick = null; //Will store the letter of the topic the user wants to play.
            char topicChar = ' '; //Will store the letter of the topic the user wants to play received from userPick.
            //While-loop verifies/ensures the user does not mistakenly provide the incorrect input that would otherwise,
            //lead to the game crashing.:
            while(catchError){
                System.out.print("Enter the character correlated to the topic you want to play: ");
                userPick = scanner.nextLine();
                if(!userPick.isEmpty()){
                    topicChar = userPick.charAt(0); //The letter the user entered.
                    if(topicChar == 'd' || topicChar == 'e' || topicChar == 'c'){
                        catchError = false;
                    }else{
                        System.out.println("Invalid input. Please enter 'd', 'e', or 'c'.");
                    }
                }else{
                    System.out.println("Input cannot be empty. Please try again.");
                }
            }//______________________________________________________________________________________
            //These if-else statements determine which topic/genre the user will decide to play:
            if (topicChar == 'd') {
                tempList = mapOfTopics.get('d');
            } else if (topicChar == 'e') {
                tempList = mapOfTopics.get('e');
            } else if (topicChar == 'c') {
                tempList = mapOfTopics.get('c');
            }//_________________________________________________________________________________
            clearScreen(); //Clear any clutter in the terminal.
            String[] arrayOfWordsFromTempList = tempList.toArray(new String[0]); //ArrayList containing the words from the topic/genre chosen.
            String wordChosen = arrayOfWordsFromTempList[randomizer]; //Word from topic/genre randomly chosen to provide variety.
            char[] dismantledWord = wordChosen.toCharArray(); //Converts the randomly chosen word into an array of Characters
            char[] wordLayout = wordChosen.toCharArray(); //Converts the randomly chosen word into an array of Characters
            char temp; //Store and transfer the letter the user enters.
            char temp2; //Used to avoid double activation in an if-statement.
            char upperCased = ' '; //Converts letters stored from lowercase to uppercase.
            System.out.println("Lives left: " + livesRemaining);
            System.out.println();
            //For-loop that converts the characters of a word into underscores:
            for (int i = 0; i < wordLayout.length; i++) {
                wordLayout[i] = '_';
            }//________________________________________________________________
            int completedWord = dismantledWord.length; //Stores the amount of letters to be guessed from chosen word.
            int leftToFinish = 0; //Amount of letters the user has guessed.
            //Prints out the word as underscores for the user to see.
            for (int i = 0; i < dismantledWord.length; i++) {
                System.out.print(" _ ");
            }//________________________________________________
            System.out.println();
            //While-loop allows user input as letters guessed and keeps track of the amount of letters guessed correctly.
            while (leftToFinish < completedWord) {
                lifeLost = true;
                String userGuess = " ";
                char guessedLetter = ' ';
                catchError = true;
                while(catchError){
                    System.out.print("Guess a letter: ");
                    userGuess = scanner.nextLine();
                    if(!userGuess.isEmpty()){
                        guessedLetter = userGuess.charAt(0); //The letter the user entered.
                        if(guessedLetter >= 'a' && guessedLetter <= 'z'){
                            catchError = false;
                        }else{
                            System.out.println("Please enter a lowercase letter.");
                        }
                    }else{
                        System.out.println("Input cannot be empty. Please try again.");
                    }
                }
                //guessedLetter = userGuess.charAt(0);
                //LinkedList storing all the letters the user has typed in for a singular game instance:
                if (!letterList.contains(guessedLetter)) {
                    letterList.add(guessedLetter);
                }//___________________________________________________
                //For-loop cycles through all letters of the word, detecting if the letter inputted by the user matches any letters of the word.
                //If the user's guessed letter and a character of the word match, that letter will be stored in a separate array to be printed
                //into the terminal and show the user's progress, this for-loop will also update the amount of letters guessed correctly.
                // In addition, each time the user guesses a letter correctly, there is a probability of this triggering/activating one of the power-ups mentioned.
                for (int i = 0; i < dismantledWord.length; i++) {
                    temp2 = Character.toUpperCase(dismantledWord[i]);
                    if ((guessedLetter == dismantledWord[i]) && (wordLayout[i] != temp2)) {
                        temp = dismantledWord[i];
                        upperCased = Character.toUpperCase(temp);
                        if (wordLayout[i] != upperCased && (wordLayout[i] != temp2)) {
                            wordLayout[i] = upperCased;
                            leftToFinish++;
                            lifeLost = false;
                            int pickOneOfTwo = random.nextInt(3);
                            if (pickOneOfTwo == 0) {
                                for (int j = 0; j < 5; j++) {
                                    matchRandom1 = random.nextInt(20);
                                    if (matchRandom1 == randomizer) {
                                        if ((leftToFinish == (completedWord - 1)) || (leftToFinish == completedWord) || (leftToFinish == (completedWord - 2))) {
                                            uncoverLetter = false;
                                        } else {
                                            uncoverLetter = true;
                                        }
                                    }
                                }
                            } else if (pickOneOfTwo == 1) {
                                for (int j = 0; j < 5; j++) {
                                    matchRandom2 = random.nextInt(20);
                                    if (matchRandom2 == randomizer) {
                                        plusOneLife = true;
                                    }
                                }
                            }
                        }
                    }
                }//__________________________________________________________________________________________________
                //If-statement determines if condition of losing a life is true. If true, the integer livesRemaining
                //is reduced by one. Once livesRemaining equals to zero, the switch to execute the Game Over screen
                //is activated.
                if (lifeLost) {
                    livesRemaining--;
                    if (livesRemaining == 0) {
                        terminate = true;
                    }
                }//____________________________________________________________________________________________________
                clearScreen();
                //If-statement that exits the while-loop which allows the player to guess letters, leading to game over.
                if (terminate) {
                    break;
                }//____________________________________________________________________________________________________
                // If-statement which executes the power-up to uncover a letter if activated.
                if (!terminate) {
                    if (uncoverLetter) {
                        while (uncoverLetter) {
                            randomLetterFromWord = random.nextInt(dismantledWord.length - 1);
                            randomLetter = dismantledWord[randomLetterFromWord];
                            for (int i = 0; i < dismantledWord.length; i++) {
                                if (randomLetter == dismantledWord[i]) {
                                    temp = dismantledWord[i];
                                    upperCased = Character.toUpperCase(temp);
                                    if (wordLayout[i] != upperCased) {
                                        wordLayout[i] = upperCased;
                                        leftToFinish++;
                                        lifeLost = false;
                                        uncoverLetter = false;
                                    }
                                }
                            }
                        }
                        System.out.println("You got lucky and uncovered the letter " + upperCased + "!");
                    }
                }//_____________________________________________________________________________________________________
                //If statement which executes the power-up to increase the user's lives remaining by one.
                if (plusOneLife) {
                    livesRemaining++;
                    System.out.println("You got lucky and got an extra life!");
                    System.out.println("Lives left: " + livesRemaining);
                    plusOneLife = false;
                } else {
                    System.out.println("Lives left: " + livesRemaining);
                }//______________________________________________________________________________________
                System.out.println("Letters guessed: " + letterList);
                System.out.println();
                //For-loop that prints out the array containing underscores(which represent the letters the user has yet to guess correctly)
                //and revealing the letters the user has already guessed correctly.
                for (int i = 0; i < wordLayout.length; i++) {
                    System.out.print(wordLayout[i] + " ");
                }
                System.out.println();
            }//__________________________________________________________________________________________
            //If-else statements that activate depending on the value of terminate. if terminate is false,
            //this indicates the game over switch didn't activate and the user fully guessed the word correctly.
            //Specifying so by printing out the statement "You guessed the word, good job!". If terminate is true,
            //then that indicates the user ran out of lives and the game over screen was activated, printing out the
            //message "Game Over." Once the game ends, it will ask the user if they desire to continue playing.
            //To continue playing, the user enters 1, or to stop, they enter 0.
            if (!terminate) {
                System.out.println("You guessed the word, good job!");
                System.out.println();
                System.out.println("Play Again?");
                System.out.println("1 = Yes     0 = No");
                int userDecision = scanner.nextInt();
                if (userDecision == 1) {
                    retry = true;
                    clearScreen();
                } else if (userDecision == 0) {
                    retry = false;
                    clearScreen();
                    System.out.println("Thank you for playing!");
                }
            } else if (terminate) {
                System.out.println("Game Over");
                System.out.println();
                System.out.println("Try Again?");
                System.out.println("1 = Yes     0 = No");
                int userDecision = scanner.nextInt();
                if (userDecision == 1) {
                    retry = true;
                    clearScreen();
                } else if (userDecision == 0) {
                    retry = false;
                    clearScreen();
                    System.out.println("Thank you for playing!");
                }
            }//_________________________________________
        }//__________________________________________
    }
    //____________________________________

    //The method multiPlayer() is a feature of the program that requires further development and could be considered
    //to be an addition in the next version of the game.
    public static void multiPlayer(){

        //The bool retry is set to true by default to initiate the while-loop. The status of retry
        //is updated within the while-loop to avoid an infinite loop.
        boolean retry = true;

        //The while-loop is used to allow the user to continue playing after the game ends.
        //This is done through the user's input. If they wish to continue playing, the user enters 1,
        //or 0 to stop playing.
        while(retry) {

            //In this block of code(lines 29 - ~68), the user enters the word to guess(This feature would be more befitting
            //for a player vs. player mode). It also checks that all the letters of the word entered by
            //the user are in lowercase. If even a single letter isn't in lowercase, a while-loop will
            //repeatedly inform and require the user to enter a fully lowercase word.
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            String userInput;
            System.out.print("Enter a lowercase word to guess: ");
            userInput = scanner.nextLine();
            char[] testWord = userInput.toCharArray();
            String upperCaseWord = userInput.toUpperCase();
            char[] verifyWord = upperCaseWord.toCharArray();
            boolean lever = false;
            boolean condition;
            int livesRemaining = 6;
            boolean lifeLost;
            boolean terminate = false;
            LinkedList<Character> letterList = new LinkedList<>();
            for (int i = 0; i < userInput.length(); i++) {
                if (verifyWord[i] == testWord[i]) {
                    lever = true;
                }
            }
            while (lever) {
                condition = false;
                System.out.println();
                System.out.print("Enter a lowercase word to guess: ");
                userInput = scanner.nextLine();
                testWord = userInput.toCharArray();
                upperCaseWord = userInput.toUpperCase();
                verifyWord = upperCaseWord.toCharArray();
                for (int i = 0; i < userInput.length(); i++) {
                    if (testWord[i] == verifyWord[i]) {
                        condition = true;
                        break;
                    }
                }
                if (!condition) {
                    lever = false;
                    clearScreen();
                }
            }
            ////////////////////////////////////////////////////////////


            char[] dismantledWord = userInput.toCharArray();
            char[] wordLayout = userInput.toCharArray();
            char temp;
            char upperCased;
            System.out.println("Lives left: " + livesRemaining);
            System.out.println();
            for (int i = 0; i < wordLayout.length; i++) {
                wordLayout[i] = '_';
            }
            int completedWord = dismantledWord.length;
            int leftToFinish = 0;
            for (int i = 0; i < dismantledWord.length; i++) {
                System.out.print(" _ ");
            }
            System.out.println();
            while (leftToFinish < completedWord) {
                lifeLost = true;
                System.out.print("Guess a letter: ");
                String userGuess = scanner.nextLine();
                char guessedLetter = userGuess.charAt(0);
                if (!letterList.contains(guessedLetter)) {
                    letterList.add(guessedLetter);
                }
                for (int i = 0; i < dismantledWord.length; i++) {
                    if (guessedLetter == dismantledWord[i]) {
                        temp = dismantledWord[i];
                        upperCased = Character.toUpperCase(temp);
                        if (wordLayout[i] != upperCased) {
                            wordLayout[i] = upperCased;
                            leftToFinish++;
                            lifeLost = false;
                        }
                    }
                }
                if (lifeLost) {
                    livesRemaining--;
                    if (livesRemaining == 0) {
                        terminate = true;
                    }
                }
                clearScreen();
                if (terminate) {
                    break;
                }
                System.out.println("Lives left: " + livesRemaining);
                System.out.println("Letters guessed: " + letterList);
                System.out.println();
                for (int i = 0; i < wordLayout.length; i++) {
                    System.out.print(wordLayout[i] + " ");
                }
                System.out.println();

            }

            if (!terminate) {
                System.out.println("You guessed the word, good job!");
                System.out.println();
                System.out.println("Play Again?");
                System.out.println("1 = Yes     0 = No");
                int userDecision = scanner.nextInt();
                if (userDecision == 1){
                    retry = true;
                    clearScreen();
                }else if (userDecision == 0){
                    retry = false;
                    clearScreen();
                    System.out.println("Thank you for playing!");
                }
            } else if (terminate) {
                System.out.println("Game Over");
                System.out.println();
                System.out.println("Try Again?");
                System.out.println("1 = Yes     0 = No");
                int userDecision = scanner.nextInt();
                if (userDecision == 1){
                    retry = true;
                    clearScreen();
                }else if (userDecision == 0){
                    retry = false;
                    clearScreen();
                    System.out.println("Thank you for playing!");
                }
            }
        }
    }
    //___________________________________

    //The method game() would contain both singlePlayer() and multiPlayer() once the development for both is finished.
    //This would allow single-player or player vs. player gameplay.
    public static void game() {}
    //__________________________


    public static void main(String[] args) {
        singlePlayer();
    }//______________________________________
}