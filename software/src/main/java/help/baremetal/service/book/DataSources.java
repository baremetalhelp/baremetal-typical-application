package help.baremetal.service.book;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class DataSources {
    private static final String[] firstNames = { "Alfredo", "Allan", "Andres", "Baby", "Barrett", "Bonita", "Calista",
            "Camden", "Cassandre", "Cassidy", "Cayla", "Charlene", "Charlie", "Colby", "Davonte", "Dayton", "Dejuan",
            "Edwardo", "Efren", "Elbert", "Emmett", "Eric", "Evalyn", "Eve", "Ewell", "Felipe", "Garry", "George",
            "Graham", "Grant", "Harmon", "Harrison", "Hayley", "Henri", "Hertha", "Idell", "Jeffery", "Jerrold",
            "Jevon", "Jo", "Johnathan", "Joshua", "Joy", "Kameron", "Katelin", "Katelyn", "Katharina", "Keenan",
            "Kendrick", "Keon", "Kiera", "Lane", "Laron", "Larry", "Lavonne", "Levi", "Lorine", "Luciano", "Lucie",
            "Margot", "Mariano", "Marilie", "Maritza", "Marlee", "Marlon", "Maverick", "Michelle", "Milton", "Minnie",
            "Miracle", "Monica", "Newell", "Noel", "Noemy", "Orin", "Orlando", "Pat", "Pearl", "Phoebe", "Rae",
            "Raegan", "Rebeka", "Reid", "Ricardo", "Rosario", "Roselyn", "Rudolph", "Shania", "Simeon", "Spencer",
            "Tyree", "Ulices", "Valentina", "Vallie", "Wilbert", "Willy", "Winifred", "Zack" };
    private static final String[] lastNames = { "Adams", "Auer", "Bauch", "Bednar", "Beer", "Block", "Bode", "Bogisich",
            "Bosco", "Bradtke", "Brakus", "Carter", "Collier", "Crona", "Cummerata", "Daniel", "Deckow", "Dubuque",
            "Effertz", "Eichmann", "Emmerich", "Fisher", "Fritsch", "Graham", "Gutmann", "Hamill", "Hammes", "Hand",
            "Harber", "Heathcote", "Hill", "Homenick", "Johns", "Keebler", "Kling", "Koch", "Koss", "Kuhlman", "Kunde",
            "Kuphal", "Lakin", "Larkin", "Lebsack", "Leffler", "Leuschke", "Lind", "Little", "Mcclure", "Mcdermott",
            "Mckenzie", "Mclaughlin", "Mertz", "Metz", "Mills", "Murray", "Nienow", "Nolan", "Oberbrunner", "Ondricka",
            "Osinski", "Predovic", "Purdy", "Ratke", "Reichel", "Rempel", "Rodriguez", "Rogahn", "Rohan", "Rosenbaum",
            "Rutherford", "Sawayn", "Schamberger", "Schimmel", "Schumm", "Schuppe", "Schuster", "Smith", "Stark",
            "Steuber", "Stracke", "Streich", "Strosin", "Tillman", "Torp", "Turcotte", "Von", "Watsica", "Wehner",
            "White", "Wuckert", "Zulauf" };
    private static final String[] adjectives = { "Hoarse", "Loose", "Suburban", "Wretched", "Squeaky", "Rich",
            "Alarming", "Sentimental", "Far-Off", "Powerless", "Boiling", "Blond", "Critical", "Shy", "Naughty",
            "Productive", "Slim", "Ornery", "Great", "Terrific", "Pointless", "Present", "Scarce", "Talkative", "Lumpy",
            "Petty", "Utilized", "Wild", "Equatorial", "Identical", "Eminent", "Harsh", "Milky", "Frozen",
            "Unacceptable", "Failing", "Male", "Cooked", "Wealthy", "Probable", "Whole", "Quirky", "Frilly", "Spiffy",
            "Wan", "Overcooked", "Guilty", "Private", "Wry", "Striped", "Shady", "Kindhearted", "Speedy", "Mammoth",
            "Forked", "Busy", "Brief", "Bite-Sized", "Dizzy", "Quarrelsome", "Key", "Natural", "Thoughtful", "Tense",
            "Untrue", "Honored", "Staid", "Trusting", "Honorable", "Bogus", "Proud", "Faint", "Unkempt", "Quarterly",
            "Rectangular", "Troubled", "Considerate", "Measly", "Baggy", "Luminous", "Flamboyant", "Dimpled",
            "Unwitting", "Heartfelt", "Cultivated", "Old", "Awful", "Any", "Gleeful", "Lone", "Heavy", "Superior",
            "Substantial", "Admirable", "Creepy", "Untidy", "Impure", "Insubstantial", "Elated", "Impeccable" };
    private static final String[] nouns = { "Brow", "Criterion", "Stopsign", "Toothpick", "Figurine", "Accident",
            "Cake", "Ambition", "Vixen", "Consciousness", "Democracy", "Maple", "Carpeting", "Lock", "Tandem", "Cod",
            "Inscription", "Anyone", "Colloquy", "Specification", "Glove", "Enquiry", "Bobcat", "Replacement",
            "Cribbage", "Wok", "Gutter", "Green", "Wreck", "Fanny", "Stretch", "Finer", "Solution", "Set",
            "Story-Telling", "Boulevard", "Interject", "Profile", "Gemsbok", "Pepperoni", "Perpendicular", "Trouble",
            "Cucumber", "Website", "Expense", "Hydrolyse", "Innervation", "Employer", "Venti", "Anklet", "Bunkhouse",
            "Race", "Association", "Overexertion", "Eligibility", "Habitat", "Spelling", "Needle", "Trash", "Kebab",
            "Larch", "Consensus", "Opinion", "Dolman", "Morphology", "Source", "Lemonade", "Lynx", "Gain", "Turnip",
            "Mixture", "Someone", "Snowflake", "Footstool", "Tongue", "Maniac", "Benefit", "Pegboard", "Marble",
            "Supreme", "Enigma", "Chief", "Doughnut", "Dwell", "Silkworm", "Flu", "Railing", "Form", "Menu", "Chasm",
            "Lack", "Mortgage", "Album", "Balcony", "Penis", "Quail", "Discount", "Enclave", "Spandex", "Valentine" };

    public static String randomName() {
        return randomWord(firstNames) + " " + randomWord(lastNames);
    }

    public static String randomTitle() {
        return randomWord(adjectives) + " " + randomWord(nouns);
    }

    public static String randomIsbn() {
        return RandomStringUtils.randomNumeric(13, 13);
    }

    private static String randomWord(final String[] array) {
        return array[new Random().nextInt(array.length)];
    }
}
