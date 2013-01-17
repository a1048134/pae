package pae.team8.flickrminr.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.DefaultPaths;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NLPConnector {
	Logger logger = LoggerFactory.getLogger("pae.team8.flickrminr.nlp.NLPConnector");
	StanfordCoreNLP pipeline;

	public NLPConnector(){
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		props.put("ner.model", DefaultPaths.DEFAULT_NER_MUC_MODEL);
		pipeline = new StanfordCoreNLP(props);
	}

	public void createNLPObject(String comment){
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(comment);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		int sentenceCount=sentences.size();
		int wordCount=0;
		int nerCount=0;
		ArrayList<String> nerItems = new ArrayList<String>();
		
		for(CoreMap sentence: sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				String ne = token.get(NamedEntityTagAnnotation.class);
				if(!"O".equals(ne)){
					nerItems.add(ne);
					nerCount++;
				}
				wordCount++;
			}	
		}
		
		float score = (sentenceCount + wordCount + nerCount) / 3;
		if(score >= 10){
			System.out.println("Analysis for Comment: " + comment);
			System.out.println("SentenceCount: " + sentenceCount);
			System.out.println("WordCount: " + wordCount);
			System.out.println("Named Entities: " + nerCount);
			for(String item : nerItems){
				System.out.println("\t" + item);
			}
			
			System.out.println("Score: " + score);
			System.out.println("-----------------------------------------------");
		}		
	}
}