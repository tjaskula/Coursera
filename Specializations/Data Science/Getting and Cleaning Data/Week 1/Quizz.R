library(rJava)
library(xlsxjars)
library(xlsx)
library(XML)
library(data.table)

question1Download <- function() {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv"
  download.file(fileUrl, destfile = "./data/cameras.csv", method = "curl")
  dateDownloaded <- date()
}

question1ReadFile <- function() {
  cameraData <- read.table("./data/cameras.csv", sep = ",", header = TRUE)
  cameraData
}

question1 <- function() {
  df <- question1ReadFile()
  nrow(df[!is.na(df["VAL"]) & df["VAL"] == 24,])
}

question3Download <- function() {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FDATA.gov_NGAP.xlsx"
  download.file(fileUrl, destfile = "./data/NGAP.xlsx", method = "curl")
  dateDownloaded <- date()
}

question3ReadFile <- function(rows, cols) {
  data <- read.xlsx("./data/NGAP.xlsx", sheetIndex = 1, colIndex = cols, rowIndex = rows)
  data
}

question3 <- function() {
  rows <- 18:23
  cols <- 7:15
  dat <- question3ReadFile(rows, cols)
  sum(dat$Zip*dat$Ext,na.rm=T)
}

question4Download <- function() {
  
  fileUrl <- "http://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Frestaurants.xml"
  doc <- xmlTreeParse(fileUrl, useInternalNodes = TRUE)
  rootNode <- xmlRoot(doc)
  rootNode
}

question4 <- function(rootNode) {
  zipCodes <- xpathSApply(rootNode, "//row[zipcode=21231]", xmlValue)
  length(zipCodes)
}

question5Download <- function() {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06pid.csv"
  download.file(fileUrl, destfile = "./data/pid.csv", method = "curl")
  dateDownloaded <- date()
}

question5ReadFile <- function() {
  dt <- fread("./data/pid.csv")
  dt
}

runFunction <- function(f) {
  for (i in 1:1000) {
    f()
  }
}