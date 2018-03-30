package com.johnsnowlabs.nlp.annotators

import com.johnsnowlabs.collections.SearchTrie
import com.johnsnowlabs.nlp._
import org.apache.spark.ml.util.Identifiable
import com.johnsnowlabs.nlp.AnnotatorType._
import com.johnsnowlabs.nlp.serialization.ArrayFeature

import scala.collection.mutable.ArrayBuffer

/**
  * Extracts entities out of provided phrases
  * @param uid internally renquired UID to make it writable
  * @@ entitiesPath: Path to file with phrases to search
  * @@ insideSentences: Should Extractor search only within sentence borders?
  */
class TextMatcherModel(override val uid: String) extends AnnotatorModel[TextMatcherModel] {

  override val annotatorType: AnnotatorType = ENTITY

  override val requiredAnnotatorTypes: Array[AnnotatorType] = Array(TOKEN)

  val parsedEntities = new ArrayFeature[Array[String]](this, "parsedEntities")

  def setEntities(value: Array[Array[String]]): this.type = set(parsedEntities, value)

  lazy val searchTrie = SearchTrie.apply($$(parsedEntities))

  /** internal constructor for writabale annotator */
  def this() = this(Identifiable.randomUID("ENTITY_EXTRACTOR"))

  /**
    * Searches entities and stores them in the annotation
    * @param text Tokenized text to search
    * @return Extracted Entities
    */
  private def search(text: Seq[Annotation]): Seq[Annotation] = {
    val words = text.map(t => t.result)
    val result = ArrayBuffer[Annotation]()

    for ((begin, end) <- searchTrie.search(words)) {
      val normalizedText = (begin to end).map(i => words(i)).mkString(" ")

      val annotation = Annotation(
        ENTITY,
        text(begin).begin,
        text(end).end,
        normalizedText,
        Map()
      )

      result.append(annotation)
    }

    result
  }


  /** Defines annotator phrase matching depending on whether we are using SBD or not */
  override def annotate(annotations: Seq[Annotation]): Seq[Annotation] = {
    search(annotations)
  }

}

object TextMatcherModel extends ParamsAndFeaturesReadable[TextMatcherModel]