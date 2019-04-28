package handson.example

import org.apache.spark.sql.SparkSession

/**
  * Random forest example
  */
object RandomForestExample {
  val homeDir = System.getProperty("user.home")
  def main(args: Array[String]): Unit = {
    // a. Set Spark session
    val spark = SparkSession.builder().master("local").getOrCreate()

    // b. Set logging level to WARNING
    spark.sparkContext.setLogLevel("WARN")

    // 1. Import required classes from Spark's ML package
    import org.apache.spark.ml.Pipeline
    import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
    import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
    import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}

    // 2. Use Spark session to read the file sample_libsvm_data.txt in libsvm format as a Spark DataFrame
    val data = spark.read.format("libsvm").load(s"${homeDir}/sample_libsvm_data.txt")

    // 3. Index the label column of the source DataFrame and create a new DataFrame with indexedLabel as additional column
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

    // 4. Index the feautures column of the source DataFrame and create a new DataFrame with indexedFeatures as additional column.
    // Make sure that features is treated as constinuos variable if there are more than four distinct values else categorical
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

    // 5. Randomly split the source DataFrame into two DataFrames with a 7 to 3 ratio. First DataFrmae is to be used for training purposes.
    // Second DataFrame is to be used for testing purpose.
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    // 6. Create a RandomForestClassifier using the builder pattern. Set label column name as indexedLabel and features coulmn name as features column.
    // Allow 10 decision trees to be used for arriving at consensus
    val randomForest = new RandomForestClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures").setNumTrees(10)

    // 7. Create a label coverter object that converts indexed labels back to original labels
    val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labels)

    // 8. Create a Pipeline object that chains label indexer, feature indexer, random forest and label converter.
    val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, randomForest, labelConverter))

    // 9. Run the pipeline to fit the the training data and create a model. Doing so runs  label indexer, feature indexer, random forest and label coverter one after the other.
    val model = pipeline.fit(trainingData)

    // 10. Using the model run predictions on test data.
    val predictions = model.transform(testData)

    // 11. Display a two rows from above creeated predictions DataFrame.
    predictions.select("predictedLabel", "label", "features").show(5, true)

    // 12. Evalue the accuracy of predicted label by comparing agaist the original label.
    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    // 13. Extract the built model getting the element at index 2 of pipeline stages
    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]

    // 14. Output contents of the built model
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
  }
}
