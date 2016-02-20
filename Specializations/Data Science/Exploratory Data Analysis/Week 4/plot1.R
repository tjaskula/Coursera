## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 1
# Have total emissions from PM2.5 decreased in the United States from 1999 to 2008? 
# Using the base plotting system, make a plot showing the total PM2.5 emission from all sources 
# for each of the years 1999, 2002, 2005, and 2008.

totalEmissionByYear <- with(NEI, tapply(Emissions, year, sum, na.rm = TRUE))

plot(x = names(totalEmissionByYear), y = totalEmissionByYear, 
     type = "p", pch = 16, col = "green", axes = FALSE,
     ylab = "Total PM2.5 Emission (in tons)", xlab = "Year", 
     main = "Total PM2.5 Emission from 1999 to 2008")
lines(x = names(totalEmissionByYear), y = totalEmissionByYear, col = "blue")
axis(2) # x axis
axis(side = 1, at = names(totalEmissionByYear)) # y axis
box()