# spatial-hot-spot-analysis

**Problem Description**

In this phase of the project, you are required to do spatial **hot spot analysis**. In particular, you need to  complete two different hot spot analysis tasks.

- **Hot zone analysis**: This task will need to perform a range join operation on a rectangle datasets and a point dataset. For each rectangle, the number of points located within the rectangle will be obtained. The hotter rectangle means that it includes more points. So this task is to calculate the hotness of all the rectangles.
- **Hot cell analysis** : This task will focus on applying spatial statistics to spatio-temporal big data in order to identify statistically significant spatial hot spots using Apache Spark.

The topic of this project phase is inspired from ACM SIGSPATIAL GISCUP 2016. Though we have special requirements in this assignments which are different from the GIS Cup.

Please find the following references on the problem description:

- A detailed problem definition is available [here](http://sigspatial2016.sigspatial.org/giscup2016/problem)
- Evaluation Instructions are available [here](http://sigspatial2016.sigspatial.org/giscup2016/submit)

**Problem Statement**

As stated in the Problem Definition page, in this task, you are asked to implement a Spark program to calculate the Getis-Ord statistic of NYC Taxi Trip datasets. We call it "Hot cell analysis". To reduce the computation power need, we made the following changes:

- The input will be a monthly taxi trip dataset from 2009 - 2012. For example, ![](Aspose.Words.a0df8ed4-0bd4-473d-82a3-fe822f90fda4.002.png)![](Aspose.Words.a0df8ed4-0bd4-473d-82a3-fe822f90fda4.003.png)yellow\_tripdata\_2009-01\_point.csv,yellow\_tripdata\_2010-02\_point.csv
- Each cell unit size is 0.01 \* 0.01 in terms of latitude and longitude degrees.
- You should use 1 day as the Time Step size. The first day of a month is step 1. Every month has 31 days.
- You only need to consider Pick-up Location.
- We don't use Jaccard similarity to check your answer. However, you don't need to worry about how to decide the cell coordinates because the code template generated cell coordinates. You just need to write the rest of the task.
