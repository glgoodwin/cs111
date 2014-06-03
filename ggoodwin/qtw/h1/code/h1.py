#Gabe Goodwin
# about 8 hours
#2/22/12
import urllib,urllib2,json
import cPickle as pickle 
import csv


# Step 1: Fetch election Contriutions 
def getOfficialContributions():
#Queries the Campaign Contributions Influence Explorer API for all contributions in support of Obama, Newt, and Romney
	sunlightAPIkey="b0294c1f63064709a0cdec2080129206"
	baseurl="http://transparencydata.com/api/1.0/contributions.json?apikey="+sunlightAPIkey
	#get Obama data
	qObama="&recipient_ft=obama&for_against=for&date=<|2012-01-31&cycle=2012"
	url2checkO=baseurl+qObama
	resultObama=urllib2.urlopen(url2checkO).read()
	resdObama=json.loads(result)
	#get Gingrich data
	qGingrich="&recipient_ft=gingrich&for_against=for&date=<|2012-01-31&cycle=2012"
	url2checkG=baseurl+qGingrich
	resultGingrich=urllib2.urlopen(url2checkG).read()
	resdGingrich=json.loads(resultGingrich)
	#get Romney data
	qRomney="&recipient_ft=obama&for_against=for&date=<|2012-01-31&cycle=2012"
	url2checkR=baseurl+qRomney
	resultRomney=urllib2.urlopen(url2checkR).read()
	resdRomney=json.loads(resultRomney)

	#put info into a dictionary
	sunlightRecords = {}
	sunlightRecords["barack obama"]=resdObama
	sunlightRecords["newt gingrich"]=resdGingrich
	sunlightRecords["mitt romney"]=resdRomney
	#save to Pickle file
	pf=open("/students/ggoodwin/qtw/h1/data/sunlightRecords.pkl","wb")  #wb= write to a binary file
	pickle.dump(sunlightRecords,pf,True)
	pf.close()


# Step 2: Fetch the Census Information
def getStateDemographics():	
	usaTodayAPIkey="kxek5j3r8zh6pkkcgfyzaw7g"
	url="http://api.usatoday.com/open/census/loc?api_key="+usaTodayAPIkey
	result=urllib2.urlopen(url).read()
	resd=json.loads(result)
	print resd
	censusresults={}
	for i in range(len(resd["response"])):
		censusresults[resd["response"][i]["StatePostal"]]=resd["response"][i]
	#save to Pickle file
	pf=open("/students/ggoodwin/qtw/h1/data/censusRecords.pkl","wb")  #wb= write to a binary file
	pickle.dump(censusresults,pf,True)
	pf.close()

