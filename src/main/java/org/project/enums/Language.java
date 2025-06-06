package org.project.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Language {
    VIETNAMESE("Vietnamese"),
    ENGLISH("English"),
    FRENCH("French"),
    GERMAN("German"),
    JAPANESE("Japanses"),
    KOREAN("Korean"),
    CHINESE("Chinese"),
    SPANISH("Spanish");

    private final String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public static Optional<Language> getLanguae(String input){
        for(Language language : Language.values()){
            if(language.getLanguage().equalsIgnoreCase(input)){
                return Optional.of(language);
            }
        }
        return Optional.empty();
    }

    public static String getAllLanguage(){
        return Arrays.stream(Language.values())
                .map(i -> "- " + i.getLanguage())
                .collect(Collectors.joining("\n"));
    }
}
