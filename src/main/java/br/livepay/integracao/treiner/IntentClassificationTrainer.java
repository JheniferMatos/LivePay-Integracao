package br.livepay.integracao.treiner;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class IntentClassificationTrainer {

    public static void main(String[] args) {
        try {
            // Carregue os dados rotulados
            File trainingDataFile = new File("src/main/java/br/livepay/integracao/treiner/training_data.txt");
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(trainingDataFile);

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            // Defina os parâmetros de treinamento
            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 100);
            params.put(TrainingParameters.CUTOFF_PARAM, 5);

            // Treine o modelo
            DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, params, new DoccatFactory());

            // Salve o modelo treinado
            File modelFile = new File("src/main/java/br/livepay/integracao/treiner/intent-model.bin");
            model.serialize(modelFile);

            System.out.println("Modelo treinado e salvo com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado ou não pode ser lido");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


