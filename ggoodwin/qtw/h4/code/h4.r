#H4: R Miscellany
#Gabe Goodwin
#Code for tasks 2,3, and 4

##Task 2: Create coded scatter plots for election data

#Begin your code for Task 2
##t2_inc.png##
contpe$Col <- as.character(contpe$IsIncumbent)
contpe$Col <- factor(contpe$Col, levels = c(2,4,6,7,1))
contpe$Col[contpe$IsIncumbent == "C"] <- 4
contpe$Col[contpe$IsIncumbent == "I"] <- 2
contpe$Col[contpe$IsIncumbent == ""] <- 6 # Other
contpe$Col[contpe$IsIncumbent == "O"] <- 7
contpe$Col[contpe$IsIncumbent == "N"] <- 1
png("t2_inc.png")
plot(y=cpe$SumC,x=cpe$InStateFrac,	
     xlab = "fraction of contributions ($) from in-state",
     ylab = "$ contribution", main = "Scatter plot of $ contribution vs. in-state contributions",
     xlim = c(0, 1), ylim = c(0, sumsrt[1711]),
     col = as.character(contpe$Col),)
legend("topright",c("incumbent","challenger","open seat",
  "other"),fill=c("red","blue","yellow","purple"))
dev.off()

##t2_won.png##
contpe$Col2 <- as.character(contpe$Won)
contpe$Col2 <- factor(contpe$Col, levels = c("green","orange"))
contpe$Col2[contpe$Won == "W"] <- "green"
contpe$Col2[contpe$Won == "L"] <- "orange"
png("t2_won.png")
plot(y=cpe$SumC,x=cpe$InStateFrac,	
     xlab = "fraction of contributions ($) from in-state",
     ylab = "$ contribution", main = "Scatter plot of $ contribution vs. in-state contributions",
     xlim = c(0, 1), ylim = c(0, sumsrt[1711]),
     pch = as.character(contpe$Won),
     col = as.character(contpe$Col2)
)
legend("topright", c("won","lost"),pch = c("W","L"), col = c("green","orange"))
dev.off()

##t2_win_inc##
contpe$Col3 <- as.character(contpe$IsIncumbent)
contpe$Col3 <- factor(contpe$Col3, levels = c(2,4,6,7,1))
contpe$Col3[contpe$IsIncumbent == "C"] <- 4
contpe$Col3[contpe$IsIncumbent == "I"] <- 2
contpe$Col3[contpe$IsIncumbent == ""] <- 6 # Other
contpe$Col3[contpe$IsIncumbent == "O"] <- 7
contpe$Col3[contpe$IsIncumbent == "N"] <- 1
png("t2_win_inc.png")
plot(y=cpe$SumC,x=cpe$InStateFrac,	
     xlab = "fraction of contributions ($) from in-state",
     ylab = "$ contribution", main = "Scatter plot of $ contribution vs. in-state contributions",
     xlim = c(0, 1), ylim = c(0, sumsrt[1711]),
     pch = as.character(contpe$Won),
     col = as.character(contpe$Col3)
)
legend("topright", c("incumbent","challenger","open seat","other", "won", "lost"),pch = c("X","X","X","X","W","L"), col = c("red","blue","yellow", "purple","black","black"))
dev.off()

##t2_win_inc_log##
contpe$Col4 <- as.character(contpe$IsIncumbent)
contpe$Col4 <- factor(contpe$Col4, levels = c(2,4,6,7,1))
contpe$Col4[contpe$IsIncumbent == "C"] <- 4
contpe$Col4[contpe$IsIncumbent == "I"] <- 2
contpe$Col4[contpe$IsIncumbent == ""] <- 6 # Other
contpe$Col4[contpe$IsIncumbent == "O"] <- 7
contpe$Col4[contpe$IsIncumbent == "N"] <- 1

png("t2_win_inc_log.png")
plot(y=log10(cpe$SumC),x=cpe$InStateFrac,	
     xlab = "fraction of contributions ($) from in-state",
     ylab = "log($ contribution)", main = "Scatter plot of $ contribution vs. in-state contributions", pch = as.character(contpe$Won), col = as.character(contpe$Col3)
	)
legend("bottomright", c("incumbent","challenger","open seat","other", "won", "lost"),pch = c("X","X","X","X","W","L"), col = c("red","blue","yellow", "purple","black","black"))
dev.off()
#End Task 2

#Task 3: Box plots by factor
##create new factor variable
contpe$frac <- as.character(contpe$InStateFrac)
contpe$frac <- factor(contpe$frac, levels = c("(0,.1]","(.1,.2]","(.2,.3]","(.3,.4]","(.4,.5]","(.5,.6]","(.6,.7]","(.7,.8]","(.8,.9]","(.9,1]"))
##t3_box.png##
contpe$frac[contpe$InStateFrac >=0 ] <- "(0,.1]"
contpe$frac[contpe$InStateFrac >=.1] <- "(.1,.2]"
contpe$frac[contpe$InStateFrac >=.2] <- "(.2,.3]"
contpe$frac[contpe$InStateFrac >=.3] <- "(.3,.4]"
contpe$frac[contpe$InStateFrac >=.4] <- "(.4,.5]"
contpe$frac[contpe$InStateFrac >=.5] <- "(.5,.6]"
contpe$frac[contpe$InStateFrac >=.6] <- "(.6,.7]"
contpe$frac[contpe$InStateFrac >=.7] <- "(.7,.8]"
contpe$frac[contpe$InStateFrac >=.8] <- "(.8,.9]"
contpe$frac[contpe$InStateFrac >=.9] <- "(.9,1]"
contwin <- subset(contpe, contpe$Won == "W")
contlose <- subset(contpe, contpe$Won == "L")
png("t3_box.png", width = 1150, height = 480, units = "px"
)
par(mfrow=c(1,2))
boxplot(contwin$SumC~contwin$frac, ylim = c(0, sumsrt[1711]),
col="green",ylab="$ contribution to campaign",
    main="$ Contribution vs. In-State Fraction (Winners)")
boxplot(contlose$SumC~contlose$frac, ylim = c(0, sumsrt[1711]), col="orange",
    main="$ Contribution vs. In-State Fraction (Losers)")
dev.off()    


#End Task 3

#Task 4: Dates on contributions

##t4_month_cont_sum##
#create a level by month 
ri$Month<-cut(ri$Date,breaks='month')
#we need a vector for each candidate with values for each month

numContMon<-as.data.frame(table(ri[,c('Month','Candidate')]))
numContMon$Month<-as.Date(numContMon$Month) 

png('t4_month_cont_num.png')
plot(y=numContMon$Freq[numContMon$Candidate=="barack obama"]
    ,x=numContMon$Month[numContMon$Candidate=="barack obama"]
    ,type='o'
    ,ylab='# monthly contributions',
    ,xlab='time of contribution'
    ,main='Line chart of weekly contributions by candidate'
    ,lty='solid'
    ,col='red'
    ,cex=2)
lines(y=numContMon$Freq[numContMon$Candidate=="mitt romney"]
    ,x=numContMon$Month[numContMon$Candidate=="mitt romney"]
    ,type='o'
    ,lty='dashed'
    ,col='green'
    ,cex=2)
lines(y=numContMon$Freq[numContMon$Candidate=="newt gingrich"]
    ,x=numContMon$Month[numContMon$Candidate=="newt gingrich"]
    ,type='o'
    ,lty='dotted'
    ,col='blue'
    ,cex=2)
dev.off()


#end Task 4 Exercise

