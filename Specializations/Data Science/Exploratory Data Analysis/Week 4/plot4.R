## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 4
# Across the United States, how have emissions from coal combustion-related sources changed from 1999–2008?

coalCombSource <- SCC[grep("Fuel Comb.*Coal", SCC$EI.Sector), "SCC"]
coalNEI <- NEI[NEI$SCC %in% coalCombSource,]

totalEmissionByYear <- with(coalNEI, tapply(Emissions, year, sum, na.rm = TRUE))

plot(x = names(totalEmissionByYear), y = totalEmissionByYear, 
     type = "p", pch = 16, col = "green", axes = FALSE,
     ylab = "Total PM2.5 Emission (in tons)", xlab = "Year", 
     main = "Total Coal related PM2.5 Emission in USA from 1999 to 2008")
lines(x = names(totalEmissionByYear), y = totalEmissionByYear, col = "blue")
axis(2) # x axis
axis(side = 1, at = names(totalEmissionByYear)) # y axis
box()