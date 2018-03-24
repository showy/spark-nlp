package com.johnsnowlabs.downloader.en.models

import com.johnsnowlabs.nlp
import com.johnsnowlabs.nlp.annotators
import com.johnsnowlabs.nlp.annotators.ner.crf.NerCrfModel
import com.johnsnowlabs.nlp.annotators.pos.perceptron.PerceptronModel
import com.johnsnowlabs.downloader.ResourceDownloader
import com.johnsnowlabs.nlp.annotator.{NorvigSweetingModel, ViveknSentimentModel}
import com.johnsnowlabs.nlp.annotators.Lemmatizer

object CloudDocumentAssembler {
  def retrieveStandard = ResourceDownloader.downloadModel(nlp.DocumentAssembler, "document_std", Some("en"))
}

object CloudSentenceDetector {
  def retrieveStandard = ResourceDownloader.downloadModel(annotators.sbd.pragmatic.SentenceDetector, "sentence_std", Some("en"))
}

object CloudTokenizer {
  def retrieveStandard = ResourceDownloader.downloadModel(annotators.Tokenizer, "tokenizer_std", Some("en"))
}

object CloudPerceptronModel {
  def retrieveSmall = ResourceDownloader.downloadModel(PerceptronModel, "pos_fast", Some("en"))
}

object CloudNerCrfModel {
  def retrieveSmall = ResourceDownloader.downloadModel(NerCrfModel, "ner_fast", Some("en"))
}

object CloudLemmatizer {
  def retrieveSmall = ResourceDownloader.downloadModel(Lemmatizer, "lemma_fast", Some("en"))
}

object CloudSpellChecker {
  def retrieveSmall = ResourceDownloader.downloadModel(NorvigSweetingModel, "spell_fast", Some("en"))
}

object CloudViveknSentiment {
  def retrieveSmall = ResourceDownloader.downloadModel(ViveknSentimentModel, "vivekn_fast", Some("en"))
}

