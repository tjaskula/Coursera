library(rJava)
library(xlsxjars)
library(xlsx)
library(xml)

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

