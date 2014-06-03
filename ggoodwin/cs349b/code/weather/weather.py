#Tyler Moore
#2/9/2012
#Code to fetch Boston weather from the Weather Underground website
#Also parses the web pages to extract max and min temperatures per day

import urllib2, time,os.path
from BeautifulSoup import BeautifulSoup

def fetchWeather(month,day,year,numdays=0,filedir='/home/qtw/public_html/data/weather/'):
    """
    This function fetches the historical weather for Boston on the requested day and stores in a local file
    arguments:
        month: number from 1-12
        day: number from 1-31
        year: 4-digit year
        numdays: number of days to go back in time. Default value is 0 (only get the requested date)
        filedir: location of the directory where weather data files should be stored. 
            Default value is /home/qtw/public_html/data/weather/, but that only 
            works for the user qtw
    """
    import datetime
    startday=datetime.datetime(year,month,day)
    #create the appropriate directory for the data if it doesn't exist
    if not os.path.isdir('/home/qtw/public_html/data/weather/'):
        os.mkdir('/home/qtw/public_html/data/weather/')   
    for daydiff in range(numdays):
        day2check=startday-datetime.timedelta(daydiff)
        url2check='http://www.wunderground.com/history/airport/KBOS/%i/%i/%i/DailyHistory.html'%(day2check.year,day2check.month,day2check.day)
        #note that we must rename the HTML file, because it is named DailyHistory.html
        urlfileloc=os.path.join(filedir,'weather-%s.html' % day2check.strftime("%Y-%m-%d"))
        #first check to see if the file already exists (i.e., has been downloaded)
        if os.path.isfile(urlfileloc):
            print 'file already exists at %s, so skipping' % urlfileloc
            continue
        #store a local copy of the file, be sure to check only after seeing if file is already stored
        htmls = urllib2.urlopen(url2check).read()
        f=open(urlfileloc,'w')
        f.write(htmls)
        f.close()
        #keep a record of the original URL and the corresponding file name
        f=open(os.path.join(filedir,'file2url.csv'),'a')
        f.write('%s,%s\n'%(urlfileloc,url2check))
        f.close()
        #wait a few seconds before checking the previous day, out of courtesy
        time.sleep(10)
        
def testWeatherParse():
    """
    helper function that contains code written to explore HTML structure
    returns BeautifulSoup object of parsed HTML file for further exploration
    """
    soup=BeautifulSoup(open('/home/qtw/public_html/data/weather/weather-2012-02-06.html').read())
    #after inspecting element in Firebug, we see that the relevant information is in the historyTable.
    ht=soup.find('table',attrs={'id':'historyTable'})

    #looks like the max and min temp are in spans.
    tableSpans=ht.findAll('span')
    for s in tableSpans:
        print s
    #from printing them out, we see it's in tableSpans[6]
    #>>> tableSpans[6]
    #<span class="nobr"><span class="b">50</span>&nbsp;&deg;F</span>
    #>>> tableSpans[6].text
    #u'50&nbsp;&deg;F'
    #>>> tableSpans[6].span.text
    #u'50'
    maxtemp=float(tableSpans[6].span.text)
    #again for the min temp
    mintemp=float(tableSpans[13].span.text)
    return soup

def parseWeather(makeFile=False,filedir='/home/qtw/public_html/data/weather/'):
    """
    Goes through all the weather files that have been fetched, extracts 
    the max and min temp, returns a dictionary mapping days to [max temp, mintemp]
    arguments:
        makeFile (default False): if True, creates CSV file of the form date,max,min
        filedir (default /home/qtw/public_html/data/weather/): directory for CSV file
    """
    #create a dictionary mapping the date to the max and min temp.
    date2temp={}
    for filename in os.listdir(filedir):
        #first make sure we're only looking at the weather html files
        if filename[:7]!='weather' or filename[-4:]!='html': continue
        soup=BeautifulSoup(open(os.path.join(filedir,filename)).read())
        #after inspecting element in Firebug, we see that the relevant 
        #information is in the historyTable.
        ht=soup.find('table',attrs={'id':'historyTable'})
        #looks like the max and min temp are in spans.
        tableSpans=ht.findAll('span')
        maxtemp=float(tableSpans[6].span.text)
        mintemp=float(tableSpans[13].span.text)
        #get the date from the file name
        day=filename[8:18]
        date2temp[day]=[maxtemp,mintemp]
    if makeFile:
        f=open(os.path.join(filedir,'temps.csv'),'w')
        f.write('day,hi,low\n')
        tempdays=date2temp.keys()
        tempdays.sort() #this ensures that the dates are listed in order
        for day in tempdays:
            f.write('%s,%.1f,%.1f\n'%(day,date2temp[day][0],date2temp[day][1]))
        f.close()
    return date2temp
        
if __name__ == '__main__':
    #fetch 30 days' worth of weather pages
    fetchWeather(2,7,2012,30)
    d2t=parseWeather()
    #time for sanity checking
    #does the min temp ever exceed or equal max?
    mingmax=[d for d in d2t if d2t[d][1]>=d2t[d][0]]
    if mingmax==[]:
        print 'Sanity check 1: no min temps >= max temps'
    else:
        print 'Sanity check 1 failed : min temps >= max temps on days: %s' %mingmax
    print "Temperatures for manual inspection"
    for d in ['2012-01-15','2012-01-31','2012-02-07']:
        print 'On %s, max: %i, min: %i'% (d,d2t[d][0],d2t[d][1])
        
