package br.livepay.integracao.service;

import java.util.HashMap;
import java.util.Map;

public class WordScores {

    private Map<String, Double> wordScores = new HashMap<>();

    // Define as palavras e valores associados
    private Map<String, Double> wordValues = new HashMap<>();

    public WordScores() {
        wordValues.put("comprar", 1.0);
        wordValues.put("quero", 0.8);
        wordValues.put("preços", 0.6);
        wordValues.put("preço", 0.6);
        wordValues.put("promoção", 0.5);

        // Adicione variações de cada palavra
        for (Map.Entry<String, Double> entry : wordValues.entrySet()) {
            wordScores.put(entry.getKey(), entry.getValue());

            // Adicione variações das palavras
            if (entry.getKey().equals("comprar")) {
                wordScores.put("comprando", entry.getValue());
                wordScores.put("compre", entry.getValue());
                wordScores.put("compra", entry.getValue());
                wordScores.put("compraria", entry.getValue());
                wordScores.put("comprado", entry.getValue());
                wordScores.put("comprador", entry.getValue());
            } else if (entry.getKey().equals("quero")) {
                wordScores.put("querendo", entry.getValue());
                wordScores.put("quer", entry.getValue());
                wordScores.put("quisesse", entry.getValue());
                wordScores.put("quiser", entry.getValue());
                wordScores.put("queria", entry.getValue());
            } else if (entry.getKey().equals("preços") || entry.getKey().equals("preço")) {
                wordScores.put("barato", entry.getValue());
                wordScores.put("desconto", entry.getValue());
                wordScores.put("oferta", entry.getValue());
            } else if (entry.getKey().equals("promoção")) {
                wordScores.put("oferta", entry.getValue());
                wordScores.put("desconto", entry.getValue());
                wordScores.put("promoções", entry.getValue());
            }
                }
    }

    public Double getWordScore(String word) {
        return wordScores.get(word);
    }
}
