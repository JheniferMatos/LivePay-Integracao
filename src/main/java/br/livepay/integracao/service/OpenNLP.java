package br.livepay.integracao.service;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;

import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class OpenNLP {

    private static final Logger logger = LoggerFactory.getLogger(OpenNLP.class);

    // Train categorizer model as per the category sample training data we created
    public DoccatModel trainCategorizerModel(String path) throws IOException {
        // Custom training data with categories as per our chat requirements
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(path));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});
        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 0);
        // Train a model with classifications from above file
        return DocumentCategorizerME.train("pt", sampleStream, params, factory);
    }

    // Detect category using given token. Use categorizer
    public String detectCategory(DoccatModel model, String[] finalTokens) {
        // Initialize document categorizer tool
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        // Get the best possible category
        double[] probabilitiesOfOutcomes = categorizer.categorize(finalTokens);
        String category = categorizer.getBestCategory(probabilitiesOfOutcomes);
        logger.debug("Category: " + category);
        return category;
    }

    // Break data into sentences using sentence detection
    public String[] breakSentences(String data, String path) throws IOException {
        // Better to read the file once at the start of the program and store the model in an instance variable
        try (InputStream model = new FileInputStream(path)) {
            SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(model));
            return myCategorizer.sentDetect(data);
        }
    }

    // Break a sentence into words and punctuation marks using a tokenizer
    public String[] tokenizeSentence(String sentence, String path) throws IOException {
        try (InputStream model = new FileInputStream(path)) {
            // Initialize the tokenizer tool
            TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(model));
            // Tokenize the sentence
            return myCategorizer.tokenize(sentence);
        }
    }

    // Find part-of-speech or POS tags of all tokens using a POS tagger
    public String[] detectPOSTags(String[] tokens, String path) throws IOException {
        // Better to read the file once at the start of the program and store the model in an instance variable
        try (InputStream model = new FileInputStream(path)) {
            // Initialize the POS tagger tool
            POSTaggerME myCategorizer = new POSTaggerME(new POSModel(model));
            // Tag the sentence
            return myCategorizer.tag(tokens);
        }
    }

    // Find the lemma of tokens using a lemmatizer
    public String[] lemmatizeTokens(String[] tokens, String[] posTags, String path) throws IOException {
        // Better to read the file once at the start of the program and store the model in an instance variable
        try (InputStream model = new FileInputStream(path)) {
            // Tag the sentence
            LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(model));
            return myCategorizer.lemmatize(tokens, posTags);
        }
    }
}
