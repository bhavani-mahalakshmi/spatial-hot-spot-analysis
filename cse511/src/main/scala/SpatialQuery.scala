package cse511

import org.apache.spark.sql.SparkSession
import math.sqrt
import math.pow

object SpatialQuery extends App{

  def getPointCoordinate(axis: Int, pointString: String): Double = {
    val pointCoordinates = pointString.split(",")
    val point = pointCoordinates(axis).toDouble
    return point
  }

  def getDistanceBetweenPoints(pointString1: String, pointString2: String): Double = {
    val p1x = getPointCoordinate(0, pointString1)
    val p1y = getPointCoordinate(1, pointString1)
    val p2x = getPointCoordinate(0, pointString2)
    val p2y = getPointCoordinate(1, pointString2)

    var distanceBetweenPoints = sqrt(pow(p1x - p2x, 2) + pow(p1y - p2y, 2))
    return distanceBetweenPoints
  }

  def ST_Contains(queryRectangle: String, pointString: String): Boolean = {
    val dist1x = getPointCoordinate(0, queryRectangle)
    val dist1y = getPointCoordinate(1, queryRectangle)
    val dist2x = getPointCoordinate(2, queryRectangle)
    val dist2y = getPointCoordinate(3, queryRectangle)

    val pointx = getPointCoordinate(0, pointString)
    val pointy = getPointCoordinate(1, pointString)

    val xpointlower = Math.min(dist1x, dist2x)
    val xpointupper = Math.max(dist1x, dist2x)
    val ypointlower = Math.min(dist1y, dist2y)
    val ypointupper = Math.max(dist1y, dist2y)

    if ( queryRectangle == null || pointString == null ) {
      return false
    }

    if (( pointx >= xpointlower && pointx <= xpointupper ) && ( pointy >= ypointlower && pointy <= ypointupper )) {
      return true
    } else {
      return false
    }
  }

  def ST_Within(pointString1: String, pointString2: String, distance: Double): Boolean = {
    var distanceBetweenPoints = getDistanceBetweenPoints(pointString1, pointString2)
    if (pointString1 == null || pointString2 == null || distance == null) {
      return false
    }

    if (distanceBetweenPoints <= distance) {
      return true
    } else {
      return false
    }
  }


  def runRangeQuery(spark: SparkSession, arg1: String, arg2: String): Long = {

	val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
	pointDf.createOrReplaceTempView("point")

	// YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
	spark.udf.register("ST_Contains",(queryRectangle:String, pointString:String)=>{ST_Contains(queryRectangle, pointString)})

	val resultDf = spark.sql("select * from point where ST_Contains('"+arg2+"',point._c0)")
	resultDf.show()

	return resultDf.count()
  }

  def runRangeJoinQuery(spark: SparkSession, arg1: String, arg2: String): Long = {

	val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
	pointDf.createOrReplaceTempView("point")

	val rectangleDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg2);
	rectangleDf.createOrReplaceTempView("rectangle")

	// YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
	spark.udf.register("ST_Contains",(queryRectangle:String, pointString:String)=>{ST_Contains(queryRectangle, pointString)})

	val resultDf = spark.sql("select * from rectangle,point where ST_Contains(rectangle._c0,point._c0)")
	resultDf.show()

	return resultDf.count()
  }

  def runDistanceQuery(spark: SparkSession, arg1: String, arg2: String, arg3: String): Long = {

	val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
	pointDf.createOrReplaceTempView("point")

	// YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
	spark.udf.register("ST_Within",(pointString1:String, pointString2:String, distance:Double)=>{ST_Within(pointString1, pointString2, distance)})

	val resultDf = spark.sql("select * from point where ST_Within(point._c0,'"+arg2+"',"+arg3+")")
	resultDf.show()

	return resultDf.count()
  }

  def runDistanceJoinQuery(spark: SparkSession, arg1: String, arg2: String, arg3: String): Long = {

	val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
	pointDf.createOrReplaceTempView("point1")

	val pointDf2 = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg2);
	pointDf2.createOrReplaceTempView("point2")

	// YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
	spark.udf.register("ST_Within",(pointString1:String, pointString2:String, distance:Double)=>{ST_Within(pointString1, pointString2, distance)})
	val resultDf = spark.sql("select * from point1 p1, point2 p2 where ST_Within(p1._c0, p2._c0, "+arg3+")")
	resultDf.show()

	return resultDf.count()
  }
}
