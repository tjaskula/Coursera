library(ggplot2)

## Reading data files
NEI <- readRDS("exdata-data-NEI_data/summarySCC_PM25.rds")
SCC <- readRDS("exdata-data-NEI_data/Source_Classification_Code.rds")

# Question 3
# Of the four types of sources indicated by the 𝚝𝚢𝚙𝚎 (point, nonpoint, onroad, nonroad) variable, 
# which of these four sources have seen decreases in emissions from 1999–2008 for Baltimore City? 
# Which have seen increases in emissions from 1999–2008? 
# Use the ggplot2 plotting system to make a plot answer this question.

isBaltimore <- NEI$fips == "24510"
baltimore <- subset(NEI, isBaltimore)
rm(isBaltimore)

totalEmissionByYearAndType <- aggregate(Emissions ~ year + type, data = baltimore, sum)

qplot(x = year, y = Emissions, data = totalEmissionByYearAndType, col = type, facets = . ~ type) + 
  geom_line() +
  labs(title = "Total PM2.5 Emission from 1999 to 2008 for Baltimore") +
  labs(x = "Year", y = "Tota5 Emission (in tons)") +
  scale_x_continuous(breaks = unique(totalEmissionByYearAndType$year))