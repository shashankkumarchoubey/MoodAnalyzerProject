import java.util.HashMap;
import java.util.Map;

public class TextAnalyzer {
    private EmotionDictionary dictionary;

    public TextAnalyzer() {
        this.dictionary = new EmotionDictionary();
    }

    // Now returns the map of scores instead of just printing
    public Map<String, Integer> analyze(String text) {
        Map<String, Integer> emotionScores = new HashMap<>();
        emotionScores.put("JOY", 0); emotionScores.put("ANGER", 0);
        emotionScores.put("SADNESS", 0); emotionScores.put("ANXIETY", 0);

        int exclamationCount = text.length() - text.replace("!", "").length();
        int ellipsisCount = (text.length() - text.replace(".", "").length()) / 3;

        String cleanText = text.replaceAll("[^a-zA-Z ]", "");
        String[] words = cleanText.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.isEmpty()) continue;

            String baseEmotion = dictionary.getEmotion(word);
            
            if (baseEmotion != null) {
                int points = 1;
                String finalEmotion = baseEmotion;

                // ALL CAPS Multiplier
                if (word.equals(word.toUpperCase()) && word.length() > 1) {
                    points += 1; 
                }

                // Negation Checker
                if (i > 0) {
                    String prevWord = words[i - 1].toLowerCase();
                    if (isNegator(prevWord)) {
                        finalEmotion = flipEmotion(baseEmotion);
                    }
                }
                emotionScores.put(finalEmotion, emotionScores.get(finalEmotion) + points);
            }
        }

        // Punctuation Context
        if (exclamationCount > 0) {
            if (emotionScores.get("ANGER") >= emotionScores.get("JOY")) {
                emotionScores.put("ANGER", emotionScores.get("ANGER") + exclamationCount);
            } else {
                emotionScores.put("JOY", emotionScores.get("JOY") + exclamationCount);
            }
        }

        if (ellipsisCount > 0) {
             emotionScores.put("ANXIETY", emotionScores.get("ANXIETY") + ellipsisCount);
             emotionScores.put("SADNESS", emotionScores.get("SADNESS") + ellipsisCount);
        }

        return emotionScores;
    }

    private boolean isNegator(String word) {
        return word.equals("not") || word.equals("never") || word.equals("no") || 
               word.equals("isnt") || word.equals("dont") || word.equals("cant");
    }

    private String flipEmotion(String emotion) {
        switch (emotion) {
            case "JOY": return "SADNESS";
            case "SADNESS": return "JOY";
            case "ANGER": return "ANXIETY"; 
            case "ANXIETY": return "JOY"; 
            default: return emotion;
        }
    }
}