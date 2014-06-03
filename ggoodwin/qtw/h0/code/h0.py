#Gabe Goodwin
#HW0  est time: 12 hours
#02/19/2012

#Step 1.1-Get the webpage
from __future__ import division
import urllib2, urllib

urlf=urllib2.urlopen("http://cs.wellesley.edu/~qtw/assign/h0/superpac.html")
page=urlf.read()
f=open("/students/ggoodwin/qtw/h0/data/superpac.html","w")
f.write(page)
f.close()


#Step 1.2-Make all the Links

def fetchSuperPac():
	from BeautifulSoup import BeautifulSoup
	soup= BeautifulSoup(open("/students/ggoodwin/qtw/h0/data/superpac.html").read())
	anchors=soup.findAll("a")
	len(anchors)
	links=["http://query.nictusa.com"+ a["href"]for a in anchors if a.contents==[u'Download']] 
	len(links)

#Step 1.3 get information from all links
	for l in links:
		soup2=BeautifulSoup(urllib2.urlopen(l).read())	
		files=soup2.findAll("a")
		fileLoc=("/students/ggoodwin/qtw/h0/data/"+(files[1]["href"][-10:])+".csv")	
		#store a local copy
		f=open(fileLoc,'w')
		csv=urllib2.urlopen("http://query.nictusa.com"+(files[1]["href"])).read()
		f.write(csv)
		f.close()
		#keep a record of the original URL and the corresponding file name
		f=open("/students/ggoodwin/qtw/h0/data/file2url.csv",'a')
		f.write('\n'+fileLoc+","+("http://query.nictusa.com"+files[1]["href"]))
		f.close()

		#wait for 15 seconds
		time.sleep(15)


#Step 2 Extract information
r2d={}
def parseReports():
	import csv
	reader1=csv.reader(open("/students/ggoodwin/qtw/h0/data/file2url.csv",'rb'))
	row=reader1.next()
	for row in reader1:
		file=row[0]
		FECID=file[-14:-8]
		reader2=csv.reader(open("/students/ggoodwin/qtw/h0/data/"+FECID+".fec.csv", "rb"))
		reader2.next()
		row1=reader2.next()
		comNum=row1[1]
		comName=row1[2]
		r2d.setdefault(FECID,[]).append(comNum)
		r2d.setdefault(FECID,[]).append(comName)
		for row in reader2:
			if row[0]=="SA11AI":
				list2=(row[20],row[19],row[15],row[16],row[5])
				r2d.setdefault(FECID,[]).append(list2)
		

#Step 3 Compute calculations
def ComputeStatsPac():
	noCont=[]
	fiftyCont=[]
	for key in r2d:
		#no contributions
		if len(r2d[key])<=2:
			noCont.append(key)
		#50 or more contributions
		if len(r2d[key])>=52:
			fiftyCont.append(r2d[key][1])
			print fiftyCont
	#Write data to file
	f=open("/students/ggoodwin/qtw/h0/data/FECSummaryStatsPac.txt",'a')
	f.write(" 1: Total Number of Super Pacs: "+str(len(r2d))+"\n 2:Super Pacs with 0 contributions: "+ str(len(noCont)) + "\n 3: Super Pacs with 50+ contributions: "+ str(fiftyCont))
	f.close

#Step 4 Create derivative CSV file
import csv
allcont=[]
writer=csv.writer(open("/students/ggoodwin/qtw/h0/data/allcont.csv",'wb'))
for key in r2d:
	if len(r2d[key])>=3: #contributions were made
		for i in range(2,len(r2d[key])):
			list1=[key,r2d[key][0],r2d[key][1]]
			list1.extend(r2d[key][i])
			#writer.writerow(list1)
			allcont.append(list1)
		


#Step 5 Compute calculations on the data
def computeStatsAll():
	#5.1 report total number of contributions
	numcont=len(allcont)

	#5.2 total number of @ least 1mil
	onemil=0
	for i in range(numcont):
		if float(allcont[i][3])>=1000000.0:
			onemil=onemil+1

	#5.3 largest contribution
	maxcont=0
	maxinf=[]
	for i in range(numcont):
		if float(allcont[i][3])>maxcont:
			maxcont=float(allcont[i][3])
			maxinf=allcont[i]

	#5.4 total dollar amount of contributions
	totalcont=0
	for i in range(numcont):
		totalcont=totalcont+float(allcont[i][3])

	#5.5 % of contributions over $100k 
	overhunkcont=0
	overhunkdol=0.00
	for i in range(numcont):
		if float(allcont[i][3])>=100000.0:
			overhunkcont=overhunkcont+1
			overhunkdol=overhunkdol+float(allcont[i][3])
	perovernum=(overhunkcont/numcont)*100
	peroverdol=(overhunkdol/totalcont)*100	

	#5.6 % of contributions over $100k in MA
	overhunkMA=0
	totalMA=0
	overhunkdolMA=0.00
	totaldolMA=0.00
	for i in range(numcont):
		if (allcont[i][5]=="MA"):
			totalMA=totalMA+1
			totaldolMA=totaldolMA+float(allcont[i][3])
			if(float(allcont[i][3])>=100000.0):
				overhunkMA=overhunkMA+1
				overhunkdolMA=overhunkdolMA+(float(allcont[i][3]))
	perovernumMA=(overhunkMA/totalMA)*100
	peroverdolMA=(overhunkdolMA/totaldolMA)*100

	#Write all this information to a file
	f=open("/students/ggoodwin/qtw/h0/data/FECSummaryStatsAll.txt",'a')
	f.write(" 1:Total number of contributions: "+str(numcont)+"\n 2:Total number of contributions of 1 million or greater: "+str(onemil)+"\n 3:Largest contribution: "+str(maxcont)+"\n 4:Total dollar amount of contributions: "+str(totalcont)+"\n 5:Percent of contributions over $100k (number/dollar): "+str(perovernum)+" / "+str(peroverdol)+"\n 6:Percentage of contributions over $100k in MA (number/dollar): "+str(perovernumMA)+" / "+str(peroverdolMA))
	f.close()



	#5.7 Mass calculations using r2d
	MAcont=0
	MAcontdol=0.00
	MAcontover=0
	MAcontoverdol=0.00
	for key in r2d:
		if len(r2d[key])>=3: #contributions were made
			for i in range(2,len(r2d[key])):
				if r2d[key][i][2]=="MA":
					MAcont=MAcont+1
					MAcontdol=MAcontdol+(float(r2d[key][i][0]))
					if(float(r2d[key][i][0]))>=100000.0:
						MAcontover=MAcontover+1
						MAcontoverdol=MAcontoverdol+(float(r2d[key][i][0]))
						
	MAperovernum=(MAcontover/MAcont)*100
	MAperoverdol=(MAcontoverdol/MAcontover)*100


#The allcont tuple is much easier to perform the percentage calculations. There are fewer layers of data to 
#work through to get to the information that you are looking for. List comprehensions are also easier than
#dictionary comprehensions.





	


            