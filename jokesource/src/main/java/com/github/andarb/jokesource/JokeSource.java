package com.github.andarb.jokesource;

import java.util.Random;

public class JokeSource {
    private final static String[] JOKES = {
            "A programmer walks to the butcher shop and buys a kilo of meat. " +
            "An hour later he comes back upset that the butcher shortchanged him by 24 grams.",
            "Debugging - removing needles from a haystack.",
            "I have got a really good UDP joke to tell you, but I don't know if you will get it.",
            "Unix is user friendly. It's just very particular about who its friends are.",
            "There is no place like 127.0.0.1",
            "Software developers like to solve problems. " +
            "If there are no problems available, they will create their own problems. ",
            "One hundred little bugs in the code\n" +
            "One hundred little bugs.\n" +
            "Fix a bug, commit the fix,\n" +
            "One hundred little bugs in the code.",
            "An optimist says: the glass is half full.\n" +
            "A pessimist says: the glass is half empty.\n" +
            "A programmer says: the glass is twice as large as necessary."
    };

    public String retrieveJoke() {
        int numberOfJokes = JOKES.length;

        Random random = new Random();
        int jokeIndex = random.nextInt(numberOfJokes);

        return JOKES[jokeIndex];
    }
}
