package org.project.service.impl;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import org.project.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Override
    public String getLanguageFromChatUser(String userMessage) {
        LanguageDetector detector = LanguageDetectorBuilder.fromLanguages(
                Language.VIETNAMESE,
                Language.ENGLISH,
                Language.CHINESE,
                Language.KOREAN,
                Language.JAPANESE).build();

        String userLanguage = userMessage;
        Map<Language, Double> confidenceMap = detector.computeLanguageConfidenceValues(userLanguage);

        Language bestLang = Language.UNKNOWN;

        double maxConfidence = 0;
        for (Map.Entry<Language, Double> entry : confidenceMap.entrySet()) {
            if (entry.getValue() > maxConfidence) {
                bestLang = entry.getKey();
                maxConfidence = entry.getValue();
            }
        }
        return bestLang.toString();
    }

}
