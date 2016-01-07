question1 <- function() {
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv"
  download.file(fileUrl, destfile = "./data/cameras.csv", mmethod = "curl")
}