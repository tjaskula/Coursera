library(httr)
library(httpuv) # only needed in R Studio
library(base64enc)

# question 1
question1 <- function() {
  githubapp <- oauth_app("github", key="c83e3dbb33b479d39a84", secret="6fa2b24bf58095a34065f3707076751bad04cf59")
  github_token <- oauth2.0_token(oauth_endpoints("github"), githubapp)
  jeffrepo <- GET("https://api.github.com/users/jtleek/repos", sig)
  # or with proxy
  jeffrepo <- GET("https://api.github.com/users/jtleek/repos", use_proxy(url = "localhost", port = 9978, username = "T.Jaskula", password = "Fd-seven-04$"))
  json <- content(jeffrepo)
  json2 <- jsonlite::fromJSON(jsonlite::toJSON(json))
  json2[grep("datasharing", json2$name), "created_at"] 
}

loadCameras <- function() {
  library(sqldf)
  
  if (!file.exists("data")) {
    dir.create("data")
  }
  
  fileUrl <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06pid.csv"
  download.file(fileUrl, destfile = "./data/cameras.csv")
  dateDownloaded <- date()
}

#question 2
question2 <- function() {
  
  if (!file.exists("./data/cameras.csv")) {
    loadCameras()
  }
  
  acs <- read.table("./data/cameras.csv", sep = ",", header = TRUE)
  
  sqldf("select pwgtp1 from acs where AGEP < 50")
}