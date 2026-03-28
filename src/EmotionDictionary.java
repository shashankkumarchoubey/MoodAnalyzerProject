import java.util.HashMap;
import java.util.Map;

public class EmotionDictionary {
    private Map<String, String> wordMap;

    public EmotionDictionary() {
        wordMap = new HashMap<>();
        
        // Joy
        wordMap.put("happy", "JOY"); wordMap.put("love", "JOY"); 
        wordMap.put("amazing", "JOY"); wordMap.put("great", "JOY");
        wordMap.put("good", "JOY"); wordMap.put("excited", "JOY");

        // Anger
        wordMap.put("hate", "ANGER"); wordMap.put("terrible", "ANGER"); 
        wordMap.put("mad", "ANGER"); wordMap.put("stupid", "ANGER");
        wordMap.put("angry", "ANGER"); wordMap.put("awful", "ANGER");

        // Sadness
        wordMap.put("sad", "SADNESS"); wordMap.put("crying", "SADNESS"); 
        wordMap.put("depressed", "SADNESS"); wordMap.put("lost", "SADNESS");
        wordMap.put("miserable", "SADNESS"); wordMap.put("heartbroken", "SADNESS");

        // Anxiety
        wordMap.put("nervous", "ANXIETY"); wordMap.put("scared", "ANXIETY"); 
        wordMap.put("worried", "ANXIETY"); wordMap.put("panic", "ANXIETY");
        wordMap.put("anxious", "ANXIETY"); wordMap.put("stressed", "ANXIETY");
    }

    public String getEmotion(String word) {
        return wordMap.get(word.toLowerCase());
    }
}