# Step 3: Join up the census information and election contributions
def makeCombinedCSV():
	#values for Obama
	for i in range(len(sunlightRecords['barack obama'])):
		contlist=['']*13
		#populate list with values
		contlist[0]='barack obama'
		contlist[1]='Regular'
		contlist[2]=sunlightRecords['barack obama'][i]['committee_ext_id']
		contlist[3]=sunlightRecords['barack obama'][i]['committee_name']
		contlist[4]=sunlightRecords['barack obama'][i]['amount']
		contlist[5]=sunlightRecords['barack obama'][i]['date'].replace('-','')
		contlist[6]=sunlightRecords['barack obama'][i]['contributor_state']
		contlist[7]=sunlightRecords['barack obama'][i]['contributor_zipcode']
		if sunlightRecords['barack obama'][i]['contributor_type']=='I':
			contlist[8]='IND'
		else: contlist[8]='PAC'
		# supply census information
		if contlist[6]!= '' and contlist[6]!= 'PR' and contlist[6]!='VI':
			contlist[9]=censusresults[contlist[6]]['Pop']
			contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
			contlist[11]=censusresults[contlist[6]]['PctBlack']
			contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
		#get rid of empty string and replace with NA
		contlist = ['NA' if x == '' else x for x in contlist]
		#write information to csv file
		write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
		write.writerow(contlist)
	#values for Gingrich Same as for Obama
	for i in range(len(sunlightRecords['newt gingrich'])):
		contlist=['']*13
		contlist[0]='newt gingrich'
		contlist[1]='Regular'
		contlist[2]=sunlightRecords['newt gingrich'][i]['committee_ext_id']
		contlist[3]=sunlightRecords['newt gingrich'][i]['committee_name']
		contlist[4]=sunlightRecords['newt gingrich'][i]['amount']
		contlist[5]=sunlightRecords['newt gingrich'][i]['date'].replace('-','')
		contlist[6]=sunlightRecords['newt gingrich'][i]['contributor_state']
		contlist[7]=sunlightRecords['newt gingrich'][i]['contributor_zipcode']
		if sunlightRecords['newt gingrich'][i]['contributor_type']=='I':
			contlist[8]='IND'
		else: contlist[8]='PAC'
		if contlist[6]!= '' and contlist[6]!= 'PR' and contlist[6]!='VI':
			contlist[9]=censusresults[contlist[6]]['Pop']
			contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
			contlist[11]=censusresults[contlist[6]]['PctBlack']
			contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
		contlist = ['NA' if x == '' else x for x in contlist]
		write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
		write.writerow(contlist)
	#values for Romney
	for i in range(len(sunlightRecords['mitt romney'])):
		contlist=['']*13
		contlist[0]='mitt romney'
		contlist[1]='Regular'
		contlist[2]=sunlightRecords['mitt romney'][i]['committee_ext_id']
		contlist[3]=sunlightRecords['mitt romney'][i]['committee_name']
		contlist[4]=sunlightRecords['mitt romney'][i]['amount']
		contlist[5]=sunlightRecords['mitt romney'][i]['date'].replace('-','')
		contlist[6]=sunlightRecords['mitt romney'][i]['contributor_state']
		contlist[7]=sunlightRecords['mitt romney'][i]['contributor_zipcode']
		if sunlightRecords['mitt romney'][i]['contributor_type']=='I':
			contlist[8]='IND'
		else: contlist[8]='PAC'
		if contlist[6]!= '' and contlist[6]!= 'PR' and contlist[6]!='VI':
			contlist[9]=censusresults[contlist[6]]['Pop']
			contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
			contlist[11]=censusresults[contlist[6]]['PctBlack']
			contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
		contlist = ['NA' if x == '' else x for x in contlist]
		write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
		write.writerow(contlist)

	#add values from allcont to csv file
	reader=csv.reader(open("/students/ggoodwin/qtw/h1/data/allcont.csv",'rb'))
	for row in reader:
		rowlist=reader.next()
		#get Obama superpac
		if rowlist[1]=='C00495861':
			contlist=['']*13
			contlist[0]='barack obama'
			contlist[1]='Super'
			contlist[2]=rowlist[1]
			contlist[3]=rowlist[2]
			contlist[4]=rowlist[3]
			contlist[5]=rowlist[4]
			contlist[6]=rowlist[5]
			contlist[7]=rowlist[6]
			contlist[8]=rowlist[7]
			contlist[9]=censusresults[contlist[6]]['Pop']
			contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
			contlist[11]=censusresults[contlist[6]]['PctBlack']
			contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
			write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
			write.writerow(contlist)
		
	#get Gingrich superpac 
	if rowlist[1]=='C00507525':
		contlist=['']*13
		contlist[0]='newt gingrich'
		contlist[1]='Super'
		contlist[2]=rowlist[1]
		contlist[3]=rowlist[2]
		contlist[4]=rowlist[3]
		contlist[5]=rowlist[4]
		contlist[6]=rowlist[5]
		contlist[7]=rowlist[6]
		contlist[8]=rowlist[7]
		contlist[9]=censusresults[contlist[6]]['Pop']
		contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
		contlist[11]=censusresults[contlist[6]]['PctBlack']
		contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
		write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
		write.writerow(contlist)

	#get Romney superpac
	if rowlist[1]=='C00490045':
		contlist=['']*13
		contlist[0]='mitt romney'
		contlist[1]='Super'
		contlist[2]=rowlist[1]
		contlist[3]=rowlist[2]
		contlist[4]=rowlist[3]
		contlist[5]=rowlist[4]
		contlist[6]=rowlist[5]
		contlist[7]=rowlist[6]
		contlist[8]=rowlist[7]
		contlist[9]=censusresults[contlist[6]]['Pop']
		contlist[10]=censusresults[contlist[6]]['PctNonHispWhite']
		contlist[11]=censusresults[contlist[6]]['PctBlack']
		contlist[12]=censusresults[contlist[6]]['USATDiversityIndex']
		write=csv.writer(open("/students/ggoodwin/qtw/h1/data/regSuperCensus.csv",'a'))
		write.writerow(contlist)





	
